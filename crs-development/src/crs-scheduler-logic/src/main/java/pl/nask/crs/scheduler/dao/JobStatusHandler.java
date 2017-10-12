package pl.nask.crs.scheduler.dao;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

import pl.nask.crs.scheduler.JobStatus;

public class JobStatusHandler implements TypeHandlerCallback {
    @Override
    public void setParameter(ParameterSetter setter, Object parameter) throws SQLException {
        String typeName = null;
        if (parameter instanceof JobStatus) {
            typeName = ((JobStatus) parameter).getName();
        } else {
            Logger.getLogger(JobStatusHandler.class).error(
                    "Unsupported parameter type for PaymentMethodHandler " + parameter);
        }
        setter.setString(typeName);
    }

    @Override
    public Object getResult(ResultGetter resultGetter) throws SQLException {
        String type = resultGetter.getString();
        return JobStatus.forJobName(type);
    }

    @Override
    public Object valueOf(String s) {
        return s;
    }
}
