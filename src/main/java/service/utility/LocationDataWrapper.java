package service.utility;

import Util.JSONHandler;
import Util.ReadString;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Random;

/**
 * Wrapper for the location data
 */
public class LocationDataWrapper {
    /**
     * File location of the location data.
     */
    private static final String fileName = "./json/locations.json";
    /**
     * The LocationData array wrapper.
     */
    public static LocationData locationData;
    /**
     * The main random instance.
     */
    private static final Random random = new Random();

    static {
        setup();
    }

    /**
     * Reads and sets up location data.
     */
    private static void setup() {
        try {
            Reader reader = new FileReader(fileName);
            String locationsData = ReadString.read(reader);
            locationData = (LocationData) JSONHandler.jsonToObject(locationsData, LocationData.class);
        } catch (IOException e) {
            e.printStackTrace(); // TODO: Logger
        }
    }

    /**
     * Gets a random location from the list of locations on-hand.
     * @return a random location.
     */
    public static Location random() {
        return locationData.getData()[random.nextInt(locationData.getData().length)];
    }
}
