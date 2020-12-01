package game;

import java.util.Objects;

public class Location {

    private int xLocation;
    private int yLocation;

    public Location(int xLocation, int yLocation) {
        this.xLocation = xLocation;
        this.yLocation = yLocation;
    }

    public int getxLocation() {
        return xLocation;
    }

    public void setxLocation(int xLocation) {
        this.xLocation = xLocation;
    }

    public int getyLocation() {
        return yLocation;
    }

    public void setyLocation(int yLocation) {
        this.yLocation = yLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return xLocation == location.xLocation &&
                yLocation == location.yLocation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(xLocation, yLocation);
    }
}
