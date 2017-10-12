package pl.nask.crs.api.common;

import javax.jws.WebService;

import pl.nask.crs.api.vo.CrsVersionInfoVO;

@WebService(
    serviceName = "CRSInfo",
    endpointInterface = "pl.nask.crs.api.common.CRSInfo",
    portName = "CRSInfoPort",
    targetNamespace = "http://domainregistry.ie/")
public class CRSInfoProxy implements CRSInfo {
    private CRSInfo service;

    public void setService(CRSInfo service) {
        this.service = service;
    }

    public CrsVersionInfoVO getVersionInfo() {
        return service.getVersionInfo();
    }

    public ServerTime getServerTime() {
        return service.getServerTime();
    }

}
