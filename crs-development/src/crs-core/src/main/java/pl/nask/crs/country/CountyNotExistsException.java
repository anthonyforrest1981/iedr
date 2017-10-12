package pl.nask.crs.country;

/**
 * @author: Marcin Tkaczyk
 */
public class CountyNotExistsException extends InvalidCountyException {
    public CountyNotExistsException(int countyId) {
        super("Invalid county id: " + countyId);
    }

    public CountyNotExistsException(int countyId, int countryId) {
        super("Invalid county id: " + countyId + " for country id: " + countryId);
    }
}
