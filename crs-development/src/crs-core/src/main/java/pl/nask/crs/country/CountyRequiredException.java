package pl.nask.crs.country;

/**
 * @author: Marcin Tkaczyk
 */
public class CountyRequiredException extends InvalidCountyException {
    public CountyRequiredException(int countryId) {
        super("County required for country id: " + countryId);
    }
}
