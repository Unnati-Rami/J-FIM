**JFIM — Java File Integrity Monitor.**

Author: Unnati Rami

JFIM (Java File Integrity Monitor) is a command-line security tool designed to detect unauthorized file modifications using cryptographic hashing.

It provides:
- File hash verification
- Snapshot-based integrity checking
- Real-time monitoring capabilities

The tool is inspired by integrity monitoring concepts used in Linux security utilities.


Features

1. Hash Authentication

Verify the integrity of a file by comparing its current hash with a trusted original hash.

- Automatically detects hash algorithm (MD5, SHA-1, SHA-256, SHA-512)
- Clearly indicates whether a file is modified or safe


2. Snapshot (Integrity Snapshot)

Create and maintain integrity snapshots of files or directories.

- Stores cryptographic hashes in a snapshot DNA file
- Recursively scans directories
- Detects unchanged and modified files
- Designed for periodic integrity checks


3. Real-Time Monitoring

Continuously monitors files or directories for changes.

- Detects file modifications in near real-time
- Logs alerts to a persistent log file
- Runs continuously until manually stopped (Ctrl + C)


Cybersecurity Relevance 🛡️

JFIM is designed with practical security applications in mind:

- Tamper Detection: Detects unauthorized modifications to system or configuration files.
- Supply Chain Security: Ensures downloaded binaries match the developer’s original hash.
- Incident Response: Provides baseline snapshots to quickly identify altered files during a security breach.

Usage

General Syntax:

jfim <command> [options]


Commands

```text
-a, --authentication — Verify file hash  
-s, --snapshot — Create or compare integrity snapshot  
-m, --monitor — Real-time file or directory monitoring  
-h, --help — Show help information
``` 


Options

```text
-f <file_path> — Specify a file path  
-p <path> — Specify a file or directory path  
-H <hash> — Provide original hash for verification
```

Examples:

Verify a file hash:
```text
jfim -a -f "C:\test\file.txt" -H <original_hash>
```

Create a snapshot of a directory:
```text
jfim -s -p "C:\test\folder"
```

Start real-time monitoring:
```text
jfim -m -p "C:\test\folder"
```

Show help:
```text
jfim -h
```


Output Style:

```text
[OK]     File unchanged
[ALERT]  File modified
[ERROR]  Processing error
```

Alerts generated during real-time monitoring are also written to:
logs/alerts.log.txt


Project Structure


```text
jfim/
├── src/
│   ├── Main.java
│   ├── features/
│   ├── utils/
│   └── help/
├── jfim.jar
├── jfim.bat
└── README.md
```



Requirements:

- Java JDK 17 or later
- Windows (tested) or Linux


Notes:

- Real-time monitoring runs continuously until stopped manually using Ctrl + C.
- Snapshot and monitor DNA files are created automatically.
- The tool is designed to be simple, transparent, and security-focused.


License:

This project is provided for educational and security research purposes.


**JFIM — Monitor what matters.**
