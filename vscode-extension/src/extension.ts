import * as vscode from "vscode";
import ContentProvider from "./provider";
import { Disposable, workspace, window } from "vscode";

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

        if (!vscode.workspace.workspaceFolders) {
          throw new Error("Workspace folder not found.");
        }

        const workspaceFolder = vscode.workspace.workspaceFolders[0].uri.fsPath;

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
      } catch (error) {
        await window.showErrorMessage(`Error opening the xml files: ${error}`);
      }
    },
  );

  const provider = new ContentProvider();

  const providerRegistrations = Disposable.from(
    workspace.registerTextDocumentContentProvider(
      ContentProvider.scheme,
      provider,
    ),
  );

  const commandRegistration = vscode.commands.registerCommand(
    "digitalservicebund.applyChanges",
    async () => {
      if (!vscode.workspace.workspaceFolders) {
        await window.showErrorMessage("No workspace open");
        return;
      }

      const workspaceFolder = vscode.workspace.workspaceFolders[0].uri.fsPath;
      const amendingLaw = vscode.Uri.file(
        `${workspaceFolder}/07_01_änderungsgesetz.xml`,
      );
      const toBeAmendedLaw = vscode.Uri.file(
        `${workspaceFolder}/07_01_zuänderndesgesetz.xml`,
      );
      const url = provider.encodeLocation(amendingLaw, toBeAmendedLaw);
      const doc = await workspace.openTextDocument(url);
      await window.showTextDocument(doc, vscode.ViewColumn.Three);
    },
  );

  context.subscriptions.push(
    openFilesInLayout,
    commandRegistration,
    providerRegistrations,
  );
}
