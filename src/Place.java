import java.util.HashSet;
import java.util.Set;

public class Place {
    private final double x;
    private final double y;
    private Set<String> service;

    public Place(double x, double y, Set<String> service) {
        this.x = x;
        this.y = y;
        this.service = service;
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public Set<String> getService() {
        return new HashSet<>(service);
    }
}
