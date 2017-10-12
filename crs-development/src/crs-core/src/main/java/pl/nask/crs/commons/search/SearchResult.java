package pl.nask.crs.commons.search;

import java.util.Collections;
import java.util.List;

public class SearchResult<T> {

    protected final SearchCriteria criteria;
    protected final List<T> results;

    public SearchResult(SearchCriteria criteria, List<T> results) {
        this.criteria = criteria;
        this.results = Collections.unmodifiableList(results);
    }

    /**
     *
     * @return criteria, which were used in search
     */
    public SearchCriteria getCriteria() {
        return criteria;
    }

    /**
     *
     * @return read-only list of results
     */
    public List<T> getResults() {
        return results;
    }

}
