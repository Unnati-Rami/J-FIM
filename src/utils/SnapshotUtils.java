package utils;

import java.io.*;
import java.nio.file.*;
import java.util.Properties;

/**
 * Utility class to manage snapshot (baseline) hashes for Feature 2.
 * Stores hashes in a file like ".jfim_snap_dna".
 */
public class SnapshotUtils {

    private static final Object lock = new Object(); // thread-safe access

    /**
     * Get the saved hash of a file from the snapshot DNA file.
     *
     * @param filePath Path to the file
     * @param dnaFile  Snapshot file name (e.g., ".jfim_snap_dna")
     * @return Saved hash string, or null if not found
     */
    public static String getSavedHash(String filePath, String dnaFile) {
        synchronized (lock) {
            try {
                File dna = new File(dnaFile);
                if (!dna.exists()) {
                    dna.createNewFile(); // create if missing
                    return null;
                }

                Properties props = new Properties();
                try (FileInputStream fis = new FileInputStream(dna)) {
                    props.load(fis);
                }

                return props.getProperty(filePath);

            } catch (Exception e) {
                System.out.println("Error reading snapshot DNA: " + e.getMessage());
                return null;
            }
        }
    }

    /**
     * Save or update the hash of a file in the snapshot DNA file.
     *
     * @param filePath Path to the file
     * @param hash     Hash to save
     * @param dnaFile  Snapshot file name (e.g., ".jfim_snap_dna")
     */
    public static void saveHash(String filePath, String hash, String dnaFile) {
        synchronized (lock) {
            try {
                File dna = new File(dnaFile);
                if (!dna.exists()) dna.createNewFile();

                Properties props = new Properties();
                try (FileInputStream fis = new FileInputStream(dna)) {
                    props.load(fis);
                }

                props.setProperty(filePath, hash);

                try (FileOutputStream fos = new FileOutputStream(dna)) {
                    props.store(fos, "Snapshot DNA File");
                }

            } catch (Exception e) {
                System.out.println("Error saving snapshot DNA: " + e.getMessage());
            }
        }
    }
}
