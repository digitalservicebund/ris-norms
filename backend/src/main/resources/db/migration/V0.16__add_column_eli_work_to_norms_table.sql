ALTER TABLE norms
    ADD COLUMN eli_work text GENERATED ALWAYS AS ((xpath(
            '//akn:act/akn:meta/akn:identification/akn:FRBRWork/akn:FRBRthis/@value', xml,
            '{{akn,http://Inhaltsdaten.LegalDocML.de/1.7/}}'))[1]::text) STORED NOT NULL;
