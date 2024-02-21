import * as vscode from "vscode";
import ContentProvider from "../provider";
import { Disposable, workspace, window } from "vscode";

export function activate(context: vscode.ExtensionContext) {
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
        `${workspaceFolder}/07_01_aenderungsgesetz.xml`,
      );
      const toBeAmendedLaw = vscode.Uri.file(
        `${workspaceFolder}/07_01_zuaenderndesgesetz.xml`,
      );
      const url = provider.encodeLocation(amendingLaw, toBeAmendedLaw);
      const doc = await workspace.openTextDocument(url);
      await window.showTextDocument(doc, vscode.ViewColumn.Three);
    },
  );

  context.subscriptions.push(commandRegistration, providerRegistrations);
}
