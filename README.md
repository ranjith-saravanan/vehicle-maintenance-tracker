# 🚗 Vehicle Maintenance Tracker

A professional Java application for tracking vehicle maintenance schedules, calculating next service dates, and alerting users for upcoming or overdue maintenance tasks. Features both CLI and GUI interfaces for maximum flexibility.

[![Java](https://img.shields.io/badge/Java-11+-orange.svg)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Maven](https://img.shields.io/badge/Maven-Project-red.svg)](https://maven.apache.org/)

## ✨ Features

- **Dual Interface**: Choose between command-line (CLI) or graphical user interface (GUI)
- **Smart Scheduling**: Automatically calculates next service dates based on interval and last service
- **Status Tracking**: Visual indicators for maintenance status (Overdue, Due Soon, OK)
- **Alert System**: Proactive notifications for upcoming and overdue maintenance
- **CSV Persistence**: Simple, portable data storage format
- **Bulk Management**: Handle hundreds of vehicles efficiently
- **Color-Coded UI**: Intuitive visual feedback in GUI mode

## 🎯 Status Categories

| Status | Criteria | Color |
|--------|----------|-------|
| **OVERDUE** | Past due date | 🔴 Red |
| **DUE SOON** | Within 7 days | 🟠 Orange |
| **OK** | More than 7 days | 🟢 Green |

## 🚀 Quick Start

### Prerequisites

- Java 11 or higher
- Maven (optional, for building)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/vehicle-maintenance-tracker.git
   cd vehicle-maintenance-tracker
   ```

2. **Compile the project**
   ```bash
   javac -d out src/main/java/com/example/maintenance/**/*.java
   ```

   Or with Maven:
   ```bash
   mvn clean compile
   ```

### Running the Application

#### Option 1: GUI Mode (Recommended)
```bash
java -cp out com.example.maintenance.gui.MaintenanceGUI
```

#### Option 2: CLI Mode
```bash
java -cp out com.example.maintenance.Main
```

## 📖 Usage

### GUI Interface

The GUI provides an intuitive point-and-click interface:

1. **View All Records**: Table displays all maintenance items with status
2. **Add Item**: Click ➕ button to add new maintenance record
3. **Delete Item**: Select row and click 🗑️ to remove
4. **Show Alerts**: Click ⚠️ to view overdue and upcoming maintenance
5. **Save**: Click 💾 to persist changes to disk
6. **Refresh**: Click 🔄 to reload data from file

### CLI Interface

Interactive menu with the following options:

```
=== Maintenance Tracker ===
1. List all items
2. Add item
3. Show alerts
4. Save & Exit
```

### Data Format

Data is stored in `data/items.csv` with the following structure:

```csv
id,name,lastServiceDate,intervalDays
veh-1,Honda Civic,2024-10-01,180
veh-2,Toyota Camry,2024-09-15,90
```

## 🏗️ Project Structure

```
vehicle-maintenance-tracker/
├── src/main/java/com/example/maintenance/
│   ├── Main.java                    # CLI entry point
│   ├── cli/
│   │   └── CLI.java                # Command-line interface
│   ├── gui/
│   │   └── MaintenanceGUI.java     # Graphical user interface
│   ├── model/
│   │   └── MaintenanceItem.java    # Data model
│   ├── core/
│   │   └── MaintenanceScheduler.java # Business logic
│   └── io/
│       └── Storage.java            # CSV persistence
├── data/
│   └── items.csv                   # Data storage
├── pom.xml                         # Maven configuration
├── LICENSE                         # MIT License
└── README.md                       # This file
```

## 🔧 Technical Details

### Architecture

- **Model-View-Controller (MVC)**: Clean separation of concerns
- **Repository Pattern**: Abstracted data access layer
- **Java Swing**: Native GUI framework for cross-platform support
- **Java Time API**: Modern date/time handling with `LocalDate`

### Technologies

- **Language**: Java 11
- **Build Tool**: Maven
- **GUI Framework**: Java Swing
- **Date/Time**: Java 8 Time API
- **Storage**: CSV (text-based)

## 📊 Example Use Cases

- **Fleet Management**: Track maintenance for company vehicles
- **Personal Vehicles**: Monitor your car's service schedule
- **Equipment Maintenance**: Manage machinery servicing
- **Rental Services**: Track maintenance across rental fleet

## 🛠️ Development

### Building from Source

```bash
# Compile all classes
javac -d out src/main/java/com/example/maintenance/**/*.java

# Or use Maven
mvn clean package
```

### Running Tests

```bash
# Compile and run with Maven
mvn test
```

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👤 Author

**RANJITH S**

## 🙏 Acknowledgments

- Built with Java Swing for cross-platform compatibility
- Uses Java 8 Time API for robust date handling
- Inspired by the need for simple, effective maintenance tracking

## 📧 Support

For support, please open an issue in the GitHub repository.

---

**Made with ❤️ by RANJITH S**
