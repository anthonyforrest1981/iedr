package pl.nask.crs.ticket.dao.ibatis.handlers;

import java.sql.SQLException;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

import pl.nask.crs.ticket.operation.IpFailureReason;

@SuppressWarnings("nullness")
public class IpFailureReasonHandler implements TypeHandlerCallback {
    public void setParameter(ParameterSetter setter, Object parameter) throws SQLException {
        if (parameter == null) {
            setter.setObject(null);
        } else if (parameter instanceof IpFailureReason) {
            IpFailureReason status = (IpFailureReason) parameter;
            setter.setString(status.getDescription());
        } else {
            throw new IllegalArgumentException(String.format("Invalid value for IpFailureReason: %s", parameter));
        }
    }

    public Object getResult(ResultGetter resultGetter) throws SQLException {
        String desc = resultGetter.getString();
        if (desc != null) {
            return IpFailureReason.forDescription(desc);
        } else {
            return null;
        }
    }

    public Object valueOf(String s) {
        return s;
    }

}
