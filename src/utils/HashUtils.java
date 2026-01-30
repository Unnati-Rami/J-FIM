package utils;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

/**
 * Utility class for calculating hashes of files.
 * Supports MD5, SHA-1, SHA-256, SHA-512.
 */
public class HashUtils {

    /**
     * Calculates the hash of a file using the specified algorithm.
     *
     * @param filePath  Path to the file
     * @param algorithm Hash algorithm (MD5, SHA-1, SHA-256, SHA-512)
     * @return Hexadecimal string of the calculated hash
     * @throws Exception If file does not exist, cannot be read, or algorithm is invalid
     */
    public static String calculateHash(String filePath, String algorithm) throws Exception {

        // 1. Validate file
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new Exception("Invalid file path: " + filePath);
        }

        if (!file.canRead()) {
            throw new Exception("Permission denied: Cannot read file");
        }

        // 2. Create MessageDigest instance
        MessageDigest digest = MessageDigest.getInstance(algorithm);

        // 3. Read file using try-with-resources (automatically closes file)
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }
        } // FileInputStream automatically closed here

        // 4. Convert byte hash to hex string
        byte[] hashBytes = digest.digest();
        StringBuilder hashBuilder = new StringBuilder();

        for (byte b : hashBytes) {
            hashBuilder.append(String.format("%02x", b));
        }

        return hashBuilder.toString();
    }
}
