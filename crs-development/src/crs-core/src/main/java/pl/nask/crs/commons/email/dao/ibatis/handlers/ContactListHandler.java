package pl.nask.crs.commons.email.dao.ibatis.handlers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import com.ibatis.sqlmap.client.extensions.ResultGetter;

import pl.nask.crs.commons.dao.ibatis.handlers.StringHandler;

public class ContactListHandler extends StringHandler {

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
