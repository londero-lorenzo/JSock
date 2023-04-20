package socket;

import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ByteListTest {

    ByteList byteList = new ByteList();

    List<Byte> arrays = new ArrayList<Byte>();
    int limit = 127;
    int size = 999;

    @Test
    void addByteListClass() {
        for (int i = 0; i < size; i++)
            this.byteList.add((byte) (i % limit));
        assertEquals(999, this.byteList.getLength());


        String s = new String(this.byteList.getBytes());

        assertEquals(999, s.length());
    }


    @Test
    void addList() {
        for (int i = 0; i < size; i++)
            this.arrays.add((byte) (i % limit));

        byte[] data = new byte[this.arrays.size()];
        final int[] no = {0};
        arrays.forEach(aByte -> {
            if (aByte != null) {
                data[no[0]] = aByte;
                no[0]++;
            }
        });

        assertEquals(999, this.arrays.size());
        String s = new String(data);

        assertEquals(999, s.length());
    }
}