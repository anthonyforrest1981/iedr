package pl.nask.crs.secondarymarket.dao.ibatis.handlers;

import java.sql.SQLException;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

import pl.nask.crs.domains.CustomerType;

public class CustomerTypeByNameHandler implements TypeHandlerCallback {

    @Override
    public Object getResult(ResultGetter getter) throws SQLException {
        String name = getter.getString();
        return CustomerType.forName(name);
    }

    @Override
    public void setParameter(ParameterSetter setter, Object obj) throws SQLException {
        if (obj instanceof CustomerType) {
            CustomerType status = (CustomerType) obj;
            setter.setString(status.name());
        } else {
            throw new IllegalArgumentException(String.format("Invalid value for handler: %s", obj));
        }
    }

    @Override
    public Object valueOf(String nullValue) {
        return nullValue;
    }

}