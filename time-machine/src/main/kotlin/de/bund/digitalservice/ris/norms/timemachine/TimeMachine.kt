package de.bund.digitalservice.ris.norms.timemachine

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.file
import de.bund.digitalservice.ris.norms.timemachine.core.applyModification
import documentToString
import org.w3c.dom.Document
import readDocumentFromFile

class TimeMachine : CliktCommand() {
  private val amendingLawFile by argument().file(mustExist = true, canBeDir = false)
  private val targetLawFile by argument().file(mustExist = true)

  override fun run() {
    val amendingLawDoc: Document = readDocumentFromFile(amendingLawFile)
    val targetLawDoc = readDocumentFromFile(targetLawFile)

    val appliedLaw = applyModification(amendingLawDoc, targetLawDoc)
    val documentString = documentToString(appliedLaw)
    echo(documentString, true, false)

    // TODO: remove if there is no strong objection
    // writeDocumentToFile(appliedLaw, targetLawFile.nameWithoutExtension + "_amended.xml")
  }
}

fun main(args: Array<String>) = TimeMachine().main(args)
