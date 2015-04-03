package com.android.zukut.bo;

import org.json.JSONObject;

/**
 * Hold output for callAccept web service.
 **/
public class AcceptCallOutput {

    private static final long serialVersionUID = 1L;
    private String rs;
    private String cs;
    private String csMsg;

    /**
     * @return the rs
     */
    public String getRs() {
        return rs;
    }

    /**
     * @param rs
     *            the rs to set
     */
    public void setRs(String rs) {
        this.rs = rs;
    }
    /**
     * @return the cs
     */
    public String getCs() {
        return cs;
    }

    /**
     * @param cs
     *            the cs to set
     */
    public void setCs(String cs) {
        this.cs = cs;
    }

    /**
     * @return the csMsg
     */
    public String getCsMsg() {
        return csMsg;
    }

    /**
     * @param csMsg
     *            the csMsg to set
     */
    public void setCsMsg(String csMsg) {
        this.csMsg = csMsg;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "AcceptCallOutput [rs=" + rs + ", cs=" + cs + ", csMsg=" + csMsg
                + "]";
    }

}
