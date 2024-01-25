package byog.lab5;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

public class MazeWorld {
    private static final long seed = 8879938;
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
        reinitializeWithOneZero(world);

//        findPath(world);

        ter.renderFrame(world);
    }

    /**
     * find a connected path in the rest 0 and 1
     * which are NOTHING and SAND
     */
    public static void findPath(TETile[][] world) {
        Point starter = findFirst(world);
        connectAPath(world, starter);


    }
    /**
     * return a Point as the starter;
     */
    private static Point findFirst(TETile[][] world) {
        int i, j;
        for (i = 1; i < WIDTH; i += 2) {
            for (j = 1; j < HEIGHT; j += 2) {
                if (world[i][j] == Tileset.SAND) {
                    return new Point(i, j);
                }
            }
        }
        return null;
    }

    public static void connectAPath(TETile[][] world, Point pos) {
        Deque<Point> chosen = new ArrayDeque<>();
        Point sourddending;
        boolean haveSANDAround = haveRoadToGo(world, pos);

        // base case:
        if (!haveSANDAround) {
            chosen.pollLast();
            return;
        }
        // chose

            sourddending = returnASurround(world, pos);
            chosen.addLast(sourddending);

            connect(world, pos, sourddending);
            connectAPath(world, sourddending);
        // explore

        // un choose
    }

    /**
     * change the given two SAND to MOUNTAIN
     */
    public static void connect(TETile[][] world, Point starter, Point toConnected) {
        world[starter.x][starter.y] = Tileset.MOUNTAIN;
        world[toConnected.x][toConnected.y] = Tileset.MOUNTAIN;
        world[(starter.x + toConnected.x) / 2][(starter.y + toConnected.y) / 2] = Tileset.MOUNTAIN;
    }

    /**
     * Return a valid surround of the given pos where the value is SAND
     * although default is return null, using haveWayToGo before returnASurround
     */
    public static Point returnASurround(TETile[][] world, Point pos) {
        int r = RANDOM.nextInt(4);
        Point returnPoint = new Point();
        Point left = new Point(pos.x - 2, pos.y);
        Point right = new Point(pos.x + 2, pos.y);
        Point top = new Point(pos.x, pos.y + 2);
        Point bottom = new Point(pos.x, pos.y - 2);

        // 返回一个有效的随机方位
        while (true) {
            switch (r) {
                case 0:
                    if (isSAND(world, left)) {
                        return left;
                    }
                    break;
                case 1:
                    if (isSAND(world, right)) {
                        return right;
                    }
                    break;
                case 2:
                    if (isSAND(world, top)) {
                        return top;
                    }
                    break;
                case 3:
                    if (isSAND(world, bottom)) {
                        return bottom;
                    }
                    break;
                default: return null;
            }
        }
    }

    /**
     *  Check if there is SAND around given Point
     *  if so, return true
     */
    public static boolean haveRoadToGo(TETile[][] world, Point pos) {
        boolean haveRestSAND = false;
        haveRestSAND = isSAND(world, new Point(pos.x - 2, pos.y)) || isSAND(world, new Point(pos.x + 2, pos.y)) ||
                isSAND(world, new Point(pos.x, pos.y - 2)) || isSAND(world, new Point(pos.x, pos.y + 2));

        return haveRestSAND;
    }

    /**
     *  Check if the given pos is a SAND and by the way check if the pos is valid
     *  else return false
     */
    public static boolean isSAND(TETile[][] world, Point pos) {
        if (pos.x >= 0 && pos.x <= WIDTH && pos.y >= 0 && pos.y <= HEIGHT) {
            return world[pos.x][pos.y] == Tileset.SAND;
        }
        return false;
    }

    /**
     *  Check if the given Point is out of bounds
     */
    public static boolean isOutOfBounds(Point pos) {
        return pos.x < 0 || pos.x > WIDTH || pos.y < 0 || pos.y > HEIGHT;

    }

    /**
     * after random setting rectangle and corner, fill the rest of the world with SAND in odd entry
     * which is, SAND
     */
    public static void reinitializeWithOneZero(TETile[][] world) {
        for (int i = 1; i < WIDTH; i += 2) {
            for (int j = 1; j < HEIGHT; j += 2) {
                if (world[i][j] != Tileset.GRASS) {
                    world[i][j] = Tileset.SAND;
                }
            }
        }
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
