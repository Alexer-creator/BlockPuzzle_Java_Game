package sk.tuke.kpi.kp.kp5.blockpuzzle.consoleui;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sk.tuke.kpi.kp.kp5.blockpuzzle.core.*;
import sk.tuke.kpi.kp.kp5.blockpuzzle.entity.Comment;
import sk.tuke.kpi.kp.kp5.blockpuzzle.entity.Rate;
import sk.tuke.kpi.kp.kp5.blockpuzzle.entity.Score;
import sk.tuke.kpi.kp.kp5.blockpuzzle.service.CommentService;
import sk.tuke.kpi.kp.kp5.blockpuzzle.service.RatingService;
import sk.tuke.kpi.kp.kp5.blockpuzzle.service.ScoreService;


import java.util.*;
@Component
public class ConsoleUI {
    private final Field field;

    @Autowired
    private ScoreService scoreService; // = new ScoreServiceJDBC();
    @Autowired
    private RatingService ratingService;
    @Autowired
    private CommentService commentService;

    private final List<Block> blocks;
    private final List<String> blockHints;
    private int activeBlockIndex;
    private final Scanner scanner;

    private static final int MAX_MOVES = 3;
    private int gameMoves = 0;


    private String userName;

    // DESIGN
    private static final String GAME_FIELD_TILE= "🟦";
    private static final String SPAWNER_TILE =   "🟨";
    private static final String BLOCK_TILE =     "🟪";

    private static final String RESET = "\u001B[0m";  // Reset color
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";

    public ConsoleUI(Field gameField) {
        this.field = gameField;
        scanner = new Scanner(System.in);  // Create scanner to read user input
        blocks = new ArrayList<>();
        blockHints = new ArrayList<>();
        activeBlockIndex = 0;
    }

    public boolean play() {
        //scoreService.addScore( new Score("mines","jana", 503, new Date()));

        //manage blocks
        generateBlocksList();
        generateBlockHintsList();
        placeBlocks();
        //
        // Show hall of fame

        printHallOfFame();

        System.out.print(GREEN + "Please Enter your name: " + RESET);
        userName = scanner.nextLine();
        // Show the initial state
        do {
            show();
            handleInputWhilePlaying();

            field.isSolved();
        }  while (field.getGameState() == GameState.PLAYING);

        show();

        if (field.getGameState() == GameState.SOLVED) {
            int score = (MAX_MOVES - gameMoves) * 7;
            scoreService.addScore(new Score(userName, "BlockPuzzle", score, new Date()));

            System.out.println(YELLOW + "------------------------------------------------------------------------------------------------------------------------------" + RESET);
            System.out.println(YELLOW + "Congratulations "+ PURPLE + userName + YELLOW + "!!! You've solved the game with a score " + BLUE + score + YELLOW + "! Can You see your name in the HALL OF FAME??" + RESET);
            System.out.println(YELLOW + "------------------------------------------------------------------------------------------------------------------------------" + RESET);

            printHallOfFame();

        } else if (field.getGameState() == GameState.FAILED) {
            System.out.println( RED + "------------------------------------------------------------------------------------------------");
            System.out.println( RED + "Game Over " + userName + "! You lost!");
            System.out.println( RED + "------------------------------------------------------------------------------------------------" + RESET);
        }

        // After game SOLVED/FAILED
        while (true) {
            String action = handleInputAfterEndOfGame();
            if (action.equals("replay")) {
                return true;
            } else if (action.equals("q")) {
                System.exit(0);
            }

        }
    }

    public void show() {
        for (int i = 0; i < field.getRowCountPlayArea() + field.getSpawnSize(); i++) {
            for (int j = 0; j < field.getColCountPlayArea() + field.getSpawnSize(); j++) {
                Tile tile = field.getTile(i, j);

                if (tile.getState() == TileState.FILLED) {
                    System.out.print(BLOCK_TILE);
                } else if (i >= field.getRowCountPlayArea() || j >= field.getColCountPlayArea()) {
                    System.out.print(SPAWNER_TILE);  // Spawn area
                } else {
                    System.out.print(GAME_FIELD_TILE);  // Empty tile
                }
            }
            System.out.println();
        }

        System.out.println(CYAN + "\nActive block: " + RESET + (activeBlockIndex + 1) + CYAN + "/" + RESET + blocks.size());
        System.out.println(blockHints.get(activeBlockIndex));
        System.out.println(BLUE + "Moves left: " + RESET + (MAX_MOVES - gameMoves));
    }

    private void handleInputWhilePlaying() {
        System.out.println(GREEN + "--------------------------------------------------------------------------------------" + RESET);
        System.out.print(GREEN + "Use " + YELLOW + "WASD" + GREEN + " to move the block and n/p to switch them, 'n' (next), 'p' (previous): " + RESET);
        String input = scanner.nextLine();  // Read input from the user

        Block activeBlock = blocks.get(activeBlockIndex);

        boolean moved = false;

        switch (input.toLowerCase()) {
            case "w":
                moved = field.moveBlock(activeBlock, 0, 1);  // Move up
                break;
            case "s":
                moved = field.moveBlock(activeBlock, 0, -1);   // Move down
                break;
            case "a":
                moved = field.moveBlock(activeBlock, -1, 0);  // Move left
                break;
            case "d":
                moved = field.moveBlock(activeBlock, 1, 0);   // Move right
                break;

            case "n":
                addIndex();
                break;

            case "p":
                subIndex();
                break;

            default:
                System.out.println( RED +"------------------------------------------" + RESET);
                System.out.println(RED +"Invalid input! Please use w, s, a, d or n/p: " + RESET);
                System.out.println(RED + "------------------------------------------" + RESET);
                return;
        }

        if (moved) {
            gameMoves++;
        }
        if (gameMoves >= MAX_MOVES) {
            field.setGameState(GameState.FAILED);
        }
    }

    private String handleInputAfterEndOfGame() {
        System.out.println(GREEN + "T------------------------------------------------------------------|" + RESET);
        System.out.println(GREEN + "| Press" + YELLOW  +  " 'c' " + GREEN + "to leave a comment");
        System.out.println(GREEN + "| Press" + CYAN +   " 'r' " + GREEN + "to rate the game");
        System.out.println(GREEN + "| Press" + PURPLE +   " 'l' " + GREEN + "to load new game(replay)");
        System.out.println(GREEN + "| Press" + RED +      " 'q' " + GREEN + "to quit the game" + RESET);
        System.out.println(YELLOW+ "|------------------------------------------------------------------|" + RESET);
        System.out.println(GREEN + "| Press" + CYAN +      " 'm' " + GREEN + "to see other players ratings" + RESET);
        System.out.println(GREEN + "| Press" + BLUE +      " 'k' " + GREEN + "to read the comments" + RESET);
        System.out.println(GREEN + "T------------------------------------------------------------------|" + RESET);
        System.out.print(YELLOW + "Enter here: ");

        String input = scanner.nextLine();
        switch (input.toLowerCase()) {
            case "c":
                // here we should implement mechanism of adding comment to database (comments)
                System.out.println(GREEN + "T------------------------------------------------------------------|");
                System.out.print("Please left your comment here: ");
                String commentBody = scanner.nextLine();
                commentService.addComment(new Comment(userName,"BlockPuzzle",commentBody, new Date()));
                System.out.println(PURPLE + "Your comment was successfully saved" + RESET);
                break;
            case "r":
                // here we should implement mechanism of adding rate in database (rate)
                System.out.println(GREEN + "------------------------------------------------------------------|" + RESET);
                System.out.print(GREEN + "Please rate the game here(1 to 10): " + RESET);
                int rate = Integer.parseInt(scanner.nextLine());
                ratingService.addRate(new Rate(userName,"BlockPuzzle", rate));
                System.out.println(PURPLE + "Your rate was successfully saved" + RESET);
                break;
            case "l":
                System.out.println(CYAN);
                for (int i = 0; i < 3; i++) {
                    slowDesignPrint("Starting new game...",240 - (i+1)*15);
                }
                System.out.println(RESET);
                input = "replay";
                break;
            case "q":
                System.exit(0);
                break;

            case "m":
                printRates();
                // implement showing list of marks
                break;
            case "k":
                printComments();
                // implement menu with comments
                break;
        }

        return input;
    }

    private void addIndex() {
        activeBlockIndex = (activeBlockIndex + 1) % blocks.size(); // Move forward
        System.out.println(CYAN + "Switched to Block " + (activeBlockIndex + 1));
    }

    private void subIndex() {
        activeBlockIndex = (activeBlockIndex - 1 + blocks.size()) % blocks.size(); // move back
        System.out.println(CYAN + "Switched to Block " + (activeBlockIndex + 1));
    }

    private void generateBlocksList() {
        blocks.add(BlockFactory.Block_1x1());
        blocks.add(BlockFactory.Block_1x4());
        blocks.add(BlockFactory.Block_3x1());
        blocks.add(BlockFactory.TBlock());
        blocks.add(BlockFactory.Block_1x1());
        blocks.add(BlockFactory.Block_2x2());
        blocks.add(BlockFactory.LBlock());
        blocks.add(BlockFactory.LBlock_mirrored());
    }

    private void generateBlockHintsList() {
        blockHints.add(PURPLE + "////" + RESET);
        blockHints.add(PURPLE + "////\n////\n////\n////" + RESET);
        blockHints.add(PURPLE + "//// //// ////" + RESET);
        blockHints.add(PURPLE + "//// //// ////\n     ////" + RESET);
        blockHints.add(PURPLE + "////" + RESET);
        blockHints.add(PURPLE + "//// ////\n//// ////" + RESET);
        blockHints.add(PURPLE + "////\n////\n//// /////" + RESET);
        blockHints.add(PURPLE + "//// ////\n    ////\n   ////" + RESET);
    }

    public void placeBlocks() {
        field.placeBlock(blocks.get(0), 17,5);    // 1x1_Block
        field.placeBlock(blocks.get(1), 1,10);    // 1x4_Block
        field.placeBlock(blocks.get(2), 14, 18);  // 3x1_Block
        field.placeBlock(blocks.get(3), 11,1);    // TBlock
        field.placeBlock(blocks.get(4), 4,11);         // 1x1
        field.placeBlock(blocks.get(5), 11,6);         // 2x2
        field.placeBlock(blocks.get(6), 8,13);         // L
        field.placeBlock(blocks.get(7), 15,11);         // _|

    }

    private void printHallOfFame() {
        List<Score> HallOfFame = scoreService.getTopScores("BlockPuzzle");

        System.out.println(YELLOW + "}-------------------------------------["+ PURPLE + "HALL_OF_FAME" + YELLOW + "]-------------------------------------{" + RESET);
        int i = 0;
        while (i < HallOfFame.size()) {
            Score score = HallOfFame.get(i);
            System.out.printf(YELLOW + "[%d. " + CYAN + "%s" + PURPLE + "    %d]\n" + RESET, i + 1 , score.getPlayerName(),score.getPoints());
            i++;
        }
        System.out.println(YELLOW + "}----------------------------------------------------------------------------------------{" + RESET);
    }

    private void slowDesignPrint(@NotNull String text, int delayMillis) {
        for (char c : text.toCharArray()) {
            System.out.print(c);
            try {
                Thread.sleep(delayMillis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println();
    }

    private void printRates(){
        List<Rate> topRates = ratingService.getTopRates("BlockPuzzle");

        // Printing TopRates
        System.out.println(BLUE + "}-------------------------------------["+ CYAN + "TOP_RATES" + BLUE + "]-------------------------------------{" + RESET);
        System.out.println("|---------------------------------------------------|");
        for (int i = 0; i < topRates.size(); i++) {
            Rate topRate = topRates.get(i);
            System.out.printf(YELLOW + "%d." + CYAN + "%s" + PURPLE + "    %d\n" + RESET, i + 1 , topRate.getPlayerName(),topRate.getRate());
            System.out.println("|---------------------------------------------------|");
        }

        // Printing WorstRates
        List<Rate> worstRates = ratingService.getWorstRates("BlockPuzzle");
        System.out.println(BLUE + "}-----------------------------------["+ RED + "WORST_RATES" + BLUE + "]-------------------------------------{" + RESET);
        System.out.println("|---------------------------------------------------|");
        for (int i = 0; i < worstRates.size(); i++) {
            Rate worstRate = worstRates.get(i);
            System.out.printf(YELLOW + "%d." + CYAN + "%s" + PURPLE + "    %d\n" + RESET, i + 1 , worstRate.getPlayerName(),worstRate.getRate());
            System.out.println("|---------------------------------------------------|");
        }

    }

    private void printComments() {
        List<Comment> comments = commentService.getComments("BlockPuzzle");
        // Printing Comment
        System.out.println(BLUE + "}--------------------------------------------------------["+ CYAN + "COMMENTS" + BLUE + "]--------------------------------------------------------{" + RESET);
        System.out.println(YELLOW + "|-------------------------------------------------------------------------------------------------------------------|" + RESET);
        for (int i = 0; i < comments.size(); i++) {
            Comment comment = comments.get(i);
            System.out.printf( PURPLE + "  --%s |" + RESET + "  %s  " + CYAN + " '%s'\n" + RESET,comment.getDate(), comment.getCommentBody(),comment.getPlayerName());
            System.out.println(YELLOW + "|-------------------------------------------------------------------------------------------------------------------|" + RESET);
        }
    }


}
