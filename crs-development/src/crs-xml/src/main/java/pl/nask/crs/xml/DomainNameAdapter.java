package pl.nask.crs.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DomainNameAdapter extends XmlAdapter<String, String> {

    @Override
    public String unmarshal(String v) throws Exception {
        if (v != null) {
            return v.toLowerCase();
        }
        return v;
    }

    @Override
    public String marshal(String v) throws Exception {
        return v;
    }
}
