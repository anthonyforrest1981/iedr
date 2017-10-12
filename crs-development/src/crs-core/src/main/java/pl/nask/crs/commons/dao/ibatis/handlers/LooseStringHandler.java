package pl.nask.crs.commons.dao.ibatis.handlers;

import java.sql.SQLException;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;

import pl.nask.crs.commons.exceptions.IncorrectUtf8FormatException;
import pl.nask.crs.commons.utils.Validator;

public class LooseStringHandler extends StringHandler {

    @Override
    public Object getResult(ResultGetter resultGetter) throws SQLException {
        try {
            return super.getResult(resultGetter);
        } catch (SQLException e) {
            if (e.getCause() instanceof IncorrectUtf8FormatException) {
                // don't throw, just normalize the string
                return Validator.getNormalizedUtf8(resultGetter.getString());
            } else
                throw e;
        }
    }

    @Override
    public void setParameter(ParameterSetter setter, Object o) throws SQLException {
        try {
            super.setParameter(setter, o);
        } catch (SQLException e) {
            if (e.getCause() instanceof IncorrectUtf8FormatException) {
                // don't throw, just normalize the string
                setter.setString(Validator.getNormalizedUtf8((String) o));
            } else
                throw e;
        }
    }
}
