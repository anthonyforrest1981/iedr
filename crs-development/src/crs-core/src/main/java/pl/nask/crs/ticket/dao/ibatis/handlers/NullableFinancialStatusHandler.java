package pl.nask.crs.ticket.dao.ibatis.handlers;

import java.sql.SQLException;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

import pl.nask.crs.ticket.FinancialStatus;

@SuppressWarnings("nullness")
public class NullableFinancialStatusHandler extends FinancialStatusHandler {
    public Object getResult(ResultGetter resultGetter) throws SQLException {
        String desc = resultGetter.getString();
        return desc == null ? null : FinancialStatus.forDescription(desc);
    }
}
