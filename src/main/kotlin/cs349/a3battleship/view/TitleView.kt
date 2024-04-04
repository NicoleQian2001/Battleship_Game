package cs349.a3battleship.view

import cs349.a3battleship.model.*
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.text.Font
import javafx.scene.text.FontWeight

// the three titles
class TitleView (
    private val game: Game
) : HBox(), IView {
    override fun updateView() {
        if (game.result != null ) {
            title2.text = "You " + if (game.result == Player.Human) "won!" else "were defeated!"
        }
    }

    val title2 = Label("My Fleet")

    init {
        val title = Label("My Formation")
        title.prefHeight = 25.0
        title.prefWidth = 350.0
        title.font = Font.font("Arial", FontWeight.BOLD ,16.0)
        title.padding = Insets(0.0)
        title.alignment = Pos.CENTER

        title2.font = Font.font("Arial", FontWeight.BOLD ,16.0)
        title2.padding = Insets(0.0)
        title2.prefHeight = 25.0
        title2.prefWidth = 175.0
        title2.alignment = Pos.CENTER

        val title3 = Label("Opponent's Formation")
        title3.font = Font.font("Arial", FontWeight.BOLD ,16.0)
        title3.padding = Insets(0.0)
        title3.prefHeight = 25.0
        title3.prefWidth = 350.0
        title3.alignment = Pos.CENTER

        children.addAll(title, title2, title3)
        prefHeight = 25.0
        prefWidth = 875.0

        game.addView(this)
    }

}
