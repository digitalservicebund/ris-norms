module.exports = {
  rules: {
    "body-case": [2, "always", "sentence-case"],
    "body-leading-blank": [2, "always"],
    "header-case": [1, "always", "sentence-case"],
    "header-full-stop": [2, "never", "."],
    "header-max-length": [2, "always", 72],
    "references-empty": [2, "never"],
  },
  parserPreset: {
    parserOpts: {
      issuePrefixes: ["NDISC-"],
    },
  },
};
