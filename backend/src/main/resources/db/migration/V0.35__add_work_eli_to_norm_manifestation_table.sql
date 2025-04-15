
ALTER TABLE norm_manifestation ADD COLUMN eli_norm_work text;

CREATE OR REPLACE FUNCTION update_eli_tables()
    RETURNS trigger AS
$$
BEGIN
    INSERT INTO norm_expression (eli_norm_expression)
    VALUES (
               -- see definition of dokumente.eli_norm_manifestation
               ((xpath(
                       '//*[local-name()="meta"]/*[local-name()="identification"]/*[local-name()="FRBRExpression"]/*[local-name()="FRBRuri"]/@value'::text,
                       NEW.xml))[1])::text)
    ON CONFLICT DO NOTHING;
    INSERT INTO norm_manifestation (eli_norm_manifestation, eli_norm_expression, eli_norm_work, expression_aktuelle_version_id)
    VALUES (
               -- see definition of dokumente.eli_norm_manifestation
               (regexp_replace(((xpath(
                       '(//*[local-name()="meta"]/*[local-name()="identification"]/*[local-name()="FRBRManifestation"]/*[local-name()="FRBRuri"]/@value)'::text,
                       NEW.xml))[1])::text, '/[^/]*$'::text, ''::text)),
               -- see definition of dokumente.eli_norm_expression
               ((xpath(
                       '//*[local-name()="meta"]/*[local-name()="identification"]/*[local-name()="FRBRExpression"]/*[local-name()="FRBRuri"]/@value'::text,
                       NEW.xml))[1])::text,
               -- see definition of dokumente.eli_norm_work
               ((xpath(
                       '//*[local-name()="meta"]/*[local-name()="identification"]/*[local-name()="FRBRWork"]/*[local-name()="FRBRuri"]/@value',
                       NEW.xml))[1])::text,
               ((xpath(
                       '//*[local-name()="meta"]/*[local-name()="identification"]/*[local-name()="FRBRExpression"]/*[local-name()="FRBRalias"][@name="aktuelle-version-id"]/@value',
                       NEW.xml))[1])::text::uuid)
    ON CONFLICT DO NOTHING;
    RETURN NEW;
END;
$$
    LANGUAGE 'plpgsql' VOLATILE SET search_path from current;

UPDATE norm_manifestation SET eli_norm_work = (SELECT dokumente.eli_norm_work FROM dokumente WHERE dokumente.eli_norm_manifestation = norm_manifestation.eli_norm_manifestation LIMIT 1);

ALTER TABLE norm_manifestation ALTER COLUMN eli_norm_work SET not null;
