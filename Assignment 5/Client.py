import threading
import datetime
import socket
import time

def startSendingTime(slave_client):
    while True:
        slave_client.send(str(datetime.datetime.now()).encode())
        print("Recent time sent successfully", end="\n\n")
        time.sleep(5)

def startReceivingTime(slave_client):
    while True:
        synchronized_time = datetime.datetime.fromisoformat(
            slave_client.recv(1024).decode())
        print("Synchronized time at the client is: " + str(synchronized_time), end="\n\n")

def initiateSlaveClient(port=2050):
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