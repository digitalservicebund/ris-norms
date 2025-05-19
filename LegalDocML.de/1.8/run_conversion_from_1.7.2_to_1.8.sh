SAXON_HE="$HOME/Downloads/SaxonHE12-7J/saxon-he-12.7.jar"

for filename in ../1.7.2/fixtures/**/*.xml; do
  echo "$filename -> ${filename/1.7.2/1.8}"
  cat "$filename" | java -jar $SAXON_HE -s:- -xsl:./xslt/1.7.2-to-1.8.xslt | java -jar $SAXON_HE -s:- -xsl:./xslt/fix-eids.xslt > "${filename}.tmp"

  # Replace the string REPLACE_WITH_NEW_UUID with unique uuids, this is hard to do in xslt itself so we do it as a post-processing step
  while IFS= read -r line;
  do
    printf '%s\n' "${line/REPLACE_WITH_NEW_UUID/$(uuidgen | awk '{print tolower($0)}')}"
  done < "${filename}.tmp" > "${filename}.tmp2"

  rm "${filename}.tmp"

  mv "${filename}.tmp2" "${filename/1.7.2/1.8}"
done
