package com.gpadilla.pedidosnow.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/database")
public class DatabaseController {

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @PostMapping("/dump")
    public ResponseEntity<String> createDatabaseDump() {
        try {
            // Extract database name from JDBC URL
            String dbName = extractDatabaseName(datasourceUrl);
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String dumpFileName = "backup_" + timestamp + ".sql";

            // Build pg_dump command
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "pg_dump",
                    "-h", "localhost", // adjust host if needed
                    "-U", username,
                    "-d", dbName,
                    "-f", dumpFileName,
                    "--no-password"
            );

            // Set PGPASSWORD environment variable
            processBuilder.environment().put("PGPASSWORD", password);

            Process process = processBuilder.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                return ResponseEntity.ok("Database dump created successfully: " + dumpFileName);
            } else {
                String error = new String(process.getErrorStream().readAllBytes());
                return ResponseEntity.status(500).body("Dump failed: " + error);
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating dump: " + e.getMessage());
        }
    }

    @PostMapping("/restore")
    public ResponseEntity<String> restoreDatabase(@RequestParam String backupFile) {
        try {
            // Validate file exists
            File file = new File(backupFile);
            if (!file.exists()) {
                return ResponseEntity.badRequest().body("Backup file not found: " + backupFile);
            }

            String dbName = extractDatabaseName(datasourceUrl);

            // Build psql command for restore
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "psql",
                    "-h", "localhost", // adjust host if needed
                    "-U", username,
                    "-d", dbName,
                    "-f", backupFile,
                    "--no-password"
            );

            processBuilder.environment().put("PGPASSWORD", password);

            Process process = processBuilder.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                return ResponseEntity.ok("Database restored successfully from: " + backupFile);
            } else {
                String error = new String(process.getErrorStream().readAllBytes());
                return ResponseEntity.status(500).body("Restore failed: " + error);
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error restoring database: " + e.getMessage());
        }
    }

    @GetMapping("/backups")
    public ResponseEntity<List<String>> listBackups() {
        try {
            File currentDir = new File(".");
            String[] backupFiles = currentDir.list((dir, name) ->
                    name.startsWith("backup_") && name.endsWith(".sql"));

            if (backupFiles == null) {
                return ResponseEntity.ok(Collections.emptyList());
            }

            return ResponseEntity.ok(Arrays.asList(backupFiles));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    private String extractDatabaseName(String jdbcUrl) {
        // Extract database name from jdbc:postgresql://localhost:5432/mydb
        String[] parts = jdbcUrl.split("/");
        String dbPart = parts[parts.length - 1];
        return dbPart.split("\\?")[0]; // Remove query parameters if any
    }
}