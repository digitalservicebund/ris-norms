DROP INDEX idx_verkuendung_process_detail_process_id;

DROP TABLE verkuendung_import_process_detail;

ALTER TABLE verkuendung_import_processes ADD COLUMN details text;
