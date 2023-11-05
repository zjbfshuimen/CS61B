package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import javax.swing.text.Position;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    /** initialize the random */
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    private static final int WIDTH = 60;
    private static final int HEIGHT = 60;
    private static class Position {
        public int x;
        public int y;
        public Position(int a, int b) {
            x = a;
            y = b;
        }
    }
    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(6);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.SAND;
            case 3: return Tileset.FLOOR;
            case 4: return Tileset.GRASS;
            case 5: return Tileset.MOUNTAIN;
            case 6: return Tileset.WATER;
            default: return Tileset.NOTHING;
        }
    }


    /** draw a Hexagon at pos p with TETile t whose height is 2 * len */
    public static void addHexagon(TETile[][] world, Position p, int len, TETile tet) {
        if (len < 2) {
            throw new IllegalArgumentException("Hexagon must be at least size 2");
        }
        addHexagonHelp(world, p, len, tet);
    }

    /**
     * Add a HexagonHelp, Position p is the most left bottom pos, from which to start
     * using randomTile as this Hexagon Tile
     */
    private static void addHexagonHelp(TETile[][] world, Position p, int length, TETile t) {
        t = randomTile();
        for (int i = 0; i < length; i++) {
            Position iLoopSub = new Position(p.x - i, p.y + i);
            Position iLoopTop = new Position(p.x - i, p.y + 2 * length - i - 1);
            drawOneLine(world, iLoopSub, length + 2 * i, t);
            drawOneLine(world, iLoopTop, length + 2 * i, t);
        }
    }

    /** Set one line TETile to specific len*/
    private static void drawOneLine(TETile[][] world, Position p, int len, TETile t) {
        for (int i = 0; i < len; i++) {
            world[p.x + i][p.y] = t;
        }
    }

    /**
     * Add num Hexagon from the bottom to top
     * also TETile is optional, based on if addHexagon is random
     */
    public static void drawOneColume(TETile[][] world, int len, Position pos, int num, TETile t) {
        for (int i = 0; i < num; i++) {
            Position linshiPos = new Position(pos.x, pos.y + 2 * len * i);
            addHexagon(world, linshiPos, len, t);
        }
    }

    /**
     * Draw the whole diagram
     * t is optional based on drawOneColume which is based on if addHexagon is random
     */
    public static void draw(TETile[][] world,Position pos ,int len) {
        TETile t = Tileset.SAND;

        for (int i = 0; i < 3; i++) {
            Position leftBottom = new Position(pos.x - i * (2 * len - 1), pos.y + i * (len ));
            Position rightBottom = new Position(pos.x + i * (2 * len - 1), pos.y + i * (len ));
            drawOneColume(world, len, leftBottom, 5 - i, t);
            drawOneColume(world, len, rightBottom, 5 -i, t);
        }
    }
    public static void drawHeart(TETile[][] world, Position pos, int len) {

    }
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for(int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        int len = 4;
        Position pos = new Position(WIDTH / 2 - len , 1);
        draw(world, pos, len);

        ter.renderFrame(world);
    }
}
