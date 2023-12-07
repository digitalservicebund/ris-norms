import * as assert from "assert";
import * as vscode from "vscode";
import * as sinon from "sinon";

suite("Extension Test Suite", () => {
  vscode.window.showInformationMessage("Start all tests.");

  let openTextDocumentStub: sinon.SinonStub;

  setup(() => {
    // Mock workspace folder
    const mockedWorkspaceFolder = {
      uri: vscode.Uri.file("/mocked/workspace/folder"),
      name: "MockedWorkspace",
      index: 0,
    };

    sinon
      .stub(vscode.workspace, "workspaceFolders")
      .value([mockedWorkspaceFolder]);

    // Define mock file paths
    const filePaths = [
      `${mockedWorkspaceFolder.uri.fsPath}/07_01_änderungsgesetz.xml`,
      `${mockedWorkspaceFolder.uri.fsPath}/07_01_geändertesGesetz_V1.1_Metadatenaenderung.xml`,
      `${mockedWorkspaceFolder.uri.fsPath}/07_01_geändertesGesetz_V1.2_konsolidierte_Fassung.xml`,
    ];

    // Mock openTextDocument to simulate opening the files
    openTextDocumentStub = sinon.stub(vscode.workspace, "openTextDocument");
    filePaths.forEach((path) => {
      openTextDocumentStub
        .withArgs(vscode.Uri.file(path))
        .resolves({ uri: vscode.Uri.file(path) });
    });
  });

  teardown(() => {
    sinon.restore();
  });

  test("Extension Activation", async () => {
    // The unique identifier for your extension
    const extensionId = "digitalservicebund.normendokumentation";
    const extension = vscode.extensions.getExtension(extensionId);

    assert.ok(extension, `Extension "${extensionId}" should be present`);

    // Check that the extension is initially not active
    assert.strictEqual(
      extension.isActive,
      false,
      `Extension "${extensionId}" should initially be inactive`,
    );

    // Activate the extension and check if it's active
    await extension.activate();
    assert.strictEqual(
      extension.isActive,
      true,
      `Extension "${extensionId}" should be active after activation`,
    );
  });

  test("openFilesInLayout Command", async () => {
    await vscode.commands.executeCommand(
      "digitalservicebund.openFilesInLayout",
    );

    assert.ok(
      openTextDocumentStub.calledWith(
        vscode.Uri.file("/mocked/workspace/folder/07_01_änderungsgesetz.xml"),
      ),
      "07_01_änderungsgesetz.xml should be opened",
    );
    assert.ok(
      openTextDocumentStub.calledWith(
        vscode.Uri.file(
          "/mocked/workspace/folder/07_01_geändertesGesetz_V1.1_Metadatenaenderung.xml",
        ),
      ),
      "07_01_geändertesGesetz_V1.1_Metadatenaenderung.xml should be opened",
    );
    assert.ok(
      openTextDocumentStub.calledWith(
        vscode.Uri.file(
          "/mocked/workspace/folder/07_01_geändertesGesetz_V1.2_konsolidierte_Fassung.xml",
        ),
      ),
      "07_01_geändertesGesetz_V1.2_konsolidierte_Fassung.xml should be opened",
    );
  });
});
