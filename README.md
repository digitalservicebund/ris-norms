# RIS Norms

[![Pipeline](https://github.com/digitalservicebund/ris-norms/actions/workflows/pipeline.yml/badge.svg)](https://github.com/digitalservicebund/ris-norms/actions/workflows/pipeline.yml)

[![Secrets Check](https://github.com/digitalservicebund/ris-norms/actions/workflows/secrets-check.yml/badge.svg)](https://github.com/digitalservicebund/ris-norms/actions/workflows/secrets-check.yml)

[![Sonarcloud time-machine Status](https://sonarcloud.io/api/project_badges/measure?project=digitalservicebund_ris-norms-time-machine&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=digitalservicebund_ris-norms-time-machine)

## Git hooks

The repo contains a [Lefthook](https://github.com/evilmartians/lefthook/blob/master/docs/full_guide.md) configuration,
providing a Git hooks setup out of the box.

This needs the following CLI tools to be installed locally: - [talisman](https://thoughtworks.github.io/talisman/docs) - scans for secrets - [gh](https://github.com/cli/cli) - check CI status (optional)

**To install these hooks, run:**

```bash
lefthook install
```

The hooks are supposed to help you to:

- commit properly formatted source code only (and not break the build otherwise)
- write [conventional commit messages](https://chris.beams.io/posts/git-commit/)
- not accidentally push [secrets and sensitive information](https://thoughtworks.github.io/talisman/)

## Architecture Decision Records

[Architecture decisions](https://cognitect.com/blog/2011/11/15/documenting-architecture-decisions)
are kept in the `docs/adr` directory.

For adding new records install the [adr-tools](https://github.com/npryce/adr-tools) package:

```bash
brew install adr-tools
```

See https://github.com/npryce/adr-tools regarding usage.

## Contributing

🇬🇧
Everyone is welcome to contribute the development of the _RIS norms documentation_. You can contribute by opening pull requests, providing documentation or answering questions or giving feedback. Please always follow the guidelines and our
[Code of Conduct](CODE_OF_CONDUCT.md).

🇩🇪
Jede:r ist herzlich eingeladen, die Entwicklung der _RIS norms documentation_ mitzugestalten. Du kannst einen Beitrag leisten,
indem du Pull-Requests eröffnest, die Dokumentation erweiterst, Fragen beantwortest oder Feedback gibst.
Bitte befolge immer die Richtlinien und unseren [Verhaltenskodex](CODE_OF_CONDUCT_DE.md).

### Contributing code

🇬🇧
Open a pull request with your changes and it will be reviewed by someone from the team. When you submit a pull request,
you declare that you have the right to license your contribution to the DigitalService and the community.
By submitting the patch, you agree that your contributions are licensed under the MIT license.

Please make sure that your changes have been tested befor submitting a pull request.

🇩🇪
Nach dem Erstellen eines Pull Requests wird dieser von einer Person aus dem Team überprüft. Wenn du einen Pull Request
einreichst, erklärst du dich damit einverstanden, deinen Beitrag an den DigitalService und die Community zu
lizenzieren. Durch das Einreichen des Patches erklärst du dich damit einverstanden, dass deine Beiträge unter der
MIT-Lizenz lizenziert sind.

Bitte stelle sicher, dass deine Änderungen getestet wurden, bevor du einen Pull Request sendest.

# normendokumentation VSCode extension

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=digitalservicebund_ris-norms-vscode-extension&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=digitalservicebund_ris-norms-vscode-extension)

### Install dependencies:

```bash
npm install
```

## Development

### Running the Extension

To run and debug the extension directly within VS Code, follow these steps:

1. **Open the Extension Directory**: Make sure that the vscode-extension directory is open in Visual Studio Code and set as the root of the workspace.

2. **Start the Extension**:

   - Press 'F5' to launch a new Extension Development Host instance of VS Code with your extension loaded.
   - Alternatively, you can go to the "Run and Debug" sidebar and click the green play button to start the extension.

### Testing

The extension uses Mocha for testing

**To run the tests:**

```bash
npm run test
```

**To Gather coverage:**

```bash
npm run coverage
```

### Style (linting & formatting)

Linting is done via [ESLint](https://eslint.org/docs/user-guide/getting-started); consistent formatting for a variety of source code files is being enforced using [Prettier](https://prettier.io/docs/en/index.html). ESLint and Prettier work in conjunction.

**Check style:**

```bash
npm run style:check
```

**Autofix issues:**

```bash
npm run style:fix
```

### Packaging the extension

The extension is packaged and stored as an artifakt on our github repo but should you want to package the extension manually you can run:

```bash
npm run vscode:pack
```

This will create a `.vsix` file within the vscode-extension project folder. That is the extension.
