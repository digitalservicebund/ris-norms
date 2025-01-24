# LegalDocML.de 1.7.2

## Folder structure

| Folder / File                                                 | description                                                                 |
| ------------------------------------------------------------- | --------------------------------------------------------------------------- |
| `schema`                                                      | The official LegalDocML 1.7.2 schema files                                  |
| `schema-extension`                                            | Our extension of the schema for the `legalDocML.de_metadaten` block         |
| `legalDocML.de-risnorms-regelungstextverkuendungsfassung.xsd` | The schema file for a regelungstext that includes our custom metadata block |
| `fixtures`                                                    | Test files for our schema-extension                                         |
| `samples`                                                     | Sample LegalDocML.de 1.7.2 files                                            |

## LDML.de Extension for custom metadata

Our application uses a custom schema file to extend LDML.de. This is necessary for adding custom metadata outside the
standard. We use LDML.de's `proprietary` tag for this, which is intended for adding such extensions in a
standard-compatible way.

The `schema-extension` folder contains this custom schema definition file. The `fixtures` folder contains files used to
test the schema.

You'll need `xmllint` to test the schema-extension:

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
