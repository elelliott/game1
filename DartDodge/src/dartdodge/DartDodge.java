package dartdodge;

import net.slashie.libjcsi.wswing.WSwingConsoleInterface;
import net.slashie.libjcsi.ConsoleSystemInterface;
import net.slashie.libjcsi.CharKey;
import java.util.ArrayList;

public class DartDodge {

    static final int WORLD_HEIGHT = 24;
    static final int WORLD_WIDTH = 79;

    public static boolean gameOverHuh(Balloon ball) {
        return ball.lifeCount <= 0;
    }

    public static boolean dartHitHuh(Balloon ball, DartsArray dartsArray) {
        for (Darts e : dartsArray.darts) {
            if ((e.xpos - 1 <= ball.xpos) && (ball.xpos <= e.xpos + 7)
                    && (e.ypos - 3 <= ball.ypos) && (ball.ypos <= e.ypos)) {
                return true;
            }
        }
        return false;
    }
    
    public static void printScore(DartsArray dartsArray, ConsoleSystemInterface cons) {
        cons.print(1, 1, "Score: " + (15 * dartsArray.tickCount), cons.WHITE);
    }

    public static void main(String[] args) throws InterruptedException {
        ConsoleSystemInterface cons = new WSwingConsoleInterface("Dart Dodge", true);
        cons.cls();
        cons.refresh();

        Balloon balloon = new Balloon();
        DartsArray dartsArray = new DartsArray();

        while (!gameOverHuh(balloon)) {
            cons.cls();
            cons.print(1, 0, "Lives: " + balloon.lifeCount, cons.WHITE);
            printScore(dartsArray, cons);
            balloon.draw(cons);
            dartsArray.draw(cons);
            cons.refresh();
            balloon = balloon.react(cons.inkey());
            dartsArray = dartsArray.tick();
            balloon = balloon.tick();
            if (dartHitHuh(balloon, dartsArray)) {
                balloon = balloon.loseLife();
            }         
        }
        cons.refresh();
        cons.print(WORLD_WIDTH/2-5, WORLD_HEIGHT/2, "GAME OVER", cons.BLUE);
    }

}
