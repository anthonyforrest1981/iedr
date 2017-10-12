package pl.nask.crs.contacts;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/
/*>>>import org.checkerframework.dataflow.qual.Pure;*/

import pl.nask.crs.commons.utils.Validator;

public class Contact {

    private String nicHandle;

    private /*>>>@Nullable*/ String name;

    private /*>>>@Nullable*/ String email;

    private /*>>>@Nullable*/ String companyName;

    private /*>>>@Nullable*/ String countryName;

    private /*>>>@Nullable*/ String countyName;

    public Contact() {}

    public Contact(String nicHandle) {
        this(nicHandle, null);
    }

    public Contact(String nicHandle, /*>>>@Nullable*/ String name) {
        Validator.assertNotNull(nicHandle, "nic handle");
        this.nicHandle = nicHandle;
        this.name = name;
    }

    public Contact(String nicHandle, /*>>>@Nullable*/ String name, /*>>>@Nullable*/ String email) {
        this.nicHandle = nicHandle;
        this.name = name;
        this.email = email;
    }

    public Contact(String nicHandle, /*>>>@Nullable*/ String name, /*>>>@Nullable*/ String email, /*>>>@Nullable*/
            String companyName, /*>>>@Nullable*/ String countryName, /*>>>@Nullable*/ String countyName) {
        this.nicHandle = nicHandle;
        this.name = name;
        this.email = email;
        this.companyName = companyName;
        this.countryName = countryName;
        this.countyName = countyName;
    }

    /**
     * Returns the nic handle of this contact. A nic handle is a unique identifier of a contact.
     *
     * @return the nic handle of the contact; never null
     */
    /*>>>@Pure*/
    public String getNicHandle() {
        return nicHandle;
    }

    /**
     * Returns the name of the contact - more verbose description of the contact.
     *
     * @return the name of the contact.
     */
    /*>>>@Pure*/
    public /*>>>@Nullable*/ String getName() {
        return name;
    }

    /*>>>@Pure*/
    public /*>>>@Nullable*/ String getEmail() {
        return email;
    }

    /*>>>@Pure*/
    public /*>>>@Nullable*/ String getCompanyName() {
        return companyName;
    }

    /*>>>@Pure*/
    public /*>>>@Nullable*/ String getCountryName() {
        return countryName;
    }

    /*>>>@Pure*/
    public /*>>>@Nullable*/ String getCountyName() {
        return countyName;
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Contact))
            return false;

        Contact contact = (Contact) o;

        if (nicHandle != null ? !nicHandle.equals(contact.nicHandle) : contact.nicHandle != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (nicHandle != null ? nicHandle.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("Contact[NH=%s, name=%s]", nicHandle, name);
    }
}
