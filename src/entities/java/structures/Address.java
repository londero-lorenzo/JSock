package structures;

public class Address {

    private final String ipv4;

    private final int port;

    private final Boolean secure = false;

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
