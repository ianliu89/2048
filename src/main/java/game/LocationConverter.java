package game;

public class LocationConverter {

    public static Location numberToLocation(int number) {
        int col = ((number + 4 -1)/4) - 1;
        int row = (number + 3) % 4;
        return new Location(20 + 120 * row, 20 + 120 * col);
    }

    public static int LocationToNumber(Location location) {
        int row = (location.getxLocation() - 20)/120;
        int col = (location.getyLocation() - 20)/120;
        return col + 1 + (4 * row);
    }
}
