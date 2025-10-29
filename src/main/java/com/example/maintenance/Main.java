package com.example.maintenance;

import com.example.maintenance.cli.CLI;
import com.example.maintenance.io.Storage;

import java.nio.file.Path;
import java.util.List;

import com.example.maintenance.model.MaintenanceItem;

public class Main {
    public static void main(String[] args) {
        try {
            Path data = Storage.defaultPath();
            Storage storage = new Storage(data);
            List<MaintenanceItem> items = storage.load();

            CLI cli = new CLI(storage, items);
            cli.run();
        } catch (Exception ex) {
            System.err.println("Fatal error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
