import * as vscode from "vscode";
import * as sinon from "sinon";

suite("applyChanges", () => {
  teardown(() => {
    sinon.restore();
  });

  test("opens resulting file", async () => {
    const mockedWorkspaceFolder = {
      uri: vscode.Uri.file("/mocked/workspace/folder"),
      name: "MockedWorkspace",
      index: 0,
    };

    sinon
      .stub(vscode.workspace, "workspaceFolders")
      .value([mockedWorkspaceFolder]);

    const expectedUri = vscode.Uri.parse(
      "timemachine-preview:Preview?amendingLaw=/mocked/workspace/folder/07_01_aenderungsgesetz.xml&targetLaw=/mocked/workspace/folder/07_01_zuaenderndesgesetz.xml",
    );
    const openTextDocumentStub = sinon.stub(
      vscode.workspace,
      "openTextDocument",
    );
    openTextDocumentStub.resolves({ uri: expectedUri } as any);

    const showTextDocumentStub = sinon
      .stub(vscode.window, "showTextDocument")
      .resolves();

    await vscode.commands.executeCommand("digitalservicebund.applyChanges");

    sinon.assert.calledWith(openTextDocumentStub, expectedUri as any);

    sinon.assert.calledWithExactly(
      showTextDocumentStub,
      { uri: expectedUri } as any,
      vscode.ViewColumn.Three as any,
    );
  });

  test("shows error when no workspace is opened", async () => {
    sinon.stub(vscode.workspace, "workspaceFolders").value(undefined as any);

    const showErrorMessageStub = sinon
      .stub(vscode.window, "showErrorMessage")
      .resolves();

    await vscode.commands.executeCommand("digitalservicebund.applyChanges");

    sinon.assert.calledWith(showErrorMessageStub, "No workspace open");
  });
});
