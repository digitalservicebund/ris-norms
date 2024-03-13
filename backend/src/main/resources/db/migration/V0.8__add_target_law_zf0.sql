ALTER TABLE article ADD COLUMN IF NOT EXISTS target_law_zf0_id uuid NOT NULL;
ALTER TABLE article ADD FOREIGN KEY (target_law_zf0_id) REFERENCES target_law(id);