ALTER TABLE dokumente ADD COLUMN subtype TEXT GENERATED ALWAYS AS ( (xpath('//*[local-name()="act" or local-name()="doc"]/@name', xml))[1]::text ) STORED NOT NULL;

/* Change the xpaths for the elis and guid to not include the akn:act part as for offenestrukturen it is akn:doc.
   PSQL 14 does not support changing the expression for a GENERATED column so we need to drop and recreate them. */

ALTER TABLE dokumente DROP CONSTRAINT fk_norm_manifestation;
ALTER TABLE dokumente DROP COLUMN eli_norm_manifestation;
ALTER TABLE dokumente
    ADD COLUMN eli_norm_manifestation text GENERATED ALWAYS AS (regexp_replace((xpath(
            '(//*[local-name()="meta"]/*[local-name()="identification"]/*[local-name()="FRBRManifestation"]/*[local-name()="FRBRuri"]/@value)',
            xml))[1]::text, '/[^/]*$', '')) STORED NOT NULL;
ALTER TABLE dokumente
    ADD CONSTRAINT fk_norm_manifestation FOREIGN KEY (eli_norm_manifestation) REFERENCES norm_manifestation;

ALTER TABLE dokumente DROP COLUMN eli_norm_expression;
ALTER TABLE dokumente
    ADD COLUMN eli_norm_expression text GENERATED ALWAYS AS ((xpath(
            '//*[local-name()="meta"]/*[local-name()="identification"]/*[local-name()="FRBRExpression"]/*[local-name()="FRBRuri"]/@value',
            xml))[1]::text) STORED NOT NULL;

ALTER TABLE dokumente DROP COLUMN eli_norm_work;
ALTER TABLE dokumente
    ADD COLUMN eli_norm_work text GENERATED ALWAYS AS ((xpath(
            '//*[local-name()="meta"]/*[local-name()="identification"]/*[local-name()="FRBRWork"]/*[local-name()="FRBRuri"]/@value',
            xml))[1]::text) STORED NOT NULL;


DROP TRIGGER update_eli_tables ON dokumente;
ALTER TABLE dokumente
    DROP COLUMN eli_dokument_manifestation;
ALTER TABLE dokumente
    ADD COLUMN eli_dokument_manifestation text GENERATED ALWAYS AS ((xpath(
            '//*[local-name()="meta"]/*[local-name()="identification"]/*[local-name()="FRBRManifestation"]/*[local-name()="FRBRthis"]/@value',
            xml))[1]::text) STORED UNIQUE NOT NULL;
CREATE TRIGGER update_eli_tables
    BEFORE INSERT OR UPDATE OF eli_dokument_manifestation
    ON dokumente
    FOR EACH ROW
EXECUTE FUNCTION update_eli_tables(NEW);

ALTER TABLE dokumente
    DROP COLUMN eli_dokument_expression;
ALTER TABLE dokumente
    ADD COLUMN eli_dokument_expression text GENERATED ALWAYS AS ((xpath(
            '//*[local-name()="meta"]/*[local-name()="identification"]/*[local-name()="FRBRExpression"]/*[local-name()="FRBRthis"]/@value',
            xml))[1]::text) STORED NOT NULL;

ALTER TABLE dokumente
    DROP COLUMN guid;
ALTER TABLE dokumente
    ADD COLUMN guid uuid GENERATED ALWAYS AS ((xpath(
            '//*[local-name()="meta"]/*[local-name()="identification"]/*[local-name()="FRBRExpression"]/*[local-name()="FRBRalias"][@name="aktuelle-version-id"]/@value',
            xml))[1]::text::uuid) STORED NOT NULL;

ALTER TABLE dokumente
    DROP COLUMN eli_dokument_work;
ALTER TABLE dokumente
    ADD COLUMN eli_dokument_work text GENERATED ALWAYS AS ((xpath(
            '//*[local-name()="meta"]/*[local-name()="identification"]/*[local-name()="FRBRWork"]/*[local-name()="FRBRthis"]/@value',
            xml))[1]::text) STORED NOT NULL;

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
    INSERT INTO norm_manifestation (eli_norm_manifestation, eli_norm_expression, expression_aktuelle_version_id)
    VALUES (
               -- see definition of dokumente.eli_norm_manifestation
               (regexp_replace(((xpath(
                       '(//*[local-name()="meta"]/*[local-name()="identification"]/*[local-name()="FRBRManifestation"]/*[local-name()="FRBRuri"]/@value)'::text,
                       NEW.xml))[1])::text, '/[^/]*$'::text, ''::text)),
               -- see definition of dokumente.eli_norm_expression
               ((xpath(
                       '//*[local-name()="meta"]/*[local-name()="identification"]/*[local-name()="FRBRExpression"]/*[local-name()="FRBRuri"]/@value'::text,
                       NEW.xml))[1])::text,
               ((xpath(
                       '//*[local-name()="meta"]/*[local-name()="identification"]/*[local-name()="FRBRExpression"]/*[local-name()="FRBRalias"][@name="aktuelle-version-id"]/@value',
                       NEW.xml))[1])::text::uuid)
    ON CONFLICT DO NOTHING;
    RETURN NEW;
END;
$$
    LANGUAGE 'plpgsql' VOLATILE SET search_path from current;
