# normendokumentation VSCode extension

# About This Extension

The Normendokumentation extension is designed to assist users in viewing and understanding amendments to legal documents. It provides a convenient way to view an amending law alongside the target law, as well as a preview of the applied changes in a legal document (LDML file).

# Features

- Viewing the Amending Law: Easily view the amending LDML file within VS Code.
- Comparing the Target Law: Display the corresponding target law LDML file alongside the amending law.
- Previewing Applied Changes: See a static preview of the changes that the amending law would apply to the target law.
- Static File Operation: Operate on a set of static files for each required input.

# Installing the Extension

Confirm that

- [VSCode](https://code.visualstudio.com/) (or [VSCodium](https://vscodium.com/) if you prefer FOSS) is installed on your machine.
- The `time-machine` is in your `$PATH` (cf. [../time-machine/README.md](../time-machine/README.md)).

Now install the `vscode-extension`:

- Download the latest development version of the extension from the `ris-norms` list of [releases](https://github.com/digitalservicebund/ris-norms/releases).
- Open the Extensions pane in vscode, click on the `...` icon, select `Install from VSIX` , and select the downloaded `.VSIX` file.

# Using the Extension

1. Download the LDML_de files:
   - Open [change law](https://github.com/digitalservicebund/ris-norms/blob/main/time-machine/src/test/resources/07_01_%C3%A4nderungsgesetz.xml) and use the download button (or `CTRL-SHIFT-S`)
   - [target law](https://github.com/digitalservicebund/ris-norms/blob/main/time-machine/src/test/resources/07_01_zu%C3%A4nderndesgesetz.xml) and use the download button (or `CTRL-SHIFT-S`)
1. Put these files into the same folder
1. Open your LDML files' folder in `VSCode``:
   - In `VSCode`, open the folder containing your amending law and target law LDML files.
1. Activate the Extension:
   - Use the `VSCode` command `Open in custom Layout` in order to activate the extension. This will open the LDML_de files in multiple panes, cf. below.<br>
     The command palette can be opened using `Ctrl+Shift+P` (or `Cmd+Shift+P` on macOS)
1. View and Compare:
   - The amending law, the target law, and a preview of the applied changes will be displayed in a split layout for easy comparison and review.
1. Update the preview:
   - Use the `VSCode` command `Apply time machine` in order to run the `time-machine` on the LDML_de files and get the preview updated by the result.

_Note: We're still in early iterations._

# Development

For development information, please see [DEVELOPING.md](https://github.com/digitalservicebund/ris-norms/blob/main/vscode-extension/DEVELOPING.md)
