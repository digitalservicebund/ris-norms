SAXON_HE="$HOME/Downloads/SaxonHE12-7J/saxon-he-12.7.jar"
SCHEMATRON_XSL_FILE="../../backend/build/resources/main/LegalDocML.de/1.8.1/schema/legalDocML.de.xsl"

FOLDER="../../backend/src/test/resources"

for filepath in $FOLDER/**/*.xml; do
  echo "$filepath"
  cat "$filepath" | java -jar $SAXON_HE -s:- -xsl:./xslt/fix-meta-namespace.xslt > "$filepath.tmp"
  mv "$filepath.tmp" "$filepath"
done

