package sk.tuke.kpi.kp.kp5.blockpuzzle.core;

public abstract class Tile {
    private TileState state;

    public Tile(TileState state) {
        this.state = state;
    }

    public TileState getState() {
        return state;
    }

    public void setState(TileState state) {
        this.state = state;
    }
}
