package pl.nask.crs.app.nichandles.wrappers;

import java.util.LinkedHashSet;
import java.util.List;

public class PhoneWrapper {
    private List<String> currentList;

    /**
     * Constructs a wrapper around the given set of phones (strings).
     * @param phones
     */
    public PhoneWrapper(List<String> phones) {
        this.currentList = phones;
    }

    public List<String> getCurrentList() {
        return currentList;
    }

    public void setPhone(List<String> phones) {
        // first element of the list is artificial
        phones = phones.subList(1, phones.size());
        currentList.clear();
        currentList.addAll(new LinkedHashSet<>(phones));
    }
}
