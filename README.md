# Othello
A JavaFX implementation of the classic board game Othello.
## How to Play
The game rules are as follows:

**Starting the Game:** Players toss a coin to decide who will play white; white moves first. Each turn, the player places one piece on the board with their color facing up.

**Initial Moves:** For the first four moves, players must place their pieces on one of the four central squares of the board. No pieces are captured or reversed during these initial moves.

**Piece Placement:** Each piece must be placed adjacent to an opponent's piece such that the opponent's piece or a row of opponent's pieces is flanked by the new piece and another piece of the player's color. All opponent's pieces between these two pieces are captured and turned over to match the player's color.

**Multiple Directions:** If a piece is placed such that it traps opponent's pieces in more than one direction, all trapped pieces in all viable directions are turned over.

**End of Game:** The game ends when neither player has a legal move (i.e., a move that captures at least one opponent's piece) or when the board is full.

<img src="./game.png" alt="Game Image" style="width: 50%;"/>

## Getting Started
### How to Install
Clone the repository to your local machine using Github's built-in features or via the command-line using `git clone <repository-URL>`.
### How to Run
- Using IntelliJ IDEA, import the project.
- Once the project is open, go to **File > Project Structure > Libraries > +** and click the + icon to add a new library. Navigate to your local JavaFX lib directory and add it to the project.
- Open the project configuration menu located at the top right of the IDE and select **Edit Configurations**.
- Add `--module-path ${PATH_TO_FX} --add-modules javafx.controls,javafx.fxml` to the **VM Options** if it is not already added.
- Choose the `Othello [run]` configuration and click **Run** to start the program. 
