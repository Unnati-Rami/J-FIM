package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MonitorUtils {

    private static final Object lock = new Object();

    // Save or update hash in monitor DNA
    public static void saveHash(String filePath, String hash, String dnaFile) {
        synchronized (lock) {
            try {
                Properties props = new Properties();
                File file = new File(dnaFile);

                if (file.exists()) {
                    try (FileInputStream fis = new FileInputStream(file)) {
                        props.load(fis);
                    }
                }

                props.setProperty(filePath, hash);

                try (FileOutputStream fos = new FileOutputStream(file)) {
                    props.store(fos, "JFIM Monitor DNA");
                }

            } catch (Exception e) {
                System.out.println("[ERROR] Unable to save monitor DNA");
            }
        }
    }

    // Read saved hash from monitor DNA
    public static String getSavedHash(String filePath, String dnaFile) {
        synchronized (lock) {
            try {
                Properties props = new Properties();
                File file = new File(dnaFile);

                if (!file.exists()) {
                    return null;
                }

                try (FileInputStream fis = new FileInputStream(file)) {
                    props.load(fis);
                }

                return props.getProperty(filePath);

            } catch (Exception e) {
                return null;
            }
        }
    }

    // ✅ THIS IS THE MISSING METHOD (your error)
    public static void saveAlert(String alertMessage, String logFile) {
        synchronized (lock) {
            try {
                File log = new File(logFile);

                // create logs folder if not exists
                if (log.getParentFile() != null) {
                    log.getParentFile().mkdirs();
                }

                try (FileWriter fw = new FileWriter(log, true)) {
                    fw.write(alertMessage + System.lineSeparator());
                }

            } catch (IOException e) {
                System.out.println("[ERROR] Unable to write alert log");
            }
        }
    }
}
