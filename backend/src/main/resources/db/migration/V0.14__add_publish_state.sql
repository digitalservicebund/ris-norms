CREATE TYPE norm_publish_state AS ENUM ('published', 'unpublished', 'queued-for-publish');

ALTER TABLE norms
    ADD COLUMN publish_state norm_publish_state NOT NULL DEFAULT 'unpublished';

CREATE FUNCTION no_update_of_published_norms() RETURNS trigger
    LANGUAGE plpgsql AS
$$
BEGIN
    IF OLD.publish_state = 'published' THEN
        RAISE EXCEPTION 'editing published norms is not allowed';
    END IF;
    RETURN NEW;
END;
$$;

CREATE TRIGGER no_update_of_published_norms
    BEFORE UPDATE
    ON norms
    FOR EACH ROW
EXECUTE PROCEDURE no_update_of_published_norms();
