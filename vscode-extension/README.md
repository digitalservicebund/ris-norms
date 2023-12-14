# normendokumentation VSCode extension

# About the Extension

The Normendokumentation extension is designed to assist users in viewing and understanding amendments to legal documents. It provides a convenient way to view an amending law alongside the target law, as well as a preview of the applied changes in a legal document (LDML file).

# Features

- Viewing the Amending Law: Easily view the amending LDML file within VS Code.
- Comparing the Target Law: Display the corresponding target law LDML file alongside the amending law.
- Previewing Applied Changes: See a static preview of the changes that the amending law would apply to the target law.
- Static File Operation: Operate on a set of static files for each required input.

# Installation and Usage

- Open the Extensions pane in vscode, click on the `...` icon, select `Install from VSIX` , and select the .vsix extension file.

## Usage

1. **Open Your LDML Files:** In VS Code, open the folder containing your amending law and target law LDML files.
2. **Activate the Extension:** Use the command palette (Ctrl+Shift+P or Cmd+Shift+P on macOS) and search for `Open in custom Layout` to activate the extension.
3. **View and Compare:** The amending law, to be amended law, and a preview of the applied changes will be displayed in a split layout for easy comparison and review.

_Note: Currently, the extension operates on static files and displays a static result. Interactive features, automatic document fetching, and diff previews are out of scope for this version._

# Development / Tech Notes

## Running the Extension

To run and debug the extension directly within VS Code, follow these steps:

1. **Open the Extension Directory**: Make sure that the vscode-extension directory is open in Visual Studio Code and set as the root of the workspace.

2. **Start the Extension**:

   - Press 'F5' to launch a new Extension Development Host instance of VS Code with your extension loaded.
   - Alternatively, you can go to the "Run and Debug" sidebar and click the green play button to start the extension.

## Testing

The extension uses Mocha for testing

To run the tests:

```bash
npm run test
```

To Gather coverage:

```bash
npm run coverage
```

## Style (linting & formatting)

Linting is done via [ESLint](https://eslint.org/docs/user-guide/getting-started); consistent formatting for a variety of source code files is being enforced using [Prettier](https://prettier.io/docs/en/index.html). ESLint and Prettier work in conjunction.

Check style:

```bash
npm run style:check
```

Autofix issues:

```bash
npm run style:fix
```

## Packaging the extension

The extension is packaged and stored as an artifakt on our github repo but should you want to package the extension manually you can run:

```bash
npm run vscode:pack
```

This will create a `.vsix` file within the vscode-extension project folder. That is the extension.
