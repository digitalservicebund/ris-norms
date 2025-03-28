CREATE TYPE verkuendung_import_processing_status AS ENUM ('CREATED', 'PROCESSING', 'SUCCESS', 'ERROR');

CREATE TABLE IF NOT EXISTS verkuendung_import_processes (
    id UUID NOT NULL PRIMARY KEY,
    status verkuendung_import_processing_status NOT NULL DEFAULT 'CREATED',
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    started_at TIMESTAMPTZ,
    finished_at TIMESTAMPTZ
);

CREATE TABLE IF NOT EXISTS verkuendung_import_process_detail (
    id UUID NOT NULL PRIMARY KEY,
    process_id UUID REFERENCES verkuendung_import_processes(id),
    type VARCHAR(255),
    title VARCHAR(255),
    detail TEXT
);

CREATE INDEX idx_verkuendung_process_detail_process_id ON verkuendung_import_process_detail(process_id);
