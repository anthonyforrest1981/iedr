package pl.nask.crs.ticket.dao.ibatis.handlers;

import java.sql.SQLException;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

import pl.nask.crs.ticket.TechStatus;

@SuppressWarnings("nullness")
public class TechStatusHandler implements TypeHandlerCallback {
    public void setParameter(ParameterSetter setter, Object parameter) throws SQLException {
        if (parameter instanceof TechStatus) {
            TechStatus status = (TechStatus) parameter;
            setter.setString(status.getDescription());
        } else {
            throw new IllegalArgumentException(String.format("Invalid value for TechStatus: %s", parameter));
        }
    }

    public Object getResult(ResultGetter resultGetter) throws SQLException {
        String desc = resultGetter.getString();
        return TechStatus.forDescription(desc);
    }

    public Object valueOf(String s) {
        return s;
    }

}
