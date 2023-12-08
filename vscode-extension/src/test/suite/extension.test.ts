import * as assert from "assert";
import * as vscode from "vscode";
import * as sinon from "sinon";
import { activate } from "../../extension";

suite("Extension Unit Test Suite", () => {
  let openTextDocumentStub: sinon.SinonStub;

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
    const context = { subscriptions: [] } as unknown as vscode.ExtensionContext;
    activate(context);

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
