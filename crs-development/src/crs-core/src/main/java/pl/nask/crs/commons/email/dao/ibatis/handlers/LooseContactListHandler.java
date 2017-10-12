package pl.nask.crs.commons.email.dao.ibatis.handlers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;

import pl.nask.crs.commons.dao.ibatis.handlers.LooseStringHandler;

public class LooseContactListHandler extends LooseStringHandler {

    @Override
    public void setParameter(ParameterSetter setter, Object o) throws SQLException {
        String param = null;
        if (o != null) {
            if (o instanceof String) {
                param = (String) o;
                if (param.isEmpty()) param = null;
            } else if (o instanceof List) {
                // assume list of Strings
                param = StringUtils.join((List) o, ", ");
                if (param.isEmpty()) param = null;
            }
        }
        super.setParameter(setter, param);
    }

    /**
     * Parses addressee list
     *
     * @param resultGetter
     * @return List<String> returns list, if there was no addressee list is empty
     * @throws SQLException
     */
    public Object getResult(ResultGetter resultGetter) throws SQLException {
        String addresses = (String) super.getResult(resultGetter);
        if (addresses == null)
            return new ArrayList<String>();
        String[] adrs = addresses.split("(\\s*,)+\\s*");
        return new ArrayList<>(Arrays.asList(adrs));
    }
}
