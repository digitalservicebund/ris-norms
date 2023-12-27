import * as cp from "child_process";
import * as assert from "assert";
import { promisify } from "util";

suite("Integration tests", () => {
  const execAsync = promisify(cp.exec);
  const executable =
    "time-machine/build/install/ris-norms-time-machine/bin/ris-norms-time-machine";

  test("ris-norms-time-machine returns 0", async () => {
    await assert.doesNotReject(async () => {
      await execAsync(
        `${executable} ldml-samples/07_01_änderungsgesetz.xml ldml-samples/07_01_zuänderndesgesetz.xml`,
      );
    });
  });

  test("ris-norms-time-machine returns non-zero when passing unknown parameter", async () => {
    await assert.rejects(
      async () => {
        await execAsync(`${executable} --unknown`);
      },
      {
        message: /Error: no such option --unknown/,
      },
    );
  });
});
