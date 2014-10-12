package dartdodge;

import net.slashie.libjcsi.ConsoleSystemInterface;
import java.util.ArrayList;

public class Darts {

    int xpos, ypos;

    Darts() {
        this(0, (int) (Math.random() * (DartDodge.WORLD_HEIGHT + 1)));
    }

    private Darts(int xpos, int ypos) {
        this.xpos = xpos;
        this.ypos = ypos;
    }

    public void draw(ConsoleSystemInterface cons) {
        cons.print(this.xpos, this.ypos, "======>", cons.RED);
    }

    public Darts tick() {
        if (this.xpos + 7 == DartDodge.WORLD_WIDTH) {
            return new Darts();
        } else {
            return new Darts(this.xpos + 1, this.ypos);
        }
    }
}
