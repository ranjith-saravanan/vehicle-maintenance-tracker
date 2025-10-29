package com.example.maintenance.core;

import com.example.maintenance.model.MaintenanceItem;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class MaintenanceScheduler {

    public static LocalDate nextServiceDate(MaintenanceItem item) {
        return item.getLastServiceDate().plusDays(item.getIntervalDays());
    }

    public static long daysUntilNext(MaintenanceItem item) {
        LocalDate next = nextServiceDate(item);
        return ChronoUnit.DAYS.between(LocalDate.now(), next);
    }

    public static Status status(MaintenanceItem item) {
        long days = daysUntilNext(item);
        if (days < 0) return Status.OVERDUE;
        if (days <= 7) return Status.DUE_SOON; // within a week
        return Status.OK;
    }

    public enum Status {OVERDUE, DUE_SOON, OK}
}
