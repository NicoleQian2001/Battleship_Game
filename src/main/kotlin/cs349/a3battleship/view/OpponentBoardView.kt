package cs349.a3battleship.view

import javafx.scene.control.Label
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.Line
import javafx.scene.shape.Rectangle

class OpponentBoardView (
) : Pane(), IView {
    override fun updateView() {

    }

    init {
        // top column label
        var letter = 65 // letter A
        for (i in 0..9) {
            // column count is i
            val colLab = Label("${i+1}        ")
            val colLab2 = Label("${i+1}        ")
            val rowLab = Label(letter.toChar().toString())
            val rowLab2 = Label(letter.toChar().toString())
            rowLab.relocate(5.0,25.0 + 30.0 * i)
            rowLab2.relocate(325.0,30.0 + 30.0 * i)
            colLab.relocate(25.0 + 30.0 * i, 0.0)
            colLab2.relocate(25.0 + 30.0 * i, 325.0)
            children.addAll(rowLab,rowLab2,colLab,colLab2)
            letter++
            for (j in 0..9) {
                val cell = Rectangle( 20.0 + 30.0 * j, 20.0 + 30.0 * i, 30.0, 30.0)
                val hLine = Line(20.0 + 30.0 * j, 20.0 + 30.0 * i, 320.0, 20.0 + 30.0 * i)
                val vLine = Line(20.0 + 30.0 * j, 20.0 + 30.0 * i, 20.0 + 30.0 * j,320.0)
                cell.fill = Color.LIGHTBLUE
                children.addAll(cell,hLine,vLine)
            }
        }
        val hLine = Line(20.0, 320.0, 320.0,320.0)
        val vLine = Line(320.0, 20.0, 320.0,320.0)
        children.addAll(hLine,vLine)
        prefWidth = 340.0
    }
}