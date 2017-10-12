package pl.nask.crs.secondarymarket.dao.ibatis.handlers;

import java.sql.SQLException;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

import pl.nask.crs.secondarymarket.BuyRequestNotificationType;

@SuppressWarnings("nullness")
public class BuyRequestNotificationTypeHandler implements TypeHandlerCallback {

    public void setParameter(ParameterSetter setter, Object parameter) throws SQLException {
        if (parameter instanceof BuyRequestNotificationType) {
            BuyRequestNotificationType notificationType = (BuyRequestNotificationType) parameter;
            setter.setString(notificationType.getDescription());
        } else {
            throw new IllegalArgumentException(String.format("Invalid value for BuyRequestNotificationType: %s",
                    parameter));
        }
    }

    public Object getResult(ResultGetter resultGetter) throws SQLException {
        String desc = resultGetter.getString();
        return BuyRequestNotificationType.forDescription(desc);
    }

    public Object valueOf(String s) {
        return s;
    }

}