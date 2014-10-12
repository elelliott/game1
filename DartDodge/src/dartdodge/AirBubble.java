package dartdodge;
import net.slashie.libjcsi.ConsoleSystemInterface;
import java.util.ArrayList;

public class AirBubble extends Darts {
    int xpos, ypos;
    boolean isBubble = true;
    
    AirBubble() {
        this(0, (int) (Math.random() * (DartDodge.WORLD_HEIGHT + 1)));
    }
    
    private AirBubble(int xpos, int ypos) {
        this.xpos = xpos;
        this.ypos = ypos;
    }
    
    public boolean isBubble() {
        return true;
    }
    
    public void draw(ConsoleSystemInterface cons) {
        cons.print(this.xpos, this.ypos,     " xx ", cons.BLUE);
        cons.print(this.xpos, this.ypos + 1, "x  x", cons.BLUE);
        cons.print(this.xpos, this.ypos + 2, " xx ", cons.BLUE);
    }
    
    public AirBubble tick() {
        if (this.xpos + 4 == DartDodge.WORLD_WIDTH) {
            return new AirBubble();
        } else {
            return new AirBubble(this.xpos + 1, this.ypos);
        }
    }
    
    
}