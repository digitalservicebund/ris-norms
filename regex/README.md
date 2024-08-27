# Regular Expression Toolchain for Reference Detection
## Motivation and Context
When displaying an amending law, potential references should be highlighted and available for interaction. Documentalists could highlight them manually, but we'd like to support them with a semi-automated reference detection.
## Example
In the following paragraph the reference detection shall capture:

(1) Ordnungswidrig im Sinne des `§ 18 Absatz 2 Nummer 11 Buchstabe a des Seefischereigesetzes` handelt, wer gegen die `Verordnung (EG) Nr. 601/2004` des Rates vom 22. März 2004 zur [...]

To tackle this challenge we decided on setting up a powerful regular expression. But the regex grew very quickly due to the many situations that could appear. This is why we set up a small tool that merges different categories (buckets) of capturing groups together and injects recurrent patterns for easier maintenance to render a total regex.
## Process
When a certain case shall be captured, that is not yet handled, the idea is:
- to fine tune the regex with this tool
- run the tool to fetch the final regex
- insert the final regex [here](https://github.com/digitalservicebund/ris-norms/blob/main/backend/src/main/java/de/bund/digitalservice/ris/norms/application/service/ReferenceService.java#L131)
## Obstacles
The bitter pill of the current approach is, that one needs to check if the regex in Java code was altered in the meantime. Meaning if someone touched the regex there without changing it here, the next time a rendered regex will be inserted there, the changes will be overwritten.

Therefore:
- Before altering anything, render the regex and compare it with what is in the ReferenceService in the backend.
- If it's the same: proceed
- If not: Changes need to be reflected here at first before doing more changes.

## How to run
Run it with `node testLinks.js` and it spits out:
- the total regex as a combination of all bucket regexes
- the detection rate for each bucket
- the overall detection rate
- the links that have issues and were not recognized
## How to fine tune the regex
If you find a new reference case that shall be covered, do:
- add the reference to the bucket txt file in the `links` directory
- (if needed) open the regarding regex101 tool to fine-tune the regex
- copy it over to the regex object
- run the tool to check if it works
- copy the total regex to the application code in the ReferenceService
