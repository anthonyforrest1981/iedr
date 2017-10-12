package pl.nask.crs.commons.dao.ibatis.handlers;

import java.sql.SQLException;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;

public class EmptyStringAsNullTypeHandler extends StringHandler {
    public void setParameter(ParameterSetter setter, Object o) throws SQLException {

        String val = null;
        if (o instanceof String && !((String) o).isEmpty()) {
            val = (String) o;
        }

        super.setParameter(setter, val);
    }

    public Object getResult(ResultGetter resultGetter) throws SQLException {
        String value = (String) super.getResult(resultGetter);
        if (value == null || value.isEmpty())
            return null;
        return value;
    }

}
