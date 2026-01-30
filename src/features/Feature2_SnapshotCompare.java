package features;

import utils.HashUtils;
import utils.SnapshotUtils;

import java.io.File;

public class Feature2_SnapshotCompare {

    // Entry point from Main.java
    // Usage: jfim -b -p <file_or_directory_path>
    public static void run(String path) {

        System.out.println("=== JFIM : SNAPSHOT ===");

        if (path == null || path.isEmpty()) {
            System.out.println("Error: No path provided.");
            return;
        }

        File target = new File(path);

        if (!target.exists()) {
            System.out.println("Error: Path does not exist.");
            return;
        }

        // Snapshot DNA file
        String dnaFile = ".jfim_snap_dna";

        if (target.isFile()) {
            processFile(target, dnaFile);
        } else if (target.isDirectory()) {
            processDirectory(target, dnaFile);  // directory processed silently, per-file messages only
        } else {
            System.out.println("Error: Invalid path type.");
        }
    }

    // -------- Process single file --------
    private static void processFile(File file, String dnaFile) {
        try {
            String hashType = "SHA-256";
            String filePath = file.getAbsolutePath();

            String currentHash = HashUtils.calculateHash(filePath, hashType);
            String savedHash = SnapshotUtils.getSavedHash(filePath, dnaFile);

            if (savedHash == null) {
                SnapshotUtils.saveHash(filePath, currentHash, dnaFile);
                System.out.println("[OK] Snapshotted: " + filePath);
            } else if (currentHash.equals(savedHash)) {
                System.out.println("[OK] Unchanged: " + filePath);
            } else {
                System.out.println("[ALERT] File modified: " + filePath);
            }

        } catch (Exception e) {
            System.out.println("[ERROR] Unable to process file: " + file.getAbsolutePath());
        }
    }

    // -------- Process directory recursively --------
    private static void processDirectory(File directory, String dnaFile) {
        File[] files = directory.listFiles();

        if (files == null) return;

        for (File file : files) {
            if (file.isFile()) {
                processFile(file, dnaFile);
            } else if (file.isDirectory()) {
                processDirectory(file, dnaFile);
            }
        }
    }
}
