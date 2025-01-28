CREATE TABLE norm_expression
(
    eli_norm_expression text PRIMARY KEY
);
INSERT INTO norm_expression
SELECT DISTINCT eli_norm_expression
FROM dokumente;

CREATE TABLE norm_manifestation
(
    eli_norm_manifestation text PRIMARY KEY,
    eli_norm_expression    text not null
        CONSTRAINT fk_norm_expression REFERENCES norm_expression
);
INSERT INTO norm_manifestation
SELECT DISTINCT eli_norm_manifestation, eli_norm_expression
FROM dokumente;

ALTER TABLE announcements
    ADD CONSTRAINT fk_norm_expression FOREIGN KEY (eli) REFERENCES norm_expression;

ALTER TABLE dokumente
    ADD CONSTRAINT fk_norm_manifestation FOREIGN KEY (eli_norm_manifestation) REFERENCES norm_manifestation;

CREATE OR REPLACE FUNCTION update_eli_tables()
    RETURNS trigger AS
$$
BEGIN
    INSERT INTO norm_expression
    VALUES (
               -- see definition of dokumente.eli_norm_manifestation
               ((xpath(
                       '//*[local-name()="act"]/*[local-name()="meta"]/*[local-name()="identification"]/*[local-name()="FRBRExpression"]/*[local-name()="FRBRuri"]/@value'::text,
                       NEW.xml))[1])::text)
    ON CONFLICT DO NOTHING;
    INSERT INTO norm_manifestation
    VALUES (
               -- see definition of dokumente.eli_norm_manifestation
               (regexp_replace(((xpath(
                       '(//*[local-name()="act"]/*[local-name()="meta"]/*[local-name()="identification"]/*[local-name()="FRBRManifestation"]/*[local-name()="FRBRuri"]/@value)'::text,
                       NEW.xml))[1])::text, '/[^/]*$'::text, ''::text)),
               -- see definition of dokumente.eli_norm_expression
               ((xpath(
                       '//*[local-name()="act"]/*[local-name()="meta"]/*[local-name()="identification"]/*[local-name()="FRBRExpression"]/*[local-name()="FRBRuri"]/@value'::text,
                       NEW.xml))[1])::text)
    ON CONFLICT DO NOTHING;
    RETURN NEW;
END;
$$
    LANGUAGE 'plpgsql' VOLATILE;

CREATE OR REPLACE TRIGGER update_eli_tables
    BEFORE INSERT OR UPDATE OF eli_dokument_manifestation
    ON dokumente
    FOR EACH ROW
EXECUTE FUNCTION update_eli_tables(NEW);
