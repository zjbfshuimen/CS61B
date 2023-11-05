package byog.lab5;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class MazeWorld {
    private static final long seed = 880979938;
    private static final Random RANDOM = new Random(seed);
    private static final int WIDTH = 81;
    private static final int HEIGHT = 31;
    private static final int length = 5;


    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];

        initialization(world);
        iterateWithRandom(world);

        ter.renderFrame(world);
    }

    /**
     * Initialize the world with Nothing
     */
    private static void initialization(TETile[][] world) {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }
    }

    /**
     * divide the world into 5 x 5 square, at each square decide which graph it would be set to
     * where i and j index is the absolute index
     */
    private static void iterateWithRandom(TETile[][] world) {
        for (int i = 1; i <= (WIDTH - 1 - length); i += length) {
            for (int j = 1; j <= (HEIGHT - 1 - length); j += length) {
                if (world[1 + i][1 + j ] == Tileset.NOTHING) {
                    setRandomGraph(world, i, j);
                }
            }
        }
    }

    /**
     * at each square left bottom 1, decide whether this square is a corner or rectangle
     */
    private static void setRandomGraph(TETile[][] world, int i, int j) {
        int r = randomNum(3);
        switch (r) {
            case 0:
                setRandomCorner(world, i, j);
            case 1:
                setRandomRectangle(world, i, j);
                break;
            default:
                break;
        }

    }

    /**
     * draw a random length and random width rectangle at given (i, j)
     */
    private static void setRandomRectangle(TETile[][] world, int i, int j) {
        int width = 0, height = 0;
        height = randomOddNum(2 * length);
        width = setRandomRectangleWidth(height, j);

        drawRectangle(world, i, j, width, height);
    }
    /**
     * set the rectangle to GRASS and the used square to unlock for the latter maze path
     */
    private static void drawRectangle(TETile[][] world, int i, int j, int width, int height) {

        for (int m = 0; m < width; m++) {
            for (int n = 0; n < height; n++) {
                world[i + m][j + n] = Tileset.GRASS;
            }
        }
    }

    /**
     * Decide the rectangle's width according to its height
     */
    private static int setRandomRectangleWidth(int height, int jIndex) {

        return randomOddNum(4);
    }

    /**
     * draw a random length and random width corner at given (i, j)
     */
    private static void setRandomCorner(TETile[][] world, int i, int j) {
        int width = 0, height = 0;
        height = randomOddNum(length * 2);
        width = setRandomCornerWidth(height, j);

    }

    /**
     * set the corner to GRASS
     */
    private static void drawCorner(TETile[][] world, int i, int j, int width, int height) {
        int k, l;
        for (k = 0; k < width; k++) {
            world[i + k][j] = Tileset.GRASS;
        }
        for (l = 0; l < height; l++) {
            world[i + width - 1][j + l] = Tileset.GRASS;
        }
    }

    /**
     * return a random for a conner according to its height
     */
    private static int setRandomCornerWidth(int height, int jIndex) {
        if (height == 1) {
            return randomOddNum(length * 2);
        }
        return 1;
    }

    /**
     * Generate a random between 0(inclusive) and bound(exclusive)
     */
    private static int randomNum(int bound) {
        return RANDOM.nextInt(bound + 1);
    }

    /**
     * Generate an odd random number between 1 and bound
     */
    private static int randomOddNum(int bound) {
        int returnNum;
        returnNum = RANDOM.nextInt(bound + 1);
        if (returnNum % 2 == 1) {
            return returnNum;
        }
        return 1;
    }


}
