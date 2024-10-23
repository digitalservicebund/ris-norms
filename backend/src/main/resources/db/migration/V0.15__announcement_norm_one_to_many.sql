ALTER TABLE announcements
    DROP CONSTRAINT announcements_eli_fkey;

-- Change eli column to contain expression instead of manifestation elis
UPDATE announcements announcement SET eli = (SELECT norms.eli_expression FROM norms WHERE eli_manifestation = announcement.eli);
