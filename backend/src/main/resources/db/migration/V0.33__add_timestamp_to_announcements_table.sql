ALTER TABLE announcements
  ADD COLUMN IF NOT EXISTS import_timestamp timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP;
