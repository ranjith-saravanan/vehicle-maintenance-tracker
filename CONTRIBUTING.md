# Contributing to Vehicle Maintenance Tracker

Thank you for your interest in contributing! We welcome contributions from the community.

## How to Contribute

### Reporting Bugs

If you find a bug, please open an issue with:
- A clear title and description
- Steps to reproduce the issue
- Expected vs actual behavior
- Your environment (OS, Java version)
- Screenshots if applicable

### Suggesting Features

We love new ideas! Please open an issue with:
- Clear description of the feature
- Use case and benefits
- Possible implementation approach

### Code Contributions

1. **Fork the Repository**
   ```bash
   git clone https://github.com/yourusername/vehicle-maintenance-tracker.git
   ```

2. **Create a Branch**
   ```bash
   git checkout -b feature/your-feature-name
   ```

3. **Make Your Changes**
   - Follow the existing code style
   - Add comments for complex logic
   - Update documentation if needed

4. **Test Your Changes**
   - Ensure the application compiles without errors
   - Test both CLI and GUI interfaces
   - Verify existing functionality still works

5. **Commit Your Changes**
   ```bash
   git add .
   git commit -m "Add: brief description of changes"
   ```

6. **Push and Create Pull Request**
   ```bash
   git push origin feature/your-feature-name
   ```

## Code Style Guidelines

- **Indentation**: 4 spaces (no tabs)
- **Naming**: 
  - Classes: `PascalCase`
  - Methods/Variables: `camelCase`
  - Constants: `UPPER_SNAKE_CASE`
- **Comments**: Use JavaDoc for public methods
- **Line Length**: Max 120 characters

## Development Setup

1. Install Java 11+
2. Clone the repository
3. Compile: `javac -d out src/main/java/com/example/maintenance/**/*.java`
4. Run: `java -cp out com.example.maintenance.gui.MaintenanceGUI`

## Testing Checklist

Before submitting a PR, please verify:
- [ ] Code compiles without errors or warnings
- [ ] GUI launches and displays correctly
- [ ] CLI mode works as expected
- [ ] Add/Delete operations function properly
- [ ] Alert system shows correct status
- [ ] Data persists correctly to CSV
- [ ] No regressions in existing features

## Questions?

Feel free to open an issue for any questions or clarifications.

Thank you for contributing! 🎉
