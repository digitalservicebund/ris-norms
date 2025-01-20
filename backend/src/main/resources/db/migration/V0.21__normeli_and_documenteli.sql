ALTER TABLE norms RENAME COLUMN eli_manifestation TO eli_dokument_manifestation;
-- For the FRBRManifestation the FRBRuri contains the subtype and format, this is different, than for FRBRExpression or FRBRWork. Therefore, we need to additionally remove those part from it.
ALTER TABLE norms
    ADD COLUMN eli_norm_manifestation text GENERATED ALWAYS AS (regexp_replace((xpath(
            '(//*[local-name()="act"]/*[local-name()="meta"]/*[local-name()="identification"]/*[local-name()="FRBRManifestation"]/*[local-name()="FRBRuri"]/@value)',
            xml))[1]::text, '/[^/]*$', '')) STORED NOT NULL;

ALTER TABLE norms RENAME COLUMN eli_expression TO eli_dokument_expression;
ALTER TABLE norms
    ADD COLUMN eli_norm_expression text GENERATED ALWAYS AS ((xpath(
            '//*[local-name()="act"]/*[local-name()="meta"]/*[local-name()="identification"]/*[local-name()="FRBRExpression"]/*[local-name()="FRBRuri"]/@value',
            xml))[1]::text) STORED NOT NULL;

ALTER TABLE norms RENAME COLUMN eli_work TO eli_dokument_work;
ALTER TABLE norms
    ADD COLUMN eli_norm_work text GENERATED ALWAYS AS ((xpath(
            '//*[local-name()="act"]/*[local-name()="meta"]/*[local-name()="identification"]/*[local-name()="FRBRWork"]/*[local-name()="FRBRuri"]/@value',
            xml))[1]::text) STORED NOT NULL;
