import * as vscode from "vscode";
import * as sinon from "sinon";

suite("openFilesInLayout", () => {
  const fileNameAmendment = "07_01_aenderungsgesetz.xml";
  const fileNameTarget = "07_01_zuaenderndesgesetz.xml";
  const fileNameConsolidated =
    "07_01_geaendertesGesetz_V1.2_konsolidierte_Fassung.xml";

  teardown(() => {
    sinon.restore();
  });

  test("shows error when no workspace is opened", async () => {
    sinon.stub(vscode.workspace, "workspaceFolders").value(undefined as any);

    const showErrorMessageStub = sinon
      .stub(vscode.window, "showErrorMessage")
      .resolves();

    await vscode.commands.executeCommand(
      "digitalservicebund.openFilesInLayout",
    );

    sinon.assert.calledWith(
      showErrorMessageStub,
      "Error opening the xml files: Error: Workspace folder not found.",
    );
  });

  test("command executes correctly", async () => {
    const mockedWorkspaceFolder = {
      uri: vscode.Uri.file("/mocked/workspace/folder"),
      name: "MockedWorkspace",
      index: 0,
    };

    sinon
      .stub(vscode.workspace, "workspaceFolders")
      .value([mockedWorkspaceFolder]);

    const filePaths = [
      `${mockedWorkspaceFolder.uri.fsPath}/${fileNameAmendment}`,
      `${mockedWorkspaceFolder.uri.fsPath}/${fileNameTarget}`,
      `${mockedWorkspaceFolder.uri.fsPath}/${fileNameConsolidated}`,
    ];

    const openTextDocumentStub = sinon.stub(
      vscode.workspace,
      "openTextDocument",
    );
    const showTextDocumentStub = sinon
      .stub(vscode.window, "showTextDocument")
      .resolves();

    filePaths.forEach((path) => {
      openTextDocumentStub
        .withArgs(vscode.Uri.file(path) as any)
        .resolves({ uri: vscode.Uri.file(path) } as vscode.TextDocument);
    });

    await vscode.commands.executeCommand(
      "digitalservicebund.openFilesInLayout",
    );

    sinon.assert.calledWith(
      openTextDocumentStub,
      vscode.Uri.file(`/mocked/workspace/folder/${fileNameAmendment}`) as any,
    );
    sinon.assert.calledWith(
      openTextDocumentStub,
      vscode.Uri.file(`/mocked/workspace/folder/${fileNameTarget}`) as any,
    );
    sinon.assert.calledWith(
      openTextDocumentStub,
      vscode.Uri.file(
        `/mocked/workspace/folder/${fileNameConsolidated}`,
      ) as any,
    );

    sinon.assert.calledWith(
      showTextDocumentStub,
      {
        uri: vscode.Uri.file(`/mocked/workspace/folder/${fileNameTarget}`),
      } as any,
      { viewColumn: vscode.ViewColumn.One },
    );

    sinon.assert.calledWith(
      showTextDocumentStub,
      {
        uri: vscode.Uri.file(`/mocked/workspace/folder/${fileNameAmendment}`),
      } as any,
      { viewColumn: vscode.ViewColumn.Two },
    );

    sinon.assert.calledWith(
      showTextDocumentStub,
      {
        uri: vscode.Uri.file(
          `/mocked/workspace/folder/${fileNameConsolidated}`,
        ),
      } as any,
      { viewColumn: vscode.ViewColumn.Three },
    );
  });
});
