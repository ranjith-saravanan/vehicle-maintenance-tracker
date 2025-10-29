# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0] - 2025-10-28

### Added
- Initial release of Vehicle Maintenance Tracker
- Command-line interface (CLI) for maintenance management
- Graphical user interface (GUI) with Java Swing
- Core maintenance scheduling logic with status tracking
- CSV-based data persistence
- Alert system for overdue and upcoming maintenance
- Support for bulk vehicle management (600+ records)
- Color-coded status indicators (Red/Orange/Green)
- Add, delete, refresh, and save operations
- Modal dialogs for data entry
- Professional project structure with Maven support

### Features
- **CLI Mode**: Interactive menu-driven interface
- **GUI Mode**: Modern Swing-based graphical interface with JTable
- **Smart Scheduling**: Automatic next service date calculation
- **Status Categories**: OVERDUE, DUE_SOON, OK
- **Alert Dialog**: HTML-formatted maintenance alerts
- **CSV Storage**: Simple text-based data format for portability

### Technical
- Java 11 target compatibility
- Maven build system
- MVC architecture pattern
- Repository pattern for data access
- Java 8 Time API for date handling
- Exception handling and input validation

### Documentation
- Comprehensive README with badges
- MIT License
- Contributing guidelines
- Professional project structure

## [Unreleased]

### Planned
- Unit tests with JUnit
- Database support (SQLite, PostgreSQL)
- Export to PDF/Excel
- Email notifications
- Multi-user support
- REST API for mobile integration
- Dark mode theme
- Internationalization (i18n)

---

[1.0.0]: https://github.com/yourusername/vehicle-maintenance-tracker/releases/tag/v1.0.0
