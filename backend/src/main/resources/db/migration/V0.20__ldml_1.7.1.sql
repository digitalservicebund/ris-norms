-- This approach avoids declaring specific namespaces and uses local-name() for flexibility.

ALTER TABLE release_norms
    DROP CONSTRAINT fk_norm_eli_manifestation;
ALTER TABLE norms
    DROP COLUMN eli_manifestation;
ALTER TABLE norms
    ADD COLUMN eli_manifestation text GENERATED ALWAYS AS ((xpath(
            '//*[local-name()="act"]/*[local-name()="meta"]/*[local-name()="identification"]/*[local-name()="FRBRManifestation"]/*[local-name()="FRBRthis"]/@value',
            xml))[1]::text) STORED UNIQUE NOT NULL;
ALTER TABLE release_norms
    ADD CONSTRAINT fk_norm_eli_manifestation FOREIGN KEY (norm_eli_manifestation) REFERENCES norms (eli_manifestation);

ALTER TABLE norms
    DROP COLUMN eli_expression;
ALTER TABLE norms
    ADD COLUMN eli_expression text GENERATED ALWAYS AS ((xpath(
            '//*[local-name()="act"]/*[local-name()="meta"]/*[local-name()="identification"]/*[local-name()="FRBRExpression"]/*[local-name()="FRBRthis"]/@value',
            xml))[1]::text) STORED NOT NULL;

ALTER TABLE norms
    DROP COLUMN guid;
ALTER TABLE norms
    ADD COLUMN guid uuid GENERATED ALWAYS AS ((xpath(
            '//*[local-name()="act"]/*[local-name()="meta"]/*[local-name()="identification"]/*[local-name()="FRBRExpression"]/*[local-name()="FRBRalias"][@name="aktuelle-version-id"]/@value',
            xml))[1]::text::uuid) STORED NOT NULL;

ALTER TABLE norms
    DROP COLUMN eli_work;
ALTER TABLE norms
    ADD COLUMN eli_work text GENERATED ALWAYS AS ((xpath(
            '//*[local-name()="act"]/*[local-name()="meta"]/*[local-name()="identification"]/*[local-name()="FRBRWork"]/*[local-name()="FRBRthis"]/@value',
            xml))[1]::text) STORED NOT NULL;
