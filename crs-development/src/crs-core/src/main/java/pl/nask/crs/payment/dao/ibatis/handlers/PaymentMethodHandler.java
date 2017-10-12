package pl.nask.crs.payment.dao.ibatis.handlers;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

import pl.nask.crs.payment.PaymentMethod;

public class PaymentMethodHandler implements TypeHandlerCallback {
    @Override
    public void setParameter(ParameterSetter setter, Object parameter) throws SQLException {
        String paymentMethodName = null;
        if (parameter instanceof PaymentMethod) {
            paymentMethodName = ((PaymentMethod) parameter).getFullName();
        } else {
            Logger.getLogger(PaymentMethodHandler.class).error(
                    "Unsupported parameter for PaymentMethodHandler " + parameter);
        }
        setter.setString(paymentMethodName);
    }

    @Override
    public Object getResult(ResultGetter resultGetter) throws SQLException {
        String type = resultGetter.getString();
        return PaymentMethod.forFullName(type);
    }

    @Override
    public Object valueOf(String s) {
        return null;
    }
}
