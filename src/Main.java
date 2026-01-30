// Main.java
import help.Help;
import features.Feature1_HashAuthenticator;
import features.Feature2_SnapshotCompare;
import features.Feature3_RealTimeMonitor;

public class Main {

    public static void main(String[] args) {
        // If no arguments provided
        if (args.length == 0) {
            System.out.println("No arguments provided. Use jfim -h or jfim --help for usage.");
            return;
        }

        // Check for help first
        if (args[0].equals("-h") || args[0].equals("--help")) {
            Help.show();
            return;
        }

        // Determine feature
        String command = args[0];

        switch (command) {
            case "-a":
            case "--authentication":
                runFeature1(args);
                break;

            case "-s":
            case "--snapshot":
                runFeature2(args);
                break;

            case "-m":
            case "--monitor":
                runFeature3(args);
                break;

            default:
                System.out.println("Unknown command: " + command);
                System.out.println("Use jfim -h or jfim --help for usage.");
        }
    }

    // ---------------- Feature 1 parser ----------------
    private static void runFeature1(String[] args) {
        String filePath = null;
        String originalHash = null;

        for (int i = 1; i < args.length; i++) {
            switch (args[i]) {
                case "-f":
                case "--file":
                    if (i + 1 < args.length) {
                        filePath = args[++i];
                    }
                    break;

                case "-H":
                case "--hash":
                    if (i + 1 < args.length) {
                        originalHash = args[++i];
                    }
                    break;
            }
        }

        if (filePath == null || originalHash == null) {
            System.out.println("Missing required arguments for authentication.");
            System.out.println("Usage: jfim -a -f <file_path> -H <hash>");
            return;
        }

        Feature1_HashAuthenticator.run(filePath, originalHash);
    }

    // ---------------- Feature 2 parser ----------------
    private static void runFeature2(String[] args) {
        String path = null;

        for (int i = 1; i < args.length; i++) {
            if (args[i].equals("-p") || args[i].equals("--path")) {
                if (i + 1 < args.length) {
                    path = args[++i];
                }
            }
        }

        if (path == null) {
            System.out.println("Missing path for snapshot check.");
            System.out.println("Usage: jfim -s -p <directory_path>");
            return;
        }

        Feature2_SnapshotCompare.run(path);
    }

    // ---------------- Feature 3 parser ----------------
    private static void runFeature3(String[] args) {
        String path = null;

        for (int i = 1; i < args.length; i++) {
            if (args[i].equals("-p") || args[i].equals("--path")) {
                if (i + 1 < args.length) {
                    path = args[++i];
                }
            }
        }

        if (path == null) {
            System.out.println("Missing path for live integrity monitoring.");
            System.out.println("Usage: jfim -m -p <directory_path>");
            return;
        }

        Feature3_RealTimeMonitor.run(path);
    }
}
