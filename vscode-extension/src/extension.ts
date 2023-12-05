import * as vscode from "vscode";

export function activate(context: vscode.ExtensionContext) {
  let openFilesInLayout = vscode.commands.registerCommand(
    "digitalservicebund.openFilesInLayout",
    async () => {
      try {
        await vscode.commands.executeCommand("vscode.setEditorLayout", {
          orientation: 0,
          groups: [
            {
              groups: [{}, {}],
              size: 0.5,
            },
            {
              size: 0.5,
            },
          ],
        });

        if (vscode.workspace.workspaceFolders) {
          const workspaceFolder =
            vscode.workspace.workspaceFolders[0].uri.fsPath;

          const amendingLaw = vscode.Uri.file(
            `${workspaceFolder}/07_01_änderungsgesetz.xml`,
          );
          const toBeAmendedLaw = vscode.Uri.file(
            `${workspaceFolder}/07_01_geändertesGesetz_V1.1_Metadatenaenderung.xml`,
          );
          const amendedLaw = vscode.Uri.file(
            `${workspaceFolder}/07_01_geändertesGesetz_V1.2_konsolidierte_Fassung.xml`,
          );

          const toBeAmendedPanel =
            await vscode.workspace.openTextDocument(toBeAmendedLaw);
          const amendingLawPanel =
            await vscode.workspace.openTextDocument(amendingLaw);
          const amendedLawPanel =
            await vscode.workspace.openTextDocument(amendedLaw);

          await vscode.window.showTextDocument(toBeAmendedPanel, {
            viewColumn: vscode.ViewColumn.One,
          });
          await vscode.window.showTextDocument(amendingLawPanel, {
            viewColumn: vscode.ViewColumn.Two,
          });
          await vscode.window.showTextDocument(amendedLawPanel, {
            viewColumn: vscode.ViewColumn.Three,
          });
        } else {
          console.error("Workspace folder not found.");
        }
      } catch (error) {
        console.error("Error opening the xml files:", error);
      }
    },
  );

  context.subscriptions.push(disposable, openFilesInLayout);
}

export function deactivate() {}
