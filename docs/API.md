# API Documentation

## Core Classes

### MaintenanceItem

Represents a single maintenance record.

#### Constructor

```java
// With auto-generated ID
public MaintenanceItem(String name, LocalDate lastServiceDate, int intervalDays)

// With explicit ID
public MaintenanceItem(String id, String name, LocalDate lastServiceDate, int intervalDays)
```

#### Methods

| Method | Return Type | Description |
|--------|-------------|-------------|
| `getId()` | String | Get unique identifier |
| `getName()` | String | Get item name |
| `getLastServiceDate()` | LocalDate | Get last service date |
| `getIntervalDays()` | int | Get interval in days |
| `toCsvLine()` | String | Serialize to CSV format |
| `fromCsvLine(String)` | MaintenanceItem | Deserialize from CSV (static) |

#### Example

```java
MaintenanceItem item = new MaintenanceItem(
    "Honda Civic",
    LocalDate.of(2024, 10, 1),
    180
);

String csv = item.toCsvLine();
// Output: "uuid,Honda Civic,2024-10-01,180"

MaintenanceItem loaded = MaintenanceItem.fromCsvLine(csv);
```

---

### MaintenanceScheduler

Handles maintenance scheduling calculations.

#### Methods

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `nextServiceDate` | MaintenanceItem | LocalDate | Calculate next service date |
| `daysUntilNext` | MaintenanceItem | long | Days until next service (negative if overdue) |
| `status` | MaintenanceItem | Status | Get maintenance status |

#### Status Enum

```java
public enum Status {
    OVERDUE,   // daysUntil < 0
    DUE_SOON,  // 0 <= daysUntil <= 7
    OK         // daysUntil > 7
}
```

#### Example

```java
MaintenanceScheduler scheduler = new MaintenanceScheduler();
MaintenanceItem item = /* ... */;

LocalDate nextDate = scheduler.nextServiceDate(item);
long daysUntil = scheduler.daysUntilNext(item);
Status status = scheduler.status(item);

if (status == Status.OVERDUE) {
    System.out.println("Maintenance is overdue by " + Math.abs(daysUntil) + " days");
}
```

---

### Storage

Manages data persistence in CSV format.

#### Methods

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `load` | - | List&lt;MaintenanceItem&gt; | Load all items from disk |
| `save` | List&lt;MaintenanceItem&gt; | void | Save items to disk |
| `defaultPath` | - | Path | Get default storage path (static) |

#### CSV Format

```csv
id,name,lastServiceDate,intervalDays
veh-1,Honda Civic,2024-10-01,180
veh-2,Toyota Camry,2024-09-15,90
```

#### Example

```java
Storage storage = new Storage();

// Load existing data
List<MaintenanceItem> items = storage.load();

// Modify data
items.add(new MaintenanceItem("Ford Focus", LocalDate.now(), 120));

// Save changes
storage.save(items);
```

---

### CLI

Command-line interface for user interaction.

#### Constructor

```java
public CLI(Storage storage, List<MaintenanceItem> items)
```

#### Methods

| Method | Description |
|--------|-------------|
| `run()` | Start interactive CLI loop |
| `checkAlerts()` | Display overdue and due soon items |
| `addItem()` | Add new maintenance item |

#### Menu Options

1. **List all items** - Display all maintenance records
2. **Add item** - Create new maintenance record
3. **Show alerts** - Show overdue and due soon items
4. **Save & Exit** - Persist data and quit

#### Example Usage

```java
Storage storage = new Storage();
List<MaintenanceItem> items = storage.load();
CLI cli = new CLI(storage, items);
cli.run();
```

---

### MaintenanceGUI

Graphical Swing-based user interface.

#### Constructor

```java
public MaintenanceGUI()
```

#### Components

| Component | Type | Purpose |
|-----------|------|---------|
| Table | JTable | Display all maintenance items |
| Add Button | JButton | Open add item dialog |
| Delete Button | JButton | Remove selected item |
| Show Alerts | JButton | Display alerts dialog |
| Refresh Button | JButton | Reload data from disk |
| Save Button | JButton | Persist changes |
| Status Label | JLabel | Show operation status |

#### Table Columns

1. ID
2. Name
3. Last Service
4. Interval (days)
5. Next Service
6. Days Until
7. Status

#### Example

```java
// Launch GUI
SwingUtilities.invokeLater(() -> {
    MaintenanceGUI gui = new MaintenanceGUI();
    gui.setVisible(true);
});

// Or use main method
public static void main(String[] args) {
    MaintenanceGUI.main(args);
}
```

---

## Usage Examples

### Example 1: Basic CRUD Operations

```java
// Create storage and load data
Storage storage = new Storage();
List<MaintenanceItem> items = storage.load();

// Add new item
MaintenanceItem newItem = new MaintenanceItem(
    "Honda Accord",
    LocalDate.of(2024, 9, 15),
    120
);
items.add(newItem);

// Update existing item
MaintenanceItem existing = items.get(0);
// Note: MaintenanceItem is immutable, create new instance
MaintenanceItem updated = new MaintenanceItem(
    existing.getId(),
    existing.getName(),
    LocalDate.now(), // Updated last service date
    existing.getIntervalDays()
);
items.set(0, updated);

// Delete item
items.removeIf(item -> item.getId().equals("veh-1"));

// Save changes
storage.save(items);
```

### Example 2: Alert System

```java
MaintenanceScheduler scheduler = new MaintenanceScheduler();
List<MaintenanceItem> items = storage.load();

List<MaintenanceItem> overdue = new ArrayList<>();
List<MaintenanceItem> dueSoon = new ArrayList<>();

for (MaintenanceItem item : items) {
    Status status = scheduler.status(item);
    
    if (status == Status.OVERDUE) {
        overdue.add(item);
    } else if (status == Status.DUE_SOON) {
        dueSoon.add(item);
    }
}

// Sort overdue by urgency (most overdue first)
overdue.sort(Comparator.comparingLong(scheduler::daysUntilNext));

System.out.println("🔴 OVERDUE: " + overdue.size());
for (MaintenanceItem item : overdue) {
    long days = Math.abs(scheduler.daysUntilNext(item));
    System.out.println("  - " + item.getName() + " (" + days + " days overdue)");
}
```

### Example 3: Custom Date Formatting

```java
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");

MaintenanceItem item = items.get(0);
String formattedDate = item.getLastServiceDate().format(formatter);
System.out.println("Last service: " + formattedDate);
// Output: "Last service: Oct 01, 2024"
```

---

## Error Handling

### CSV Parsing Errors

```java
try {
    MaintenanceItem item = MaintenanceItem.fromCsvLine(line);
} catch (Exception e) {
    System.err.println("Malformed line: " + line);
    // Line is skipped, processing continues
}
```

### File I/O Errors

```java
try {
    storage.save(items);
} catch (IOException e) {
    System.err.println("Failed to save: " + e.getMessage());
    // Handle error appropriately
}
```

### Date Parsing Errors

```java
try {
    LocalDate date = LocalDate.parse(dateString);
} catch (DateTimeParseException e) {
    System.err.println("Invalid date format. Use YYYY-MM-DD");
}
```

---

## Configuration

### Default Paths

| Resource | Path |
|----------|------|
| Data File | `data/items.csv` |
| Compiled Classes | `out/` |
| Source Code | `src/main/java/` |

### Date Format

All dates use ISO-8601 format: `YYYY-MM-DD`

Example: `2024-10-28`

---

**Version**: 1.0.0  
**Last Updated**: October 28, 2025
