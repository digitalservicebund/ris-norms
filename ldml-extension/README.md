# LDML.de Extensions

Our application uses custom schema files to extend LDML.de. This is necessary, for example, for adding custom metadata outside of the standard. We use LDML.de's `proprietary` tag for this, which is intended for adding such extensions in a standard-compatible way.

This folder contains those custom schema definition files, as well as some fixtures used to test the schemas.

## Getting started

The custom schemas depend on the schemas from LDML.de. These are included as a [submodule](https://git-scm.com/book/en/v2/Git-Tools-Submodules). To install the submodule, run:

```sh
git submodule init
git submodule update
```

You'll also need `xmllint`:

```sh
# homebrew
brew install libxml2

# linux
sudo apt-get install libxml2-utils
```

## Testing changes

After making changes to schemas, you can test them against the fixtures in `fixtures` by running `xmllint`, e.g.

```sh
xmllint --noout --schema fixtures/ldml1.6_ds_regelungstext.xsd fixtures/SaatG_regelungstext.xml
```

## Extensions

| Extension                               | Notes                                                                                                                                                                     |
| --------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `metadata.xsd`                          | Use the metadata.xsd schema for a custom `akn:proprierary` section to define metadata that is not included in the current LDML.de specification.                          |
| `fixtures/ldml1.6_ds_regelungstext.xsd` | The `fixtures/ldml1.6_ds_regelungstext.xsd` helps with testing by referencing all schema definitions used when validating a regelungstext. This is used for testing only. |
