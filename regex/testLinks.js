const fs = require('fs');
const os = require('os');

const path = './links/';
const files = fs.readdirSync(path);

// alle: https://regex101.com/r/PYtKz9
/*
String raw:
If using the RegExp constructor with a string literal, remember that the backslash is an
escape in string literals, so to use it in the regular expression, you need to escape it
at the string literal level. /a\*b/ and new RegExp("a\\*b") create the same expression,
which searches for "a" followed by a literal "*" followed by "b".
*/

const RegRgbl = String.raw`\((?:R|B)GBl\.(?: \d+)? I(?:I)? (?:S\. \w+(?:, \d+)?(?:\; \d+ I S\. \d+)?|Nr\. \d+)\)`;
const RegDate = String.raw`vom \d+\. (?:.*?) \d+`;

const regexes = {

    // https://regex101.com/r/WeLPfj
    "anhang.txt": String.raw`^Anhang(?: \w+(?: Teil \w+(?: Buchstabe \w+(?: Unterabsatz \w+(?: Satz \w+)?)?)?)?)?$`,

    // https://regex101.com/r/qhCOO3
    "anlage.txt": String.raw`^Anlage(?: \w+)?(?:(?:.*?) ${RegDate})?(?: ${RegRgbl})?$`,

    // https://regex101.com/r/EdV0GC
    "artikel-der-verordnung.txt": String.raw`^Artikel(?:s)? \w+(?: Nummer \w+(?: Absatz \w+)?)? der Verordnung ${RegDate} ${RegRgbl}$`,

    // https://regex101.com/r/ErsHLb
    "artikel-des-gesetzes.txt": String.raw`^Artikel(?:s)? \w+(?: Nummer \w+(?: (?:Abs(?:atz|.)?) \w+)?)?(?: (?:Abs(?:atz|.)?) \w+(?: Satz \d+(?: und \d+)?)?)? des (?:G|(?:.*?)g)esetzes(?: ${RegDate} ${RegRgbl})?$`,

    // https://regex101.com/r/8HrdSC
    "artikel.txt": String.raw`^Artikel \w+(?: (?:Abs(?:atz|.)?) \d+(?: (?:und|oder) \d+)?(?: (?:Unterabs|S)atz \d+(?: (?:und|oder) \d+)?(?: [a-zA-Z]+ Gedankenstrich)?| Buchstabe \w+(?: Satz \w+)?)?| Buchstabe \w+| Nummer \d+(?: Satz \d+)?)?$`,

    // https://regex101.com/r/rZNSDf
    "para.txt": String.raw`^(?<!§)§ \w+(?: (?:(?:Abs(?:atz|.) (?:\w+(?: (?:und|bis|oder) \w+)?)(?: Satz (?:\w+(?: (?:und|bis|oder) \w+)?)(?: (?:Nummer|Nr.) (?:\w+(?: (?:und|bis|oder) \w+)?)(?: Buchstabe \w+(?: Satz \w+)?| Satz \w+(?: Buchstabe \w+)?)?| Buchstabe \w+)?| (?:Nummer|Nr.) (?:\w+(?: (?:und|bis|oder) \w+)?)(?: Satz (?:\w+(?: (?:und|bis|oder) \w+)?)(?: (?:Nummer|Nr.) \w+| Buchstabe \w+)?| Buchstabe \w+(?: Satz \w+| und \w+)?)?| Buchstabe \w+)?)|Nummer \w+(?: Satz \w+(?: und \w+| Buchstabe \w+(?: Satz \w+)?)?)?|Satz \w+)| in der Fassung der Bekanntmachung)?(?:(?:.*?) ${RegDate} ${RegRgbl}| des (?:.*?)(?:-G|g)esetzes)?$`,

    // https://regex101.com/r/w0RYHQ
    "parapara.txt": String.raw`^§§ (\w+), (\w+) (?:des ((?:.*?)gesetz(?:es)?)|der (?:.*?)ordnung)$`,

    // https://regex101.com/r/biVSIS
    "richtlinie.txt": String.raw`^Richtlinie(?: \(EU\))? \d+\/\d+(?:\/EG|\/EWG)?$`,

    // https://regex101.com/r/gYuG4P
    "richtlinien.txt": String.raw`^Richtlinien(?: \(EU\))? \d+\/\d+(?:\/EG|\/EWG)? und \d+\/\d+(?:\/EG|\/EWG)?$`,

    // https://regex101.com/r/v8Bgv2
    "verordnung.txt": String.raw`^(?:(?:[a-zA-ZüöäÜÄÖ]*?)v|V)erordnung \((?:EG|EU)\)(?: Nr.)? \d+\/\d+(?:(?:.*?) \(ABl\. L \w+ vom \d+\.\d+\.\d+\, S\. \d+\))?$`,

    // https://regex101.com/r/huYQAk
    "verordnungen.txt": String.raw`^(?:Verordnungen|und|,) \((?:EG|EU|EWG)\)(?: Nr.)? \d+\/\d+$`,

    // https://regex101.com/r/xOWSau
    "voll.txt": String.raw`^(?:[a-z0-9A-Z\-üöäßÜÖÄ]+(?: und [a-z0-9A-Z\-üöäßÜÖÄ]+)?)?(?:[gG]esetz|[vV]erordnung|Protokoll zum|ordnung)(?:.*?) vom \d+\. (?:.*?) \d+ \((?:R|B)GBl\.(?: \d+)? I(?:I)? (?:S\. \w+(?:, \d+)?(?:\; \d+ I S\. \d+)?|Nr\. \d+)\)(?: in der Fassung des § \d+ Abs\. \d+ des (?:.*?)gesetzes vom \d+\. (?:.*?) \d+ \((?:R|B)GBl\.(?: \d+)? I(?:I)? (?:S\. \w+(?:, \d+)?(?:\; \d+ I S\. \d+)?|Nr\. \d+)\)|, (?:die|das)(?: zuletzt)? durch Artikel \d+(?: Absatz \d+)? (?:des Gesetzes|der Verordnung) vom \d+\. (?:.*?) \d+ \((?:R|B)GBl\.(?: \d+)? I(?:I)? (?:S\. \w+(?:, \d+)?(?:\; \d+ I S\. \d+)?|Nr\. \d+)\))?(?: geändert worden ist)?$`,

};

let regexTotal = "(?:";
let i = 1;

for (const key of Object.keys(regexes)) {
    const regex = regexes[key].replace(/\^/, "").replace(/\$/, "");
    regexTotal += regex;

    if (i < Object.keys(regexes).length) {
        regexTotal += "|";
    }
    i++;
}

regexTotal += ")";
console.log(regexTotal);

const allLinks = [];
const allUnmatchedLinks = [];

for (const filename of files) {

    console.log(" ")
    console.log("Checking: " + filename);

    if (!regexes.hasOwnProperty(filename)) {
        console.log("No Regex for ", filename);
        continue;
    }

    const linkFileContent = fs.readFileSync(path + filename, 'utf8');
    const links = linkFileContent.split(os.EOL);

    const numberOfLinks = links.length;
    console.log("Total links: ", numberOfLinks);

    if (numberOfLinks === 0) {
        console.log("No links!");
        continue;
    }

    console.log(regexes[filename]);

    let numberOfMatches = numberOfLinks;
    for (const link of links) {

        allLinks.push(link);

        const matchResult = new RegExp(regexes[filename]).test(link);

        if (!matchResult) {
            numberOfMatches--;
            console.log(link);
            allUnmatchedLinks.push(link);
        }

    }

    const quote = (numberOfMatches / numberOfLinks);
    console.log("Match rate: ", (quote * 100).toFixed(2), "%");

}

console.log("\nAll unmatched links:")
for (const unmatchedLink of allUnmatchedLinks) {
    console.log(unmatchedLink);
}

console.log("\n Total match rate: ", (((allLinks.length - allUnmatchedLinks.length) / allLinks.length) * 100).toFixed(2), "%");
