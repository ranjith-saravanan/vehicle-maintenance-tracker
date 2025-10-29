package com.example.maintenance.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class MaintenanceItem {
    private final String id;
    private String name;
    private LocalDate lastServiceDate;
    private int intervalDays; // service interval in days

    private static final DateTimeFormatter F = DateTimeFormatter.ISO_LOCAL_DATE;

    public MaintenanceItem(String id, String name, LocalDate lastServiceDate, int intervalDays) {
        this.id = id;
        this.name = name;
        this.lastServiceDate = lastServiceDate;
        this.intervalDays = intervalDays;
    }

    public MaintenanceItem(String name, LocalDate lastServiceDate, int intervalDays) {
        this(UUID.randomUUID().toString(), name, lastServiceDate, intervalDays);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getLastServiceDate() {
        return lastServiceDate;
    }

    public void setLastServiceDate(LocalDate lastServiceDate) {
        this.lastServiceDate = lastServiceDate;
    }

    public int getIntervalDays() {
        return intervalDays;
    }

    public void setIntervalDays(int intervalDays) {
        this.intervalDays = intervalDays;
    }

    public String toCsvLine() {
        return String.format("%s,%s,%s,%d", id, escape(name), lastServiceDate.format(F), intervalDays);
    }

    public static MaintenanceItem fromCsvLine(String line) {
        // id,name,lastDate,interval
        String[] parts = line.split(",", 4);
        if (parts.length < 4) return null;
        String id = parts[0];
        String name = unescape(parts[1]);
        LocalDate date = LocalDate.parse(parts[2], F);
        int interval = Integer.parseInt(parts[3]);
        return new MaintenanceItem(id, name, date, interval);
    }

    private static String escape(String s) {
        return s.replace("\n", " ").replace(",", "\\,");
    }

    private static String unescape(String s) {
        return s.replace("\\,", ",");
    }

    @Override
    public String toString() {
        return String.format("%s (id=%s) - last: %s, interval: %d days", name, id, lastServiceDate.format(F), intervalDays);
    }
}
