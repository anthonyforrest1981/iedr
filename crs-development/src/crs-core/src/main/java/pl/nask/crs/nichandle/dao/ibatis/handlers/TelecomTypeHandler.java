package pl.nask.crs.nichandle.dao.ibatis.handlers;

import java.sql.SQLException;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

import pl.nask.crs.nichandle.dao.ibatis.objects.TelecomType;

public class TelecomTypeHandler implements TypeHandlerCallback {

    @Override
    public Object getResult(ResultGetter getter) throws SQLException {
        String code = getter.getString();
        if (code == null) {
            // This handler is used for queries that select nic handles and join them with Telecom table. Not all nic
            // handles have telecoms though.
            return null;
        } else {
            return TelecomType.forCode(code);
        }
    }

    @Override
    public void setParameter(ParameterSetter setter, Object obj) throws SQLException {
        if (obj instanceof TelecomType) {
            TelecomType status = (TelecomType) obj;
            setter.setString(status.getCode());
        } else {
            throw new IllegalArgumentException(String.format("Invalid value for TelecomType: %s", obj));
        }
    }

    @Override
    public Object valueOf(String nullValue) {
        return nullValue;
    }

}
