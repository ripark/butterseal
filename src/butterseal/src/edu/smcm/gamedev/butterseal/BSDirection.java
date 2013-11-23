package edu.smcm.gamedev.butterseal;

public enum BSDirection {
    NORTH(0,1),
    SOUTH(0,-1),
    EAST(1,0),
    WEST(-1,0);

    int dx, dy;

    BSDirection(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }
}

// Local Variables:
// indent-tabs-mode: nil
// End:
