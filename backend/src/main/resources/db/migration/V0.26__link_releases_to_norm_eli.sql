ALTER TABLE release_norms DROP CONSTRAINT fk_norm_eli_manifestation;

UPDATE release_norms SET norm_eli_manifestation = regexp_replace(norm_eli_manifestation, '/[^/]*$', '');

ALTER TABLE release_norms
    ADD CONSTRAINT fk_norm_manifestation FOREIGN KEY (norm_eli_manifestation) REFERENCES norm_manifestation;
