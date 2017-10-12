package pl.nask.crs.api.vo;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/
/*>>>import org.checkerframework.dataflow.qual.Pure;*/

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import pl.nask.crs.security.authentication.AuthenticatedUser;

@XmlAccessorType(XmlAccessType.FIELD)
public class AuthenticatedUserVO implements AuthenticatedUser, Serializable {

    private static final long serialVersionUID = 5689853262140677947L;

    @XmlElement(required = true)
    private String username;

    @XmlElement(name = "authenticationToken", required = true)
    private String token;

    @XmlElement(name = "passwordChangeRequired")
    private boolean passwordChangeRequired;

    @SuppressWarnings("nullness")
    public AuthenticatedUserVO() {}

    public AuthenticatedUserVO(AuthenticatedUser authUser) {
        this.token = authUser.getAuthenticationToken();
        this.username = authUser.getUsername();
    }

    @Override
    /*>>>@Pure*/
    public String getAuthenticationToken() {
        return token;
    }

    @Override
    /*>>>@Pure*/
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAuthenticationToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "AuthenticatedUserVO [token=" + token + ", username=" + username + "]";
    }

    public boolean isPasswordChangeRequired() {
        return passwordChangeRequired;
    }

    public void setPasswordChangeRequired(boolean passwordChangeRequired) {
        this.passwordChangeRequired = passwordChangeRequired;
    }

    @Override
    @XmlTransient
    /*>>>@Pure*/
    public /*>>>@Nullable*/ String getSuperUserName() {
        return null;
    }
}
