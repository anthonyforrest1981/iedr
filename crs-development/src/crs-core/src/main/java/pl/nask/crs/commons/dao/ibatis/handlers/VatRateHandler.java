package pl.nask.crs.commons.dao.ibatis.handlers;

import java.math.BigDecimal;
import java.sql.SQLException;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

import pl.nask.crs.commons.MoneyUtils;

public class VatRateHandler implements TypeHandlerCallback {

    @Override
    public Object getResult(ResultGetter resultGetter) throws SQLException {
        BigDecimal value = (BigDecimal) resultGetter.getObject();
        return MoneyUtils.getScaledVatValue(value);
    }

    @Override
    public void setParameter(ParameterSetter parameterSetter, Object o) throws SQLException {
        parameterSetter.setObject(o);
    }

    @Override
    public Object valueOf(String s) {
        return null;
    }

}
