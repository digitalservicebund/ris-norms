import * as vscode from "vscode";
import * as sinon from "sinon";

suite("Extension Unit Test Suite", () => {
  let openTextDocumentStub: sinon.SinonStub;
  let showTextDocumentStub: sinon.SinonStub;

  setup(() => {
    const mockedWorkspaceFolder = {
      uri: vscode.Uri.file("/mocked/workspace/folder"),
      name: "MockedWorkspace",
      index: 0,
    };

    sinon
      .stub(vscode.workspace, "workspaceFolders")
      .value([mockedWorkspaceFolder]);

    const filePaths = [
      `${mockedWorkspaceFolder.uri.fsPath}/07_01_änderungsgesetz.xml`,
      `${mockedWorkspaceFolder.uri.fsPath}/07_01_geändertesGesetz_V1.1_Metadatenaenderung.xml`,
      `${mockedWorkspaceFolder.uri.fsPath}/07_01_geändertesGesetz_V1.2_konsolidierte_Fassung.xml`,
    ];

    openTextDocumentStub = sinon.stub(vscode.workspace, "openTextDocument");
    showTextDocumentStub = sinon
      .stub(vscode.window, "showTextDocument")
      .resolves();

    filePaths.forEach((path) => {
      openTextDocumentStub
        .withArgs(vscode.Uri.file(path))
        .resolves({ uri: vscode.Uri.file(path) });
    });
  });

  teardown(() => {
    sinon.restore();
  });

  test("openFilesInLayout command executes correctly", async () => {
    await vscode.commands.executeCommand(
      "digitalservicebund.openFilesInLayout",
    );

    sinon.assert.calledWith(
      openTextDocumentStub,
      vscode.Uri.file("/mocked/workspace/folder/07_01_änderungsgesetz.xml"),
    );
    sinon.assert.calledWith(
      openTextDocumentStub,
      vscode.Uri.file(
        "/mocked/workspace/folder/07_01_geändertesGesetz_V1.1_Metadatenaenderung.xml",
      ),
    );
    sinon.assert.calledWith(
      openTextDocumentStub,
      vscode.Uri.file(
        "/mocked/workspace/folder/07_01_geändertesGesetz_V1.2_konsolidierte_Fassung.xml",
      ),
    );

    sinon.assert.calledWith(
      showTextDocumentStub,
      {
        uri: vscode.Uri.file(
          "/mocked/workspace/folder/07_01_geändertesGesetz_V1.1_Metadatenaenderung.xml",
        ),
      },
      { viewColumn: vscode.ViewColumn.One },
    );

    sinon.assert.calledWith(
      showTextDocumentStub,
      {
        uri: vscode.Uri.file(
          "/mocked/workspace/folder/07_01_änderungsgesetz.xml",
        ),
      },
      { viewColumn: vscode.ViewColumn.Two },
    );

    sinon.assert.calledWith(
      showTextDocumentStub,
      {
        uri: vscode.Uri.file(
          "/mocked/workspace/folder/07_01_geändertesGesetz_V1.2_konsolidierte_Fassung.xml",
        ),
      },
      { viewColumn: vscode.ViewColumn.Three },
    );
  });

  test("applyChanges opens resulting file in correct column", async () => {
    await vscode.commands.executeCommand("digitalservicebund.applyChanges");

    sinon.assert.calledWith(
      openTextDocumentStub,
      vscode.Uri.parse(
        "timemachine-preview:Preview?amendingLaw=/mocked/workspace/folder/07_01_änderungsgesetz.xml&targetLaw=/mocked/workspace/folder/07_01_zuänderndesgesetz.xml",
      ),
    );
  });
});
