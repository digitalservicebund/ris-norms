import * as vscode from "vscode";
import * as sinon from "sinon";

suite("openFilesInLayout", () => {
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
    sinon.replaceGetter(
      vscode.workspace,
      "workspaceFolders",
      sinon.fake.returns([
        {
          uri: vscode.Uri.file("/mocked/workspace/folder"),
          name: "MockedWorkspace",
          index: 0,
        },
      ]),
    );

    // We can't directly fake the properties of vscode.workspace.fs as they are readonly. Therefore, we need to create an additional fake for fs.
    sinon.replaceGetter(
      vscode.workspace,
      "fs",
      sinon.fake.returns({
        ...vscode.workspace.fs,
        readDirectory: sinon.fake.resolves([
          ["07_01_aenderungsgesetz.xml", vscode.FileType.File],
          ["07_01_zuaenderndesgesetz.xml", vscode.FileType.File],
          [
            "07_01_geaendertesGesetz_V1.2_konsolidierte_Fassung.xml",
            vscode.FileType.File,
          ],
        ]),
      }),
    );

    const showTextDocumentStub = sinon
      .stub(vscode.window, "showTextDocument")
      .resolves();

    const openTextDocumentStub = sinon.stub(
      vscode.workspace,
      "openTextDocument",
    );
    openTextDocumentStub
      .withArgs(
        vscode.Uri.file(
          "/mocked/workspace/folder/07_01_aenderungsgesetz.xml",
        ) as any,
      )
      .resolves({
        uri: vscode.Uri.file(
          "/mocked/workspace/folder/07_01_aenderungsgesetz.xml",
        ),
      } as vscode.TextDocument);
    openTextDocumentStub
      .withArgs(
        vscode.Uri.file(
          "/mocked/workspace/folder/07_01_zuaenderndesgesetz.xml",
        ) as any,
      )
      .resolves({
        uri: vscode.Uri.file(
          "/mocked/workspace/folder/07_01_zuaenderndesgesetz.xml",
        ),
      } as vscode.TextDocument);
    openTextDocumentStub
      .withArgs(
        vscode.Uri.file(
          "/mocked/workspace/folder/07_01_geaendertesGesetz_V1.2_konsolidierte_Fassung.xml",
        ) as any,
      )
      .resolves({
        uri: vscode.Uri.file(
          "/mocked/workspace/folder/07_01_geaendertesGesetz_V1.2_konsolidierte_Fassung.xml",
        ),
      } as vscode.TextDocument);

    await vscode.commands.executeCommand(
      "digitalservicebund.openFilesInLayout",
    );

    sinon.assert.calledWith(
      showTextDocumentStub,
      {
        uri: vscode.Uri.file(
          `/mocked/workspace/folder/07_01_zuaenderndesgesetz.xml`,
        ),
      } as any,
      { viewColumn: vscode.ViewColumn.One },
    );

    sinon.assert.calledWith(
      showTextDocumentStub,
      {
        uri: vscode.Uri.file(
          `/mocked/workspace/folder/07_01_aenderungsgesetz.xml`,
        ),
      } as any,
      { viewColumn: vscode.ViewColumn.Two },
    );

    sinon.assert.calledWith(
      showTextDocumentStub,
      {
        uri: vscode.Uri.file(
          `/mocked/workspace/folder/07_01_geaendertesGesetz_V1.2_konsolidierte_Fassung.xml`,
        ),
      } as any,
      { viewColumn: vscode.ViewColumn.Three },
    );
  });

  test("shows error when no amending law exists", async () => {
    sinon.replaceGetter(
      vscode.workspace,
      "workspaceFolders",
      sinon.fake.returns([
        {
          uri: vscode.Uri.file("/mocked/workspace/folder"),
          name: "MockedWorkspace",
          index: 0,
        },
      ]),
    );

    // We can't directly fake the properties of vscode.workspace.fs as they are readonly. Therefore, we need to create an additional fake for fs.
    sinon.replaceGetter(
      vscode.workspace,
      "fs",
      sinon.fake.returns({
        ...vscode.workspace.fs,
        readDirectory: sinon.fake.resolves([
          ["07_01_zuaenderndesgesetz.xml", vscode.FileType.File],
          [
            "07_01_geaendertesGesetz_V1.2_konsolidierte_Fassung.xml",
            vscode.FileType.File,
          ],
        ]),
      }),
    );

    const showErrorMessageStub = sinon
      .stub(vscode.window, "showErrorMessage")
      .resolves();

    await vscode.commands.executeCommand(
      "digitalservicebund.openFilesInLayout",
    );

    sinon.assert.calledWith(
      showErrorMessageStub,
      "Error opening the xml files: Error: Amending law not found.",
    );
  });
});
