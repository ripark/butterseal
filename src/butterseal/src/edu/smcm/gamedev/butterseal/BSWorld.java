package edu.smcm.gamedev.butterseal;

import java.util.HashSet;
import java.util.Set;

/**
 * A singleton class representing the game world.
 * It has functionalities to add routes to the world-map and
 * to retrieve all possible destinations from a given {@link BSMap}.
 * @author Sean
 *
 */
public class BSWorld {
    /**
     * This class represents one route between two maps the {@link #source} and
     * {@link #destination}. The route also carries with it an arbitrary
     * {@link Object} of {@link #data}.
     * 
     * This is a helper class for {@link BSWorld}.
     * 
     * @author Sean
     * 
     */
    private static class Route {
        BSMap source;
        BSMap destination;

        Object data;

        /**
         * Creates a route between two maps.
         * 
         * @param source
         *            the source of this route (from)
         * @param destination
         *            the destination of this route (to)
         * @param data
         *            arbitrary data to hold to this route
         */
        public Route(BSMap source, BSMap destination, Object data) {
            this.source = source;
            this.destination = destination;
            this.data = data;
        }
    }

    /**
     * A set of all routes that comprise this world.
     */
    Set<Route> routes;

    public BSWorld() {
        this.routes = new HashSet<BSWorld.Route>();
    }

    /**
     * Adds a new route to the world between two maps, carrying arbitrary data.
     * @param source The source map.
     * @param destination The destination map.
     * @param data The route data.
     * @throws BSException Neither source nor destination can be null.
     */
    public void addRoute(BSMap source, BSMap destination, Object data) {
        routes.add(new Route(source, destination, data));
    }

    /**
     * 
     * @param source
     * @param destination
     * @return True if there is a route from source to destination; false otherwise.
     */
    public boolean isRoute(BSMap source, BSMap destination) {
        for (Route r : routes)
            if (r.source == source && r.destination == destination)
                return true;
        return false;
    }

    /**
     *  
     * @param source
     * @param destination
     * @return Route data for a route from source to destination if it exists; null if no such route exists 
     */
    public Object getRoute(BSMap source, BSMap destination) {
        for (Route r : routes)
            if (r.source == source && r.destination == destination)
                return r.data;
        return null;
    }

    /**
     * Since we are storing this world as a set of routes (see {#link #routes}),
     * we don't need to store the maps separately.  This function generates such a set
     * dynamically, returning all nodes of the world-graph.
     * @return a set containing all {@link BSMap}s M such that there exists a {@link Route} in {@link #routes} that mentions M.
     */
    public Set<BSMap> getMaps() {
        Set<BSMap> maps = new HashSet<BSMap>();
        for (Route r : this.routes) {
            maps.add(r.source);
            maps.add(r.destination);
        }
        return maps;
    }

    /**
     * It is even more useful to get a set of destinations you can go to from a particular map.
     * @param source Your current location in the world
     * @return a set of {@link BSMap}s you can get to
     */
    public Set<BSMap> getMaps(BSMap source) {
        Set<BSMap> maps = new HashSet<BSMap>();
        for (Route r : this.routes)
            if (source == r.source)
                maps.add(r.destination);
        return maps;
    }
}

// Local Variables:
// indent-tabs-mode: nil
// End:
