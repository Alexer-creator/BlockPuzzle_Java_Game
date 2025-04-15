package sk.tuke.kpi.kp.kp5.blockpuzzle.core;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class FieldTest {
    @Test
    void testFieldInitialisation() {
        Field field = new Field(10,10,10);
        assertEquals(10,field.getRowCountPlayArea(),"Row count of play area should be 10 in our case");
        assertEquals(10,field.getColCountPlayArea(),"Col count of play area should be 10 in our case");
        assertEquals(10,field.getSpawnSize(),       "Spawn size should be 10 in our case");

        assertEquals(20,field.getRowCountPlayArea() + field.getSpawnSize(), "Full field area(row count + spawn area) should be 20 in our case");
        assertEquals(20, field.getColCountPlayArea() + field.getSpawnSize(),"Full field area(col count + spawn area) should be 20 in our case");
    }
    @Test
    void testGameIsSolved() {
        Field field = new Field(10,10,10);
        // isSolved testing
        for (int i = 0; i < field.getRowCountPlayArea(); i++) {
            for (int j = 0; j < field.getColCountPlayArea(); j++) {
                field.getTile(i,j).setState(TileState.FILLED);
            }
        }
        assertTrue(field.isSolved(), "Game should be solved if all Tiles in 2D gameFieldTile[][] are FILLED");
    }

    @Test
    void testGameIsNotSolved() {
        Field field = new Field(10,10,10);
        // isSolved testing
        for (int i = 0; i < field.getRowCountPlayArea(); i++) {
            for (int j = 0; j < field.getColCountPlayArea(); j++) {
                field.getTile(i,j).setState(TileState.FILLED);
            }
        }

        int i = new Random().nextInt(field.getRowCountPlayArea() - 1) + 1;
        int j = new Random().nextInt(field.getColCountPlayArea() - 1) + 1;
        field.getTile(i,j).setState(TileState.EMPTY);

        assertFalse(field.isSolved(),"If at least one Tile in 2D gameFieldTile[][] are EMPTY the game is NOT solved");

    }

    @Test
    void testGetTile() {
        Field field = new Field(10,10,10);

        int i = new Random().nextInt(field.getRowCountPlayArea());
        int j = new Random().nextInt(field.getColCountPlayArea());
        Tile tile = field.getTile(i,j);
        assertNotNull(tile,"Tile should be found");
    }


    @Test
    void testPlaceBlock() {
        Field field = new Field(10,10,10);
        Block block = BlockFactory.testBlockOneTile();

        int i = new Random().nextInt(field.getRowCountPlayArea() - 1) + 1;
        int j = new Random().nextInt(field.getColCountPlayArea() - 1) + 1;

        field.placeBlock(block,i,j);
        assertSame(TileState.FILLED, field.getTile(j, i).getState());
    }
    @Test
    void testSetGetGameState() {
        Field field = new Field(10,10,10);

        field.setGameState(GameState.PLAYING);

        assertSame(GameState.PLAYING, field.getGameState());

    }

    @Test
    void testMoveBlockMovingToParticularTile() {
        Field field = new Field(10, 10, 10);
        Block block = BlockFactory.testBlockOneTile();

        field.placeBlock(block, field.getRowCountPlayArea() / 2, field.getColCountPlayArea() / 2);

        field.placeBlock(block, 0, 0);
        int RandomDirectionStepX = new Random().nextInt(2);
        int RandomDirectionStepY = new Random().nextInt(2);

        field.moveBlock(block, RandomDirectionStepX, RandomDirectionStepY);
        assertEquals(TileState.FILLED, field.getTile(block.getY(), block.getX()).getState(), "State of the current block after movement should be FILLED");
    }
}