package cs349.a3battleship.view

import cs349.a3battleship.model.*
import cs349.a3battleship.model.ships.Ship
import cs349.a3battleship.model.ships.ShipType
import javafx.geometry.Point2D
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.layout.BorderPane
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.shape.Line
import javafx.scene.shape.Rectangle
import kotlin.system.exitProcess

class PlayerBoardView (
    private val game: Game
) : BorderPane(), IView {
    var gameOver = false
    override fun updateView() {
        var unsunkShips = mutableListOf<Ship>()
        if (game.result != null ) {
            gameOver = true
            for (ship in ships) {
                for (cell in ship.getCells()) {
                    if (!cell.attacked) {
                        unsunkShips.add(ship)
                        break
                    }
                }
            }
            for (ship in unsunkShips) {
                uiShipPos0[ships.indexOf(ship)] = uiShipPos00[ships.indexOf(ship)]
                uiShips.elementAt(ships.indexOf(ship)).relocate(uiShipPos0[ships.indexOf(ship)].x, uiShipPos0[ships.indexOf(ship)].y)
                ships.elementAt(ships.indexOf(ship)).setOrient(Orientation.VERTICAL)
                uiShips.elementAt(ships.indexOf(ship)).rotate = 0.0
            }
        }
        var board = game.getBoard(Player.AI)
        updateBoard(board, uiAICells, gameOver)
        board = game.getBoard(Player.Human)
        updateBoard(board, uiHumanCells, gameOver)
    }

    private fun updateBoard(board: Array<Array<CellState>>, uiCells : Array<Array<Rectangle>>, gameOver : Boolean) {
        for (arr in board) {
            val j = board.indexOf(arr)
            var i = 0
            for (cellstate in arr) {
                if (cellstate == CellState.Ocean || (gameOver && (cellstate == CellState.Attacked || cellstate == CellState.ShipHit))) {
                    uiCells[i][j].fill = Color.LIGHTBLUE
                } else if (cellstate == CellState.Attacked && !gameOver) {
                    uiCells[i][j].fill = Color.GREEN
                } else if (cellstate == CellState.ShipHit && !gameOver) {
                    uiCells[i][j].fill = Color.CORAL
                } else {
                    uiCells[i][j].fill = Color.DARKGRAY
                }
                i ++
            }
        }
    }

    var pane = Pane()
    val board2BaseX = 535.0
    val uiHumanCells = Array(10) { Array(10) { Rectangle()} }
    val uiAICells = Array(10) { Array(10) { Rectangle()} }
    var uiShipPos0 = ArrayList<Point2D>()
    val uiShips = ArrayList<Rectangle>() // list of ui ships in the order in ships
    var uiShipPos00 = ArrayList<Point2D>()
    // fleets
    var ships = mutableListOf<Ship>( // list of ships on board
        Ship.MakeShip(ShipType.Destroyer,Orientation.VERTICAL, Cell(0,0,false)),
        Ship.MakeShip(ShipType.Cruiser,Orientation.VERTICAL, Cell(0,0,false)),
        Ship.MakeShip(ShipType.Submarine,Orientation.VERTICAL, Cell(0,0,false)),
        Ship.MakeShip(ShipType.Battleship,Orientation.VERTICAL, Cell(0,0,false)),
        Ship.MakeShip(ShipType.Carrier,Orientation.VERTICAL, Cell(0,0,false))
    )

    init {
        top = TitleView(game)

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
            pane.children.addAll(rowLab,rowLab2,colLab,colLab2)

            val colLab3 = Label("${i+1}        ")
            val colLab4 = Label("${i+1}        ")
            val rowLab3 = Label(letter.toChar().toString())
            val rowLab4 = Label(letter.toChar().toString())
            rowLab3.relocate(board2BaseX + 5.0,25.0 + 30.0 * i)
            rowLab4.relocate(board2BaseX + 325.0,30.0 + 30.0 * i)
            colLab3.relocate(board2BaseX + 25.0 + 30.0 * i, 0.0)
            colLab4.relocate(board2BaseX + 25.0 + 30.0 * i, 325.0)
            pane.children.addAll(rowLab3,rowLab4,colLab3,colLab4)

            letter++
            for (j in 0..9) {
                uiHumanCells[j][i] = Rectangle( 20.0 + 30.0 * j, 20.0 + 30.0 * i, 30.0, 30.0)
                val hLine = Line(20.0 + 30.0 * j, 20.0 + 30.0 * i, 320.0, 20.0 + 30.0 * i)
                val vLine = Line(20.0 + 30.0 * j, 20.0 + 30.0 * i, 20.0 + 30.0 * j,320.0)
                uiHumanCells[j][i].fill = Color.LIGHTBLUE
                pane.children.addAll(uiHumanCells[j][i],hLine,vLine)
                uiAICells[j][i] = Rectangle( board2BaseX + 20.0 + 30.0 * j, 20.0 + 30.0 * i, 30.0, 30.0)
                val hLine2 = Line(board2BaseX + 20.0 + 30.0 * j, 20.0 + 30.0 * i, board2BaseX + 320.0, 20.0 + 30.0 * i)
                val vLine2 = Line(board2BaseX + 20.0 + 30.0 * j, 20.0 + 30.0 * i, board2BaseX + 20.0 + 30.0 * j,320.0)
                uiAICells[j][i].fill = Color.LIGHTBLUE
                pane.children.addAll(uiAICells[j][i],hLine2,vLine2)
            }
        }
        var hLine = Line(20.0, 320.0, 320.0,320.0)
        var vLine = Line(320.0, 20.0, 320.0,320.0)
        pane.children.addAll(hLine,vLine)
        hLine = Line(board2BaseX + 20.0, 320.0, board2BaseX + 320.0,320.0)
        vLine = Line(board2BaseX + 320.0, 20.0, board2BaseX + 320.0,320.0)
        pane.children.addAll(hLine,vLine)

        var uiShipPos = ArrayList<Point2D>()
        var shipPlaced = ArrayList<Cell?>()
        for (i in 0..4) {
            val p = Point2D( 370.0 + i * 25.0, 5.0)
            uiShipPos00.add(p)
            uiShipPos0.add(p)
            shipPlaced.add(null)
            val rect = Rectangle(uiShipPos0[i].x, uiShipPos0[i].y, 15.0, ships.elementAt(i).getLen() * 30.0)

            uiShips.add(rect)
            uiShipPos.add(Point2D(0.0, 0.0))
            pane.children.add(uiShips.elementAt(i))
        }

        var hitShip : Int = -1 // index of selected ship in uiShips
        var isLeftBtn = false
        var isRightBtn = false
        var isGameStarted = false
        var isExitGame = false
        addEventFilter(MouseEvent.MOUSE_CLICKED) { e ->
            isLeftBtn = e.button == MouseButton.PRIMARY
            isRightBtn = e.button == MouseButton.SECONDARY
            if (hitShip == -1 && isLeftBtn && !isGameStarted && !isExitGame) {
                for (ship in uiShips) {
                    val hit = hittest (ship, ships.elementAt(uiShips.indexOf(ship)).getOrient(), e.x, e.y)

                    if (hit) {
                        uiShipPos[uiShips.indexOf(ship)] = Point2D(e.x, e.y)
                        hitShip = uiShips.indexOf(ship)
                        if (shipPlaced[hitShip] != null) {
                            game.removeShip(Player.Human, shipPlaced[hitShip]!!)
                            shipPlaced[hitShip] = null
                        }
                        break
                    }
                }
            } else if (isLeftBtn && !isGameStarted && !isExitGame) { // place ship
                val ship = uiShips[hitShip]
                var cellX = -1
                var cellY = -1

                // mouse pointer coordinates
                var mx = e.x - uiShipPos[uiShips.indexOf(ship)].x + uiShipPos0[uiShips.indexOf(ship)].x
                var my = e.y - uiShipPos[uiShips.indexOf(ship)].y + uiShipPos0[uiShips.indexOf(ship)].y
                if (ships.elementAt(hitShip).getOrient() == Orientation.HORIZONTAL) {
                    mx = mx + ship.width / 2 - ship.height / 2
                    my = my + ship.height / 2 - ship.width / 2
                }

                for (i in 0..9) {
                    for (j in 0..9) {
                        if (mx >= 20.0 + 30.0 * j && mx < 50.0 + 30.0 * j && my >= 20.0 + 30.0 * i && my < 50.0 + 30.0 * i) {
                            cellX = j
                            cellY = i
                        }
                    }
                }
                val cell = Cell(cellX, cellY) // coordinates of top-left corner of the rectangle on board
                val r = game.placeShip(
                    Player.Human,
                    ships.elementAt(uiShips.indexOf(ship)).shipType,
                    ships.elementAt(uiShips.indexOf(ship)).getOrient(),
                    cell)

                // place ship
                if (r != null) {
                    val x =
                        if (ships.elementAt(hitShip).getOrient() == Orientation.VERTICAL)
                            27.5 + 30.0 * cellX
                        else 20 + 30.0 * cellX + ship.height / 2 - ship.width / 2
                    val y =
                        if (ships.elementAt(hitShip).getOrient() == Orientation.VERTICAL) 20.0 + 30.0 * cellY
                        else 42.5 + 30.0 * cellY - ship.height / 2 - ship.width / 2
                    ship.relocate(x, y)
                    uiShipPos0[uiShips.indexOf(ship)] = Point2D(x, y)
                    shipPlaced[hitShip] = cell
                    ships[hitShip] = r
                } else {
                    // ship get returned to its position on my fleet
                    uiShipPos0[uiShips.indexOf(ship)] = uiShipPos00[uiShips.indexOf(ship)]
                    ship.relocate(uiShipPos0[uiShips.indexOf(ship)].x, uiShipPos0[uiShips.indexOf(ship)].y)
                    ships.elementAt(uiShips.indexOf(ship)).setOrient(Orientation.VERTICAL)
                    uiShips.elementAt(hitShip).rotate = 0.0
                }
                hitShip = -1 // no ship is selected
            } else if (hitShip >= 0 && isRightBtn && !isGameStarted && !isExitGame) { // rotate ship
                val curOrient = ships.elementAt(hitShip).getOrient()
                val ship = uiShips.elementAt(hitShip)
                if (curOrient == Orientation.VERTICAL) {
                    ships.elementAt(hitShip).setOrient(Orientation.HORIZONTAL)
                    uiShips.elementAt(hitShip).rotate = 90.0
                } else {
                    ships.elementAt(hitShip).setOrient(Orientation.VERTICAL)
                    uiShips.elementAt(hitShip).rotate = 0.0
                }
            } else if (isGameStarted && !gameOver && !isExitGame) {
                for (i in 0..9) {
                    for (j in 0..9) {
                        if (e.x >= board2BaseX + 20.0 + 30.0 * j && e.x < board2BaseX + 50.0 + 30.0 * j && e.y >= 45.0 + 30.0 * i && e.y < 75.0 + 30.0 * i) {
                            val cell = Cell(j,i)
                            val attackedCells = game.getAttackedCells(Player.AI)
                            if (!attackedCells.contains(cell)) {
                                game.attackCell(cell)
                            }
                        }
                    }
                }

            }
        }
        addEventFilter(MouseEvent.MOUSE_MOVED) { e ->
            if (hitShip >= 0) {
                val ship = uiShips[hitShip]
                ship.relocate(e.x - uiShipPos[uiShips.indexOf(ship)].x + uiShipPos0[uiShips.indexOf(ship)].x,
                    e.y - uiShipPos[uiShips.indexOf(ship)].y + uiShipPos0[uiShips.indexOf(ship)].y)
            }
        }

        // buttons
        val startBtn = Button("Start Game")
        val exitBtn = Button("Exit Game")
        startBtn.prefWidth = 170.0
        exitBtn.prefWidth = 170.0

        startBtn.setOnAction {
            if (game.getShipsPlacedCount(Player.Human) == 5) {
                game.startGame() // if all ship are placed
                isGameStarted = true
            }
        }

        exitBtn.setOnAction {
            exitProcess(0) // kill the kotlin process
        }

        val buttons = VBox(startBtn, exitBtn)
        buttons.relocate(350.0, 265.0)

        pane.children.addAll(buttons)
        left = pane
        game.addView(this)
    }


    private fun hittest(ship: Rectangle, orientation: Orientation, x: Double, y: Double): Boolean {
        var rect: Rectangle? = null
        if (orientation == Orientation.VERTICAL) {
            rect = Rectangle(ship.x + ship.layoutX, ship.y + ship.layoutY, ship.width, ship.height)
        } else {
            rect = Rectangle(
                ship.x + ship.layoutX + ship.width / 2 - ship.height / 2,
                ship.y + ship.layoutY + ship.height / 2 - ship.width / 2,
                ship.height, ship.width)
        }
        return rect.contains(x, y - 25.0)
    }
}
