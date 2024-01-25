package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.List;

public class Room {
    private Position bottomLeft;
    private Position upRight;
    private static int roomMaxNum = 20;
    private static int roomMaxWidth = 8;
    private static int roomMaxHeight = 6;

    public Room (Position bottomLeft, Position upRight) {
        this.bottomLeft = bottomLeft;
        this.upRight = upRight;
    }

    public static boolean isLegal(Room r) {
        boolean isXLegal = r.bottomLeft.getX() != r.upRight.getX();
        boolean isYLegal = r.bottomLeft.getY() != r.upRight.getY();
        boolean isWidthLegal = r.getWidth() < Room.roomMaxWidth;
        boolean isHeightLegal = r.getHeight() < Room.roomMaxHeight;
        return isXLegal && isYLegal && isWidthLegal && isHeightLegal;
    }


    public int getWidth() {
        return this.upRight.getX() - this.bottomLeft.getX();
    }

    public int getHeight() {
        return this.upRight.getY() - this.bottomLeft.getY();
    }

    public static int getRoomMaxNum() {
        return roomMaxNum;
    }

    public static void setRoomMaxNum(int maxRoomNum) {
        Room.roomMaxNum = maxRoomNum;
    }

    public boolean isOverLapped(Room other) {
        Position firstCentre = getCentralPos();
        Position secondCentre = other.getCentralPos();

        int xCentreDiff = Math.abs(firstCentre.getX() - secondCentre.getX());
        boolean isXOver = xCentreDiff <= getWidth() / 2 + other.getWidth() / 2 + 2;
        int yCentreDiff = Math.abs(firstCentre.getY() - secondCentre.getY());
        boolean isYOver = yCentreDiff <= getHeight() / 2 + other.getHeight() / 2 + 2;
        return isXOver && isYOver;
    }

    public boolean isOverLapped(List<Room> rooms) {
        if (rooms.isEmpty()) {
            return false;
        }
        for (Room r : rooms) {
            if (this.isOverLapped(r)) {
                return true;
            }
        }
        return false;
    }

    private Position getCentralPos() {
        return new Position((this.bottomLeft.getX() + this.upRight.getX()) / 2,
                this.bottomLeft.getY() + this.upRight.getY() / 2);
    }

    public void drawRoom(TETile[][] world, TETile t) {
        for (int i = this.bottomLeft.getX(); i <= this.upRight.getX(); i++) {
            for (int j = this.bottomLeft.getY(); j < this.upRight.getY(); j++) {
                world[i][j] = t;
            }
        }
    }
}
