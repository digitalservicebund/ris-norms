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

  test("openFilesInLayout shows error when no workspace is opened", async () => {
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

  test("applyChanges opens resulting file", async () => {
    const expectedUri = vscode.Uri.parse(
      "timemachine-preview:Preview?amendingLaw=/mocked/workspace/folder/07_01_änderungsgesetz.xml&targetLaw=/mocked/workspace/folder/07_01_zuänderndesgesetz.xml",
    );
    openTextDocumentStub.resolves({ uri: expectedUri });

    await vscode.commands.executeCommand("digitalservicebund.applyChanges");

    sinon.assert.calledWith(openTextDocumentStub, expectedUri);

    sinon.assert.calledWithExactly(
      showTextDocumentStub,
      { uri: expectedUri },
      vscode.ViewColumn.Three,
    );
  });

  test("applyChanges shows error when no workspace is opened", async () => {
    sinon.stub(vscode.workspace, "workspaceFolders").value(undefined as any);

    const showErrorMessageStub = sinon
      .stub(vscode.window, "showErrorMessage")
      .resolves();

    await vscode.commands.executeCommand("digitalservicebund.applyChanges");

    sinon.assert.calledWith(showErrorMessageStub, "No workspace open");
  });
});
