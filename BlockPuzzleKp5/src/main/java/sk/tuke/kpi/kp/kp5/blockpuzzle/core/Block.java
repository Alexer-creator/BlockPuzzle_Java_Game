package sk.tuke.kpi.kp.kp5.blockpuzzle.core;

public class Block {
    private final Tile[][] shape;
    private final int rowCount;
    private final int colCount;
    private int x;
    private int y;

    public Block(TileState[][] blockShape) {
        this.rowCount = blockShape.length;
        this.colCount = blockShape[0].length;
        this.shape = new Tile[rowCount][colCount];

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                this.shape[i][j] = new GameFieldTile();
                this.shape[i][j].setState(blockShape[i][j]);
            }
        }
        this.x = 0;
        this.y = 0;
    }

    public boolean move(int dx, int dy, Field field) {
        if (Math.abs(dx) + Math.abs(dy) > 1) {
            System.out.println("------------------------------------------");
            System.out.println("Step over more than one Tile is forbidden!");
            System.out.println("------------------------------------------");
            return false;
        }

        int xNew = x + dx;  // Move in width direction
        int yNew = y - dy;  // Move in height direction

        // Temporary erasing of the block
        eraseBlock(field);

        boolean canMove = true;
        // Check if the new position is within bounds for the **entire block**
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                if (shape[i][j].getState() == TileState.FILLED) {
                    int testTileY = yNew + i;
                    int testTileX = xNew + j;

                    if (testTileX < 0 || testTileX >= field.getColCountPlayArea() + field.getSpawnSize() ||
                        testTileY < 0 || testTileY >= field.getRowCountPlayArea() + field.getSpawnSize()) {

                        System.out.println("-------------------------------------");
                        System.out.println("Placement out of bounds is forbidden!");
                        System.out.println("-------------------------------------");
                        canMove = false;
                    }

                    else if (field.getTile(testTileY,testTileX).getState() == TileState.FILLED) {
                        System.out.println("----------------------------------------");
                        System.out.println("Collision! Another block is placed here!");
                        System.out.println("----------------------------------------");
                        canMove = false;
                    }

                }
                if (!canMove) break;
            }
            if (!canMove) break;
        }

        // Return block to its previous position if we can't move the block
        if (!canMove) {
            placeBlockOnField(field);
            return false;
        }

        // Update coordinates of block
        this.x = xNew;
        this.y = yNew;

        // place block into new position
        placeBlockOnField(field);

        return true;
    }

    public boolean place(int x, int y, Field field) {
        // check if block can be placed
        for (int i = 0; i < rowCount; i++ ) {
            for (int j = 0; j < colCount; j++) {
                if (shape[i][j].getState() == TileState.FILLED) {
                    int newTileY = y + i;
                    int newTileX = x + j;

                    if (newTileX < 0 || newTileX >= field.getColCountPlayArea() + field.getSpawnSize() ||
                        newTileY < 0 || newTileY >= field.getRowCountPlayArea() + field.getSpawnSize()) {
                        System.out.println("-------------------------------------");
                        System.out.println("Placement out of bounds is forbidden!");
                        System.out.println("-------------------------------------");
                        return false;
                    }

                    if (field.getTile(newTileY, newTileX).getState() == TileState.FILLED) {
                        System.out.println("-----------------------------------------");
                        System.out.println("Collision! That spot is already occupied!");
                        System.out.println("-----------------------------------------");
                        return false;
                    }
                }
            }
        }

        // place the block
        this.x = x;
        this.y = y;

        // set new position
        placeBlockOnField(field);

        return true;
    }

    private void eraseBlock(Field field) {
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                if (shape[i][j].getState() == TileState.FILLED) {
                    field.getTile(y + i, x + j).setState(TileState.EMPTY);
                }
            }
        }
    }

    private void placeBlockOnField(Field field) {
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                if (shape[i][j].getState() == TileState.FILLED) {
                    field.getTile(y + i, x + j).setState(TileState.FILLED);
                }
            }
        }
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColCount() {
        return colCount;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public Tile getShapeTile(int row, int col) {
        return shape[row][col];
    }

}
