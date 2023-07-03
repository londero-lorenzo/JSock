package socket;


public class ByteList extends structures.List<Byte> {


    public void removeLastByte() {
        this.removeLastElement();
    }

    public byte[] getBytes() {
        byte[] data = new byte[this.elements.size()];
        int i = 0;
        for (Byte b : this.elements)
            if (b != null) {
                data[i] = b;
                i++;
            }
        return data;
    }

    public byte getLastByte() {
        Byte lastByte = this.getLastElement();
        return (lastByte != null) ? lastByte : 0;
    }

    public String getStringFromByteList() {
        return new String(this.getBytes());
    }

    public int getIntFromByteList() {
        // TODO: " " will be a relevant char, so replace (b'0000 0000') bytes.
        // receiving: "c  " -> [01100011], [00000000], [00000000]
        return Integer.parseInt(this.getStringFromByteList().replace(" ", ""));
    }


    public void print() {
        System.out.println("Elements: " + this.elements);
    }
}
