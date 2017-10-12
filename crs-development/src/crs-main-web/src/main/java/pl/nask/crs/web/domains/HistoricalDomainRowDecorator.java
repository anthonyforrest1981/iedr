package pl.nask.crs.web.domains;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.displaytag.decorator.TableDecorator;

/**
 * @author Piotr Tkaczyk
 */
public class HistoricalDomainRowDecorator extends TableDecorator {

    public String addRowId() {
        return super.addRowId();
    }

    public String getFormatedDate(Date date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
            return sdf.format(date);
        }
        return null;
    }

}
