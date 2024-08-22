| Module           | Status                                                                                                                                                                                                                                                         |
| ---------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| All modules      | [![Pipeline](https://github.com/digitalservicebund/ris-norms/actions/workflows/pipeline.yml/badge.svg)](https://github.com/digitalservicebund/ris-norms/actions/workflows/pipeline.yml)                                                                        |
| frontend         | [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=digitalservicebund_ris-norms-frontend&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=digitalservicebund_ris-norms-frontend)                                  |
| backend          | [![Quality Gate Status Backend](https://sonarcloud.io/api/project_badges/measure?project=digitalservicebund_ris-norms-backend&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=digitalservicebund_ris-norms-backend)                            |                  |

# RIS Norms

This repository contains a web app supporting the Federal Documentation of Statutes (DE: ["Normendokumentation"](https://www.bundesjustizamt.de/DE/Themen/Rechtsetzung/Normendokumentation/Normendokumentation_node.html)) in their task of keeping the documentation of federal norms up-to-date through amendments (DE: "Fortschreibung").

The name "RIS Norms" refers to

- "RIS", which is the German acronym for "information system on the law" (DE: "Rechtsinformationssystem")
- "Norms", which makes explicit that within RIS, we're explicitly dealing with federal laws and similar documents (and not, for example, with court verdicts)

# Top Level Structure of the Repository

## Code
This is a mono-repository containing

- [`./backend`](./backend) - The backend service
- [`./frontend`](./frontend) - The main browser-based entry point for users of _RIS-norms_
- [`./ldml-extension`](./ldml-extension) - Extensions to the [LegalDocDML.de](https://gitlab.opencode.de/bmi/e-gesetzgebung/ldml_de) schema
- [`./regex`](./regex/) - A utility for creating regex schemas

Each of the above has its own `README.md` with more.

## Sample Data
- [`./ldml-samples`](./ldml-samples/) contains files in the [LegalDocML.de](https://gitlab.opencode.de/bmi/e-gesetzgebung/ldml_de) format for testing and reference purposes

## Documentation
- [`./doc`](./doc) contains information on
  -  the domain model
  -  the API specification and
  -  our Architecture Decision Records (ADR)

- Additionally, there is the general [RIS Documentation](https://digitalservicebund.github.io/ris-reports/), which shows the RIS Norms
  - [architecture diagrams](https://digitalservicebund.github.io/ris-reports/docs/architecture/diagrams_list.html)
  - backend [JavaDocs](https://digitalservicebund.github.io/ris-reports/docs/backend-code-documentation/norms-java.html)

# Development

Please refer to [`DEVELOPING.md`](./DEVELOPING.md) for further details.

# Contributions Welcome!

See [`CONTRIBUTING.md`](./CONTRIBUTING.md) for details.
