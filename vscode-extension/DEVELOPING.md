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

## Creating a stable release

1. Update the version number in the `package.json`, and make a commit.

2. Create a new tag with the new version number. The tag name should start with `v` followed by the version number. Include a message for the tag to describe the release.
   ```bash
   git tag vX.X.X -m "Stable release vX.X.X"
   ```
3. Push the newly created tag and the version change to the remote repository

   ```bash
   git push origin vX.X.X
   ```
