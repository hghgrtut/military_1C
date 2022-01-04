package com.example.view

import java.io.FileOutputStream
import java.math.BigInteger
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javafx.scene.text.Font
import org.apache.poi.xwpf.usermodel.ParagraphAlignment
import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.apache.poi.xwpf.usermodel.XWPFParagraph
import org.apache.poi.xwpf.usermodel.XWPFRun
import org.apache.poi.xwpf.usermodel.XWPFTable
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation
import tornadofx.*


class MainView : View("Hello TornadoFX") {
    override val root = hbox {
        button("Press me").apply { action {
            val doc = XWPFDocument().apply { font = Font.font("Times New Roman") }
            val body = doc.document.body
            if (!body.isSetSectPr) body.addNewSectPr()
            val section = body.sectPr.apply { if (!isSetPgSz) addNewPgSz() }
            section.pgSz.apply {
                orient = STPageOrientation.LANDSCAPE
                w = BigInteger.valueOf(16840)
                h = BigInteger.valueOf(11900)
            }

            val komandirName = "Дорощенко А.А."
            val komandirVCh =  "Командир войсковой части _______"

            fun XWPFRun.setDefaults() = apply {
                fontSize = 14
                fontFamily = "Times New Roman"
            }

            doc.createParagraph()
                .apply {
                    alignment = ParagraphAlignment.LEFT
                    indentationLeft = 8400
                    indentationRight = -50
                }.createRun()
                .setDefaults()
                .setText(with(StringBuilder("УТВЕРЖДАЮ\r$komandirVCh\r$komandirName")) {

                    fun getZapolnitel(symbolsAlreadyDisplayed: Int) =
                        CharArray(komandirVCh.length - symbolsAlreadyDisplayed) {'_'}

                    val (dayMonth, year) = LocalDate.now().format(DateTimeFormatter.ofPattern("«dd»_LLLL yyyy")).split(" ")
                    append(getZapolnitel(komandirName.length))
                    append("\r")
                    append(dayMonth)
                    append(getZapolnitel(dayMonth.length + year.length))
                    append(year)
                }.toString())

            val act = "A К Т № ${(1..1000).random()}\rсписания (снятия остатков)"
            doc.createParagraph().apply { alignment = ParagraphAlignment.CENTER }.createRun().setDefaults().setText(act)

            val upperTable: XWPFTable = doc.createTable(2, 8)
            val firstRowCells = upperTable.rows[0].tableCells

            val fileName = "C:\\Users\\nata4\\Desktop\\test.docx"
            //val fileName = "F:\\test.docx"
            doc.write(FileOutputStream(fileName))
            close()
        } }
    }
}
