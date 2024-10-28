-- If multiple releases happen on the same date the old release needs to be removed from the table, but as it never will be actually released I think that's ok. Otherwise the release_norms relation can not use the UNIQUE constraint on norm_eli_manifestation, and we could have multiple releases of the same norm.
CREATE TABLE IF NOT EXISTS
    releases
(
    id uuid NOT NULL PRIMARY KEY,
    released_at timestamp with time zone
);

CREATE TABLE IF NOT EXISTS
    release_norms
(
    release_id uuid NOT NULL,
    norm_eli_manifestation text NOT NULL UNIQUE
);

ALTER TABLE  release_norms ADD CONSTRAINT fk_release_id
    FOREIGN KEY (release_id)
        REFERENCES releases (id);

ALTER TABLE  release_norms ADD CONSTRAINT fk_norm_eli_manifestation
    FOREIGN KEY (norm_eli_manifestation)
        REFERENCES norms (eli_manifestation);

CREATE TABLE IF NOT EXISTS
    announcement_releases
(
    announcement_id uuid NOT NULL,
    release_id uuid NOT NULL UNIQUE
);

ALTER TABLE  announcement_releases ADD CONSTRAINT fk_announcement_id
    FOREIGN KEY (announcement_id)
        REFERENCES announcements (id);

ALTER TABLE  announcement_releases ADD CONSTRAINT fk_release_id
    FOREIGN KEY (release_id)
        REFERENCES releases (id);
