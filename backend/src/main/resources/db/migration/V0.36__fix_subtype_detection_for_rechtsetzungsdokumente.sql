ALTER TABLE dokumente DROP COLUMN subtype;
ALTER TABLE dokumente ADD COLUMN subtype TEXT GENERATED ALWAYS AS ( (xpath('//*[local-name()="act" or local-name()="doc" or local-name()="documentCollection"]/@name', xml))[1]::text ) STORED NOT NULL;
