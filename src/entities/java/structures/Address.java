package structures;

import exceptions.ExceptionHandler;
import exceptions.SocketException;
import exceptions.InvalidIpv4AddressProvided;
import exceptions.InvalidPortProvided;

public class Address {

    private String ipv4;

    private int port;

    private ExceptionHandler exceptionHandler;

    private boolean usable = true;

    public Address(String ipv4, int port) {
        this.ipv4 = ipv4;
        this.port = port;
    }


    public String getIpv4() {
        return ipv4;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return this.ipv4 + ":" + this.port;
    }
}
