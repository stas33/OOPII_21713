package Service;

import java.util.Map;

public interface Comparable<Traveller> {
    /* public int compare(Map.Entry<String, Service.Traveller> o1, Map.Entry<String, Service.Traveller> o2); */
    public int compareTo(Traveller t);
}
