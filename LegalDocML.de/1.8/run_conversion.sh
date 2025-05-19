for filename in ./fixtures/**/*.xml; do
  echo "$filename"
  cat "$filename" | java -jar ~/Downloads/SaxonHE12-7J/saxon-he-12.7.jar -s:- -xsl:./xslt/1.7.2-to-1.8.xslt > "${filename}.tmp"
  mv "${filename}.tmp" "${filename}"
done
