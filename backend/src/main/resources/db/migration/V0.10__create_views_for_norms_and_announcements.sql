CREATE TABLE IF NOT EXISTS
    announcements
(
    id                           uuid NOT NULL PRIMARY KEY,
    eli                          text NOT NULL UNIQUE,
    released_by_documentalist_at timestamp with time zone
);

CREATE TABLE IF NOT EXISTS
    norms
(
    guid uuid NOT NULL PRIMARY KEY,
    eli  text NOT NULL UNIQUE,
    xml  text
);

ALTER TABLE announcements
    ADD FOREIGN KEY (eli) REFERENCES norms (eli)
