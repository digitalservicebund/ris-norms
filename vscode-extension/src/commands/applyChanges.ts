import * as vscode from "vscode";
import ContentProvider from "../provider";
import { Disposable, workspace, window } from "vscode";
import { findAmendingLawFile, findToBeAmendedLawFile } from "@/findLawFiles";

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

      const workspaceFolder = vscode.workspace.workspaceFolders[0].uri;

      const amendingLaw = await findAmendingLawFile(workspaceFolder);
      if (amendingLaw === undefined) {
        await window.showErrorMessage("Amending law not found.");
        return;
      }

      const toBeAmendedLaw = await findToBeAmendedLawFile(workspaceFolder);
      if (toBeAmendedLaw === undefined) {
        await window.showErrorMessage("To be amended law not found.");
        return;
      }

      const url = provider.encodeLocation(amendingLaw, toBeAmendedLaw);
      const doc = await workspace.openTextDocument(url);
      await window.showTextDocument(doc, vscode.ViewColumn.Three);
    },
  );

  context.subscriptions.push(commandRegistration, providerRegistrations);
}
