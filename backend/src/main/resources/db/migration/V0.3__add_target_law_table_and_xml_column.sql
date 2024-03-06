ALTER TABLE amending_law ADD COLUMN IF NOT EXISTS xml text NOT NULL DEFAULT '';

CREATE TABLE IF NOT EXISTS
    target_law
(
    id                              uuid                    NOT NULL PRIMARY KEY,
    eli                             character varying(255) NOT NULL,
    xml                             text                     NOT NULL
);

ALTER TABLE article RENAME COLUMN eli to eid;
ALTER TABLE article ADD COLUMN IF NOT EXISTS target_law_id uuid;

ALTER TABLE article ADD FOREIGN KEY (target_law_id) REFERENCES target_law(id);
