package features;

import utils.HashUtils;
import utils.MonitorUtils;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Feature3_RealTimeMonitor {

    // Entry point from Main.java
    // Usage: jfim -m -p <file_or_directory_path>
    public static void run(String path) {

        System.out.println("=== JFIM : REAL-TIME MONITOR ===");

        // Validate path
        if (path == null || path.isEmpty()) {
            System.out.println("Error: No path provided for monitoring.");
            return;
        }

        File target = new File(path);
        if (!target.exists()) {
            System.out.println("Error: File or directory does not exist: " + path);
            return;
        }

        // DNA file for persistent storage
        String dnaFile = ".jfim_monitor_dna";

        try {
            if (target.isFile()) {
                monitorFile(target, dnaFile);
            } else if (target.isDirectory()) {
                System.out.println("Monitoring directory: " + target.getAbsolutePath());
                monitorDirectory(target, dnaFile);
            } else {
                System.out.println("Error: Invalid path type.");
            }
        } catch (Exception e) {
            System.out.println("Monitoring failed: " + e.getMessage());
        }
    }

    // Monitor a single file
    private static void monitorFile(File file, String dnaFile) throws Exception {
        String hashType = "SHA-256";
        String filePath = file.getAbsolutePath();

        // Get previous hash from DNA, or calculate initial snapshot
        String previousHash = MonitorUtils.getSavedHash(filePath, dnaFile);
        if (previousHash == null) {
            previousHash = HashUtils.calculateHash(filePath, hashType);
            MonitorUtils.saveHash(filePath, previousHash, dnaFile);
        }

        System.out.println("Monitoring started for: " + filePath);
        System.out.println("Press Ctrl + C to stop\n");

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Infinite monitoring loop
        while (true) {
            Thread.sleep(3000); // check every 3 seconds

            String currentHash = HashUtils.calculateHash(filePath, hashType);

            // Only print alerts, no "No change detected"
            if (!currentHash.equals(previousHash)) {
                String alertMessage = "[ALERT] File modified at "
                        + LocalDateTime.now().format(formatter)
                        + " | " + filePath;

                System.out.println(alertMessage);

                // Save alert and update snapshot in DNA
                MonitorUtils.saveAlert(alertMessage, "logs/alerts.log.txt");
                previousHash = currentHash;
                MonitorUtils.saveHash(filePath, currentHash, dnaFile);
            }
        }
    }

    // Monitor a directory recursively
    private static void monitorDirectory(File directory, String dnaFile) throws Exception {
        File[] files = directory.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isFile()) {
                // Monitor each file in a separate thread
                File monitorFile = file;
                new Thread(() -> {
                    try {
                        monitorFile(monitorFile, dnaFile);
                    } catch (Exception e) {
                        System.out.println("[ERROR] " + monitorFile.getAbsolutePath());
                    }
                }).start();
            } else if (file.isDirectory()) {
                monitorDirectory(file, dnaFile);
            }
        }
    }
}
