package de.bund.digitalservice.ris.norms.timemachine

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.file
import de.bund.digitalservice.ris.norms.timemachine.core.applyModification
import documentToString
import fileToDocument
import org.w3c.dom.Document
import writeDocumentToFile

class TimeMachine : CliktCommand() {
  private val amendingLawFile by argument().file(mustExist = true, canBeDir = false)
  private val targetLawFile by argument().file(mustExist = true)
  private val doPrintToStandardOutput by option("--stdout").flag()

  override fun run() {
    val amendingLawDoc: Document = fileToDocument(amendingLawFile)
    val targetLawDoc = fileToDocument(targetLawFile)

    val appliedLaw = applyModification(amendingLawDoc, targetLawDoc)

    if (doPrintToStandardOutput) {
      val documentString = documentToString(appliedLaw)
      echo(documentString, true, false)
    }

    writeDocumentToFile(appliedLaw, targetLawFile.nameWithoutExtension + "_amended.xml")
  }
}

fun main(args: Array<String>) = TimeMachine().main(args)
