package pl.nask.crs.commons.dao.ibatis.handlers;

import java.sql.SQLException;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

public class CharToBooleanHandler implements TypeHandlerCallback {

    public static final String NO = "NO";
    public static final String YES = "YES";

    public void setParameter(ParameterSetter setter, Object o) throws SQLException {
        if (o instanceof Boolean) {
            boolean b = (Boolean) o;
            String stringValue = NO;
            if (b) {
                stringValue = YES;
            }

            setter.setString(stringValue);
        } else {
            throw new IllegalArgumentException(String.format("Invalid value for handler: %s", o));
        }

    }

    public Object getResult(ResultGetter resultGetter) throws SQLException {
        String value = resultGetter.getString();
        if (YES.equals(value)) {
            return true;
        } else {
            // default is also false cause we might miss whole row on join, resulting in nulls even though
            // column in not null
            return false;
        }
    }

    public Object valueOf(String s) {
        return null;
    }
}
