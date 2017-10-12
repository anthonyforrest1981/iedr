package pl.nask.crs.iedrapi;

import java.util.HashMap;
import java.util.Map;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

import pl.nask.crs.xml.Constants;

public class CustomPrefixMapper extends NamespacePrefixMapper {

    Map<String, String> prefixes = new HashMap<String, String>();
    {
        prefixes.put(Constants.IEAPICOM_NAMESPACE, "ieapicom");
        prefixes.put(Constants.IEAPI_CONTACT_NAMESPACE, "contact");
        prefixes.put("http://www.w3.org/2001/XMLSchema-instance", "xsi");
        prefixes.put(Constants.IEAPI_ACCOUNT_NAMESPACE, "account");
        prefixes.put(Constants.IEAPI_DOMAIN_NAMESPACE, "domain");
        prefixes.put(Constants.IEAPI_BUY_REQUEST_NAMESPACE, "registrantTransferBuyRequest");
        prefixes.put(Constants.IEAPI_SELL_REQUEST_NAMESPACE, "registrantTransferSellRequest");
        prefixes.put(Constants.IEAPI_TICKET_NAMESPACE, "ticket");
        prefixes.put(Constants.SECDNS_NAMESPACE, "secDNS");
    }

    @Override
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
        String prefix = prefixes.get(namespaceUri);
        return (prefix != null ? prefix : "");
    }

}
