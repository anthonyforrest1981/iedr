package pl.nask.crs.api.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.api.validation.ValidationHelper;
import pl.nask.crs.ticket.operation.IpFieldChange;
import pl.nask.crs.ticket.operation.NameserverChange;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class NameserverChangeVO {

    private SimpleDomainFieldChangeVO<String> name;

    private IpFieldChangeVO ipv4Address;

    private IpFieldChangeVO ipv6Address;

    public NameserverChangeVO() {}

    public NameserverChangeVO(NameserverChange chg) {
        this.name = ValidationHelper.isEmptySimpleDomainFieldChange(chg.getName()) ? null
                : new SimpleDomainFieldChangeVO<>(chg.getName());
        this.ipv4Address = ValidationHelper.isEmptyIpFieldChange(chg.getIpv4Address()) ? null
                : new IpFieldChangeVO(chg.getIpv4Address());
        this.ipv6Address = ValidationHelper.isEmptyIpFieldChange(chg.getIpv6Address()) ? null
                : new IpFieldChangeVO(chg.getIpv6Address());
    }

    public SimpleDomainFieldChangeVO<String> getName() {
        return name;
    }

    public IpFieldChangeVO getIpv4Address() {
        return ipv4Address;
    }

    public IpFieldChangeVO getIpv6Address() {
        return ipv6Address;
    }

    public void setName(SimpleDomainFieldChangeVO<String> name) {
        if (name != null) {
            this.name = name;
        } else {
            this.name = new SimpleDomainFieldChangeVO<>(null, null, null);
        }
    }
}
