ALTER TABLE norms
    DROP COLUMN guid;

ALTER TABLE norms
    ADD COLUMN guid uuid GENERATED ALWAYS AS ((xpath(
            '//akn:act/akn:meta/akn:identification/akn:FRBRExpression/akn:FRBRalias[@name="aktuelle-version-id"]/@value',
            xml,
            '{{akn,http://Inhaltsdaten.LegalDocML.de/1.7/}}'))[1]::text::uuid) STORED NOT NULL;
