package pl.nask.crs.payment.dao.ibatis.handlers;

import java.sql.SQLException;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

import pl.nask.crs.domains.CustomerType;

public class CustomerTypeFromBoolean implements TypeHandlerCallback {

    @Override
    public void setParameter(ParameterSetter setter, Object o) throws SQLException {}

    @Override
    public Object getResult(ResultGetter resultGetter) throws SQLException {
        boolean isDirect = resultGetter.getBoolean();
        return isDirect ? CustomerType.Direct : CustomerType.Registrar;
    }

    @Override
    public Object valueOf(String s) {
        return null;
    }
}
