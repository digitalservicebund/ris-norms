# normendokumentation VSCode extension

### About the Extension

The Normendokumentation extension is designed to assist users in viewing and understanding amendments to legal documents. It provides a convenient way to view an amending law alongside the target law, as well as a preview of the applied changes in a legal document (LDML file).

### Features

- Amending Law Viewing: Easily view the amending LDML file within VS Code.
- Target Law Comparison: Display the corresponding target law LDML file alongside the amending law.
- Preview Applied Changes: See a static preview of the changes that the amending law would apply to the target law.
- Static File Operation: Operate on a set of static files for each required input.

### Installation

- Open the Extensions pane in vscode, click on the `...` icon, select `Install from VSIX` , and select the .vsix extension file.

### Usage

1. **Open Your LDML Files:** In VS Code, open the folder containing your amending law and target law LDML files.
2. **Activate the Extension:** Use the command palette (Ctrl+Shift+P or Cmd+Shift+P on macOS) and search for `Open in custom Layout` to activate the extension.
3. **View and Compare:** The amending law, to be amended law, and a preview of the applied changes will be displayed in a split layout for easy comparison and review.

_Note: Currently, the extension operates on static files and displays a static result. Interactive features, automatic document fetching, and diff previews are out of scope for this version._
