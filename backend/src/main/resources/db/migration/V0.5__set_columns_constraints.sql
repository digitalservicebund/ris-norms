-- amending_law
ALTER TABLE amending_law
    ALTER COLUMN title SET NOT NULL,
    ALTER COLUMN publication_date SET NOT NULL,
    ALTER COLUMN digital_announcement_medium TYPE VARCHAR(15);

-- article
ALTER TABLE article
    ALTER COLUMN amending_law_id SET NOT NULL,
    ALTER COLUMN target_law_id SET NOT NULL;

-- target_law
ALTER TABLE target_law
    ALTER COLUMN title SET NOT NULL;
