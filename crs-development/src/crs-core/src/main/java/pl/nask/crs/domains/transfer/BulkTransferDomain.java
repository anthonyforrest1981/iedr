package pl.nask.crs.domains.transfer;

import java.util.Date;

public class BulkTransferDomain {
    private String name;
    private Date transferDate;
    private String hostmasterNh;

    public BulkTransferDomain() {}

    public BulkTransferDomain(String name, Date transferDate) {
        super();
        this.name = name;
        this.transferDate = transferDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public void setHostmasterNh(String hostmasterNh) {
        this.hostmasterNh = hostmasterNh;
    }

    public String getHostmasterNh() {
        return hostmasterNh;
    }

}
