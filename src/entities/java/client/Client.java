package client;

import structures.Address;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {
    private Address serverAddress;

    private socket.Socket socket;


    public void connectTo(Address serverAddress)
    {
        this.serverAddress = serverAddress;
        this.connect();
    }

    private void connect()
    {
        this.socket = new socket.Socket(this.serverAddress);
    }
}
