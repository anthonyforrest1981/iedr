package pl.nask.crs.ticket.dao.ibatis.handlers;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

import pl.nask.crs.ticket.operation.DomainOperation.DomainOperationType;

@SuppressWarnings("nullness")
public class DomainOperationTypeHandler implements TypeHandlerCallback {
    final static private Logger logger = Logger.getLogger(DomainOperationTypeHandler.class);

    public void setParameter(ParameterSetter parameterSetter, Object o) throws SQLException {
        String fullName = null;
        if (o instanceof DomainOperationType) {
            fullName = ((DomainOperationType) o).getFullName();
        } else {
            logger.error("Unsupported parameter type for DomainOperationTypeHandler: " + o);
        }
        parameterSetter.setString(fullName);
    }

    public Object getResult(ResultGetter resultGetter) throws SQLException {
        String type = resultGetter.getString();
        return DomainOperationType.forFullname(type);
    }

    public Object valueOf(String s) {
        return null;
    }
}
