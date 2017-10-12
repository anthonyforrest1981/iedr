package pl.nask.crs.api.common;

import pl.nask.crs.api.vo.CrsVersionInfoVO;
import pl.nask.crs.commons.config.ApplicationConfig;

public class InfoEndpoint implements CRSInfo {
    private ApplicationConfig applicationConfig;

    @Override
    public CrsVersionInfoVO getVersionInfo() {
        return new CrsVersionInfoVO();
    }

    @Override
    public ServerTime getServerTime() {
        return new ServerTime();
    }

    public void setApplicationConfig(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }
}
