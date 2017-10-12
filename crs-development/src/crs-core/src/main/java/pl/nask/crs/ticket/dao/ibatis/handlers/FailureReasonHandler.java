package pl.nask.crs.ticket.dao.ibatis.handlers;

import java.sql.SQLException;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

import pl.nask.crs.ticket.operation.FailureReason;

@SuppressWarnings("nullness")
public class FailureReasonHandler implements TypeHandlerCallback {
    public void setParameter(ParameterSetter setter, Object parameter) throws SQLException {
        if (parameter == null) {
            setter.setString(null);
        } else if (parameter instanceof FailureReason) {
            FailureReason status = (FailureReason) parameter;
            setter.setString(status.getDescription());
        } else {
            throw new IllegalArgumentException(String.format("Invalid value for FailureReason: %s", parameter));
        }
    }

    public Object getResult(ResultGetter resultGetter) throws SQLException {
        String desc = resultGetter.getString();
        if (desc != null) {
            return FailureReason.forDescription(desc);
        } else {
            return null;
        }
    }

    public Object valueOf(String s) {
        return s;
    }

}
