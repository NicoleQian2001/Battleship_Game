package cs349.a3battleship

import cs349.a3battleship.view.PlayerBoardView
import cs349.a3battleship.model.Game
import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage

// controller
class Battleship : Application() {
    override fun start(stage: Stage) {
        var game = Game(10, true)
        var computer = AI(game)
        // var player = ...
        game.startGame()

        // stage.scene = ...
        val main = PlayerBoardView(game)

        val scene = Scene(main, 875.0, 375.0)
        stage.scene = scene
        stage.title = "A3 Battleship (j55qian)"

        stage.show()

        if (game.isExitGame) {
            stage.close()
        }
    }
}