package pl.nask.crs.ticket.dao.ibatis.handlers;

import java.sql.SQLException;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

import pl.nask.crs.ticket.CustomerStatus;

@SuppressWarnings("nullness")
public class CustomerStatusHandler implements TypeHandlerCallback {

    public void setParameter(ParameterSetter setter, Object parameter) throws SQLException {
        if (parameter instanceof CustomerStatus) {
            CustomerStatus status = (CustomerStatus) parameter;
            setter.setString(status.getDescription());
        } else {
            throw new IllegalArgumentException(String.format("Invalid value for CustomerStatus: %s", parameter));
        }
    }

    public Object getResult(ResultGetter resultGetter) throws SQLException {
        String desc = resultGetter.getString();
        return CustomerStatus.forDescription(desc);
    }

    public Object valueOf(String s) {
        return s;
    }

}
