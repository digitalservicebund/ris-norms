CREATE TABLE binary_files (
    content bytea NOT NULL,
    eli_dokument_manifestation text NOT NULL UNIQUE,
    eli_norm_manifestation text NOT NULL GENERATED ALWAYS AS ( regexp_replace(eli_dokument_manifestation, '/[^/]*$', '') ) STORED
);

ALTER TABLE binary_files
    ADD FOREIGN KEY (eli_norm_manifestation) REFERENCES norm_manifestation (eli_norm_manifestation)
