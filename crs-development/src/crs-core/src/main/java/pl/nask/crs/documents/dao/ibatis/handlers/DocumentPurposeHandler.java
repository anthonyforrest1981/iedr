package pl.nask.crs.documents.dao.ibatis.handlers;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

import pl.nask.crs.documents.DocumentPurpose;

public class DocumentPurposeHandler implements TypeHandlerCallback {

    @Override
    public void setParameter(ParameterSetter parameterSetter, Object o) throws SQLException {
        String purpose = null;
        if (o instanceof DocumentPurpose) {
            purpose = ((DocumentPurpose) o).getValue();
        } else {
            Logger.getLogger(DocumentPurposeHandler.class).fatal(
                    "DocumentPurposeHandler used with unsupported parameter " + o);
        }
        parameterSetter.setString(purpose);
    }

    @Override
    public Object getResult(ResultGetter resultGetter) throws SQLException {
        String purpose = resultGetter.getString();
        return DocumentPurpose.fromValue(purpose);
    }

    @Override
    public Object valueOf(String s) {
        return s;
    }
}
