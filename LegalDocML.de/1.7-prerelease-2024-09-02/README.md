# LegalDocML.de 1.6

## Folder structure

| Folder / File                                                 | description                                                                 |
|---------------------------------------------------------------|-----------------------------------------------------------------------------|
| `schema`                                                      | The official LegalDocML 1.7 schema files                                    |
| `schema-extension`                                            | Our extension of the schema for the `legalDocML.de_metadaten` block         |                                                                                          |
| `xslt`                                                        | XSLT files for converting 1.6 to 1.7 and to fix eids                        |                                                                                          |
| `legalDocML.de-risnorms-regelungstextverkuendungsfassung.xsd` | The schema file for a regelungstext that includes our custom metadata block |
| `fixtures`                                                    | Test files for our schema-extension                                         |
| `samples`                                                     | Sample LegalDocML.de 1.7 files                                              |

## XSLT for converting LegalDocML.de 1.6 to 1.7

The xslt folder contains multiple xsl files that support convert from LegalDocML.de 1.6 to 1.7.

### Local use

Install the latest Saxon-HE release from https://github.com/Saxonica/Saxon-HE/releases

Run like this (you need to adjust the folder with the files to convert and the path to the saxon installation)

```sh
for filename in /some/folder/with/ldml/files/*.xml; do
  cat "$filename" | java -jar /saxon-he-12.4.jar -s:- -xsl:./xslt/1.6-to-1.7.xsl | java -jar /saxon-he-12.4.jar -s:- -xsl:./xslt/fix-eids.xsl > "${filename}.tmp"
  mv "${filename}.tmp" "${filename}"
done
```

### fix-eids.xsl

Corrects all eids in the xml file and updates all the references to these within the same file

### 1.6-to-1.7.xsl

Converts a LegalDocML.de 1.6 document to LegalDocML.de 1.7. This does not include fixing the eids so this must be done
using `fix-eids.xsl` afterward.

## LDML.de Extension for custom metadata

TODO: not yet converted for LegalDocML.de 1.7

Our application uses a custom schema file to extend LDML.de. This is necessary for adding custom metadata outside the
standard. We use LDML.de's `proprietary` tag for this, which is intended for adding such extensions in a
standard-compatible way.

The `schema-extension` folder contains this custom schema definition file. The `fixtures` folder contains files used to
test the schema.

You'll need `xmllint` to test the schema-extenstion:

```sh
# homebrew
brew install libxml2

# linux
sudo apt-get install libxml2-utils
```

### Testing changes

After making changes to schemas, you can test them against the fixtures in `fixtures` by running `xmllint`, e.g.

```sh
xmllint --noout --schema legalDocML.de-risnorms-regelungstextverkuendungsfassung.xsd fixtures/SaatG_regelungstext.xml
```
