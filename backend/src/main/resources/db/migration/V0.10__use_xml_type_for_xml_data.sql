-- amending law

ALTER TABLE amending_law ADD COLUMN xml_temp xml; -- create temporary column with XML data type
UPDATE amending_law SET xml_temp=XMLPARSE(DOCUMENT xml);  -- fill with XML values

ALTER TABLE amending_law DROP COLUMN xml; -- remove old column

ALTER TABLE amending_law ADD COLUMN xml xml; -- new column with XML data type
UPDATE amending_law SET xml=xml_temp; -- fill with XML values
ALTER TABLE amending_law ALTER COLUMN xml SET NOT NULL; -- enforce NOT NULL

ALTER TABLE amending_law DROP COLUMN xml_temp; -- remove temporary column

-- target law

ALTER TABLE target_law ADD COLUMN xml_temp xml; -- create temporary column with XML data type
UPDATE target_law SET xml_temp=XMLPARSE(DOCUMENT xml);  -- fill with XML values

ALTER TABLE target_law DROP COLUMN xml; -- remove old column

ALTER TABLE target_law ADD COLUMN xml xml; -- new column with XML data type
UPDATE target_law SET xml=xml_temp; -- fill with XML values
ALTER TABLE target_law ALTER COLUMN xml SET NOT NULL; -- enforce NOT NULL

ALTER TABLE target_law DROP COLUMN xml_temp; -- remove temporary column
