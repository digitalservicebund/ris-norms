SAXON_HE="$HOME/Downloads/SaxonHE12-7J/saxon-he-12.7.jar"
SCHEMATRON_XSL_FILE="./legalDocML.de.xsl"

FOLDER_OLD="../1.7.2/fixtures"
FOLDER_NEW="../1.8/fixtures"

# FOLDER_OLD="../1.7.2/schema-extension-fixtures"
# FOLDER_NEW="../1.8/schema-extension-fixtures"

# FOLDER_OLD="../1.7.2/samples"
# FOLDER_NEW="../1.8/samples"

for filepath in $FOLDER_OLD/**/*.xml; do
  new_filepath=${filepath/$FOLDER_OLD/$FOLDER_NEW}
  filename="${filepath##*/}"
  create_rechtsetzungsdokument=false

  if [[ ${filename%-*} != "rechtsetzungsdokument" ]]; then
    if [[ ! -e "${filepath/$filename/rechtsetzungsdokument-1.xml}" ]]; then
      create_rechtsetzungsdokument=true
    fi

    if [[ ${filename%-*} == "regelungstext" ]]; then
      new_filepath="${new_filepath/regelungstext/regelungstext-verkuendung}"
    elif [[ ${filename%-*} == "offenestruktur" ]]; then
      new_filepath="${new_filepath/offenestruktur/anlage-regelungstext}"
    elif [[ ${filename%-*} == "bekanntmachungstext" ]]; then
      new_filepath=$new_filepath
    else
      mkdir -p "${new_filepath/.xml/}"
      new_filepath="${new_filepath/.xml//regelungstext-verkuendung-1.xml}"
    fi
  fi

  echo "$filepath -> $new_filepath"
  cat "$filepath" | java -jar $SAXON_HE -s:- -xsl:./xslt/1.7.2-to-1.8.xslt | java -jar $SAXON_HE -s:- -xsl:./xslt/fix-eids.xslt > "$filepath.tmp"

  # Replace the string REPLACE_WITH_NEW_UUID with unique uuids, this is hard to do in xslt itself so we do it as a post-processing step
  while IFS= read -r line;
  do
    printf '%s\n' "${line/REPLACE_WITH_NEW_UUID/$(uuidgen | awk '{print tolower($0)}')}"
  done < "$filepath.tmp" > "$filepath.tmp2"

  rm "$filepath.tmp"
  mkdir -p "$(dirname "$new_filepath")"
  mv "$filepath.tmp2" "$new_filepath"

  new_filename=${new_filepath##*/}

  schema_file="unknown_file_type"

  if [[ ${new_filename%-*} = "rechtsetzungsdokument" ]]; then
    schema_file="legalDocML.de-risnorms-rechtsetzungsdokument.xsd"
  fi
  if [[ ${new_filename%-*} = "regelungstext-verkuendung" ]]; then
    schema_file="legalDocML.de-risnorms-regelungstextverkuendungsfassung.xsd"
  fi
  if [[ ${new_filename%-*} = "anlage-regelungstext" ]]; then
    schema_file="legalDocML.de-risnorms-offenestruktur.xsd"
  fi
  if [[ ${new_filename%-*} = "bekanntmachungstext" ]]; then
    schema_file="legalDocML.de-risnorms-bekanntmachung.xsd"
  fi

  if [[ "$create_rechtsetzungsdokument" = true  ]]; then
    filepath_rechtsetzungsdokument="${new_filepath/$new_filename/rechtsetzungsdokument-1.xml}"

    echo "Create Rechtsetzungsdokument $filepath_rechtsetzungsdokument"

    cat "$new_filepath" | java -jar $SAXON_HE -s:- -xsl:./xslt/create-rechtsetzungsdokument-from-regelungstext.xslt | java -jar $SAXON_HE -s:- -xsl:./xslt/fix-eids.xslt > "$filepath_rechtsetzungsdokument.tmp"

    # Replace the string REPLACE_WITH_NEW_UUID with unique uuids, this is hard to do in xslt itself so we do it as a post-processing step
    while IFS= read -r line;
    do
      printf '%s\n' "${line/REPLACE_WITH_NEW_UUID/$(uuidgen | awk '{print tolower($0)}')}"
    done < "$filepath_rechtsetzungsdokument.tmp" > "$filepath_rechtsetzungsdokument"

    rm "$filepath_rechtsetzungsdokument.tmp"

    xmllint --noout --schema legalDocML.de-risnorms-rechtsetzungsdokument.xsd $filepath_rechtsetzungsdokument
    cat $filepath_rechtsetzungsdokument | java -jar $SAXON_HE -s:- -xsl:$SCHEMATRON_XSL_FILE | xpath -q -e "//svrl:failed-assert"
  fi

  if [[ ${new_filename%-*} != "rechtsetzungsdokument" ]]; then
    cat "$new_filepath" | java -jar $SAXON_HE -s:- -xsl:./xslt/remove-rechtsetzungsdokument-metadata.xslt > "$new_filepath.tmp"
    mv "$new_filepath.tmp" "$new_filepath"
  fi;

  if [[ ${new_filename%-*} != "regelungstext-verkuendung" ]]; then
    cat "$new_filepath" | java -jar $SAXON_HE -s:- -xsl:./xslt/remove-regelungstext-metadata.xslt > "$new_filepath.tmp"
    mv "$new_filepath.tmp" "$new_filepath"
  fi;

  xmllint --noout --schema $schema_file $new_filepath
  cat $new_filepath | java -jar $SAXON_HE -s:- -xsl:$SCHEMATRON_XSL_FILE | xpath -q -e "//svrl:failed-assert"
done

