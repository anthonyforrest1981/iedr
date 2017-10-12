package pl.nask.crs.commons.dao;

import java.util.Date;

import pl.nask.crs.history.HistoricalObject;

/*>>>import org.checkerframework.checker.nullness.qual.NonNull;*/

public interface HistoricalDAO</*>>>@NonNull*/ DTO, /*>>>@NonNull*/ KEY> extends GenericDAO<HistoricalObject<DTO>, KEY> {

    KEY create(DTO dto, Date changeDate, String changedBy);

}
