package pl.nask.crs.payment.dao.ibatis.handlers;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

import pl.nask.crs.payment.OperationType;

public class OperationTypeHandler implements TypeHandlerCallback {
    public void setParameter(ParameterSetter setter, Object parameter) throws SQLException {
        String value = null;
        if (parameter instanceof OperationType) {
            value = ((OperationType) parameter).getTypeName();
        } else {
            Logger.getLogger(OperationTypeHandler.class).fatal(
                    "OperationTypeHandler used with unsupported parameter " + parameter);
        }
        setter.setString(value);
    }

    public Object getResult(ResultGetter resultGetter) throws SQLException {
        String type = resultGetter.getString();
        if (type == null) {
            return null;
        }
        return OperationType.forTypeName(type);
    }

    public Object valueOf(String s) {
        return null;
    }

}
