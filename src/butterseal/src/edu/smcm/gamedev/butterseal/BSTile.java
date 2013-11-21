package edu.smcm.gamedev.butterseal;

import java.util.Map;

public class BSTile {
    public int x, y;
    public BSTile(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public BSTile(BSTile other) {
        this(other.x, other.y);
    }

    public Map<String,Map<String,String>> getProperties(BSMap map) {
        return null;
    }
}

// Local Variables:
// indent-tabs-mode: nil
// End:
