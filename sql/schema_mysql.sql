-- MySQL schema for Vehicle Maintenance Tracker
-- Run with: mysql -u user -p yourdb < sql/schema_mysql.sql

CREATE TABLE IF NOT EXISTS maintenance_items (
  id VARCHAR(128) PRIMARY KEY,
  name VARCHAR(512) NOT NULL,
  last_service_date DATE NOT NULL,
  interval_days INT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  -- computed/generated column for next service date (MySQL 5.7+)
  next_service_date DATE AS (DATE_ADD(last_service_date, INTERVAL interval_days DAY)) STORED
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX idx_maintenance_last_service_date ON maintenance_items(last_service_date);

-- Example query: items due within 7 days or overdue
-- SELECT id, name, last_service_date, interval_days, next_service_date,
--        DATEDIFF(next_service_date, CURRENT_DATE()) AS days_until_next
-- FROM maintenance_items
-- WHERE DATEDIFF(next_service_date, CURRENT_DATE()) <= 7
-- ORDER BY days_until_next ASC;

-- Import CSV (client-side) using LOAD DATA LOCAL INFILE:
-- LOAD DATA LOCAL INFILE 'data/items.csv'
-- INTO TABLE maintenance_items
-- FIELDS TERMINATED BY ','
-- OPTIONALLY ENCLOSED BY '"'
-- LINES TERMINATED BY '\n'
-- (id, name, last_service_date, interval_days);

-- If your MySQL server forbids LOCAL, copy the file to server and use LOAD DATA INFILE instead.
