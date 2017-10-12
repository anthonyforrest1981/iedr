package pl.nask.crs.api.common;

import javax.jws.WebService;

import pl.nask.crs.api.vo.CrsVersionInfoVO;

@WebService(targetNamespace = "http://domainregistry.ie/")
public interface CRSInfo {

    CrsVersionInfoVO getVersionInfo();

    ServerTime getServerTime();

}
