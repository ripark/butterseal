package edu.smcm.gamedev.butterseal;

public class BSTile {
    public int x, y;
    public BSTile(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public BSTile(BSTile other) {
        this(other.x, other.y);
    }
}

// Local Variables:
// indent-tabs-mode: nil
// End:
