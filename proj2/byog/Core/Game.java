package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 81;
    public static final int HEIGHT = 31;

    private static final long seed = 1234567;
    private static final Random RANDOM = new Random(seed);


    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    // TODO: Fill out this method to run the game using the input passed in,
    // and return a 2D tile representation of the world that would have been
    // drawn if the same inputs had been given to playWithKeyboard().
    public TETile[][] playWithInputString(String input) {
        input = toLower(input);
        TETile[][] world = null;
        char firstChar = input.charAt(0);
        if (firstChar == 'n') {
            world = newGame(input);
        } else if (firstChar == 'l') {
        //world = loadGame(input);
        } else if (firstChar == '1') {
            System.exit(0);
        }
        return world;
    }

    private TETile[][] newGame(String input) {
        TETile[][] world;
        int indexS = input.indexOf('s');
        long seed = convertSeed(input.substring(1, indexS));
        world = generateWorld(seed);
        return world;
    }

    /**
     * Initialize the given TETile[][] world with Tileset.NOTHING;
     */
    public void initialization(TETile[][] world) {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                world[i][j] = Tileset.WALL;
            }
        }

        for (int x = 0; x < WIDTH; x += 2) {
            for (int y = 0; y < HEIGHT; y += 2) {
                world[x][y] = Tileset.TREE;
            }
        }

    }

    /**
     *  convert the input String to corresponding seed
     */
    private String toLower(String input) {
        StringBuilder sb =new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (Character.isUpperCase(ch)) {
                sb.append(Character.toLowerCase(ch));
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    private long convertSeed(String seedString) {
        return Long.valueOf(seedString.toString());
    }

    private TETile[][] generateWorld(long seed) {
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        initialization(world);

        Random r = new Random(seed);

        List<Room> rooms = generateRooms(world, r, 10);
        generateHalls(world, r);
        generateConnectors(world, r, rooms);
        if (!rooms.isEmpty()) {
            carveDeadEnd(world);
        }
        carveExtraWalls(world);
        addDoorAndInitiaPlayer(world, r);
        return world;
    }

    private List<Room> generateRooms(TETile[][] world, Random r, int roomNum) {
        Room.setRoomMaxNum(roomNum);
        List<Room> rooms = new ArrayList<>();
        for (int i = 0; i < Room.getRoomMaxNum();) {
            Room newRoom;
            do {
                Position p1 =
                    new Position(decide(r, 1, WIDTH - 3), decide(r, 1, HEIGHT - 3));
                Position p2 =
                    new Position(decide(r, p1.getX() + 1, WIDTH - 1 ), decide(r, p1.getY() + 1, HEIGHT - 1 ));
                newRoom = new Room(p1, p2);
            } while (!Room.isLegal(newRoom));
            if (!newRoom.isOverLapped(rooms)) {
                rooms.add(newRoom);
                i++;
                newRoom.drawRoom(world, Tileset.FLOOR);
            }

        }
        return rooms;
    }

    private int decide(Random r, int start, int end) {
        int x = RandomUtils.uniform(r, start, end);
        if (x % 2 == 0) {
            if (RandomUtils.bernoulli(r)) {
                x++;
            } else {
                x--;
            }
        }
        return x;
    }
}
