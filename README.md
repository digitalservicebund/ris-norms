| Module           | Status                                                                                                                                                                                                                                                         |
| ---------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| All modules      | [![Pipeline](https://github.com/digitalservicebund/ris-norms/actions/workflows/pipeline.yml/badge.svg)](https://github.com/digitalservicebund/ris-norms/actions/workflows/pipeline.yml)                                                                        |
| frontend         | [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=digitalservicebund_ris-norms-frontend&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=digitalservicebund_ris-norms-frontend)                                  |
| backend          | [![Quality Gate Status Backend](https://sonarcloud.io/api/project_badges/measure?project=digitalservicebund_ris-norms-backend&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=digitalservicebund_ris-norms-backend)                            |
| time-machine     | [![Quality Gate time-machine](https://sonarcloud.io/api/project_badges/measure?project=digitalservicebund_ris-norms-time-machine&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=digitalservicebund_ris-norms-time-machine)                    |
| vscode-extension | [![Quality Gate Status VSCode Extension](https://sonarcloud.io/api/project_badges/measure?project=digitalservicebund_ris-norms-vscode-extension&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=digitalservicebund_ris-norms-vscode-extension) |

# RIS Norms

This repository contains tools for supporting the Federal Documentation of Statutes (DE: ["Normendokumentation"](https://www.bundesjustizamt.de/DE/Themen/Rechtsetzung/Normendokumentation/Normendokumentation_node.html)) in their task of keeping the documentation of federal norms up-to-date through amendments (DE: "Fortschreibung").

The name "RIS Norms" refers to

- "RIS", which is the German acronym for "Information system on the law" (DE: "Rechtsinformationssystem")
- "Norms", which makes explicit that within RIS, we're explicitly dealing with federal laws and similar documents (and not, for example, with court verdicts)

# Structure of the Repository

This is a mono-repository containing several software products. Right now, there are four of them:

- [`./frontend`](./frontend): The main browser-based entry point for users of _RIS-norms_
- [`./backend`](./backend): The backend service
- [`./time-machine`](./time-machine): A command line tool for applying LDML_de change commands to existing laws
- [`./vscode-extension`](./vscode-extension): A VSCode extension supporting workflows on LDML_de change commands

Each product has its own `README.md` with more details.

# Additional Documentation
- The [`./doc`](./doc) folder contains additional information e.g. on architecture and domain model
- Back end JavaDocs can be found [here](https://digitalservicebund.github.io/ris-reports/docs/backend-code-documentation/norms-java.html)

# How to run the VSCode Extension and the Time Machine locally

## Prerequisites

- `Java 17` (check by running `java --version` in a terminal/shell)
- `VSCode` (check with running `code --version`)
  - or `VSCodium` (check with running `codium --version`, accordingly)

## Downloading the tools

- From the [Release page](https://github.com/digitalservicebund/ris-norms/releases) download the latest `ris-norms-release.zip`
- Extract that file

## Setting up the tools

- Set up the time-machine CLI
  - The release contains a folder called `ris-norms-time-machine`
  - That folder in turn contains folders `bin` and `lib`
  - Add the `bin` folder to your $PATH (check with `ris-norms-time-machine`)
    - This allows the VSCode extension to use the time-machine CLI
- Install the VSCode extension
  - Start VSCode
  - Open the extensions pane in vscode
  - Click on the `...` icon
  - Select `Install from VSIX`
  - Then select the `VSIX` file from `ris-norms-release/` (e.g. `ris-norms-0.0.1-210.vsix`)

## Using the extension

1. Download all four LDML_de files from the [ldml-samples](https://github.com/digitalservicebund/ris-norms/tree/main/vscode-extension/ldml-samples) folder
1. Put these files into a local folder
1. Open the local LDML files' folder in `VSCode`
1. (Optional) Close all editor panels
1. Activate the Extension:
   - Use the `VSCode` command `Open in custom Layout` in order to activate the extension. This will open the LDML_de files in multiple panels, cf. below.<br>
     The command palette can be opened using `Ctrl+Shift+P` (or `Cmd+Shift+P` on macOS)
1. View and Compare:
   - The amending law, the target law, and a preview of the applied changes will be displayed in a split layout for easy comparison and review.
1. Update the preview:
   - Use the `VSCode` command `Apply time machine` in order to run the `time-machine` on the LDML_de files and get the preview updated by the result.

_Note: We're still in early iterations._

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
