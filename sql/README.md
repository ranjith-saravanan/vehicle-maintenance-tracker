SQL schema and import guide for Vehicle Maintenance Tracker

Files:
- schema_postgres.sql  — PostgreSQL schema + view + import examples
- schema_mysql.sql     — MySQL schema + generated column + import examples

How to use
1. Pick the SQL file matching your database (Postgres or MySQL).
2. Create a database (e.g. `vehicle_maintenance`) and run the schema file.

Postgres example:
```bash
createdb vehicle_maintenance
psql -d vehicle_maintenance -f sql/schema_postgres.sql
\copy maintenance_items(id,name,last_service_date,interval_days) FROM 'data/items.csv' WITH (FORMAT csv, HEADER false)
```

MySQL example:
```sql
CREATE DATABASE vehicle_maintenance CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE vehicle_maintenance;
SOURCE sql/schema_mysql.sql;
-- from shell (client-side):
mysql --local-infile=1 -u root -p vehicle_maintenance -e "LOAD DATA LOCAL INFILE 'data/items.csv' INTO TABLE maintenance_items FIELDS TERMINATED BY ',' (id,name,last_service_date,interval_days);"
```

CSV format expected:
- Columns order: id, name, last_service_date, interval_days
- Date format: YYYY-MM-DD (ISO)
- No header (if your CSV has a header, use the COPY/\copy/LOAD DATA options to skip it)

Useful queries
- List items due/overdue:
  SELECT * FROM maintenance_with_next WHERE days_until_next <= 7 ORDER BY days_until_next;

- Mark an item as serviced (update last_service_date):
  UPDATE maintenance_items SET last_service_date = '2025-10-28', updated_at = now() WHERE id = 'veh-1';

- Compute next service date on the fly (if you don't use the view or generated column):
  SELECT id, name, (last_service_date + (interval '1 day' * interval_days))::date AS next_service_date FROM maintenance_items;

Notes
- The project currently stores CSV data at `data/items.csv`. You can import that into the DB to migrate from file-based storage to a proper relational backend.
- If you'd like, I can add a simple Java DAO layer using JDBC and a `config/database.properties` to let the app read/write from a database instead of CSV. Say the word and I'll implement it.
