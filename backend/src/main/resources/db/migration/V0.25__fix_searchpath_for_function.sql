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
    LANGUAGE 'plpgsql' VOLATILE SET search_path from current;
