package de.bund.digitalservice.ris.norms.timemachine

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.file
import de.bund.digitalservice.ris.norms.timemachine.core.applyModification
import de.bund.digitalservice.ris.norms.timemachine.helper.FileToDocumentConverter
import de.bund.digitalservice.ris.norms.timemachine.helper.XmlFileWriter
import org.w3c.dom.Document

class TimeMachine : CliktCommand() {
  private val amendingLawFile by argument().file(mustExist = true, canBeDir = false)
  private val targetLawFile by argument().file(mustExist = true)

  override fun run() {

    val amendingLawDoc: Document = FileToDocumentConverter().convert(amendingLawFile)
    val targetLawDoc = FileToDocumentConverter().convert(targetLawFile)

    val appliedLaw = applyModification(amendingLawDoc, targetLawDoc)
    XmlFileWriter().writeDocumentToFile(appliedLaw, targetLawFile.name + "_amended.xml")
  }
}

fun main(args: Array<String>) = TimeMachine().main(args)
