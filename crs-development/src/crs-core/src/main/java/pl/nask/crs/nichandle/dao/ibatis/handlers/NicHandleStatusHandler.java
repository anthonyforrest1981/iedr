package pl.nask.crs.nichandle.dao.ibatis.handlers;

import java.sql.SQLException;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

import pl.nask.crs.nichandle.NicHandleStatus;

/**
 * @author Marianna Mysiorska, Artur Gniadzik
 */
public class NicHandleStatusHandler implements TypeHandlerCallback {

    public void setParameter(ParameterSetter setter, Object parameter) throws SQLException {
        if (parameter instanceof NicHandleStatus) {
            NicHandleStatus status = (NicHandleStatus) parameter;
            setter.setString(status.getCode());
        } else {
            throw new IllegalArgumentException(String.format("Invalid value for NicHandleStatus: %s", parameter));
        }
    }

    public Object getResult(ResultGetter resultGetter) throws SQLException {
        String code = resultGetter.getString();
        return NicHandleStatus.forCode(code);
    }

    public Object valueOf(String nullValue) {
        return nullValue;
    }
}
