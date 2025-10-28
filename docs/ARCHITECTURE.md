# Architecture Documentation

## System Overview

The Vehicle Maintenance Tracker is built using a layered architecture following MVC (Model-View-Controller) and Repository patterns for clean separation of concerns.

## Architecture Layers

### 1. Presentation Layer
Handles user interaction through two interfaces:

#### CLI (Command-Line Interface)
- **Class**: `CLI.java`
- **Purpose**: Text-based interactive menu
- **Features**: List items, add items, show alerts, save data
- **Entry Point**: `Main.java`

#### GUI (Graphical User Interface)
- **Class**: `MaintenanceGUI.java`
- **Framework**: Java Swing
- **Components**: JFrame, JTable, JDialog, JButton
- **Features**: Visual table, modal dialogs, color-coded status
- **Entry Point**: `MaintenanceGUI.main()`

### 2. Business Logic Layer
Core application logic for maintenance scheduling:

#### MaintenanceScheduler
- **Responsibilities**:
  - Calculate next service date
  - Determine days until next service
  - Classify status (OVERDUE, DUE_SOON, OK)
- **Algorithm**:
  ```
  nextServiceDate = lastServiceDate + intervalDays
  daysUntil = nextServiceDate - today
  status = classify(daysUntil)
  ```

### 3. Model Layer
Data representation:

#### MaintenanceItem
- **Attributes**:
  - `id`: String (unique identifier)
  - `name`: String (vehicle/equipment name)
  - `lastServiceDate`: LocalDate
  - `intervalDays`: int (days between services)
- **Methods**:
  - `toCsvLine()`: Serialize to CSV
  - `fromCsvLine()`: Deserialize from CSV

### 4. Data Access Layer
Persistence management:

#### Storage
- **Pattern**: Repository Pattern
- **Format**: CSV (Comma-Separated Values)
- **Location**: `data/items.csv`
- **Operations**:
  - `load()`: Read all items from disk
  - `save(List<MaintenanceItem>)`: Write items to disk
  - `defaultPath()`: Get storage location

## Data Flow

### Read Operation
```
User → CLI/GUI → Storage.load() → Parse CSV → List<MaintenanceItem>
```

### Write Operation
```
User → CLI/GUI → Modify List → Storage.save() → Write CSV
```

### Alert Check
```
User → CLI/GUI → MaintenanceScheduler.status() → Filter → Display Alerts
```

## Design Patterns

### 1. Model-View-Controller (MVC)
- **Model**: `MaintenanceItem`
- **View**: `CLI`, `MaintenanceGUI`
- **Controller**: `MaintenanceScheduler`

### 2. Repository Pattern
- **Repository**: `Storage`
- **Abstraction**: Separates data access from business logic
- **Benefit**: Easy to swap CSV for database

### 3. Factory Pattern (Implicit)
- `MaintenanceItem.fromCsvLine()`: Creates objects from strings

## Component Diagram

```
┌─────────────────────────────────────────┐
│         Presentation Layer              │
│  ┌──────────┐         ┌──────────────┐ │
│  │   CLI    │         │ MaintenanceGUI│ │
│  └────┬─────┘         └──────┬───────┘ │
└───────┼────────────────────── ┼─────────┘
        │                       │
┌───────┼───────────────────────┼─────────┐
│       │   Business Logic      │         │
│  ┌────▼────────────────────────▼─────┐  │
│  │    MaintenanceScheduler           │  │
│  └───────────────┬───────────────────┘  │
└──────────────────┼──────────────────────┘
                   │
┌──────────────────┼──────────────────────┐
│       Model      │                      │
│  ┌───────────────▼───────────────────┐  │
│  │       MaintenanceItem            │  │
│  └──────────────┬───────────────────┘  │
└─────────────────┼──────────────────────┘
                  │
┌─────────────────┼──────────────────────┐
│   Data Access   │                      │
│  ┌──────────────▼──────────────────┐   │
│  │         Storage                │   │
│  └──────────────┬──────────────────┘   │
└─────────────────┼──────────────────────┘
                  │
            ┌─────▼──────┐
            │ items.csv  │
            └────────────┘
```

## Technology Stack

| Layer | Technology |
|-------|------------|
| Language | Java 11 |
| GUI Framework | Java Swing |
| Date/Time | Java 8 Time API |
| Build Tool | Maven |
| Storage | CSV (text files) |
| Version Control | Git |

## Status Classification Logic

```java
if (daysUntil < 0)
    return Status.OVERDUE;      // Past due date
else if (daysUntil <= 7)
    return Status.DUE_SOON;     // Within 7 days
else
    return Status.OK;            // More than 7 days
```

## Extension Points

### Adding Database Support
Replace `Storage.java` with:
```java
public interface StorageRepository {
    List<MaintenanceItem> load();
    void save(List<MaintenanceItem> items);
}

// Implementations:
// - CsvStorage (current)
// - SqliteStorage (future)
// - PostgresStorage (future)
```

### Adding Export Features
Extend business logic:
```java
public class ExportService {
    public void exportToPdf(List<MaintenanceItem> items);
    public void exportToExcel(List<MaintenanceItem> items);
    public void exportToJson(List<MaintenanceItem> items);
}
```

### Adding Notifications
New layer:
```java
public interface NotificationService {
    void sendEmail(List<MaintenanceItem> overdueItems);
    void sendSMS(List<MaintenanceItem> dueSoonItems);
}
```

## Security Considerations

- **Input Validation**: CSV parsing handles malformed lines
- **Path Traversal**: Storage uses fixed directory structure
- **Injection**: CSV escaping prevents data corruption
- **Future**: Add authentication for multi-user support

## Performance

- **Scalability**: Linear O(n) for most operations
- **Memory**: Loads entire dataset into memory
- **Optimization**: Add indexing for 10,000+ records
- **Caching**: Consider caching calculated dates

## Testing Strategy

### Unit Tests (Future)
- `MaintenanceSchedulerTest`: Test date calculations
- `MaintenanceItemTest`: Test serialization
- `StorageTest`: Test file I/O

### Integration Tests
- End-to-end CLI workflow
- End-to-end GUI workflow
- Data persistence verification

### Manual Testing
- Current approach: Live testing with 600+ records
- Verified: CLI, GUI, alerts, save/load

---

**Version**: 1.0.0  
**Last Updated**: October 28, 2025
