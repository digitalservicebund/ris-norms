SAXON_HE="$HOME/Downloads/SaxonHE12-7J/saxon-he-12.7.jar"

FOLDER_OLD="../1.7.2/fixtures"
FOLDER_NEW="../1.8/fixtures"

# FOLDER_OLD="../1.7.2/schema-extension-fixtures"
# FOLDER_NEW="../1.8/schema-extension-fixtures"

for filepath in $FOLDER_OLD/**/*.xml; do
  new_filepath=${filepath/$FOLDER_OLD/$FOLDER_NEW}
  filename="${filepath##*/}"
  create_rechtsetzungsdokument=false

  if [[ ${filename%-*} != "rechtsetzungsdokument" ]]; then
    if [[ ${filename%-*} == "regelungstext" || ${filename%-*} == "offenestruktur"  || ${filename%-*} == "bekanntmachungstext" ]]; then
      if [[ ! -e "${filepath/$filename/rechtsetzungsdokument-1.xml}" ]]; then
        create_rechtsetzungsdokument=true
      fi
    else
      mkdir -p "${new_filepath/.xml/}"
      new_filepath="${new_filepath/.xml//regelungstext-1.xml}"
      create_rechtsetzungsdokument=true
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
  mv "$filepath.tmp2" "$new_filepath"

  if [[ "$create_rechtsetzungsdokument" = true  ]]; then
      echo "TODO: create rechtsetzungsdokument"
  fi
done

