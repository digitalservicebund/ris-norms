ALTER TABLE migration_log
  ADD COLUMN IF NOT EXISTS completed boolean NOT NULL DEFAULT FALSE;
  UPDATE migration_log SET completed = TRUE;
