ALTER TABLE norms
    ALTER COLUMN xml TYPE xml USING xml::xml;

ALTER TABLE norms
    ADD COLUMN eli_manifestation text GENERATED ALWAYS AS ((xpath(
            '//akn:act/akn:meta/akn:identification/akn:FRBRManifestation/akn:FRBRthis/@value', xml,
            '{{akn,http://Inhaltsdaten.LegalDocML.de/1.7/}}'))[1]::text) STORED UNIQUE NOT NULL;

ALTER TABLE norms
    ADD COLUMN eli_expression text GENERATED ALWAYS AS ((xpath(
            '//akn:act/akn:meta/akn:identification/akn:FRBRExpression/akn:FRBRthis/@value', xml,
            '{{akn,http://Inhaltsdaten.LegalDocML.de/1.7/}}'))[1]::text) STORED UNIQUE NOT NULL;

ALTER TABLE announcements
    DROP CONSTRAINT announcements_eli_fkey;

ALTER TABLE announcements
    ADD FOREIGN KEY (eli) REFERENCES norms (eli_expression);

ALTER TABLE norms
    DROP COLUMN eli;

-- Change primary key from guid to a custom id as guid is not necessary unique
ALTER TABLE norms
    DROP CONSTRAINT norms_pkey;

ALTER TABLE norms
    ADD COLUMN id uuid PRIMARY KEY DEFAULT gen_random_uuid();
