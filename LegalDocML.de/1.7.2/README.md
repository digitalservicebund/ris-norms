# LegalDocML.de 1.7.2

This folder includes:

| Folder/File                                                   | Description                                                                 |
|---------------------------------------------------------------|-----------------------------------------------------------------------------|
| `fixtures`                                                    | LegalDocML.de 1.7.2 files used for testing (eg. in the backend unit tests)  |
| `schema`                                                      | The official LegalDocML 1.7.2 schema files                                  |
| `schema-extension`                                            | Our extension of the schema for the `legalDocML.de_metadaten` block         |
| `schema-extension-fixtures`                                   | Test files for our schema-extension                                         |
| `legalDocML.de-risnorms-regelungstextverkuendungsfassung.xsd` | The schema file for a regelungstext that includes our custom metadata block |
| `samples`                                                     | Sample LegalDocML.de 1.7.2 files                                            |

## LDML.de Extension for Custom Metadata

Our application uses a custom schema file to extend LDML.de. This is necessary for adding custom metadata outside the standard. We use LDML.de's `proprietary` tag for this, which is intended for adding such extensions in a standard-compatible way.

The `schema-extension` folder contains this custom schema definition file. The `fixtures` folder contains files used to test the schema.

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
xmllint --noout --schema legalDocML.de-risnorms-regelungstextverkuendungsfassung.xsd schema-extension-fixtures/SaatG_regelungstext.xml
```


### Creating ZIP archives

There is a `create-archives.sh` script in this directory that helps you package all `samples` subfolders into `.zip` files and optionally generate cryptographic signatures for them.

#### Usage

```bash
./create-archives.sh [PRIVATE_KEY_PATH]
```
PRIVATE_KEY_PATH: (optional) Path to an RSA private key in PEM format. If provided, the script will generate a .sig file for each .zip file.

#### What it does

For each subfolder in the current directory (e.g., samples/):
1. Creates a .zip archive with the same name as the folder.
2. If a private key is provided, it also generates a .sig signature file for the archive.

#### Example
Given this structure:
```samples/
├── folder-a/
│   └── file-a.xml
├── folder-b/
│   └── file-b.xml
├── create-archives.sh
├── private-key.pem
```
Running:
```bash
./create-archives.sh private-key.pem
```
Produces:
```folder-a.zip
folder-a.sig
folder-b.zip
folder-b.sig
```
#### Generating a test key pair
To create a self-signed RSA key pair, use the following command:
```bash
openssl req -x509 -newkey rsa:2048 -keyout private-key.pem -out certificate.pem -days 365 -nodes
```
This creates:
- `private-key.pem`: your private key (used to sign files)
- `certificate.pem`: your public certificate (used to verify signatures)

ℹ️ The script uses OpenSSL with SHA-256 to create signatures, and writes them in binary format.
