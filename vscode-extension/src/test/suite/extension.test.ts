import * as vscode from "vscode";
import * as sinon from "sinon";
import * as assert from "assert";

suite("Extension Unit Test Suite", () => {
  teardown(() => {
    sinon.restore();
  });

  test("extension activates", async () => {
    assert.equal(
      vscode.extensions.getExtension("digitalservicebund.ris-norms")?.isActive,
      true,
    );
  });
});
