package pl.nask.crs.country;

public class InvalidCountryException extends Exception {

    public InvalidCountryException(int countryId) {
        super("Invalid country id: " + countryId);
    }

    public InvalidCountryException(String countryName) {
        super("Invalid country name: " + countryName);
    }
}
