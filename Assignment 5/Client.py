# ============================================================
# Assignment 5: Berkeley Clock Synchronization - SLAVE (client)
# ------------------------------------------------------------
# Each slave does two things concurrently, forever:
#   1. Every 5s, send its own current local time to the master
#      (this is the "noisy" clock the master will correct).
#   2. Whenever the master pushes back a synchronized time,
#      print it (in a real system you'd actually set your local
#      clock to this value).
#
# Run the master (Server.py) FIRST, then one or more instances
# of this client.
# ============================================================

import threading
import datetime
import socket
import time

def startSendingTime(slave_client):
    # Runs forever in its own thread: reports this slave's local
    # clock to the master every 5s so the master can measure the
    # offset (master_time - this_time).
    while True:
        slave_client.send(str(datetime.datetime.now()).encode())
        print("Recent time sent successfully", end="\n\n")
        time.sleep(5)

def startReceivingTime(slave_client):
    # Runs forever in its own thread: blocks on recv() until the
    # master's sync thread pushes a new corrected time, then prints it.
    while True:
        synchronized_time = datetime.datetime.fromisoformat(
            slave_client.recv(1024).decode())
        print("Synchronized time at the client is: " + str(synchronized_time), end="\n\n")

def initiateSlaveClient(port=2050):
    # Connects to the master on localhost:2050, then starts the
    # send and receive loops as separate threads so this slave can
    # simultaneously report its clock and listen for corrections.
    slave_client = socket.socket()
    slave_client.connect(('127.0.0.1', port))

    print("Starting to send time to server\n")
    send_time_thread = threading.Thread(target=startSendingTime, args=(slave_client,))
    send_time_thread.start()

    print("Starting to receive synchronized time from server\n")
    receive_time_thread = threading.Thread(target=startReceivingTime, args=(slave_client,))
    receive_time_thread.start()

if __name__ == '__main__':
    initiateSlaveClient(port=2050)