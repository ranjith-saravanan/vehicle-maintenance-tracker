module oops_project_.java {
    requires java.base;
    requires java.desktop;
    requires java.logging;
    
    exports com.example.maintenance;
    exports com.example.maintenance.model;
    exports com.example.maintenance.core;
    exports com.example.maintenance.io;
    exports com.example.maintenance.cli;
    exports com.example.maintenance.gui;
}