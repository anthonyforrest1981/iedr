package pl.nask.crs.commons.dao.ibatis.handlers;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

import pl.nask.crs.commons.exceptions.IncorrectUtf8FormatException;
import pl.nask.crs.commons.utils.Validator;

public class StringHandler implements TypeHandlerCallback {

    public static final Logger LOGGER = Logger.getLogger(StringHandler.class);

    @Override
    public Object getResult(ResultGetter resultGetter) throws SQLException {
        String value = resultGetter.getString();
        try {
            return Validator.getNormalizedAndValidatedUtf8(value);
        } catch (IncorrectUtf8FormatException e) {
            String hexValue = "";
            try {
                hexValue = Hex.encodeHexString(value.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e1) {
                LOGGER.error("Unsupported encoding UTF-8", e1);
            }
            LOGGER.fatal("String from database >>" + value + "<< failed to validate as utf-8 string. Hex form: "
                    + hexValue, e);
            throw new SQLException(e);
        }
    }

    @Override
    public void setParameter(ParameterSetter setter, Object o) throws SQLException {
        String str = (String) o;
        try {
            setter.setString(Validator.getNormalizedAndValidatedUtf8(str));
        } catch (IncorrectUtf8FormatException e) {
            String hexValue = "";
            try {
                hexValue = Hex.encodeHexString(str.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e1) {
                LOGGER.error("Unsupported encoding UTF-8", e1);
            }
            LOGGER.fatal("String >>" + str + "<< passed to database failed to validate as utf-8 string. Hex form: "
                    + hexValue, e);
            throw new SQLException(e);
        }
    }

    @Override
    public Object valueOf(String s) {
        return s;
    }
}
