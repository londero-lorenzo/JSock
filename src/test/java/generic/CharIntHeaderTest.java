package generic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CharIntHeaderTest {

    int HeaderIntValue = 99;

    char HeaderCharValue = (char) HeaderIntValue;

    @Test
    void toByte()
    {
        System.out.println((byte) HeaderIntValue);
        System.out.println(Integer.toBinaryString(HeaderIntValue));
    }

    @Test
    void toChar()
    {
        System.out.println((char) HeaderIntValue);
    }

    @Test
    void ByteIntComparison()
    {
        assertEquals(HeaderCharValue, HeaderIntValue);
    }

    @Test
    void ByteCharComparison()
    {
        assertEquals(HeaderCharValue,(char) HeaderIntValue);
    }

    @Test
    void toInt()
    {
        System.out.println((int) HeaderCharValue);
    }
}
