-- New column for counting also binary files migrated
ALTER TABLE migration_log ADD COLUMN binary_size INT NOT NULL DEFAULT 0;

-- Also rename the old `size` column
ALTER TABLE migration_log RENAME COLUMN size TO xml_size;
