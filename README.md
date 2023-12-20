| Module | Status |
| ----- | ----- |
| All modules | [![Pipeline](https://github.com/digitalservicebund/ris-norms/actions/workflows/pipeline.yml/badge.svg)](https://github.com/digitalservicebund/ris-norms/actions/workflows/pipeline.yml) |
| time-machine | [![Quality Gate time-machine](https://sonarcloud.io/api/project_badges/measure?project=digitalservicebund_ris-norms-time-machine&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=digitalservicebund_ris-norms-time-machine) |
| vscode-extension | [![Quality Gate Status VSCode Extension](https://sonarcloud.io/api/project_badges/measure?project=digitalservicebund_ris-norms-vscode-extension&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=digitalservicebund_ris-norms-vscode-extension)

# RIS Norms

This repository contains tools for supporting the Federal Documentation of Statutes (DE: ["Normendokumentation"](https://www.bundesjustizamt.de/DE/Themen/Rechtsetzung/Normendokumentation/Normendokumentation_node.html)) in their task of keeping the documentation of federal norms up-to-date through amendments (DE: "Fortschreibung").

The name "RIS Norms" refers to 
* "RIS", which is the German acronym for "Information system on the law" (DE: "Rechtsinformationssystem")
* "Norms", which makes explicit that within RIS, we're explicitly dealing with federal laws and similar documents (and not, for example, with court verdicts)

# Structure of the Repository
This is a mono-repository containing several software products. Right now, there's two of them:
* `./time-machine`: A command line tool for applying LDML_de change commands to existing laws
* `./vscode-extension`: A VSCode extension supporting workflows on LDML_de change commands

# Development

Please refer to [`DEVELOPING.md`](./DEVELOPING.md) for details.

# Contributing

🇬🇧
Everyone is welcome to contribute the development of the _RIS norms documentation_. You can contribute by opening pull requests, providing documentation or answering questions or giving feedback. Please always follow the guidelines and our
[Code of Conduct](CODE_OF_CONDUCT.md).

🇩🇪
Jede:r ist herzlich eingeladen, die Entwicklung der _RIS norms documentation_ mitzugestalten. Du kannst einen Beitrag leisten, indem du Pull-Requests eröffnest, die Dokumentation erweiterst, Fragen beantwortest oder Feedback gibst. Bitte befolge immer die Richtlinien und unseren [Verhaltenskodex](CODE_OF_CONDUCT_DE.md).

## Contributing code

🇬🇧
Open a pull request with your changes and it will be reviewed by someone from the team. When you submit a pull request, you declare that you have the right to license your contribution to the DigitalService and the community. By submitting the patch, you agree that your contributions are licensed under the MIT license.

Please make sure that your changes have been tested befor submitting a pull request.

🇩🇪
Nach dem Erstellen eines Pull Requests wird dieser von einer Person aus dem Team überprüft. Wenn du einen Pull Request einreichst, erklärst du dich damit einverstanden, deinen Beitrag an den DigitalService und die Community zu lizenzieren. Durch das Einreichen des Patches erklärst du dich damit einverstanden, dass deine Beiträge unter der MIT-Lizenz lizenziert sind.

Bitte stelle sicher, dass deine Änderungen getestet wurden, bevor du einen Pull Request sendest.
