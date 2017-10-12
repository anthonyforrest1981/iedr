package pl.nask.crs.commons.email.service;

import java.util.*;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import pl.nask.crs.contacts.Contact;

/*>>>import org.checkerframework.checker.nullness.qual.NonNull;*/
/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

public class EmailParametersUtils {
    private EmailParametersUtils() {}

    private static Set<ParameterName> adminRelatedParameterNames;
    static {
        adminRelatedParameterNames = new HashSet<ParameterName>(Arrays.asList(ParameterNameEnum.ADMIN_C_EMAIL,
                ParameterNameEnum.ADMIN_C_NAME, ParameterNameEnum.ADMIN_C_NIC));
    }

    public static /*>>>@NonNull*/ List<? extends ParameterName> getAdminRelatedParameterNames() {
        return new ArrayList<>(adminRelatedParameterNames);
    }

    public static boolean isAdminRelatedParameterName(String name) {
        return adminRelatedParameterNames.contains(ParameterNameEnum.forName(name));
    }

    public static /*>>>@Nullable*/ String getAdminRelatedParameterValue(/*>>>@Nullable*/ List<Contact> adminContacts,
            String name, boolean html) {
        ParameterNameEnum param = ParameterNameEnum.forName(name);
        switch (param) {
            case ADMIN_C_EMAIL:
                return getAdminContactEmails(adminContacts);

            case ADMIN_C_NAME:
                return adminContacts != null && !adminContacts.isEmpty() ? adminContacts.get(0).getName() : null;

            case ADMIN_C_NIC:
                return adminContacts != null && !adminContacts.isEmpty() ? adminContacts.get(0).getNicHandle() : null;

            default:
                return null;
        }
    }

    public static String getAdminContactEmails(/*>>>@Nullable*/ List<Contact> adminContacts) {
        if (adminContacts == null) {
            adminContacts = Collections.EMPTY_LIST;
        }
        return Joiner.on(",").join(Lists.transform(adminContacts, new Function<Contact, String>() {
            @Override
            public String apply(Contact contact) {
                return contact.getEmail();
            }
        }));
    }
}
