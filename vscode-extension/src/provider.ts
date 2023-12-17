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

  provideTextDocumentContent(uri: vscode.Uri): vscode.ProviderResult<string> {
    const parameters = new URLSearchParams(uri.query);
    const amendingLaw = parameters.get("amendingLaw");
    const targetLaw = parameters.get("targetLaw");

    // ris-norms-time-machine has to be in the PATH
    //
    // TODO: Use the output of the time machine (actually a file written somewhere, might be a problem as the filesystem is read-only?)
    return execShell(
      `ris-norms-time-machine --stdout "${amendingLaw}" "${targetLaw}"`,
    );
  }
}

export function encodeLocation(
  amendingLaw: vscode.Uri,
  targetLaw: vscode.Uri,
): vscode.Uri {
  return vscode.Uri.parse(
    `${Provider.scheme}:Preview?amendingLaw=${amendingLaw.fsPath}&targetLaw=${targetLaw.fsPath}`,
  );
}
