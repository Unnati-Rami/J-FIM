package help;

public class Help {

    public static void show() {

        System.out.println("J-FIM - Java File Integrity Monitor");
        System.out.println("Detects unauthorized file modifications using cryptographic hashing.");
        System.out.println("Designed for integrity monitoring and basic intrusion detection.");
        System.out.println();

        System.out.println("Usage:");
        System.out.println("  jfim <command> [options]");
        System.out.println();

        System.out.println("Commands:");
        System.out.println("  -a, --authentication    Verify file integrity using hash comparison");
        System.out.println("  -s, --snapshot          Create baseline snapshot of file or directory");
        System.out.println("  -m, --monitor           Real-time integrity monitoring");
        System.out.println("  -h, --help              Show this help message");
        System.out.println();

        System.out.println("Options:");
        System.out.println("  -f <file_path>          Target file path");
        System.out.println("  -p <path>               Target file or directory path");
        System.out.println("  -H <hash>               Original SHA-256 hash for verification");
        System.out.println();

        System.out.println("Examples:");
        System.out.println("  jfim -h");
        System.out.println("  jfim -a -f \"C:\\test\\file.txt\" -H <hash>");
        System.out.println("  jfim -s -p \"C:\\test\\folder\"");
        System.out.println("  jfim -m -p \"C:\\test\\folder\"");
    }
}
