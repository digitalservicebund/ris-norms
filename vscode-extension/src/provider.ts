import * as vscode from "vscode";
import * as cp from "child_process";

const execShell = (cmd: string) =>
  new Promise<string>((resolve, reject) => {
    cp.exec(cmd, (err, out) => {
      if (err) {
        return reject(err);
      }
      return resolve(out);
    });
  });

export default class Provider implements vscode.TextDocumentContentProvider {
  static scheme = "timemachine-preview";

  provideTextDocumentContent(
    uri: vscode.Uri,
    token: vscode.CancellationToken,
  ): vscode.ProviderResult<string> {
    const parameters = new URLSearchParams(uri.query);
    const amendingLaw = parameters.get("amendingLaw");
    const targetLaw = parameters.get("targetLaw");
    return execShell(`cat "${targetLaw}"`);
  }
}

export function encodeLocation(
  amendingLaw: vscode.Uri,
  targetLaw: vscode.Uri,
): vscode.Uri {
  return vscode.Uri.parse(
    `${Provider.scheme}:Vorschau?amendingLaw=${amendingLaw.fsPath}&targetLaw=${targetLaw.fsPath}`,
  );
}
