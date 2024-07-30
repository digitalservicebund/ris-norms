# regex toolchain

run it with `node testLinks.js` and it spits out:
- the total regex as a combination of all bucket regexes
- the detection rate for each bucket
- the overall detection rate
- the links that have issues and were not recognized

If you find a new link that shall be covered do:
- add the link to the bucket txt file in the `links` directory
- (if needed) open the regarding regex101 tool to fine-tune the regex
- copy it over to the regex object
- run the tool to check if it works out
- copy the total regex to the application code
