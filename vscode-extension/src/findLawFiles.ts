import * as vscode from "vscode";

/**
 * Finds all file names of files within the given folder.
 *
 * @param folder The folder in which is searched for files.
 * @returns An array of filenames of the files in the folder.
 */
async function findFiles(folder: vscode.Uri): Promise<string[]> {
  const files = await vscode.workspace.fs.readDirectory(folder);

  return files
    .filter(([, fileType]) => fileType === vscode.FileType.File)
    .map(([filename]) => filename);
}

/**
 * Finds the first file that is an amending law.
 *
 * @param folder The folder in which is searched for such files.
 * @returns The uri to the file of the first amending law or undefined if no such file exists.
 */
export async function findAmendingLawFile(
  folder: vscode.Uri,
): Promise<vscode.Uri | undefined> {
  const files = await findFiles(folder);
  const filename = files.find((filename) =>
    filename.endsWith("aenderungsgesetz.xml"),
  );

  if (filename === undefined) {
    return undefined;
  }

  return vscode.Uri.joinPath(folder, filename);
}

/**
 * Finds the first file that is a to-be-amended law.
 *
 * @param folder The folder in which is searched for such files.
 * @returns The uri to the file of the first to-be-amended law or undefined if no such file exists.
 */
export async function findToBeAmendedLawFile(
  folder: vscode.Uri,
): Promise<vscode.Uri | undefined> {
  const files = await findFiles(folder);
  const filename = files.find((filename) =>
    filename.endsWith("zuaenderndesgesetz.xml"),
  );

  if (filename === undefined) {
    return undefined;
  }

  return vscode.Uri.joinPath(folder, filename);
}

/**
 * Finds the first file that is an amended law.
 *
 * @param folder The folder in which is searched for such files.
 * @returns The uri to the file of the first amended law or undefined if no such file exists.
 */
export async function findAmendedLawFile(
  folder: vscode.Uri,
): Promise<vscode.Uri | undefined> {
  const files = await findFiles(folder);
  const filename = files.find((filename) =>
    filename.endsWith("konsolidierte_Fassung.xml"),
  );

  if (filename === undefined) {
    return undefined;
  }

  return vscode.Uri.joinPath(folder, filename);
}
