import * as vscode from "vscode";
import { window } from "vscode";

export function activate(context: vscode.ExtensionContext) {
  const openFilesInLayoutCommandRegistration = vscode.commands.registerCommand(
    "digitalservicebund.openFilesInLayout",
    openFilesInLayout,
  );

  context.subscriptions.push(openFilesInLayoutCommandRegistration);
}

export async function openFilesInLayout() {
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
      `${workspaceFolder}/07_01_aenderungsgesetz.xml`,
    );
    const toBeAmendedLaw = vscode.Uri.file(
      `${workspaceFolder}/07_01_zuaenderndesgesetz.xml`,
    );
    const amendedLaw = vscode.Uri.file(
      `${workspaceFolder}/07_01_geaendertesGesetz_V1.2_konsolidierte_Fassung.xml`,
    );

    const amendingLawPanel =
      await vscode.workspace.openTextDocument(amendingLaw);
    const toBeAmendedPanel =
      await vscode.workspace.openTextDocument(toBeAmendedLaw);
    const amendedLawPanel = await vscode.workspace.openTextDocument(amendedLaw);

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
}
