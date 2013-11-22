package edu.smcm.gamedev.butterseal;

import java.util.Map;

public class BSUtil {
    /**
     * Iterate through the properties we were given.
     * First check if the map contains the key.
     * If it does (cf. short-circuiting),
     * then see if it equals the value we were given.
     *
     * @param map A property listing within which to check
     * @param value a value to check for
     * @param properties a list of properties to check
     * @return there exists property in properties such that property=value
     */
    public static boolean any(Map<String, String> map, String value, String... properties) {
        for (String property : properties) {
            if (map.containsKey(property) && map.get(property).equals(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Same algorithm as {@link #any(Map, String, String...)}.
     * @param map A property listing within which to check
     * @param value a value to check for
     * @param properties a list of properties to check
     * @return for each property in properties, property=value
     */
    public static boolean all(Map<String, String> map, String value, String... properties) {
        for (String property : properties) {
            if (!map.containsKey(property) || !map.get(property).equals(value)) {
                return false;
            }
        }
        return true;
    }
}
