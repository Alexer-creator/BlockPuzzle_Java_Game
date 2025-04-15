package sk.tuke.kpi.kp.kp5.blockpuzzle;

import sk.tuke.kpi.kp.kp5.blockpuzzle.consoleui.ConsoleUI;
import sk.tuke.kpi.kp.kp5.blockpuzzle.core.*;

public class BlockPuzzle {
    public static void main(String[] args) {
        while (true) {
            Field gameField = new Field(10, 10, 10); // 5x5 play area, 3-row spawn area
            ConsoleUI ui = new ConsoleUI(gameField);

            boolean replay = ui.play();
            if(!replay) {
                break;
            }
        }
    }
}
