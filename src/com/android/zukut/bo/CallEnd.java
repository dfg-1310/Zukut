package com.android.zukut.bo;

public class CallEnd {

    private String cd;
    private double tcc;
    private double rc;
    private String cs;
    private String csMsg;

    /**
     * @return the cd
     */
    public String getCd() {
        return cd;
    }

    /**
     * @param cd
     *            the cd to set
     */
    public void setCd(String cd) {
        this.cd = cd;
    }

    /**
     * @return the tcc
     */
    public double getTcc() {
        return tcc;
    }

    /**
     * @param tcc
     *            the tcc to set
     */
    public void setTcc(double tcc) {
        this.tcc = tcc;
    }

    /**
     * @return the rc
     */
    public double getRc() {
        return rc;
    }

    /**
     * @param rc
     *            the rc to set
     */
    public void setRc(double rc) {
        this.rc = rc;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "CallEnd [cd=" + cd + ", tcc=" + tcc + ", rc=" + rc + "]";
    }

    public String getCs() {
        return cs;
    }

    public void setCs(String cs) {
        this.cs = cs;
    }

    public String getCsMsg() {
        return csMsg;
    }

    public void setCsMsg(String csMsg) {
        this.csMsg = csMsg;
    }

}
