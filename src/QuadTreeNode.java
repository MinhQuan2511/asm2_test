import java.util.HashSet;
import java.util.Set;

public class QuadTreeNode {
    private double north, south, east, west;
    private QuadTreeNode[] child;
    private Set<Place> places;

    public double getNorth() {
        return north;
    }

    public double getSouth() {
        return south;
    }

    public double getEast() {
        return east;
    }

    public double getWest() {
        return west;
    }

    public Set<Place> getPlaces() {
        return new HashSet<>(places);
    }

    public QuadTreeNode(double north, double south, double east, double west) {
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
        this.child = new QuadTreeNode[4];
        this.places = new HashSet<Place>();
    }

    public void addPlace(Place place) {
        if (child[0] != null) {
            int index = getIndex(place.getX(), place.getY());
            child[index].addPlace(place);
        } else {
            places.add(place);
        }
        if(places.size() > 4) {
            splitTree();
        }
    }

    public void removePlace(Place place) {
        if (child[0] != null) {
            int index = getIndex(place.getX(), place.getY());
            child[index].removePlace(place);
        } else {
            places.remove(place);
        }
    }

    public Set<Place> search(double north, double south, double east, double west, Set<String> serviceTypes) {
        Set<Place> result = new HashSet<>();

        if (north < this.north && south > this.south && east > this.west && west < this.east) {
            result.addAll(places);
        } else {
            for (int i = 0; i < 4; i++) {
                if (child[i] != null) {
                    result.addAll(child[i].search(north, south, east, west, serviceTypes));
                }
            }
        }

        if (!serviceTypes.isEmpty()) {
            Set<Place> serviceTypeResults = new HashSet<>();
            for (Place place : result) {
                if (place.getService().containsAll(serviceTypes)) {
                    serviceTypeResults.add(place);
                }
            }
            result = serviceTypeResults;
        }

        return result;
    }

    private void splitTree(){
        double midPOX = west + east/2;
        double midPOY = north + south/2;

        child[0] = new QuadTreeNode(north, midPOY, midPOX, west); // TOP LEFT
        child[1] = new QuadTreeNode(north, midPOY, east, midPOX); // TOP RIGHT
        child[2] = new QuadTreeNode(midPOY, south, midPOX, west); // BOTTOM LEFT
        child[3] = new QuadTreeNode(midPOY, south, east, midPOX); // BOTTOM RIGHT

        for (Place place : places) {
            int index = getIndex(place.getX(), place.getY());
            child[index].addPlace(place);
        }

        places.clear();
    }

    private int getIndex(double x, double y) {
        if (x < west + east/2) {
            if (y > north + south/2) {
                return 0;
            } else {
                return 1;
            }
        } else {
            if (y < north + south/2) {
                return 2;
            } else {
                return 3;
            }
        }
    }
}
