package pl.nask.crs.api.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.security.authentication.AuthenticatedUser;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class NewAccountVO {
    private AuthenticatedUserVO user;
    private String secret;

    NewAccountVO() {}

    public NewAccountVO(AuthenticatedUser user, String secret) {
        this.user = new AuthenticatedUserVO(user);
        this.secret = secret;
    }

}
