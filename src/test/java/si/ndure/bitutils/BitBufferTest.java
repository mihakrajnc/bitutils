package si.ndure.bitutils;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BitBufferTest {

    //TODO: Write way more tests

    private static final int BUFFER_SIZE = 2;

    private BitBuffer bitBuffer;

    @Before
    public void setUp() throws Exception {
        bitBuffer = new BitBuffer(BUFFER_SIZE);
    }

    @Test
    public void testPut() throws Exception {
        bitBuffer.put(false);
        bitBuffer.put(true);
        bitBuffer.put(true);
        bitBuffer.put(false);

        byte[] buffer = bitBuffer.getBytes();

        assertEquals(96, buffer[0]);

        bitBuffer.put(false);
        bitBuffer.put(true);
        bitBuffer.put(true);
        bitBuffer.put(false);
        bitBuffer.put(true);

        buffer = bitBuffer.getBytes();
        assertEquals(-128, buffer[1]);
    }
}