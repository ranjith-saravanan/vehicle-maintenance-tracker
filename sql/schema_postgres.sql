-- PostgreSQL schema for Vehicle Maintenance Tracker
-- Run with: psql -d yourdb -f sql/schema_postgres.sql

CREATE TABLE IF NOT EXISTS maintenance_items (
    id TEXT PRIMARY KEY,
    name TEXT NOT NULL,
    last_service_date DATE NOT NULL,
    interval_days INTEGER NOT NULL,
    created_at TIMESTAMPTZ DEFAULT now(),
    updated_at TIMESTAMPTZ DEFAULT now()
);

-- Optional generated column (Postgres 12+): next_service_date
-- If your Postgres version supports generated columns, uncomment the following and adapt if needed.
-- ALTER TABLE maintenance_items
--     ADD COLUMN next_service_date DATE GENERATED ALWAYS AS (
--         (last_service_date + (interval '1 day' * interval_days))::date
--     ) STORED;

-- Create an index on last_service_date to speed queries
CREATE INDEX IF NOT EXISTS idx_maintenance_last_service_date ON maintenance_items(last_service_date);

-- Helpful view that computes next service date and days until next service
CREATE OR REPLACE VIEW maintenance_with_next AS
SELECT
  id,
  name,
  last_service_date,
  interval_days,
  (last_service_date + (interval '1 day' * interval_days))::date AS next_service_date,
  ( (last_service_date + (interval '1 day' * interval_days))::date - CURRENT_DATE ) AS days_until_next
FROM maintenance_items;

-- Example query: upcoming due within 7 days or overdue
-- SELECT *, next_service_date, days_until_next FROM maintenance_with_next
-- WHERE days_until_next <= 7
-- ORDER BY days_until_next ASC;

-- Import from CSV (if your CSV lives on the DB server):
-- COPY maintenance_items(id,name,last_service_date,interval_days)
-- FROM '/path/to/data/items.csv' DELIMITER ',' CSV HEADER;

-- If the CSV has no header and matches columns order: id,name,last_service_date,interval_days
-- you can use: COPY maintenance_items FROM '/path/to/data/items.csv' DELIMITER ',' CSV;

-- Alternatively, use psql with client-side input:
-- \copy maintenance_items(id,name,last_service_date,interval_days) FROM 'data/items.csv' WITH (FORMAT csv, HEADER false);

-- Make sure dates in CSV are ISO format (YYYY-MM-DD) for easy import.
