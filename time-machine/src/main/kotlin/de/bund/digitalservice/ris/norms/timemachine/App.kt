package de.bund.digitalservice.ris.norms.timemachine

class App {
  val greeting: String
    get() = "Hello World!"
}

fun main(args: Array<String>) {
  val app = App()
  println(app.greeting)
}
