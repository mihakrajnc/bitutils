package si.ndure.bitutils;

import java.io.IOException;
import java.io.OutputStream;

public class BitWriter {

    //TODO: This works, for now.

    private OutputStream outputStream;

    private byte currentByte = 0;
    private byte padding = -1;

    public BitWriter(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void write(boolean... bits) throws IOException {
        for (boolean bit : bits) {
            padding <<= 1;
            currentByte <<= 1;
            currentByte |= bit ? 0x1 : 0x0;

            if (padding == 0) {
                outputStream.write(new byte[]{currentByte});
                padding = -1;
                currentByte = 0;
            }
        }
    }

    public byte flush() throws IOException {
        if (padding == -1) {
            endStream();
            return 0x0;
        } else {
            byte backPadding = 0x0;

            do {
                backPadding <<= 1;
                backPadding |= 0x1;
                padding <<= 1;
                currentByte <<= 1;
            } while (padding != 0);

            outputStream.write(new byte[]{currentByte});
            endStream();
            return backPadding;
        }
    }

    private void endStream() throws IOException {
        outputStream.flush();
        outputStream.close();
    }
}
