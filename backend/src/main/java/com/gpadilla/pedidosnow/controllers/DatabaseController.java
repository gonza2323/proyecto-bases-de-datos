package com.gpadilla.pedidosnow.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

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
    public ResponseEntity<String> createDatabaseDump(@AuthenticationPrincipal Jwt jwt) {
        checkIfAdmin(jwt.getSubject());
        try {
            // Extract database name from JDBC URL
            String dbName = extractDatabaseName(datasourceUrl);
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String dumpFileName = "backup_" + timestamp + ".sql";

            String host = extractHost(datasourceUrl);
            String port = extractPort(datasourceUrl);

            // Build pg_dump command
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "pg_dump",
                    "-h", host,
                    "-p", port,
                    "-U", username,
                    "-d", dbName,
                    "-f", dumpFileName,
                    "-Fc", // Custom format
                    "--no-password"
            );

            // Set PGPASSWORD environment variable
            processBuilder.environment().put("PGPASSWORD", password);

            Process process = processBuilder.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Database dump created successfully: " + dumpFileName);
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
    public ResponseEntity<String> restoreDatabase(@AuthenticationPrincipal Jwt jwt, @RequestParam String backupFile) {
        checkIfAdmin(jwt.getSubject());
        try {
            // Validate file exists
            File file = new File(backupFile);
            if (!file.exists()) {
                return ResponseEntity.badRequest().body("Backup file not found: " + backupFile);
            }

            String dbName = extractDatabaseName(datasourceUrl);
            String host = extractHost(datasourceUrl);
            String port = extractPort(datasourceUrl);

            // Build psql command for restore
            ProcessBuilder restoreBuilder = new ProcessBuilder(
                    "pg_restore",
                    "-h", host,
                    "-p", port,
                    "-U", username,
                    "-d", dbName,
                    "--clean",
                    "--if-exists",
                    "--no-owner",
                    "--no-acl",
                    backupFile
            );

            restoreBuilder.environment().put("PGPASSWORD", password);

            Process process = restoreBuilder.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Database restored successfully from: " + backupFile);
                return ResponseEntity.ok("Database restored successfully from: " + backupFile);
            } else {
                String error = new String(process.getErrorStream().readAllBytes());
                System.out.println("Restore failed: " + error);
                return ResponseEntity.status(500).body("Restore failed: " + error);
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error restoring database: " + e.getMessage());
        }
    }

    @GetMapping("/backups")
    public ResponseEntity<List<String>> listBackups(@AuthenticationPrincipal Jwt jwt) {
        checkIfAdmin(jwt.getSubject());
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

    @DeleteMapping("/backups/{backup}")
    public ResponseEntity<String> deleteBackup(@AuthenticationPrincipal Jwt jwt, @PathVariable String backup) {
        checkIfAdmin(jwt.getSubject());
        try {
            // Validate filename to prevent path traversal
            if (!backup.matches("^backup_\\d{8}_\\d{6}\\.sql$")) {
                return ResponseEntity.badRequest().body("Invalid backup filename format");
            }

            File backupFile = new File(backup);
            if (!backupFile.exists()) {
                return ResponseEntity.notFound().build();
            }

            if (backupFile.delete()) {
                return ResponseEntity.ok("Backup deleted successfully: " + backup);
            } else {
                return ResponseEntity.status(500).body("Failed to delete backup file");
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting backup: " + e.getMessage());
        }
    }

    @GetMapping("/backups/{backup}")
    public ResponseEntity<Resource> downloadBackup(@AuthenticationPrincipal Jwt jwt, @PathVariable String backup) {
        checkIfAdmin(jwt.getSubject());
        try {
            // Validate filename to prevent path traversal
            if (!backup.matches("^backup_\\d{8}_\\d{6}\\.sql$")) {
                return ResponseEntity.badRequest().build();
            }

            File backupFile = new File(backup);
            if (!backupFile.exists()) {
                return ResponseEntity.notFound().build();
            }

            Resource resource = new FileSystemResource(backupFile);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + backup + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, "application/sql")
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/backups/upload")
    public ResponseEntity<String> uploadBackup(@AuthenticationPrincipal Jwt jwt, @RequestParam("file") MultipartFile file) {
        checkIfAdmin(jwt.getSubject());
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("No file provided");
            }

            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || !originalFilename.endsWith(".sql")) {
                return ResponseEntity.badRequest().body("File must be a .sql file");
            }

            // Generate a safe filename with timestamp
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String safeFilename = "backup_uploaded_" + timestamp + ".sql";

            File uploadedFile = new File(safeFilename);
            file.transferTo(uploadedFile);

            return ResponseEntity.ok("Backup uploaded successfully as: " + safeFilename);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error uploading backup: " + e.getMessage());
        }
    }

    private String extractDatabaseName(String jdbcUrl) {
        // Extract database name from jdbc:postgresql://localhost:5432/mydb
        String[] parts = jdbcUrl.split("/");
        String dbPart = parts[parts.length - 1];
        return dbPart.split("\\?")[0]; // Remove query parameters if any
    }

    private void checkIfAdmin(String auth0Id) {
        if (!auth0Id.equals("google-oauth2|107018146032061883735")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    private String extractHost(String jdbcUrl) {
        // Extract host from jdbc:postgresql://localhost:5432/mydb
        try {
            String withoutPrefix = jdbcUrl.replace("jdbc:postgresql://", "");
            String hostPort = withoutPrefix.split("/")[0];
            return hostPort.split(":")[0];
        } catch (Exception e) {
            return "localhost"; // fallback
        }
    }

    private String extractPort(String jdbcUrl) {
        // Extract port from jdbc:postgresql://localhost:5432/mydb
        try {
            String withoutPrefix = jdbcUrl.replace("jdbc:postgresql://", "");
            String hostPort = withoutPrefix.split("/")[0];
            String[] parts = hostPort.split(":");
            return parts.length > 1 ? parts[1] : "5432";
        } catch (Exception e) {
            return "5432"; // fallback
        }
    }
}