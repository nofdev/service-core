package org.nofdev.servicefacade
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
            return (CallId) get(CALLID)
        } else {
            return null
        }
    }
    /**
     * 如果已经存在CallId就不创建，否则就创建
     * @return
     */
    public CallId generatCallIdIfAbsente() {
        if (get(CALLID) != null) {
            return (CallId) get(CALLID)
        } else {
            String thisId = UUID.randomUUID().toString()
            CallId callId = new CallId(root: thisId, parent: thisId, id: thisId)
            setCallId(callId)
            return callId
        }
    }

    public CallId generateCallId() {
        CallId callId = this.getCallId()
        String thisId = UUID.randomUUID().toString()
        if (callId) {
            callId.setParent(callId.getId())
            callId.setId(thisId)
        } else {
            callId = new CallId(root: thisId, parent: thisId, id: thisId)
            setCallId(callId)
        }
        callId
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
