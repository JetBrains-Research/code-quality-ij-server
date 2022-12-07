import socket
import json

if __name__ == '__main__':
    HOST, PORT = "localhost", 8080
    code = {"text": "println(123)", "languageId": "Python"} # a real dict.
    data = json.dumps(code)

    # Create a socket (SOCK_STREAM means a TCP socket)
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    try:
        # Connect to server and send data
        sock.connect((HOST, PORT))
        sock.sendall(bytes(len(data)))
        sock.sendall(bytes(data, encoding="utf-8"))


        # # Receive data from the server and shut down
        # received = sock.recv(1024)
        # received = received.decode("utf-8")

    finally:
        sock.close()

    print("Sent:     {}".format(data))
    # print("Received: {}".format(received))