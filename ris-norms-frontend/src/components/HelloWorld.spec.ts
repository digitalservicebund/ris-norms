import { render, screen } from "@testing-library/vue"
import { it } from "vitest"
import HelloWorld from "./HelloWorld.vue"

it("increments value on click", async () => {
  render(HelloWorld, {
    props: {
      msg: "my message",
    },
  })

  screen.getByText("my message")
})
