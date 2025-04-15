package sk.tuke.kpi.kp.kp5.blockpuzzle.core;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class BlocksTest {
    @Test
    void testBlockInitialisation() {
        TileState[][] shape = new TileState[][] {
                {TileState.FILLED,TileState.FILLED},
                {TileState.EMPTY,TileState.EMPTY}
        } ;

        Block block = new Block(shape);

        assertEquals(0, block.getX());
        assertEquals(0, block.getY());
    }

    @Test
    void testMoveBlockIncorrectMovement() {
        Field field = new Field(10, 10, 10);
        Block block = BlockFactory.testBlockOneTile();

        field.placeBlock(block, field.getRowCountPlayArea() / 2, field.getColCountPlayArea() / 2);
        assertFalse(field.moveBlock(block, 1, 1), "You can't move block through more than 1 tile");
    }
    @Test
    void testMoveBlockMovingOutOfBounds() {
        Field field = new Field(10, 10, 10);
        Block block = BlockFactory.testBlockOneTile();

        field.placeBlock(block, 0, 0);
        assertFalse(field.moveBlock(block, -1, 0), "You can't move block out of boundaries");

    }

    @Test
    void testMoveBlockCollision() {
        Field field = new Field(10, 10, 5);
        Block block1 = BlockFactory.testBlockOneTile();
        Block block2 = BlockFactory.testBlockOneTile();

        block1.place(1, 1, field);
        block2.place(1, 2, field);

        boolean moved = block1.move(0, -1, field);

        assertFalse(moved, "Collision should prevent movement");
    }

    @Test
    void testMultiTileBlockMovement() {
        Field field = new Field(10, 10, 2);
        Block block = BlockFactory.TBlock(); // Or any multi-tile shape

        boolean placed = block.place(3, 3, field);
        assertTrue(placed);

        boolean moved = block.move(0, -1, field); // Move down
        assertTrue(moved);
    }

    @Test
    void testBlockNotNull() {
        Block block = BlockFactory.TBlock();
        for (int i = 0; i < block.getRowCount(); i++) {
            for (int j = 0; j < block.getColCount(); j++) {
                assertNotNull(block.getShapeTile(i, j), "Any Tile in the block should be not null");
            }
        }
    }

    @Test
    void testBlock_1x1() {
        Block block = BlockFactory.Block_1x1();
        assertEquals(2, block.getRowCount());
        assertEquals(2, block.getColCount());
    }

    @Test
    void testBlock_2x2() {
        Block block = BlockFactory.Block_2x2();
        assertEquals(4, block.getRowCount());
        assertEquals(4, block.getColCount());
    }

    @Test
    void testBlock_3x1() {
        Block block = BlockFactory.Block_3x1();
        assertEquals(2, block.getRowCount());
        assertEquals(6, block.getColCount());

    }

    @Test
    void testBlock_1x4() {
        Block block = BlockFactory.Block_1x4();
        assertEquals(8, block.getRowCount());
        assertEquals(2, block.getColCount());
    }

    @Test
    void testTBlock() {
        Block block = BlockFactory.TBlock();

        assertEquals(4, block.getRowCount());
        assertEquals(6, block.getColCount());
    }

    @Test
    void testLBlock() {
        Block block = BlockFactory.LBlock();
        assertEquals(6, block.getRowCount());
        assertEquals(4, block.getColCount());
    }

    @Test
    void testLBlock_mirrored() {
        Block block = BlockFactory.LBlock_mirrored();

        assertEquals(6, block.getRowCount());
        assertEquals(4, block.getColCount());
    }
}