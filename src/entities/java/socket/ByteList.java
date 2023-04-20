package socket;

import java.util.ArrayList;
import java.util.List;

public class ByteList {

    private List<Byte> bytes = new ArrayList<>();

    public void add(byte b) {
        this.bytes.add(b);
    }

    public int getLength() {
        return this.bytes.size();
    }

    public byte[] getBytes() {
        byte[] data = new byte[this.bytes.size()];
        int i = 0;
        for (Byte b : this.bytes)
            if (b != null) {
                data[i] = b;
                i++;
            }
        return data;
    }

    public void print() {
        System.out.println("Elements: " + this.bytes);
    }
}
