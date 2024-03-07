| Module           | Status                                                                                                                                                                                                                                                         |
| ---------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| All modules      | [![Pipeline](https://github.com/digitalservicebund/ris-norms/actions/workflows/pipeline.yml/badge.svg)](https://github.com/digitalservicebund/ris-norms/actions/workflows/pipeline.yml)                                                                        |
| frontend         | [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=digitalservicebund_ris-norms-frontend&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=digitalservicebund_ris-norms-frontend)                                  |
| backend          | [![Quality Gate Status Backend](https://sonarcloud.io/api/project_badges/measure?project=digitalservicebund_ris-norms-backend&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=digitalservicebund_ris-norms-backend)                            |                  |

# RIS Norms

This repository contains tools for supporting the Federal Documentation of Statutes (DE: ["Normendokumentation"](https://www.bundesjustizamt.de/DE/Themen/Rechtsetzung/Normendokumentation/Normendokumentation_node.html)) in their task of keeping the documentation of federal norms up-to-date through amendments (DE: "Fortschreibung").

The name "RIS Norms" refers to

- "RIS", which is the German acronym for "Information system on the law" (DE: "Rechtsinformationssystem")
- "Norms", which makes explicit that within RIS, we're explicitly dealing with federal laws and similar documents (and not, for example, with court verdicts)

# Structure of the Repository

This is a mono-repository containing several software products. Right now, there are four of them:

- [`./frontend`](./frontend): The main browser-based entry point for users of _RIS-norms_
- [`./backend`](./backend): The backend service

Each product has its own `README.md` with more details.

# Additional Documentation
- The [`./doc`](./doc) folder contains additional information e.g. on architecture and domain model
- Back end JavaDocs can be found [here](https://digitalservicebund.github.io/ris-reports/docs/backend-code-documentation/norms-java.html)

# Development

Please refer to [`DEVELOPING.md`](./DEVELOPING.md) for details.

# Contributions Welcome!

🇬🇧
Everyone is welcome to contribute to the development of _RIS Norms_. You can contribute by opening pull requests, providing documentation, answering questions or giving feedback. Please do follow our guidelines and our [Code of Conduct](CODE_OF_CONDUCT.md).

🇩🇪
Jede:r ist herzlich eingeladen, die Entwicklung von _RIS Norms_ mitzugestalten. Du kannst einen Beitrag leisten, indem du Pull-Requests eröffnest, die Dokumentation erweiterst, Fragen beantwortest oder Feedback gibst. Bitte befolge die Richtlinien und unseren [Verhaltenskodex](CODE_OF_CONDUCT_DE.md).

## Code Contributions

🇬🇧
Open a pull request with your changes and it will be reviewed by someone from the team. When you submit a pull request, you declare that you have the right to license your contribution to DigitalService and the community. By submitting the patch, you agree that your contributions are licensed under the MIT license.

Please make sure that your changes have been tested before submitting a pull request.

🇩🇪
Nach dem Erstellen eines Pull Requests wird dieser von einer Person aus dem Team überprüft. Wenn du einen Pull Request einreichst, erklärst du dich damit einverstanden, deinen Beitrag an den DigitalService und die Community zu lizenzieren. Durch das Einreichen des Patches erklärst du dich damit einverstanden, dass deine Beiträge unter der MIT-Lizenz lizenziert sind.

Bitte stelle sicher, dass deine Änderungen getestet wurden bevor du einen Pull Request sendest.
