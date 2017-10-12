package pl.nask.crs.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.user.User;
import pl.nask.crs.user.dao.UserDAO;
import pl.nask.crs.user.search.UserSearchCriteria;
import pl.nask.crs.user.service.UserSearchService;

public class UserSearchServiceImpl implements UserSearchService {

    private UserDAO dao;

    public UserSearchServiceImpl(UserDAO dao) {
        this.dao = dao;
    }

    public LimitedSearchResult<User> find(UserSearchCriteria searchCriteria, long offset, long limit) {
        return dao.find(searchCriteria, offset, limit);
    }

    public SearchResult<User> find(UserSearchCriteria searchCriteria) {
        return dao.find(searchCriteria);
    }

    public User get(String nicHandleId) {
        return dao.get(nicHandleId);
    }

    @Override
    public List<String> findInternalUsers() {
        UserSearchCriteria criteria = new UserSearchCriteria();
        criteria.setIsInternal(true);
        SearchResult<User> result = find(criteria);
        List<String> internalUsers = new ArrayList<>();
        for (User user : result.getResults()) {
            internalUsers.add(user.getUsername());
        }
        return internalUsers;
    }
}
