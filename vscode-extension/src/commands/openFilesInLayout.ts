import {
  findAmendedLawFile,
  findAmendingLawFile,
  findToBeAmendedLawFile,
} from "../findLawFiles";
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

    const workspaceFolder = vscode.workspace.workspaceFolders[0].uri;

    const amendingLaw = await findAmendingLawFile(workspaceFolder);
    if (amendingLaw === undefined) {
      throw new Error("Amending law not found.");
    }

    const toBeAmendedLaw = await findToBeAmendedLawFile(workspaceFolder);
    if (toBeAmendedLaw === undefined) {
      throw new Error("To be amended law not found.");
    }

    const amendedLaw = await findAmendedLawFile(workspaceFolder);
    if (amendedLaw === undefined) {
      throw new Error("Amended law not found.");
    }

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
