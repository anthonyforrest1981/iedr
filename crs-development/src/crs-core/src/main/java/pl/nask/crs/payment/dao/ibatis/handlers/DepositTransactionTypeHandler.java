package pl.nask.crs.payment.dao.ibatis.handlers;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

import pl.nask.crs.payment.DepositTransactionType;

public class DepositTransactionTypeHandler implements TypeHandlerCallback {
    @Override
    public void setParameter(ParameterSetter setter, Object parameter) throws SQLException {
        String name = null;
        if (parameter instanceof DepositTransactionType) {
            name = ((DepositTransactionType) parameter).name();
        } else {
            Logger.getLogger(DepositTransactionTypeHandler.class).error("Unsupported parameter type " + parameter);
        }
        setter.setString(name);
    }

    @Override
    public Object getResult(ResultGetter resultGetter) throws SQLException {
        final String value = resultGetter.getString();
        try {
            DepositTransactionType result = DepositTransactionType.valueOf(value);
            return result;
        } catch (IllegalArgumentException e) {
            Logger.getLogger(DepositTransactionTypeHandler.class).error(
                    "Unknown value for DepositTransactionType " + value);
            return null;
        }
    }

    @Override
    public Object valueOf(String s) {
        return null;
    }
}
