package si.ndure.bitutils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.BitSet;

public class BitReader {

    //TODO: I don't even know if this works

    private static final int BUFFER_SIZE = 512;
    private byte[] byteBuffer = new byte[BUFFER_SIZE];
    private InputStream inputStream;
    private int bytesRead;
    private BitSet bitSet = new BitSet();
    private int bitSetPosition = 0;
    private int paddingBits = 0;

    public BitReader(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public boolean read() throws IOException {
        if (bitSetPosition == bytesRead - 1) {
            readFromStream();
        }

        return bitSet.get(bitSetPosition++);
    }

    public int read(boolean[] buffer) throws IOException {
        int bytesRead = 0;
        for (int i = 0; i < buffer.length; i++) {
            if (hasNext()) {
                buffer[i] = read();
                bytesRead++;
            } else {
                break;
            }
        }

        return bytesRead;
    }

    public boolean hasNext() {
        if (bitSetPosition != bytesRead) {
            return true;
        } else {
            try {
                readFromStream();
            } catch (IOException e) {
                return false;
            }

            return bytesRead != 0;
        }
    }

    //FIXME: Include padding bits
    public void setPaddingBits(int paddingBits) {
        this.paddingBits = paddingBits;
    }

    private void readFromStream() throws IOException {
        bytesRead = inputStream.read(byteBuffer);
        byte[] tempBuffer = new byte[bytesRead];
        Arrays.copyOfRange(tempBuffer, 0, bytesRead - 1);
        bitSet = BitSet.valueOf(tempBuffer);
    }
}
