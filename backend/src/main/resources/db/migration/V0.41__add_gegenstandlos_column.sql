ALTER TABLE dokumente
    ADD COLUMN gegenstandlos boolean
        GENERATED ALWAYS AS (xpath_exists('//*[local-name()="legalDocML.de_metadaten"]/*[local-name()="gegenstandlos"]', xml)) STORED;
