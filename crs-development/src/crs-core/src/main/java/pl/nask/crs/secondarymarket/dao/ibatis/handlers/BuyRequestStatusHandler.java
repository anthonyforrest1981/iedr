package pl.nask.crs.secondarymarket.dao.ibatis.handlers;

import java.sql.SQLException;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

import pl.nask.crs.secondarymarket.BuyRequestStatus;

@SuppressWarnings("nullness")
public class BuyRequestStatusHandler implements TypeHandlerCallback {
    public void setParameter(ParameterSetter setter, Object parameter) throws SQLException {
        if (parameter instanceof BuyRequestStatus) {
            BuyRequestStatus status = (BuyRequestStatus) parameter;
            setter.setString(status.getDescription());
        } else {
            throw new IllegalArgumentException(String.format("Invalid value for BuyRequestStatus: %s", parameter));
        }
    }

    public Object getResult(ResultGetter resultGetter) throws SQLException {
        String desc = resultGetter.getString();
        return BuyRequestStatus.forDescription(desc);
    }

    public Object valueOf(String s) {
        return s;
    }

}