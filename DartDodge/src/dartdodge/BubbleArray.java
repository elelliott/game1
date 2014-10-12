package dartdodge;
import net.slashie.libjcsi.ConsoleSystemInterface;
import java.util.ArrayList;

public class BubbleArray extends DartsArray {
    ArrayList<AirBubble> bubbles = new ArrayList();
    
    BubbleArray() {
        bubbles.add(new AirBubble());
    }
    
    private BubbleArray(ArrayList<AirBubble> bubbles) {
        this.bubbles = bubbles;
    }
    
    public void draw(ConsoleSystemInterface cons) {
        for (AirBubble b : bubbles) {
            b.draw(cons);
        }
    }
    
    public BubbleArray tick() {
        if (tickCount % 150 == 0) {
            this.bubbles.add(new AirBubble());
        }

        for (int i = 0; i < bubbles.size(); i++) {
            this.bubbles.set(i, bubbles.get(i).tick());
        }
        return new BubbleArray(this.bubbles);
    }
}