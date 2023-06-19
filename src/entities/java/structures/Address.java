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

    public Address(SocketException socketException) {
        exceptionHandler = new ExceptionHandler();
        exceptionHandler.setException(socketException);
        this.usable = false;
    }

    public static Address getAddressFromString(String addressAsString) {
        String[] addressParts = addressAsString.split(":");
        Ipv4 ipv4 = Ipv4.createIpv4FromString(addressParts[0]);
        if (ipv4 == null)
            return new Address(new InvalidIpv4AddressProvided());
        int port = -1;
        try {
            port = Integer.parseInt(addressParts[1]);
        } catch (NumberFormatException ignored) {
            return new Address(new InvalidPortProvided());
        }
        return new Address(ipv4.toString(), port);
    }

    public boolean isUsable() {
        return this.usable;
    }

    public ExceptionHandler getExceptionHandler() {
        return this.exceptionHandler;
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
