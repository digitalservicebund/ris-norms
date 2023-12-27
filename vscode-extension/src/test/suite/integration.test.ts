import * as cp from "child_process";
import * as assert from "assert";
import { promisify } from "util";

suite("Integration tests", () => {
  const execAsync = promisify(cp.exec);
  const executable =
    "../time-machine/build/install/ris-norms-time-machine/bin/ris-norms-time-machine";

  test("ris-norms-time-machine returns 0", async () => {
    await assert.doesNotReject(async () => {
      await execAsync(
        `${executable} ldml-samples/07_01_änderungsgesetz.xml ldml-samples/07_01_zuänderndesgesetz.xml`,
      );
    });
  });

  function stripAnsi(str: string): string {
    return str.replace(
      /[\u001b\u009b][[()#;?]*(?:[0-9]{1,4}(?:;[0-9]{0,4})*)?[0-9A-ORZcf-nq-uy=><]/g,
      "",
    );
  }

  test("ris-norms-time-machine returns non-zero when passing unknown parameter", async () => {
    await assert.rejects(
      async () => {
        await execAsync(`${executable} --unknown`);
      },
      (err: any) => {
        const message = stripAnsi(err.message); // Clean the message of ANSI codes
        return /Error: no such option --unknown/.test(message);
      },
    );
  });
});
