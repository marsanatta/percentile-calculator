package util;

import java.io.Closeable;
import java.io.IOException;

public class IOUtil {
    private IOUtil() {
        // static helper
    }

    /**
     * quietly close
     * @param c closable
     */
    public static void close(Closeable c) {
        if (c == null) return;
        try {
            c.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
