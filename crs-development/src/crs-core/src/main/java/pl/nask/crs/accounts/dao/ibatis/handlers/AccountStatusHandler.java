package pl.nask.crs.accounts.dao.ibatis.handlers;

import java.sql.SQLException;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

import pl.nask.crs.accounts.AccountStatus;

public class AccountStatusHandler implements TypeHandlerCallback {

    @Override
    public Object getResult(ResultGetter getter) throws SQLException {
        String code = getter.getString();
        return AccountStatus.forCode(code);
    }

    @Override
    public void setParameter(ParameterSetter setter, Object obj) throws SQLException {
        if (obj instanceof AccountStatus) {
            AccountStatus status = (AccountStatus) obj;
            setter.setString(status.getCode());
        } else {
            throw new IllegalArgumentException(String.format("Invalid value for AccountStatus: %s", obj));
        }
    }

    @Override
    public Object valueOf(String nullValue) {
        return nullValue;
    }

}
