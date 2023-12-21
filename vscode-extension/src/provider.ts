import * as vscode from "vscode";
import * as cp from "child_process";
import * as fs from "fs";
import { Uri } from "vscode";

export default class Provider implements vscode.TextDocumentContentProvider {
  static scheme = "timemachine-preview";

  provideTextDocumentContent(uri: vscode.Uri): vscode.ProviderResult<string> {
    const parameters = new URLSearchParams(uri.query);
    const amendingLaw = parameters.get("amendingLaw");
    const targetLaw = parameters.get("targetLaw");

    if (!amendingLaw || !targetLaw) {
      throw new Error("Amending law or target law not specified.");
    }

    const amendingLawFile = vscode.Uri.file(amendingLaw);
    const targetLawFile = vscode.Uri.file(targetLaw);

    return this.applyTimeMachine(amendingLawFile, targetLawFile);
  }

  async applyTimeMachine(
    amendingLawFile: Uri,
    targetLawFile: Uri,
  ): Promise<string> {
    if (
      !fs.existsSync(amendingLawFile.fsPath) ||
      !fs.existsSync(targetLawFile.fsPath)
    ) {
      throw new Error("Amending law or target law file not found.");
    }

    // TODO: This is not tested
    // ris-norms-time-machine has to be in the $PATH
    const cmd = `ris-norms-time-machine "${amendingLawFile.fsPath}" "${targetLawFile.fsPath}"`;

    return new Promise<string>((resolve, reject) => {
      cp.exec(cmd, (err, out) => {
        if (err) {
          return reject(err);
        }
        return resolve(out);
      });
    });
  }

  encodeLocation(amendingLaw: vscode.Uri, targetLaw: vscode.Uri): vscode.Uri {
    return vscode.Uri.parse(
      `${Provider.scheme}:Preview?amendingLaw=${amendingLaw.fsPath}&targetLaw=${targetLaw.fsPath}`,
    );
  }
}
