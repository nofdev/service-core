package org.nofdev.servicefacade;
/**
 * Created by Qiang on 11/4/15.
 */
public class CallId {
    private String id;
    private String parent;
    private String root;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }
}
