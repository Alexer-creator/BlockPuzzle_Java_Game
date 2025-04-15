    package sk.tuke.kpi.kp.kp5.blockpuzzle.core;

    public class Field {
        private final Tile[][] field;

        private GameState gameState;
        private final int spawnSize;
        private final int rowCountPlayArea;
        private final int colCountPlayArea;

        public Field(int rowCount, int colCount, int spawnSize) {
            this.rowCountPlayArea = rowCount;
            this.colCountPlayArea = colCount;
            this.spawnSize = spawnSize;
            this.gameState = GameState.PLAYING ;

            field = new Tile[rowCount + spawnSize][colCount + spawnSize];

            for (int i = 0; i < rowCount + spawnSize; i++) {
                for (int j = 0; j < colCount + spawnSize; j++) {

                    // A) If in top-left region => Play area
                    if (i < rowCount && j < colCount) {
                        field[i][j] = new GameFieldTile();

                        // B) If in bottom-right region => Spawn area
                    } else if (i >= rowCount && j >= colCount) {
                        field[i][j] = new BlockAreaTile();

                        // C) Otherwise => leftover region
                    }
                    else {
                        field[i][j] = new GameFieldTile();
                    }
                }
            }
        }

        public boolean isSolved() {
            for (int i = 0; i < rowCountPlayArea; i++) {
                for (int j = 0; j < colCountPlayArea; j++) {
                    if (field[i][j].getState() == TileState.EMPTY) {
                        return false;
                    }
                }
            }
            gameState = GameState.SOLVED;
            return true;
        }

        public Tile getTile(int row, int col) {
            return field[row][col];
        }

        public int getRowCountPlayArea() {
            return rowCountPlayArea;
        }

        public int getColCountPlayArea() {
            return colCountPlayArea;
        }

        public int getSpawnSize() {
            return spawnSize;
        }

        public GameState getGameState() {
            return gameState;
        }

        public void setGameState(GameState gameState) {
            this.gameState = gameState;
        }

        public boolean moveBlock(Block block, int dx, int dy) {
            return block.move(dx,dy,this);
        }

        public boolean placeBlock(Block block, int dx, int dy) {
            return block.place(dx,dy, this);
        }
    }
