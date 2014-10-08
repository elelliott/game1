package dartdodge;
import net.slashie.libjcsi.ConsoleSystemInterface;
import net.slashie.libjcsi.CharKey;

public class Balloon {
    int xpos, ypos;
    // dx and dy range between -1 and 1
    int dx, dy;
    // lifeCount ranges between 0 and 3
    int lifeCount;
    
    Balloon() {
        this(DartDodge.WORLD_WIDTH / 2, DartDodge.WORLD_HEIGHT / 2, 0, 0, 3);
    }
    
    private Balloon(int xpos, int ypos, int dx, int dy, int lives) {
        this.xpos = xpos;
        this.ypos = ypos;
        this.dx = dx;
        this.dy = dy;
        this.lifeCount = lives;
    }
    
    public Balloon loseLife() {
        return new Balloon(this.xpos, this.ypos, 
                this.dx, this.dy, this.lifeCount - 1);
    }
    
    public void draw(ConsoleSystemInterface cons) {
        cons.print(this.xpos, this.ypos,     "  ____   ", cons.WHITE);
        cons.print(this.xpos, this.ypos + 1, " /    \\ ", cons.WHITE);
        cons.print(this.xpos, this.ypos + 2, "|      |", cons.WHITE);
        cons.print(this.xpos, this.ypos + 3, " \\____/ ", cons.WHITE);
    }
    
    public Balloon react(CharKey ke) {
        if (ke.isUpArrow()) {
            return new Balloon(this.xpos, this.ypos, 0, -1, this.lifeCount);
        } else if (ke.isDownArrow()) {
            return new Balloon(this.xpos, this.ypos, 0, 1, this.lifeCount);
        } else if (ke.isLeftArrow()) {
            return new Balloon(this.xpos, this.ypos, -1, 0, this.lifeCount);
        } else if (ke.isRightArrow()) {
            return new Balloon(this.xpos, this.ypos, 1, 0, this.lifeCount);
        } else {
            return this;
        }
    }
    
    public Balloon tick() {
        int newX = this.xpos + this.dx;
        int newY = this.ypos + this.dy;
        
        // the ball loops past the y boundaries, but cannot move past
        // x boundaries
        if (newX > DartDodge.WORLD_WIDTH - 8) {
            // at right of world: can't move to right anymore
            return new Balloon(DartDodge.WORLD_WIDTH - 8, newY,
                    this.dx, this.dy, this.lifeCount);
        } else if (newX < 0) {
            // at left of world: can't move left anymore
            return new Balloon(0, newY, 
                    this.dx, this.dy, this.lifeCount);
        } else if (newY > DartDodge.WORLD_HEIGHT) {
            // at bottom of world: loop back to top
            return new Balloon(newX, 0, this.dx, this.dy, this.lifeCount);
        } else if (newY < 0) {
            // at top of world: loop to bottom
            return new Balloon(newX, DartDodge.WORLD_HEIGHT, 
                    this.dx, this.dy, this.lifeCount);
        } else {
            // if ball is not at boundaries, place it at the new coords
            return new Balloon(newX, newY, this.dx, this.dy, this.lifeCount);
        }
    }
}