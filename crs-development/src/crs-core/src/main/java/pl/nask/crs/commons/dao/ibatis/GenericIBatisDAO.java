package pl.nask.crs.commons.dao.ibatis;

import java.util.*;

import org.apache.log4j.Logger;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;

/*>>>import org.checkerframework.checker.nullness.qual.EnsuresNonNull;*/
/*>>>import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;*/
/*>>>import org.checkerframework.checker.nullness.qual.MonotonicNonNull;*/
/*>>>import org.checkerframework.checker.nullness.qual.NonNull;*/
/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/
/*>>>import org.checkerframework.checker.nullness.qual.RequiresNonNull;*/
/*>>>import org.checkerframework.checker.initialization.qual.UnderInitialization;*/

public class GenericIBatisDAO</*>>>@NonNull*/ DTO, /*>>>@NonNull*/ KEY> extends SqlMapClientLoggingDaoSupport implements
        GenericDAO</*>>>@NonNull*/ DTO, /*>>>@NonNull*/ KEY> {

    private /*>>>@MonotonicNonNull*/ String createQueryId;

    private /*>>>@MonotonicNonNull*/ String getQueryId;

    private /*>>>@MonotonicNonNull*/ String lockQueryId;

    private /*>>>@MonotonicNonNull*/ String updateQueryId;

    private /*>>>@MonotonicNonNull*/ String updateUsingHistoryQueryId;

    private /*>>>@MonotonicNonNull*/ String deleteQueryId;

    private /*>>>@MonotonicNonNull*/ String findQueryId;

    private /*>>>@MonotonicNonNull*/ String limitedFindQueryId;

    private /*>>>@MonotonicNonNull*/ String countFindQueryId;

    private Map<String, String> sortMapping = new HashMap<>();

    /**
     * Sets mapping for the sorting parameters.
     *
     * @param map if empty, parameters will be simply passed-by to a query
     */
    public void setSortMapping(/*>>>@UnderInitialization(Object.class) GenericIBatisDAO<DTO, KEY> this, */Map<String, String> map) {
        this.sortMapping.clear();
        this.sortMapping.putAll(map);
    }

    /*>>>@EnsuresNonNull("createQueryId")*/
    public void setCreateQueryId(
            /*>>>@UnderInitialization(Object.class) GenericIBatisDAO<DTO, KEY> this, */String createQueryId) {
        this.createQueryId = createQueryId;
    }

    /*>>>@EnsuresNonNull("getQueryId")*/
    public void setGetQueryId(
            /*>>>@UnderInitialization(Object.class) GenericIBatisDAO<DTO, KEY> this, */String getQueryId) {
        this.getQueryId = getQueryId;
    }

    /*>>>@EnsuresNonNull("lockQueryId")*/
    public void setLockQueryId(
            /*>>>@UnderInitialization(Object.class) GenericIBatisDAO<DTO, KEY> this, */String lockQueryId) {
        this.lockQueryId = lockQueryId;
    }

    /*>>>@EnsuresNonNull("updateQueryId")*/
    public void setUpdateQueryId(
            /*>>>@UnderInitialization(Object.class) GenericIBatisDAO<DTO, KEY> this, */String updateQueryId) {
        this.updateQueryId = updateQueryId;
    }

    /*>>>@EnsuresNonNull("updateUsingHistoryQueryId")*/
    public void setUpdateUsingHistoryQueryId(
            /*>>>@UnderInitialization(Object.class) GenericIBatisDAO<DTO, KEY> this, */
            String updateUsingHistoryQueryId) {
        this.updateUsingHistoryQueryId = updateUsingHistoryQueryId;
    }

    /*>>>@EnsuresNonNull("deleteQueryId")*/
    public void setDeleteQueryId(
            /*>>>@UnderInitialization(Object.class) GenericIBatisDAO<DTO, KEY> this, */String deleteQueryId) {
        this.deleteQueryId = deleteQueryId;
    }

    /*>>>@EnsuresNonNull("findQueryId")*/
    public void setFindQueryId(
            /*>>>@UnderInitialization(Object.class) GenericIBatisDAO<DTO, KEY> this, */String findQueryId) {
        this.findQueryId = findQueryId;
    }

    /*>>>@EnsuresNonNull("limitedFindQueryId")*/
    public void setLimitedFindQueryId(
            /*>>>@UnderInitialization(Object.class) GenericIBatisDAO<DTO, KEY> this, */String limitedFindQueryId) {
        this.limitedFindQueryId = limitedFindQueryId;
    }

    /*>>>@EnsuresNonNull("countFindQueryId")*/
    public void setCountFindQueryId(
            /*>>>@UnderInitialization(Object.class) GenericIBatisDAO<DTO, KEY> this, */String countFindQueryId) {
        this.countFindQueryId = countFindQueryId;
    }

    @Override
    public void create(DTO dto) {
        if (createQueryId == null)
            throw new UnsupportedOperationException("generic dao create");
        Validator.assertNotNull(dto, "the object to be created");
        performInsert(createQueryId, dto);
    }

    @Override
    public/*@Nullable*/DTO get(KEY id) {
        if (getQueryId == null)
            throw new UnsupportedOperationException("generic dao get");
        Validator.assertNotNull(id, "the id of the object to be retrieved");
        return performQueryForObject(getQueryId, id);
    }

    @Override
    public boolean lock(KEY id) {
        if (lockQueryId == null)
            throw new UnsupportedOperationException("generic dao lock");

        Validator.assertNotNull(id, "the id of the object to be locked");
        return performQueryForObject(lockQueryId, id) != null;
    }

    @Override
    public void update(DTO dto) {
        if (updateQueryId == null)
            throw new UnsupportedOperationException("generic dao update");
        Validator.assertNotNull(dto, "the object to be updated");
        performUpdate(updateQueryId, dto);
    }

    @Override
    public void updateUsingHistory(long changeId) {
        if (updateUsingHistoryQueryId == null)
            throw new UnsupportedOperationException("generic dao updateUsingHistory");
        performUpdate(updateUsingHistoryQueryId, changeId);
    }

    @Override
    public void deleteById(KEY id) {
        if (deleteQueryId == null)
            throw new UnsupportedOperationException("generic dao delete");
        Validator.assertNotNull(id, "the id of the object to be deleted");
        performDelete(deleteQueryId, id);
    }

    @Override
    public void delete(DTO dto) {
        if (deleteQueryId == null)
            throw new UnsupportedOperationException("generic dao delete");
        Validator.assertNotNull(dto, "the object to be deleted");
        performDelete(deleteQueryId, dto);
    }

    public int count(SearchCriteria<? super DTO> criteria) {
        if (countFindQueryId == null) {
            throw new UnsupportedOperationException("generic dao count");
        }
        return performCount(countFindQueryId, criteria);
    }

    protected int performCount(String countQueryId, SearchCriteria<?> criteria) {
        return performCount(countQueryId, new FindParameters(criteria));
    }

    protected int performCount(String countQueryId, FindParameters params) {
        Integer total = performQueryForObject(countQueryId, params);
        assert total != null : "@AssumeAssertion(nullness)";
        return total;
    }

    public boolean exists(SearchCriteria<? super DTO> criteria) {
        return (count(criteria) > 0);
    }

    public SearchResult<DTO> find(SearchCriteria<? super DTO> criteria) {
        return find(criteria, Collections.<SortCriterion>emptyList());
    }

    public LimitedSearchResult<DTO> find(SearchCriteria<? super DTO> criteria, long offset, long limit) {
        return find(criteria, offset, limit, Collections.<SortCriterion>emptyList());
    }

    public LimitedSearchResult<DTO> find(SearchCriteria<? super DTO> criteria, long offset, long limit,
    /*>>>@Nullable*/ List<SortCriterion> sortBy) {
        if ((findQueryId == null && limitedFindQueryId == null) || countFindQueryId == null) {
            StringBuilder msgBuilder = new StringBuilder("generic dao limited find :");
            if (findQueryId == null && limitedFindQueryId == null) {
                msgBuilder.append(" No limitedFindQueryId or findQueryId defined.");
            }
            if (countFindQueryId == null)
                msgBuilder.append(" No countFindQueryId defined.");
            throw new UnsupportedOperationException(msgBuilder.toString());
        }
        @SuppressWarnings("nullness")
        String queryId = limitedFindQueryId != null ? limitedFindQueryId : findQueryId;
        assert queryId != null : "@AssumeAssertion(nullness)";
        return performFind(queryId, countFindQueryId, criteria, offset, limit, sortBy);
    }

    public LimitedSearchResult<DTO> performFind(String findQUeryId, String countQueryId, SearchCriteria<?> criteria,
            long offset, long limit, /*>>>@Nullable*/ List<SortCriterion> sortBy) {
        return performFindWithParameters(findQUeryId, countQueryId, criteria, offset, limit, sortBy, null);
    }

    public LimitedSearchResult<DTO> performFindWithParameters(String findQUeryId, String countQueryId,
            SearchCriteria<?> criteria, long offset, long limit, /*>>>@Nullable*/ List<SortCriterion> sortBy,
            /*>>>@Nullable*/ Map<String, Object> specialParams) {
        FindParameters params = new FindParameters(criteria, specialParams);
        int total = performCount(countQueryId, params);
        List<DTO> list;
        if (total == 0) {
            list = new ArrayList<>();
        } else {
            params.setOrderBy(sortBy).setLimit(offset, limit);
            list = performQueryForList(findQUeryId, params);
        }
        return new LimitedSearchResult<>(criteria, limit, offset, list, total);
    }

    public SearchResult<DTO> find(SearchCriteria<? super DTO> criteria, /*>>>@Nullable*/ List<SortCriterion> sortBy) {
        if (findQueryId == null)
            throw new UnsupportedOperationException("generic dao find");

        FindParameters params = new FindParameters(criteria).setOrderBy(sortBy);
        List<DTO> list = performQueryForList(findQueryId, params);
        return new SearchResult<>(criteria, list);
    }

    protected <T> List<T> performQueryForList(String queryId, FindParameters params) {
        Map<String, Object> parameterMap = params.getMap();
        return performQueryForList(queryId, parameterMap);
    }

    protected </*>>>@Nullable*/ T> T performQueryForObject(String queryId, FindParameters params) {
        Map<String, Object> parameterMap = params.getMap();
        return performQueryForObject(queryId, parameterMap);
    }

    public class FindParameters {
        public static final int DEFAULT_OFFSET = 0;
        public static final int DEFAULT_LIMIT = 1;

        Map<String, Object> map = new HashMap<>();

        public FindParameters() {
            map.put("criteria", new HashMap<String, Object>());
        }

        public FindParameters(SearchCriteria criteria) {
            map.put("criteria", criteria);
        }

        public FindParameters(SearchCriteria criteria, /*>>>@Nullable*/ Map<String, Object> specialParams) {
            this(criteria);
            if (specialParams != null) {
                for (String key : specialParams.keySet()) {
                    addSpecialParameter(key, specialParams.get(key));
                }
            }
        }

        public FindParameters setOrderBy(/*>>>@Nullable*/ List<SortCriterion> sortBy) {
            map.put("sortCriteria", mapSortCriteria(sortBy, sortMapping));
            return this;
        }

        public FindParameters setOrderBy(/*>>>@Nullable*/ List<SortCriterion> sortBy, Map<String, String> sortMap) {
            map.put("sortCriteria", mapSortCriteria(sortBy, sortMap));
            return this;
        }

        public FindParameters setLimit(long offset, long limit) {
            map.put("offset", offset);
            map.put("limit", limit);
            return this;
        }

        public Map<String, Object> getMap() {
            return map;
        }

        protected List<SortCriterion> mapSortCriteria(/*>>>@Nullable*/ List<SortCriterion> sortBy,
                Map<String, String> sortMapping) {
            if (sortBy == null) {
                sortBy = Collections.emptyList();
            }

            List<SortCriterion> result = new ArrayList<>(sortBy.size());
            final Logger logger = Logger.getLogger(this.getClass());
            if (sortMapping.isEmpty() && !sortBy.isEmpty()) {
                logger.warn("Empty sortMap, but passed in sort criteria. They will be ignored.");
                return result;
            }
            for (SortCriterion criterion : sortBy) {
                String columnName = sortMapping.get(criterion.getSortBy());
                if (columnName != null) {
                    result.add(new SortCriterion(columnName, criterion.isAscending()));
                } else {
                    logger.warn("Unknown sortBy column " + criterion.getSortBy());
                }
            }

            return result;
        }

        public FindParameters addCriterion(String key, Object value) {
            Object criteria = map.get("criteria");
            if (criteria instanceof Map) {
                ((Map) criteria).put(key, value);
            } else {
                throw new IllegalStateException(
                        "Can't add criterion if the criteria was added during the construction of the object");
            }
            return this;
        }

        public void addSpecialParameter(/*>>>@UnderInitialization(Object.class) FindParameters this, */String key,
                Object value) {
            Object criteria = map.get("special");
            if (criteria == null) {
                criteria = new HashMap<String, Object>();
                map.put("special", criteria);
            }
            ((Map) criteria).put(key, value);
        }

        public long getOffset() {
            if (map.containsKey("offset")) {
                Long offset = (Long) map.get("offset");
                assert offset != null : "@AssumeAssertion(nullness)";
                return offset;
            }
            return DEFAULT_OFFSET;
        }

        public long getLimit() {
            if (map.containsKey("limit")) {
                Long limit = (Long) map.get("limit");
                assert limit != null : "@AssumeAssertion(nullness)";
                return limit;
            }
            return DEFAULT_LIMIT;
        }
    }
}
