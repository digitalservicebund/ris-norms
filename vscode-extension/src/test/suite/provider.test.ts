import sinon = require("sinon");
import Provider from "../../provider"; // Replace '../provider' with the correct path to the Provider class
import * as assert from "assert";
import { Uri } from "vscode";

suite("Provider", () => {
  test("provideTextDocumentContent calls execShell with the correct parameters and returns its return value", async () => {
    const provider = new Provider();

    const execShellStub = sinon.stub(provider, "execShell");
    execShellStub
      .withArgs(
        'ris-norms-time-machine --stdout "/path/to/amendingLaw" "/path/to/targetLaw"',
      )
      .resolves("Sample text document content");

    const uri = Uri.parse(
      "timemachine-preview://Preview?amendingLaw=/path/to/amendingLaw&targetLaw=/path/to/targetLaw",
      true,
    );
    const content = await provider.provideTextDocumentContent(uri);
    assert.equal(content, "Sample text document content");

    sinon.assert.calledWith(
      execShellStub,
      'ris-norms-time-machine --stdout "/path/to/amendingLaw" "/path/to/targetLaw"',
    );
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

  test("execShell returns the output of a shell invocation", async () => {
    const provider = new Provider();

    const output = await provider.execShell("echo Something");
    assert.equal(output, "Something\n");
  });

  test("execShell rejects if the command exits non-zero", async () => {
    const provider = new Provider();

    assert.rejects(async () => {
      await provider.execShell("echo Test && exit 1");
    });
  });
});
