package dartdodge;

import net.slashie.libjcsi.wswing.WSwingConsoleInterface;
import net.slashie.libjcsi.ConsoleSystemInterface;
import net.slashie.libjcsi.CharKey;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class DartDodge {

    static final int WORLD_HEIGHT = 24;
    static final int WORLD_WIDTH = 79;

    public static boolean gameOverHuh(Balloon ball) {
        return ball.lifeCount <= 0;
    }

    public static boolean dartHitHuh(Balloon ball, DartsArray dartsArray) {
        for (Darts e : dartsArray.darts) {
            if ((e.xpos + 3 <= ball.xpos) && (ball.xpos <= e.xpos + 7)
                    && (e.ypos - 3 <= ball.ypos) && (ball.ypos <= e.ypos)) {
                return true;
            }
        }
        return false;
    }

    public static void printScore(DartsArray dartsArray, ConsoleSystemInterface cons) {
        cons.print(1, 1, "Score: " + (15 * dartsArray.tickCount), cons.WHITE);
    }

    public static void tickTest(DartsArray darts) throws Exception {
        int rand = (int) Math.random() * 100;
        DartsArray dartsT = darts;
        for (int i = 0; i < rand; i++) {
            dartsT = darts.tick();
        }
        
        for (int i = 0; i < darts.darts.size(); i++) {
            if (dartsT.darts.get(i).xpos != darts.darts.get(i).xpos + rand) {
                throw new Exception();
            }
        }
    }
    
    public static void tickReactTest() throws Exception {
        int rand = (int) Math.random() * 100;
        Balloon ballL = new Balloon();
        Balloon ballR = new Balloon();
        ballL = ballL.react(new CharKey(CharKey.LARROW));
        for (int i = 0; i < rand; i++) {
            ballL = ballL.tick();
        }
        ballR = ballR.react(new CharKey(CharKey.RARROW));
        for (int i = 0; i < rand; i++) {
            ballR = ballR.tick();
        }
        
        if (!(ballL.xpos >= 0 && ballR.xpos <= WORLD_WIDTH - 8)) {
            throw new Exception();
        }
    }

    public static void test() throws Exception {
        Balloon ball = new Balloon();
        DartsArray darts = new DartsArray();
        // call tick x times on a darts array <=> xpos of each dart in array + 1
        tickTest(darts);
        // call react(larrow) and tick x times --> ball.xpos >= 0 && 
        //     call react(rarrow) & tick x times --> ball.xpos <=  max width - 8 
        tickReactTest();
        // ball.lifeCount = 0 --> gameOverHuh = true
        if (ball.lifeCount == 0 && gameOverHuh(ball)) {
            throw new Exception();
        }
        // ball.loseLife() <=> ball with lifeCount - 1
        if (ball.loseLife().lifeCount != ball.lifeCount - 1) {
            throw new Exception();
        }
        // 
    }

    public static void main(String[] args) throws InterruptedException, Exception {
        String mode = "test";
        ConsoleSystemInterface cons = new WSwingConsoleInterface("Dart Dodge", true);
        cons.cls();
        cons.refresh();

        Balloon balloon = new Balloon();
        DartsArray dartsArray = new DartsArray();
        
        test();

        if (mode.equals("test")) {
            while (!gameOverHuh(balloon)) {
                cons.cls();
                cons.print(1, 0, "Lives: " + balloon.lifeCount, cons.WHITE);
                printScore(dartsArray, cons);
                balloon.draw(cons);
                dartsArray.draw(cons);
                cons.refresh();
                CharKey ke;
                switch ((int) (Math.random() * 5)) {
                    case 0: ke = new CharKey(CharKey.UARROW);
                        break;
                    case 1: ke = new CharKey(CharKey.DARROW);
                        break;
                    case 2: ke = new CharKey(CharKey.LARROW);
                        break;
                    default: ke = new CharKey(CharKey.RARROW);
                }
                balloon = balloon.react(ke);
                dartsArray = dartsArray.tick();
                balloon = balloon.tick();
                if (dartHitHuh(balloon, dartsArray)) {
                    balloon = balloon.loseLife();
                }
                TimeUnit.MILLISECONDS.sleep(16*4);
            }
        } else {
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
                balloon = balloon.tick();
                if (dartHitHuh(balloon, dartsArray)) {
                    balloon = balloon.loseLife();
                }
            }
        }
        cons.refresh();
        cons.print(WORLD_WIDTH / 2 - 5, WORLD_HEIGHT / 2, "GAME OVER", cons.BLUE);
    }

}
