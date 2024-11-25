-- once we support psql 17 this can be solved simpler, see: https://dba.stackexchange.com/questions/250868/how-to-change-definition-of-a-postgres-generated-stored-column
ALTER TABLE release_norms DROP CONSTRAINT fk_norm_eli_manifestation;

ALTER TABLE norms DROP COLUMN eli_manifestation;

ALTER TABLE norms
    ADD COLUMN eli_manifestation text GENERATED ALWAYS AS ((xpath(
            '//akn:act/akn:meta/akn:identification/akn:FRBRManifestation/akn:FRBRthis/@value', xml,
            '{{akn,http://Inhaltsdaten.LegalDocML.de/1.7.1/}}'))[1]::text) STORED UNIQUE NOT NULL;

ALTER TABLE release_norms ADD CONSTRAINT fk_norm_eli_manifestation
    FOREIGN KEY (norm_eli_manifestation)
        REFERENCES norms (eli_manifestation);

ALTER TABLE norms DROP COLUMN eli_expression;

ALTER TABLE norms
    ADD COLUMN eli_expression text GENERATED ALWAYS AS ((xpath(
            '//akn:act/akn:meta/akn:identification/akn:FRBRExpression/akn:FRBRthis/@value', xml,
            '{{akn,http://Inhaltsdaten.LegalDocML.de/1.7.1/}}'))[1]::text) STORED NOT NULL;

ALTER TABLE norms DROP COLUMN guid;

ALTER TABLE norms
    ADD COLUMN guid uuid GENERATED ALWAYS AS ((xpath(
            '//akn:act/akn:meta/akn:identification/akn:FRBRExpression/akn:FRBRalias[@name="aktuelle-version-id"]/@value',
            xml,
            '{{akn,http://Inhaltsdaten.LegalDocML.de/1.7.1/}}'))[1]::text::uuid) STORED NOT NULL;

ALTER TABLE norms DROP COLUMN eli_work;

ALTER TABLE norms
    ADD COLUMN eli_work text GENERATED ALWAYS AS ((xpath(
            '//akn:act/akn:meta/akn:identification/akn:FRBRWork/akn:FRBRthis/@value', xml,
            '{{akn,http://Inhaltsdaten.LegalDocML.de/1.7.1/}}'))[1]::text) STORED NOT NULL;
