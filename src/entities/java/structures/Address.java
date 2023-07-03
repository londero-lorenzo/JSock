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
        int port;
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


class Ipv4 {
    private final int xxx;
    private final int yyy;
    private final int zzz;
    private final int kkk;

    Ipv4(int xxx, int yyy, int zzz, int kkk) {
        this.xxx = xxx;
        this.yyy = yyy;
        this.zzz = zzz;
        this.kkk = kkk;
    }

    static Ipv4 createIpv4FromString(String ipv4AsString) {
        String[] ipv4Parts = ipv4AsString.split("\\.");
        if (ipv4Parts.length == 4) {
            for (String ipv4Part : ipv4Parts)
                if (ipv4Part.length() > 3)
                    return null;
            int[] ipv4 = new int[4];
            for (int i = 0; i < 4; i++)
                try {
                    ipv4[i] = Integer.parseInt(ipv4Parts[i]);
                } catch (NumberFormatException ignored) {
                    return null;
                }
            return new Ipv4(ipv4[0], ipv4[1], ipv4[2], ipv4[3]);
        }
        return null;
    }

    @Override
    public String toString() {
        return Integer.toString(xxx) + '.' + yyy + '.' + zzz + '.' + kkk;
    }
}
