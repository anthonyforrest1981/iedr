package pl.nask.crs.iedrapi;

import pl.nask.crs.iedrapi.exceptions.IedrApiException;

import ie.domainregistry.ieapi_1.CommandType;
import ie.domainregistry.ieapi_1.ResponseType;

public interface CommandExecutor {

    ResponseType execute(AuthData auth, CommandType command, ValidationCallback callback) throws IedrApiException;

}
