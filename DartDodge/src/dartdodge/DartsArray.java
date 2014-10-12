package dartdodge;
import net.slashie.libjcsi.ConsoleSystemInterface;
import java.util.ArrayList;

public class DartsArray {
    ArrayList<Darts> darts = new ArrayList();
    int tickCount = 0;
    
    DartsArray() {
        darts.add(new Darts());
    }
    
    private DartsArray(ArrayList<Darts> darts, int tick) {
        this.darts = darts;
        this.tickCount = tick;
    }
    
    public void draw(ConsoleSystemInterface cons) {
        for (Darts e : darts) {
            e.draw(cons);
        }
    }
    
    public DartsArray tick() {
        this.tickCount++;
        if (tickCount % 50 == 0) {
            this.darts.add(new Darts());
        }
        
        if (tickCount % 150 == 0) {
            this.darts.add(new AirBubble());
        }

        for (int i = 0; i < darts.size(); i++) {
            this.darts.set(i, darts.get(i).tick());
        }
        return new DartsArray(this.darts, this.tickCount);
    }
}