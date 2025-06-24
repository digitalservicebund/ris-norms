SAXON_HE="$HOME/Downloads/SaxonHE12-7J/saxon-he-12.7.jar"
SCHEMATRON_XSL_FILE="../../backend/build/resources/main/LegalDocML.de/1.8.1/schema/legalDocML.de.xsl"

FOLDER="/Users/maltelaukoetter/projects/ris-norms/backend/src/test/resources/de/bund/digitalservice/ris/norms"

for filepath in $FOLDER/**/*.xml; do
  echo "$filepath"
  cat "$filepath" | java -jar $SAXON_HE -s:- -xsl:./xslt/convert-list-to-blocklist.xslt | java -jar $SAXON_HE -s:- -xsl:./xslt/fix-eids.xslt > "$filepath.tmp"

  # Replace the string REPLACE_WITH_NEW_UUID with unique uuids, this is hard to do in xslt itself so we do it as a post-processing step
  while IFS= read -r line;
  do
    printf '%s\n' "${line/REPLACE_WITH_NEW_UUID/$(uuidgen | awk '{print tolower($0)}')}"
  done < "$filepath.tmp" > "$filepath.tmp2"

  rm "$filepath.tmp"

  # just run it again to fix the formatting after adding GUIDs
  cat "$filepath.tmp2" | java -jar $SAXON_HE -s:- -xsl:./xslt/convert-list-to-blocklist.xslt > "$filepath"
  rm "$filepath.tmp2"

  filename=${filepath##*/}
  schema_file="unknown_file_type"

  if [[ ${filename%-*} = "rechtsetzungsdokument" ]]; then
    schema_file="legalDocML.de-risnorms-rechtsetzungsdokument.xsd"
  fi
  if [[ ${filename%-*} = "regelungstext-verkuendung" ]]; then
    schema_file="legalDocML.de-risnorms-regelungstextverkuendungsfassung.xsd"
  fi
  if [[ ${filename%-*} = "anlage-regelungstext" ]]; then
    schema_file="legalDocML.de-risnorms-offenestruktur.xsd"
  fi
  if [[ ${filename%-*} = "bekanntmachungstext" ]]; then
    schema_file="legalDocML.de-risnorms-bekanntmachung.xsd"
  fi

  #xmllint --noout --schema $schema_file $filepath
  #cat $filepath | java -jar $SAXON_HE -s:- -xsl:$SCHEMATRON_XSL_FILE | xpath -q -e "//svrl:failed-assert"
done

