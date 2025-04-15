package sk.tuke.kpi.kp.kp5.blockpuzzle.core;

public abstract class BlockFactory {
    public static Block testBlockOneTile() {
        return new Block(new TileState[][] {
                {TileState.FILLED}
        });
    }

    public static Block Block_1x1() {
        return new Block(new TileState[][] {
                {TileState.FILLED, TileState.FILLED},
                {TileState.FILLED, TileState.FILLED},
        });
    }

    public static Block Block_1x4() {
        return new Block(new TileState[][] {
                {TileState.FILLED, TileState.FILLED},
                {TileState.FILLED, TileState.FILLED},
                {TileState.FILLED, TileState.FILLED},
                {TileState.FILLED, TileState.FILLED},
                {TileState.FILLED, TileState.FILLED},
                {TileState.FILLED, TileState.FILLED},
                {TileState.FILLED, TileState.FILLED},
                {TileState.FILLED, TileState.FILLED}
        });
    }

    public static Block Block_3x1() {
        return new Block(new TileState[][] {
                {TileState.FILLED, TileState.FILLED, TileState.FILLED, TileState.FILLED, TileState.FILLED, TileState.FILLED},
                {TileState.FILLED, TileState.FILLED, TileState.FILLED, TileState.FILLED, TileState.FILLED, TileState.FILLED},
        });
    }

    public static Block TBlock() {
        return new Block(new TileState[][] {
                {TileState.FILLED, TileState.FILLED, TileState.FILLED, TileState.FILLED, TileState.FILLED, TileState.FILLED},  //        \\\///
                {TileState.FILLED,  TileState.FILLED, TileState.FILLED, TileState.FILLED, TileState.FILLED, TileState.FILLED},  //         ||
                {TileState.EMPTY,  TileState.EMPTY,  TileState.FILLED,  TileState.FILLED,  TileState.EMPTY,  TileState.EMPTY}, //          ||
                {TileState.EMPTY,  TileState.EMPTY,  TileState.FILLED, TileState.FILLED,  TileState.EMPTY,  TileState.EMPTY},
        });
    }

    public static Block LBlock() {
        return new Block(new TileState[][] {
                {TileState.FILLED, TileState.FILLED,  TileState.EMPTY,  TileState.EMPTY},  //           //
                {TileState.FILLED, TileState.FILLED,  TileState.EMPTY,  TileState.EMPTY},  //           //
                {TileState.FILLED, TileState.FILLED,  TileState.EMPTY,  TileState.EMPTY},   //           /\///
                {TileState.FILLED, TileState.FILLED,  TileState.EMPTY,  TileState.EMPTY},
                {TileState.FILLED, TileState.FILLED,  TileState.FILLED, TileState.FILLED},
                {TileState.FILLED, TileState.FILLED,  TileState.FILLED, TileState.FILLED},
        });
    }

    public static Block LBlock_mirrored() {
        return new Block(new TileState[][] {
                {TileState.FILLED, TileState.FILLED,  TileState.FILLED,  TileState.FILLED},  //           //
                {TileState.FILLED, TileState.FILLED,  TileState.FILLED,  TileState.FILLED},  //           //
                {TileState.EMPTY, TileState.EMPTY,  TileState.FILLED,  TileState.FILLED},   //           /\///
                {TileState.EMPTY, TileState.EMPTY,  TileState.FILLED,  TileState.FILLED},
                {TileState.EMPTY, TileState.EMPTY,  TileState.FILLED,  TileState.FILLED},
                {TileState.EMPTY, TileState.EMPTY,  TileState.FILLED,  TileState.FILLED},   //          ///\/
        });
    }

    public static Block Block_2x2() {
        return new Block(new TileState[][] {
                {TileState.FILLED, TileState.FILLED, TileState.FILLED, TileState.FILLED},
                {TileState.FILLED, TileState.FILLED, TileState.FILLED, TileState.FILLED},
                {TileState.FILLED, TileState.FILLED, TileState.FILLED, TileState.FILLED},
                {TileState.FILLED, TileState.FILLED, TileState.FILLED, TileState.FILLED}
        });
    }
}
