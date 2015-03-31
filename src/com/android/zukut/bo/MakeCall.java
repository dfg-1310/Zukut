package com.android.zukut.bo;

import android.os.Parcel;
import android.os.Parcelable;

public class MakeCall implements Parcelable {

    private String sId;
    private String tId;
    private long chId;
    private long uId;
    private int iId;
    private String fnm;
    private String lnm;
    private int ws;
    private String lc;
    private String cs;
    private String csMsg;
    private int ads;
    private long chtId;

    private String rtcSid;
    private String rtcTid;

    private int hvCht;

    /**
     * @return the hvCht
     */
    public int getHvCht() {
        return hvCht;
    }

    /**
     * @param hvCht
     *            the hvCht to set
     */
    public void setHvCht(int hvCht) {
        this.hvCht = hvCht;
    }

    /**
     * @return the rtcSid
     */
    public String getRtcSid() {
        return rtcSid;
    }

    /**
     * @param rtcSid
     *            the rtcSid to set
     */
    public void setRtcSid(String rtcSid) {
        this.rtcSid = rtcSid;
    }

    /**
     * @return the rtcTid
     */
    public String getRtcTid() {
        return rtcTid;
    }

    /**
     * @param rtcTid
     *            the rtcTid to set
     */
    public void setRtcTid(String rtcTid) {
        this.rtcTid = rtcTid;
    }

    /**
     * @return the chtId
     */
    public long getChtId() {
        return chtId;
    }

    /**
     * @param chtId
     *            the chtId to set
     */
    public void setChtId(long chtId) {
        this.chtId = chtId;
    }

    /**
     * @return the ads
     */
    public int getAds() {
        return ads;
    }

    /**
     * @param ads
     *            the ads to set
     */
    public void setAds(int ads) {
        this.ads = ads;
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

    /**
     * @return the sId
     */
    public String getsId() {
        return sId;
    }

    /**
     * @param sId
     *            the sId to set
     */
    public void setsId(String sId) {
        this.sId = sId;
    }

    /**
     * @return the tId
     */
    public String gettId() {
        return tId;
    }

    /**
     * @param tId
     *            the tId to set
     */
    public void settId(String tId) {
        this.tId = tId;
    }

    /**
     * @return the chId
     */
    public long getChId() {
        return chId;
    }

    /**
     * @param chId
     *            the chId to set
     */
    public void setChId(long chId) {
        this.chId = chId;
    }

    /**
     * @return the uId
     */
    public long getuId() {
        return uId;
    }

    /**
     * @param uId
     *            the uId to set
     */
    public void setuId(long uId) {
        this.uId = uId;
    }

    /**
     * @return the iId
     */
    public int getiId() {
        return iId;
    }

    /**
     * @param iId
     *            the iId to set
     */
    public void setiId(int iId) {
        this.iId = iId;
    }

    /**
     * @return the fnm
     */
    public String getFnm() {
        return fnm;
    }

    /**
     * @param fnm
     *            the fnm to set
     */
    public void setFnm(String fnm) {
        this.fnm = fnm;
    }

    /**
     * @return the lnm
     */
    public String getLnm() {
        return lnm;
    }

    /**
     * @param lnm
     *            the lnm to set
     */
    public void setLnm(String lnm) {
        this.lnm = lnm;
    }

    /**
     * @return the ws
     */
    public int getWs() {
        return ws;
    }

    /**
     * @param ws
     *            the ws to set
     */
    public void setWs(int ws) {
        this.ws = ws;
    }

    /**
     * @return the lc
     */
    public String getLc() {
        return lc;
    }

    /**
     * @param lc
     *            the lc to set
     */
    public void setLc(String lc) {
        this.lc = lc;
    }

    public MakeCall() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "MakeCall [sId=" + sId + ", tId=" + tId + ", chId=" + chId
                + ", uId=" + uId + ", iId=" + iId + ", fnm=" + fnm + ", lnm="
                + lnm + ", ws=" + ws + ", lc=" + lc + ", cs=" + cs + ", csMsg="
                + csMsg + "]";
    }

    public MakeCall(Parcel source) {
        sId = source.readString();
        tId = source.readString();
        chId = source.readLong();
        uId = source.readLong();
        iId = source.readInt();
        fnm = source.readString();
        lnm = source.readString();
        ws = source.readInt();
        lc = source.readString();
        ads = source.readInt();

        chtId = source.readLong();
        rtcSid = source.readString();
        rtcTid = source.readString();
        hvCht = source.readInt();
    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sId);
        dest.writeString(tId);
        dest.writeLong(chId);
        dest.writeLong(uId);
        dest.writeInt(iId);
        dest.writeString(fnm);
        dest.writeString(lnm);
        dest.writeInt(ws);
        dest.writeString(lc);
        dest.writeInt(ads);
        dest.writeLong(chtId);
        dest.writeString(rtcSid);
        dest.writeString(rtcTid);
        dest.writeInt(hvCht);

    }

    public static final Parcelable.Creator<MakeCall> CREATOR = new Creator<MakeCall>() {

        @Override
        public MakeCall[] newArray(int arg0) {
            // TODO Auto-generated method stub
            return new MakeCall[arg0];
        }

        @Override
        public MakeCall createFromParcel(Parcel arg0) {
            // TODO Auto-generated method stub
            return new MakeCall(arg0);
        }
    };

}
