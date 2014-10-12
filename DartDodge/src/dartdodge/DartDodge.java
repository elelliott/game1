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
    
    public static boolean bubbleHitHuh(Balloon ball, BubbleArray bubArray) {
        for (AirBubble e : bubArray.bubbles) {
            if ((e.xpos - 1 <= ball.xpos) && (ball.xpos <= e.xpos + 5)
                    && (e.ypos - 4 <= ball.ypos) && (ball.ypos <= e.ypos + 1)) {
                return true;
            }
        }
        return false;
    }
    
    public static void printScore(DartsArray dartsArray, ConsoleSystemInterface cons) {
        cons.print(1, 1, "Score: " + (15 * dartsArray.tickCount), cons.WHITE);
    }
    
    public static void tester(Object x, Object y) throws Exception {
        if (x != y) {
            throw new Exception("Oops! " + x + " does not equal " + y);
        }
    }
    
    public static void test() throws Exception {
        // TESTERS HERE
    }

    public static void main(String[] args) throws InterruptedException {
        ConsoleSystemInterface cons = new WSwingConsoleInterface("Dart Dodge", true);
        cons.cls();
        cons.refresh();

        Balloon balloon = new Balloon();
        DartsArray dartsArray = new DartsArray();
        BubbleArray bubbleArray = new BubbleArray();

        while (!gameOverHuh(balloon)) {
            cons.cls();
            cons.print(1, 0, "Lives: " + balloon.lifeCount, cons.WHITE);
            printScore(dartsArray, cons);
            balloon.draw(cons);
            dartsArray.draw(cons);
            cons.refresh();
            CharKey ke = cons.inkey();
            balloon = balloon.react(ke);
            dartsArray = dartsArray.tick();
            bubbleArray = bubbleArray.tick();
            balloon = balloon.tick();
            if (dartHitHuh(balloon, dartsArray)) {
                balloon = balloon.loseLife();
            }
            if (bubbleHitHuh(balloon, bubbleArray)) {
                balloon = balloon.increaseLife();
            }
        }
        cons.refresh();
        cons.print(WORLD_WIDTH/2-5, WORLD_HEIGHT/2, "GAME OVER", cons.BLUE);
    }

}
