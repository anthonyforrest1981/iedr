package pl.nask.crs.commons.dao.ibatis.handlers;

import java.sql.SQLException;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;

public class LooseStringPrefixHandler extends LooseStringHandler {

    public void setParameter(ParameterSetter setter, Object o) throws SQLException {
        String param = (o == null ? "" : o.toString()) + "%";
        super.setParameter(setter, param);
    }
}
