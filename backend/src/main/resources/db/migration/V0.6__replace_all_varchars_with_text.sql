-- amending_law
ALTER TABLE amending_law
    ALTER COLUMN eli TYPE TEXT,
    ALTER COLUMN print_announcement_gazette TYPE TEXT,
    ALTER COLUMN digital_announcement_medium TYPE TEXT,
    ALTER COLUMN print_announcement_page TYPE TEXT,
    ALTER COLUMN digital_announcement_edition TYPE TEXT,
    ALTER COLUMN title TYPE TEXT;

-- article
ALTER TABLE article
    ALTER COLUMN enumeration TYPE TEXT,
    ALTER COLUMN eid TYPE TEXT,
    ALTER COLUMN title TYPE TEXT;


-- target_law
ALTER TABLE target_law
    ALTER COLUMN eli TYPE TEXT,
    ALTER COLUMN title TYPE TEXT;

