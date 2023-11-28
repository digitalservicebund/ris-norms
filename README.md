**WIP** 

❌ indicates where we know something is not yet up to speed

# RIS Norms

❌ [![Pipeline](https://github.com/digitalservicebund/ris-norms/actions/workflows/pipeline.yml/badge.svg)](https://github.com/digitalservicebund/ris-norms/actions/workflows/pipeline.yml)

❌ [![Scan](https://github.com/digitalservicebund/ris-norms/actions/workflows/scan.yml/badge.svg)](https://github.com/digitalservicebund/ris-norms/actions/workflows/scan.yml)

❌ [![Secrets Check](https://github.com/digitalservicebund/ris-norms/actions/workflows/secrets-check.yml/badge.svg)](https://github.com/digitalservicebund/ris-norms/actions/workflows/secrets-check.yml)

[![Sonarcloud time-machine Status](https://sonarcloud.io/api/project_badges/measure?project=digitalservicebund_ris-norms-time-machine&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=digitalservicebund_ris-norms-time-machine)

## Git hooks

The repo contains a [Lefthook](https://github.com/evilmartians/lefthook/blob/master/docs/full_guide.md) configuration,
providing a Git hooks setup out of the box.

This needs the following CLI tools to be installed locally:
    - [talisman](https://thoughtworks.github.io/talisman/docs) - scans for secrets
    - [gh](https://github.com/cli/cli) - check CI status (optional)

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
