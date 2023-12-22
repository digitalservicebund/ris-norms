import sinon = require("sinon");
import Provider from "../../provider";
import * as assert from "assert";
import { Uri, WorkspaceConfiguration } from "vscode";
import * as vscode from "vscode";
import * as cp from "child_process";
import * as fs from "fs";
import { afterEach } from "mocha";

suite("Provider", () => {
  const existsSyncStub = sinon.stub(fs, "existsSync");
  const execStub = sinon.stub(cp, "exec");
  const getConfigurationStub = sinon.stub(vscode.workspace, "getConfiguration");

  afterEach(sinon.reset);

  test("provideTextDocumentContent calls applyTimeMachine with the correct parameters and returns its return value", async () => {
    const provider = new Provider();

    const applyTimeMachineStub = sinon.stub(provider, "applyTimeMachine");
    applyTimeMachineStub.resolves("Sample text document content");

    const uri = Uri.parse(
      "timemachine-preview://Preview?amendingLaw=/path/to/amendingLaw&targetLaw=/path/to/targetLaw",
      true,
    );
    const content = await provider.provideTextDocumentContent(uri);
    assert.equal(content, "Sample text document content");

    sinon.assert.calledWith(
      applyTimeMachineStub,
      Uri.file("/path/to/amendingLaw"),
      Uri.file("/path/to/targetLaw"),
    );
  });

  test("provideTextDocumentContent throws if files not specified", () => {
    const provider = new Provider();

    const uri = Uri.parse("timemachine-preview://Preview");
    assert.rejects(async () => {
      await provider.provideTextDocumentContent(uri);
    }, "Amending law or target law not specified.");
  });

  test("encodeLocation encodes the correct location", () => {
    const provider = new Provider();

    const amendingLaw = Uri.file("/some/path/to/amendingLaw");
    const targetLaw = Uri.file("/some/path/to/targetLaw");
    const uri = provider.encodeLocation(amendingLaw, targetLaw);
    const expectedUri = Uri.parse(
      "timemachine-preview:Preview?amendingLaw=/some/path/to/amendingLaw&targetLaw=/some/path/to/targetLaw",
    );

    assert.deepStrictEqual(uri, expectedUri);
  });

  test("applyTimeMachine rejects if files not existing", async () => {
    const provider = new Provider();

    existsSyncStub.withArgs("/some/path/to/amendingLaw").returns(true);

    existsSyncStub.withArgs("/some/path/to/targetLaw").returns(false);

    await assert.rejects(async () => {
      await provider.applyTimeMachine(
        Uri.file("/some/path/to/amendingLaw"),
        Uri.file("/some/path/to/targetLaw"),
      );
    }, new Error("Amending law or target law file not found."));
  });

  test("applyTimeMachine execs time-machine command with the default path if not defined", async () => {
    const provider = new Provider();

    existsSyncStub.returns(true);

    execStub
      .withArgs(
        'ris-norms-time-machine --stdout "/path/to/amendingLaw" "/path/to/targetLaw"',
      )
      .callsArgWith(1, null, "Sample text document content");

    const workspaceConfigurationStub = {
      get: sinon.stub().withArgs("time-machine-executable").returns(undefined),
      has: sinon.stub(),
      inspect: sinon.stub(),
      update: sinon.stub(),
    };
    getConfigurationStub
      .withArgs("ris-norms" as string)
      .returns(workspaceConfigurationStub);

    const content = await provider.applyTimeMachine(
      Uri.file("/path/to/amendingLaw"),
      Uri.file("/path/to/targetLaw"),
    );

    sinon.assert.calledWith(
      execStub,
      'ris-norms-time-machine --stdout "/path/to/amendingLaw" "/path/to/targetLaw"',
    );
    assert.equal(content, "Sample text document content");
  });

  test("applyTimeMachine execs time-machine command with the specified path", async () => {
    const provider = new Provider();

    existsSyncStub.returns(true);

    execStub
      .withArgs(
        '/path/to/ris-norms-time-machine --stdout "/path/to/amendingLaw" "/path/to/targetLaw"',
      )
      .callsArgWith(1, null, "Sample text document content");

    const workspaceConfigurationStub = {
      get: sinon
        .stub()
        .withArgs("time-machine-executable")
        .returns("/path/to/ris-norms-time-machine"),
      has: sinon.stub(),
      inspect: sinon.stub(),
      update: sinon.stub(),
    };
    getConfigurationStub
      .withArgs("ris-norms" as string)
      .returns(workspaceConfigurationStub);

    const content = await provider.applyTimeMachine(
      Uri.file("/path/to/amendingLaw"),
      Uri.file("/path/to/targetLaw"),
    );

    sinon.assert.calledWith(
      execStub,
      '/path/to/ris-norms-time-machine --stdout "/path/to/amendingLaw" "/path/to/targetLaw"',
    );

    assert.equal(content, "Sample text document content");
  });

  test("applyTimeMachine rejects if the exec command rejects", async () => {
    const provider = new Provider();

    existsSyncStub.returns(true);

    const workspaceConfigurationStub = {
      get: sinon.stub().withArgs("time-machine-executable").returns(undefined),
      has: sinon.stub(),
      inspect: sinon.stub(),
      update: sinon.stub(),
    };
    getConfigurationStub
      .withArgs("ris-norms" as string)
      .returns(workspaceConfigurationStub);

    const error = new Error("Sample text document content");
    execStub.callsArgWith(1, error);

    await assert.rejects(async () => {
      await provider.applyTimeMachine(
        Uri.file("/path/to/amendingLaw"),
        Uri.file("/path/to/targetLaw"),
      );
    }, error);
  });
});
