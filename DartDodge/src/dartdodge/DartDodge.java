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

    public static boolean dartHitHuh(Balloon ball, Darts[] darts) {
        return false;
    }

    public static void main(String[] args) throws InterruptedException {
        ConsoleSystemInterface cons = new WSwingConsoleInterface("Dart Dodge", true);
        cons.cls();
        cons.refresh();

        Balloon balloon = new Balloon();
        DartsArray dartsArray = new DartsArray();

        while (!gameOverHuh(balloon)) {
            cons.cls();
            balloon.draw(cons);
            dartsArray.draw(cons);
            dartsArray = dartsArray.tick();
            cons.refresh();
            dartsArray = dartsArray.tick();
            balloon = balloon.react(cons.inkey());
            dartsArray = dartsArray.tick();
            balloon = balloon.tick();
        }
    }

}
