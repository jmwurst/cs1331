/**
 * Represents a coordinate point as a combination of a latitude and
 * longitude, as on a map.
 *
 * @author Justin Wurst
 * @version 10-16-18
 */
public class Coordinates {
    private final double latitude, longitude;

    /**
     * Initializes a Coordinates object based on a given latitude and longitude.
     *
     * @param latitude The latitude of the coordinate.
     * @param longitude The longitude of the coordinate.
     */
    public Coordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * @return this coordinate's latitude.
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @return this coordinate's longitude.
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Indicates whether another object is equivalent to the current
     * instance of Coordinates.
     *
     * @param other Object to compare the current instance of Coordinates to.
     * @return a boolean value indicating if the two objects are equal.
     */
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof Coordinates)) {
            return false;
        }
        return (this.latitude == ((Coordinates) other).latitude
                && this.longitude == ((Coordinates) other).longitude);
    }

    /**
     * Generates a string representation of the coordinate.
     *
     * @return a String containing this coordinate's latitude and longitude.
     */
    public String toString() {
        return String.format("latitude: %.2f, longitude: %.2f",
                             this.latitude, this.longitude);
    }
}