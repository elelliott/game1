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
            if ((e.xpos <= ball.xpos) && (ball.xpos <= e.xpos + 7)
                    && (e.ypos - 3 <= ball.ypos) && (ball.ypos <= e.ypos)) {
                return true;
            }
        }
        return false;
    }

    public static int getScore(DartsArray darts) {
        return 15 * darts.tickCount;
    }

    public static void printScore(DartsArray dartsArray, ConsoleSystemInterface cons) {
        cons.print(1, 1, "Score: " + getScore(dartsArray), cons.WHITE);
    }

    public static void scoreTest(DartsArray darts) throws Exception {
        int rand = (int) (Math.random() * 100) + 100;
        System.out.println("Score Tests passed: " + rand);
        DartsArray dartsT = darts;
        for (int i = 0; i < rand; i++) {
            dartsT = dartsT.tick();
        }

        if (getScore(dartsT) != 15 * rand) {
            throw new Exception();
        }
    }

    public static void tickReactTest() throws Exception {
        int rand = (int) (Math.random() * 100) + 100;
        System.out.println("Tick/React Tests passed: " + rand);
        for (int x = 0; x < rand; x++) {
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

            if (ballL.xpos < 0 || ballR.xpos > WORLD_WIDTH - 8) {
                throw new Exception();
            }
        }
    }

    public static void addDartTest(DartsArray darts) throws Exception {
        int rand = (int) (Math.random() * 25) + 1;
        System.out.println("Add Dart Tests passed: " + rand);
        for (int x = 0; x < rand; x++) {
            DartsArray dartsT = darts;
            int added = 0;
            for (int i = 0; i < rand; i++) {
                if (dartsT.tickCount % 50 == 0) { added++; }
                dartsT = dartsT.tick();
            }

            if (darts.darts.size() + added != dartsT.darts.size()) {
                throw new Exception();
            }
        }
    }

    public static void test() throws Exception {
        Balloon ball = new Balloon();
        DartsArray darts = new DartsArray();
        // call tick x times on a darts array <=> score = 15 * x
        scoreTest(darts);
        // call react(larrow) and tick x times --> ball.xpos >= 0 && 
        //     call react(rarrow) & tick x times --> ball.xpos <=  max width - 8 
        tickReactTest();
        // tick a dart array x times --> length of dart array = old length + rand/50
        addDartTest(darts);
        // ball.lifeCount = 0 --> gameOverHuh = true
        if (ball.lifeCount == 0 && !gameOverHuh(ball)) {
            throw new Exception();
        }
        System.out.println("Game Over Test passed");
        // ball.loseLife() <=> ball with lifeCount - 1
        if (ball.loseLife().lifeCount != ball.lifeCount - 1) {
            throw new Exception();
        }
        System.out.println("Lose Life Test passed");
        // ball lies within dart.xpos, dart.xpos + 7, dart.ypos - 3, and dart.ypos
        //     --> dartHitHuh(ball, dart array) = true
        int randInd = (int) (Math.random() * darts.darts.size());
        Darts dart = darts.darts.get(randInd);
        if (dart.xpos <= ball.xpos && ball.xpos <= dart.xpos + 7 && 
                dart.ypos - 3 <= ball.ypos && ball.ypos <= dart.ypos &&
                !dartHitHuh(ball, darts)) {
            throw new Exception();
        }
        System.out.println("Dart Hit Test passed");
    }

    public static void runSim(Balloon balloon, DartsArray dartsArray,
            ConsoleSystemInterface cons) throws InterruptedException {
        while (!gameOverHuh(balloon)) {
            cons.cls();
            cons.print(1, 0, "Lives: " + balloon.lifeCount, cons.WHITE);
            printScore(dartsArray, cons);
            balloon.draw(cons);
            dartsArray.draw(cons);
            cons.refresh();
            CharKey ke;
            switch ((int) (Math.random() * 4)) {
                case 0:
                    ke = new CharKey(CharKey.UARROW);
                    break;
                case 1:
                    ke = new CharKey(CharKey.DARROW);
                    break;
                case 2:
                    ke = new CharKey(CharKey.LARROW);
                    break;
                default:
                    ke = new CharKey(CharKey.RARROW);
                    break;
            }
            balloon = balloon.react(ke);
            dartsArray = dartsArray.tick();
            balloon = balloon.tick();
            if (dartHitHuh(balloon, dartsArray)) {
                balloon = balloon.loseLife();
            }
            TimeUnit.MILLISECONDS.sleep(16 * 4);
        }

        cons.refresh();
        cons.print(WORLD_WIDTH / 2 - 5, WORLD_HEIGHT / 2, "GAME OVER", cons.BLUE);
        TimeUnit.SECONDS.sleep(5);
        cons.refresh();
    }
    
    public static void playGame(Balloon balloon, DartsArray dartsArray,
            ConsoleSystemInterface cons) {
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

        cons.refresh();
        cons.print(WORLD_WIDTH / 2 - 5, WORLD_HEIGHT / 2, "GAME OVER", cons.BLUE);
    }

    public static void main(String[] args) throws InterruptedException, Exception {
        ConsoleSystemInterface cons = new WSwingConsoleInterface("Dart Dodge", true);
        cons.cls();
        cons.refresh();

        Balloon balloon = new Balloon();
        DartsArray dartsArray = new DartsArray();

        // run tests
        test();
        // simulation
        runSim(new Balloon(), new DartsArray(), cons);
        // normal gameplay
        playGame(balloon, dartsArray, cons);
    }

}
