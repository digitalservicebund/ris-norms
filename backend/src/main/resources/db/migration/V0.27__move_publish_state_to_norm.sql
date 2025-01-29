ALTER TABLE norm_manifestation
    ADD COLUMN publish_state norm_publish_state NOT NULL DEFAULT 'UNPUBLISHED';

UPDATE norm_manifestation SET publish_state = dokumente.publish_state FROM dokumente WHERE dokumente.eli_norm_manifestation = norm_manifestation.eli_norm_manifestation;

ALTER TABLE dokumente DROP COLUMN publish_state;

ALTER TABLE norm_manifestation
    ADD COLUMN expression_aktuelle_version_id uuid;
UPDATE norm_manifestation SET expression_aktuelle_version_id = dokumente.guid FROM dokumente WHERE norm_manifestation.eli_norm_manifestation = dokumente.eli_norm_manifestation;
DELETE FROM norm_manifestation WHERE expression_aktuelle_version_id IS NULL;
ALTER TABLE norm_manifestation ALTER COLUMN expression_aktuelle_version_id SET NOT NULL;

CREATE OR REPLACE FUNCTION update_eli_tables()
    RETURNS trigger AS
$$
BEGIN
    INSERT INTO norm_expression (eli_norm_expression)
    VALUES (
               -- see definition of dokumente.eli_norm_manifestation
               ((xpath(
                       '//*[local-name()="act"]/*[local-name()="meta"]/*[local-name()="identification"]/*[local-name()="FRBRExpression"]/*[local-name()="FRBRuri"]/@value'::text,
                       NEW.xml))[1])::text)
    ON CONFLICT DO NOTHING;
    INSERT INTO norm_manifestation (eli_norm_manifestation, eli_norm_expression, expression_aktuelle_version_id)
    VALUES (
               -- see definition of dokumente.eli_norm_manifestation
               (regexp_replace(((xpath(
                       '(//*[local-name()="act"]/*[local-name()="meta"]/*[local-name()="identification"]/*[local-name()="FRBRManifestation"]/*[local-name()="FRBRuri"]/@value)'::text,
                       NEW.xml))[1])::text, '/[^/]*$'::text, ''::text)),
               -- see definition of dokumente.eli_norm_expression
               ((xpath(
                       '//*[local-name()="act"]/*[local-name()="meta"]/*[local-name()="identification"]/*[local-name()="FRBRExpression"]/*[local-name()="FRBRuri"]/@value'::text,
                       NEW.xml))[1])::text,
               ((xpath(
                       '//*[local-name()="act"]/*[local-name()="meta"]/*[local-name()="identification"]/*[local-name()="FRBRExpression"]/*[local-name()="FRBRalias"][@name="aktuelle-version-id"]/@value',
                       NEW.xml))[1])::text::uuid)
    ON CONFLICT DO NOTHING;
    RETURN NEW;
END;
$$
    LANGUAGE 'plpgsql' VOLATILE SET search_path from current;

/* It is now a lot harder to check that published norms are not modified on the database level. So we will not do it for now. */
DROP TRIGGER no_update_of_published_norms ON dokumente;
DROP FUNCTION thrown_on_update_of_published_norm;
