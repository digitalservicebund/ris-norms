# 17. Test files for norms

**Date**: 2025-03-20

## Status

Proposal

## Context

We currently have a lot of xml files and also xml-strings in use for backend unit and integration tests.

The xml-strings in tests create some challenges when adjusting them for new LegalDocMl.de versions as it is harder to automatically update them.

There are also some challenges with the xml files, mostly stemming from them being all thrown in a folder without much organization. This makes it hard to:
* know which xml-file(s) to use if one needs "just a norm"
* finding the correct related norms. E.g. the Zielnorm of a Änderungsgesetz
* figuring out the eli of a norm, as it is often needed in other parts of tests
* choosing multiple xml-file(s) for multiple norms as many xml-files use the same elis
* creating a norm that includes Offenestrukturen or binary attachments (all need to be identified and loaded individually and then combined)

Additionally, some tests files only include very minimal data that makes them schema-invalid, which creates challenges with using them in certain scenarios.

**We can not use real norms imported from the migration but need to use our own test norms**

## Decision

1. Xml-Strings may only be used for short snippets. Whole norms should always be loaded from a xml-file.
2. We organize the xml files in two categories of test-norms:
   1. General test norms
      1. can be used by any test, and should thus be "just" some norms that could happen to be in our system during normal use
      2. are stored in a folder structure according to their manifestation elis
      3. should be documented in a README.md file explaining what norm it is (e.g. "Änderungsgesetz for the Vereinsgesetz")
      4. should not be changed for adding edge-cases. But can be extended if some data is fitting for the norm and "just" missing (e.g. a new metadata field is added).
   2. Test specific test norms
      1. may only be used in tests for one class
      2. stored in the resource folder for the class in a folder with the class name. (e.g. for LdmlDeValidatorTest in `src/test/resources/de/bund/digitalservice/ris/norms/application/service/LdmlDeValidatorTest`)
      3. are given a name that explains what they are used for
3. All norm test files need to be schema-valid, unless specified in the name of the test file. All the general test norms must be schema-valid.

## Consequences

- Any of the general test norms can be used when needing "just a norm"
- If a specific kind of norm (e.g. a Änderungsgesetz) is needed the README.md shows if such a norm exists as a general test norm
- If none of the general test norms are useful for the specific test a new test specific norm should to be created
- All the different files related to one norm are in the same folder
  - this allows us to add a method to the Fixtures class to create a norm based on all files in a folder. We will add this method once we need it.
- The eli of a general test norm is always clearly visible as it is used within `Fixtures::loadNormFromDisk`
- All general test norms have unique elis
- All general test norms are schema valid
- For updating the LegalDocMl.de version all norms are available as xml files

