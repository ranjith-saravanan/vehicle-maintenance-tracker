package com.example.maintenance.cli;

import com.example.maintenance.core.MaintenanceScheduler;
import com.example.maintenance.model.MaintenanceItem;
import com.example.maintenance.io.Storage;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class CLI {
    private final Scanner in = new Scanner(System.in);
    private final Storage storage;
    private final List<MaintenanceItem> items;

    public CLI(Storage storage, List<MaintenanceItem> items) {
        this.storage = storage;
        this.items = items;
    }

    public void run() {
        boolean running = true;
        while (running) {
            printMenu();
            String cmd = in.nextLine().trim();
            switch (cmd) {
                case "1":
                    listItems();
                    break;
                case "2":
                    addItem();
                    break;
                case "3":
                    checkAlerts();
                    break;
                case "4":
                    saveAndExit();
                    running = false;
                    break;
                default:
                    System.out.println("Unknown option. Pick 1-4.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\nMaintenance Tracker");
        System.out.println("1) List items");
        System.out.println("2) Add item");
        System.out.println("3) Show alerts");
        System.out.println("4) Save and exit");
        System.out.print("Choose: ");
    }

    private void listItems() {
        if (items.isEmpty()) {
            System.out.println("(no items)");
            return;
        }
        int i = 1;
        for (MaintenanceItem it : items) {
            System.out.printf("%d) %s -> next: %s (in %d days)\n", i++, it.toString(),
                    MaintenanceScheduler.nextServiceDate(it), MaintenanceScheduler.daysUntilNext(it));
        }
    }

    private void addItem() {
        System.out.print("Name: ");
        String name = in.nextLine().trim();
        System.out.print("Last service date (YYYY-MM-DD): ");
        String ds = in.nextLine().trim();
        LocalDate date;
        try {
            date = LocalDate.parse(ds);
        } catch (Exception ex) {
            System.out.println("Invalid date format. Aborting add.");
            return;
        }
        System.out.print("Interval days (e.g., 180): ");
        String si = in.nextLine().trim();
        int interval;
        try {
            interval = Integer.parseInt(si);
        } catch (Exception ex) {
            System.out.println("Invalid number. Aborting add.");
            return;
        }
        MaintenanceItem it = new MaintenanceItem(name, date, interval);
        items.add(it);
        System.out.println("Added: " + it);
    }

    private void checkAlerts() {
        boolean any = false;
        for (MaintenanceItem it : items) {
            MaintenanceScheduler.Status s = MaintenanceScheduler.status(it);
            if (s != MaintenanceScheduler.Status.OK) {
                any = true;
                long days = MaintenanceScheduler.daysUntilNext(it);
                String how = s == MaintenanceScheduler.Status.OVERDUE ? "OVERDUE by %d days" : "due in %d days";
                System.out.printf("%s -> next: %s -> " + how + "\n", it.getName(), MaintenanceScheduler.nextServiceDate(it), Math.abs(days));
            }
        }
        if (!any) System.out.println("No upcoming or overdue maintenance.");
    }

    private void saveAndExit() {
        try {
            storage.save(items);
            System.out.println("Saved " + items.size() + " items to " + Storage.defaultPath());
        } catch (Exception ex) {
            System.err.println("Failed to save: " + ex.getMessage());
        }
        System.out.println("Goodbye.");
    }
}
