# Screenshots

## GUI Interface

### Main Window
The main GUI window displays all maintenance records in a sortable table with color-coded status indicators.

![Main Window](docs/images/main-window.png)

**Features:**
- 🔴 Red: Overdue maintenance
- 🟠 Orange: Due within 7 days
- 🟢 Green: OK status
- 7-column table: ID, Name, Last Service, Interval, Next Service, Days Until, Status

### Add Item Dialog
Modal dialog for adding new maintenance records with validation.

![Add Item](docs/images/add-item-dialog.png)

**Fields:**
- Vehicle Name
- Last Service Date (YYYY-MM-DD)
- Interval in Days

### Alerts Dialog
HTML-formatted alert view showing overdue and upcoming maintenance sorted by priority.

![Alerts](docs/images/alerts-dialog.png)

**Sections:**
- 🔴 Overdue (most critical first)
- 🟠 Due Soon (next 7 days)

## CLI Interface

### Main Menu
Interactive command-line menu with 4 options.

```
=== Maintenance Tracker ===
1. List all items
2. Add item
3. Show alerts
4. Save & Exit
```

### List View
Displays all maintenance items with calculated next service dates.

```
ID: veh-1
Name: Honda Civic
Last Service: 2024-10-01
Interval: 180 days
Next Service: 2025-03-30
Days Until Next: 153
Status: OK
---
```

### Alert System
Shows color-coded alerts in the console.

```
🔴 OVERDUE:
- veh-15: Toyota Corolla (45 days overdue)
- veh-23: Ford Focus (32 days overdue)

🟠 DUE SOON:
- veh-8: Honda Accord (3 days remaining)
- veh-12: Nissan Altima (6 days remaining)
```

## Demo Data

The project supports bulk management with 600+ vehicle records for testing and demonstration purposes.

---

*Note: Screenshot images should be placed in `docs/images/` directory*
