package org.nofdev.servicefacade;

import java.util.HashMap;

/**
 * Created by Qiang on 11/4/15.
 */
public class ServiceContext extends HashMap<String, Object> {

    public static final String PREFIX = "Service-Context";
    public static final String CALLID = "Service-Context-CallId";
    public static final String USER = "Service-Context-User";

    public void setCallId(CallId callId) {
        put(CALLID, callId);
    }

    public CallId getCallId() {
        if (get(CALLID) != null) {
            return (CallId) get(CALLID);
        } else {
            return null;
        }
    }


    public void setUser(User user) {
        put(USER, user);
    }

    public User getUser() {
        if (get(USER) != null) {
            return (User) get(USER);
        } else {
            return null;
        }
    }
}
