package pl.nask.crs.ticket.operation;

public enum IpFailureReason {

    INCORRECT("Incorrect"),
    IP_BUFFER_TOO_SMALL("IP buffer too small"),
    IP_DESTINATION_NET_UNREACHABLE("IP Destination net unreachable"),
    IP_DEST_HOST_UNREACHABLE("IP dest host unreachable"),
    IP_DEST_PORT_UNREACHABLE("IP dest port unreachable"),
    IP_NO_RESOURCES("IP no resources"),
    IP_BAD_OPTION("IP bad option"),
    IP_HW_ERROR("IP hw_error"),
    IP_PACKET_TOO_BIG("IP packet too_big"),
    IP_REQ_TIMED_OUT("IP req timed out"),
    IP_BAD_REQ("IP bad req"),
    IP_BAD_ROUTE("IP bad route"),
    IP_TTL_EXPIRED_TRANSIT("IP ttl expired transit"),
    IP_TTL_EXPIRED_REASSEM("IP ttl expired reassem"),
    IP_PARAM_PROBLEM("IP param_problem"),
    IP_SOURCE_QUENCH("IP source quench"),
    IP_OPTION_TOO_BIG("IP option too_big"),
    IP_BAD_DESTINATION("IP bad destination"),
    IP_ADDR_DELETED("IP addr deleted"),
    IP_SPEC_MTU_CHANGE("IP spec mtu change"),
    IP_MTU_CHANGE("IP mtu_change"),
    IP_UNLOAD("IP unload"),
    IP_ADDR_ADDED("IP addr added"),
    IP_GENERAL_FAILURE("IP general failure"),
    IP_PENDING("IP pending"),
    PING_TIMEOUT("Ping timeout"),
    UNKNOWN_MSG_RETURNED("Unknown msg returned");

    private final String description;

    IpFailureReason(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static IpFailureReason forDescription(final String description) {
        if (description.equals(INCORRECT.description)) {
            return INCORRECT;
        } else if (description.equals(IP_BUFFER_TOO_SMALL.description)) {
            return IP_BUFFER_TOO_SMALL;
        } else if (description.equals(IP_DESTINATION_NET_UNREACHABLE.description)) {
            return IP_DESTINATION_NET_UNREACHABLE;
        } else if (description.equals(IP_DEST_HOST_UNREACHABLE.description)) {
            return IP_DEST_HOST_UNREACHABLE;
        } else if (description.equals(IP_DEST_PORT_UNREACHABLE.description)) {
            return IP_DEST_PORT_UNREACHABLE;
        } else if (description.equals(IP_NO_RESOURCES.description)) {
            return IP_NO_RESOURCES;
        } else if (description.equals(IP_BAD_OPTION.description)) {
            return IP_BAD_OPTION;
        } else if (description.equals(IP_HW_ERROR.description)) {
            return IP_HW_ERROR;
        } else if (description.equals(IP_PACKET_TOO_BIG.description)) {
            return IP_PACKET_TOO_BIG;
        } else if (description.equals(IP_REQ_TIMED_OUT.description)) {
            return IP_REQ_TIMED_OUT;
        } else if (description.equals(IP_BAD_REQ.description)) {
            return IP_BAD_REQ;
        } else if (description.equals(IP_BAD_ROUTE.description)) {
            return IP_BAD_ROUTE;
        } else if (description.equals(IP_TTL_EXPIRED_TRANSIT.description)) {
            return IP_TTL_EXPIRED_TRANSIT;
        } else if (description.equals(IP_TTL_EXPIRED_REASSEM.description)) {
            return IP_TTL_EXPIRED_REASSEM;
        } else if (description.equals(IP_PARAM_PROBLEM.description)) {
            return IP_PARAM_PROBLEM;
        } else if (description.equals(IP_SOURCE_QUENCH.description)) {
            return IP_SOURCE_QUENCH;
        } else if (description.equals(IP_OPTION_TOO_BIG.description)) {
            return IP_OPTION_TOO_BIG;
        } else if (description.equals(IP_BAD_DESTINATION.description)) {
            return IP_BAD_DESTINATION;
        } else if (description.equals(IP_ADDR_DELETED.description)) {
            return IP_ADDR_DELETED;
        } else if (description.equals(IP_SPEC_MTU_CHANGE.description)) {
            return IP_SPEC_MTU_CHANGE;
        } else if (description.equals(IP_MTU_CHANGE.description)) {
            return IP_MTU_CHANGE;
        } else if (description.equals(IP_UNLOAD.description)) {
            return IP_UNLOAD;
        } else if (description.equals(IP_ADDR_ADDED.description)) {
            return IP_ADDR_ADDED;
        } else if (description.equals(IP_GENERAL_FAILURE.description)) {
            return IP_GENERAL_FAILURE;
        } else if (description.equals(IP_PENDING.description)) {
            return IP_PENDING;
        } else if (description.equals(PING_TIMEOUT.description)) {
            return PING_TIMEOUT;
        } else if (description.equals(UNKNOWN_MSG_RETURNED.description)) {
            return UNKNOWN_MSG_RETURNED;
        } else {
            throw new IllegalArgumentException("Unsupported value for IpFailureReason: " + description);
        }
    }
}
