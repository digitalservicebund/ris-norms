import sinon = require("sinon");
import * as assert from "assert";
import { Uri } from "vscode";
import * as vscode from "vscode";
import { afterEach } from "mocha";
import {
  findAmendedLawFile,
  findAmendingLawFile,
  findToBeAmendedLawFile,
} from "../../findLawFiles";

suite("findLawFiles", () => {
  afterEach(sinon.restore);

  test("findAmendingLawFile finds amending law files", async () => {
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

    assert.equal(
      (await findAmendingLawFile(Uri.file("/workspace")))?.fsPath,
      "/workspace/07_01_aenderungsgesetz.xml",
    );
  });

  test("findToBeAmendedLawFile finds to be amended amended law files", async () => {
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

    assert.equal(
      (await findToBeAmendedLawFile(Uri.file("/workspace")))?.fsPath,
      "/workspace/07_01_zuaenderndesgesetz.xml",
    );
  });

  test("findAmendedLawFile finds amended law files", async () => {
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

    assert.equal(
      (await findAmendedLawFile(Uri.file("/workspace")))?.fsPath,
      "/workspace/07_01_geaendertesGesetz_V1.2_konsolidierte_Fassung.xml",
    );
  });
});
