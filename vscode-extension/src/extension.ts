import * as vscode from "vscode";
import { activate as activateOpenFilesInLayoutCommand } from "./commands/openFilesInLayout";
import { activate as activateApplyChangesCommand } from "./commands/applyChanges";

export function activate(context: vscode.ExtensionContext) {
  activateOpenFilesInLayoutCommand(context);
  activateApplyChangesCommand(context);

  vscode.commands.executeCommand("digitalservicebund.openFilesInLayout");
}
