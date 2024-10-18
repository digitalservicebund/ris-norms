CREATE TYPE norm_publish_state AS ENUM ('PUBLISHED', 'UNPUBLISHED', 'QUEUED_FOR_PUBLISH');

ALTER TABLE norms
    ADD COLUMN publish_state norm_publish_state NOT NULL DEFAULT 'UNPUBLISHED';

CREATE FUNCTION thrown_on_update_of_published_norm() RETURNS trigger
    LANGUAGE plpgsql AS
$$
BEGIN
    IF OLD.publish_state = 'PUBLISHED' THEN
        RAISE EXCEPTION 'editing published norms is not allowed';
    END IF;
    RETURN NEW;
END;
$$;

CREATE TRIGGER no_update_of_published_norms
    BEFORE UPDATE
    ON norms
    FOR EACH ROW
EXECUTE PROCEDURE thrown_on_update_of_published_norm();
