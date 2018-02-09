package org.nofdev.servicefacade

import groovy.transform.CompileStatic

/**
 * Created by Qiang on 11/4/15.
 */
@CompileStatic
public class ServiceContext extends HashMap<String, Object> {

    public static final String PREFIX = "Service-Context";
    public static final String CALLID = "Service-Context-CallId";
    public static final String USER = "Service-Context-User";

    public void putUserdata(String key, String value) {
        this.put("${PREFIX}-${key}".toString(), value)
    }

    public String getUserdata(String key) {
        if (this.get("${PREFIX}-${key}")) {
            this.get("${PREFIX}-${key}")
        } else {
            this.get("${PREFIX.toLowerCase()}-${key}")
        }
    }

    public void removeUserData(String key) {
        this.remove("${PREFIX}-${key}")
        this.remove("${PREFIX.toLowerCase()}-${key}")
    }

    public HashMap<String, Object> getUserDataMap() {
        this.findAll {
            //UserData不包含 CallId, 因为 CallId 是变化的
            it.key.toLowerCase().startsWith("${PREFIX.toLowerCase()}") && (it.key.toLowerCase() != "${CALLID.toLowerCase()}")
        }.collectEntries {
            [it.key.substring(16), it.value]
        } as HashMap<String, Object>
    }

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
    public CallId generateCallIdIfAbsent() {
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
