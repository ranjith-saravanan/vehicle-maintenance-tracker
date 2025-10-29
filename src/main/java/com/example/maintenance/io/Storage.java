package com.example.maintenance.io;

import com.example.maintenance.model.MaintenanceItem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private final Path dataFile;

    public Storage(Path dataFile) {
        this.dataFile = dataFile;
    }

    public List<MaintenanceItem> load() throws Exception {
        List<MaintenanceItem> out = new ArrayList<>();
        File f = dataFile.toFile();
        if (!f.exists()) {
            Files.createDirectories(dataFile.getParent());
            f.createNewFile();
            return out;
        }

        try (BufferedReader r = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = r.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                try {
                    MaintenanceItem it = MaintenanceItem.fromCsvLine(line);
                    if (it != null) out.add(it);
                } catch (Exception ex) {
                    // skip malformed
                    System.err.println("Skipping malformed line: " + line);
                }
            }
        }
        return out;
    }

    public void save(List<MaintenanceItem> items) throws Exception {
        File f = dataFile.toFile();
        if (!f.exists()) {
            Files.createDirectories(dataFile.getParent());
            f.createNewFile();
        }
        try (BufferedWriter w = new BufferedWriter(new FileWriter(f, false))) {
            for (MaintenanceItem it : items) {
                w.write(it.toCsvLine());
                w.newLine();
            }
        }
    }

    public static Path defaultPath() {
        return Path.of("data", "items.csv");
    }
}
