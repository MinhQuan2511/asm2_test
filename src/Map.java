import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Map {
    private QuadTreeNode root;

    public Map(){
        root = new QuadTreeNode(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    }
    public void add(Place place) {
        root.addPlace(place);
    }

    public void remove(Place place) {
        root.removePlace(place);
    }

    public Set<Place> search(double north, double south, double east, double west, Set<String> serviceTypes) {
        return root.search(north, south, east, west, serviceTypes);
    }

    public static void main(String[] args) {
        Map map = new Map();

        // Add places
        map.add(new Place(10, 10, new HashSet<Place>(Arrays.asList("ATM", "Restaurant"))));
        map.add(new Place(20, 20, new HashSet<Place>(Arrays.asList("ATM", "Hospital"))));
        map.add(new Place(30, 30, new HashSet<Place>(Arrays.asList("Restaurant"))));

        // Search for places
        Set<Place> results = map.search(0, 50, 0, 50, new HashSet<Place>(Arrays.asList("ATM", "Hospital")));
    }
}
