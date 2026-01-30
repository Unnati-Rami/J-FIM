package features;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

public class Feature1_HashAuthenticator {

    public static void run(String filePath, String originalHash) {

        // 1. Input validation
        if (filePath == null || originalHash == null) {
            System.out.println("Error: File path or hash not provided.");
            return;
        }

        // Header + file path (ADDED)
        System.out.println("=== JFIM : FILE INTEGRITY VERIFICATION ===");
        System.out.println();
        System.out.println("Target File     : " + filePath);

        // 2. Prepare original hash
        originalHash = originalHash.trim().toLowerCase();

        // 2a. Detect hash type
        String hashType;
        switch (originalHash.length()) {
            case 32:
                hashType = "MD5";
                break;
            case 40:
                hashType = "SHA-1";
                break;
            case 64:
                hashType = "SHA-256";
                break;
            case 128:
                hashType = "SHA-512";
                break;
            default:
                System.out.println("Error: Unsupported or unknown hash type.");
                return;
        }

        // 2b. Print hash details
        System.out.println("Hash Algorithm  : " + hashType);
        System.out.println("Original Hash   : " + originalHash);

        // 3. File validation
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            System.out.println("Error: File not found at the specified path.");
            return;
        }
        if (!file.canRead()) {
            System.out.println("Error: Permission denied. Cannot read file.");
            return;
        }

        try {
            // 4. Calculate file hash
            MessageDigest digest = MessageDigest.getInstance(hashType);
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }
            fis.close();

            byte[] hashBytes = digest.digest();

            // Convert to hex
            StringBuilder fileHashBuilder = new StringBuilder();
            for (byte b : hashBytes) {
                fileHashBuilder.append(String.format("%02x", b));
            }
            String fileHash = fileHashBuilder.toString();

            // 5. Print file hash
            System.out.println("File Hash       : " + fileHash);

            // 6. Result (UPDATED TEXT ONLY)
            if (fileHash.equals(originalHash)) {
                System.out.println("Result          : [OK] FILE IS SAFE AND UNMODIFIED");
            } else {
                System.out.println("Result          : [ALERT] FILE MODIFIED OR CORRUPTED");
            }

        } catch (Exception e) {
            System.out.println("Error while calculating file hash: " + e.getMessage());
            System.out.println("Result          : [ALERT] UNABLE TO VERIFY FILE INTEGRITY");
        }
    }
}