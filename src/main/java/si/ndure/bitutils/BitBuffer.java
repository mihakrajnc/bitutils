package si.ndure.bitutils;

import java.util.Arrays;

public class BitBuffer {

    //TODO: This should be more efficient

    private static final int GROW_SIZE = 512;

    private byte[] buffer;
    private int bytePosition;
    private byte currentPadding = -1;

    public BitBuffer(int initialByteCapacity) {
        buffer = new byte[initialByteCapacity];
        bytePosition = 0;
    }

    public void put(boolean bit) {
        if (currentPadding == 0) {
            if (bytePosition == buffer.length) {
                buffer = Arrays.copyOf(buffer, buffer.length - 1 + GROW_SIZE);
            }
            bytePosition++;
            currentPadding = -1;
        }

        if (bit) {
            buffer[bytePosition] |= (byte) (currentPadding & (~(byte) ((currentPadding & 0xFF) >> 1)));
        }

        currentPadding = (byte) ((currentPadding & 0xFF) >> 1);
    }

    public byte[] getBytes() {
        return Arrays.copyOf(buffer, bytePosition + 1);
    }
}