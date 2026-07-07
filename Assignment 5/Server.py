# ============================================================
# Assignment 5: Berkeley Clock Synchronization - MASTER (server)
# ------------------------------------------------------------
# The master never trusts its own clock as "ground truth" (that
# would be Cristian's algorithm). Instead, Berkeley's algorithm:
#   1. Collects each slave's clock time and the offset from its
#      own clock (time_difference = master_now - slave_time).
#   2. Averages ALL those offsets (including its own, here
#      implicitly 0 since the master doesn't send itself data).
#   3. Broadcasts back "now + average_offset" to every slave so
#      they all converge to roughly the same synchronized time.
#
# One thread accepts new slave connections, one thread per slave
# keeps receiving its clock pings, and a separate thread runs
# the sync cycle every 5 seconds - all concurrently.
# ============================================================

import threading
import datetime
import socket
import time

# Shared state: address -> {clock_time, time_difference, connector}
# Updated by receiver threads, read by the sync thread.
client_data = {}

def startReceivingClockTime(connector, address):
    # Runs forever in its own thread, one per connected slave.
    # Every 5s (matching the client's send interval) we record
    # how far off that slave's clock is from ours.
    while True:
        clock_time_string = connector.recv(1024).decode()  # blocks until slave sends its clock (bytes -> str)
        clock_time = datetime.datetime.fromisoformat(clock_time_string)  # parse text back into a datetime
        clock_time_diff = datetime.datetime.now() - clock_time  # how far ahead/behind the slave is

        client_data[address] = {  # store/overwrite this slave's latest reading
            "clock_time": clock_time,          # the slave's reported time
            "time_difference": clock_time_diff, # offset used later for averaging
            "connector": connector             # socket, so we can send corrections back
        }

        print("Client Data updated with: " + str(address), end="\n\n")  # progress log
        time.sleep(5)  # matches the slave's 5s send interval

def startConnecting(master_server):
    # Runs forever accepting new slave connections. Each accepted
    # slave gets its own dedicated receiver thread so slaves don't
    # block each other.
    while True:
        master_slave_connector, addr = master_server.accept()  # blocks until a slave connects
        slave_address = str(addr[0]) + ":" + str(addr[1])  # "ip:port" used as the dict key

        print(slave_address + " got connected successfully")  # log the new slave

        current_thread = threading.Thread(
            target=startReceivingClockTime,
            args=(master_slave_connector, slave_address,))  # one receiver thread per slave
        current_thread.start()  # runs concurrently, doesn't block accept() from taking the next slave

def getAverageClockDiff():
    # Core of Berkeley's algorithm: average every slave's clock
    # offset from the master. This average becomes the correction
    # applied to everyone (master included) when broadcasting.
    time_difference_list = list(
        client['time_difference']
        for client_addr, client in client_data.items())  # collect every slave's offset

    sum_of_clock_difference = sum(
        time_difference_list, datetime.timedelta(0, 0))  # sum timedeltas, starting at zero

    average_clock_difference = sum_of_clock_difference / len(client_data)  # mean offset across all slaves
    return average_clock_difference

def synchronizeAllClocks():
    # Runs forever, once every 5s: compute the average offset
    # across all currently-connected slaves, then push each slave
    # a synchronized time = master's current time + that offset.
    while True:
        print("New synchronization cycle started.")  # cycle start marker
        print("Number of clients to be synchronized: " + str(len(client_data)))  # how many slaves connected

        if len(client_data) > 0:
            average_clock_difference = getAverageClockDiff()  # single correction shared by all slaves

            for client_addr, client in client_data.items():  # broadcast to every connected slave
                try:
                    synchronized_time = datetime.datetime.now() + average_clock_difference  # corrected time
                    client['connector'].send(str(synchronized_time).encode())  # push it to that slave
                except Exception:
                    print("Something went wrong with " + str(client_addr))  # e.g. slave disconnected mid-send
        else:
            print("No client data. Synchronization not applicable.")  # nobody to sync yet

        print("\n")
        time.sleep(5)  # run one sync cycle every 5s

def initiateClockServer(port=2050):
    # Bootstraps the master: open the listening socket, then
    # launch the two background threads (accept-loop and
    # sync-loop) that run for the lifetime of the process.
    master_server = socket.socket()  # default TCP/IPv4 socket
    master_server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)  # allow quick restart on the same port
    print("Socket at master node created successfully\n")

    master_server.bind(('', port))  # listen on all interfaces, port 2050
    master_server.listen(10)  # allow up to 10 queued pending connections
    print("Clock server started...\n")

    print("Starting to make connections...\n")
    master_thread = threading.Thread(target=startConnecting, args=(master_server,))  # accept-loop thread
    master_thread.start()

    print("Starting synchronization parallelly...\n")
    sync_thread = threading.Thread(target=synchronizeAllClocks, args=())  # sync-loop thread
    sync_thread.start()

if __name__ == '__main__':
    initiateClockServer(port=2050)  # entry point: start the master on port 2050