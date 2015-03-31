package com.android.zukut.bo;

import android.os.Parcel;
import android.os.Parcelable;

public class CallDetail implements Parcelable {

    private int ads;
    private long chId;
    private String tId;
    private String sId;
    private String iText;
    private String cs;
    private String csMsg;

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
     * @return the iText
     */
    public String getiText() {
        return iText;
    }

    /**
     * @param iText
     *            the iText to set
     */
    public void setiText(String iText) {
        this.iText = iText;
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


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */

    public CallDetail() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "CallDetail [ads=" + ads + ", chId=" + chId + ",  tId=" + tId + ", sId=" + sId + ", iText=" + iText
                + ", cs=" + cs + ", csMsg=" + csMsg + ", chtId=" + chtId
                + ", rtcSid=" + rtcSid + ", rtcTid=" + rtcTid + ", hvCht="
                + hvCht + "]";
    }

    public CallDetail(Parcel source) {
        tId = source.readString();
        sId = source.readString();
        iText = source.readString();
        chId = source.readLong();
        ads = source.readInt();
        chtId = source.readLong();
        rtcSid = source.readString();
        rtcTid = source.readString();

        hvCht = source.readInt();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tId);
        dest.writeString(sId);
        dest.writeString(iText);
        dest.writeLong(chId);
        dest.writeInt(ads);
        dest.writeLong(chtId);
        dest.writeString(rtcSid);
        dest.writeString(rtcTid);
        dest.writeInt(hvCht);
    }

    public Long getChtId() {
        return chtId;
    }

    public void setChtId(Long chtId) {
        this.chtId = chtId;
    }

    public String getRtcSid() {
        return rtcSid;
    }

    public void setRtcSid(String rtcSid) {
        this.rtcSid = rtcSid;
    }

    public String getRtcTid() {
        return rtcTid;
    }

    public void setRtcTid(String rtcTid) {
        this.rtcTid = rtcTid;
    }

    public static final Parcelable.Creator<CallDetail> CREATOR = new Creator<CallDetail>() {

        @Override
        public CallDetail[] newArray(int arg0) {
            // TODO Auto-generated method stub
            return new CallDetail[arg0];
        }

        @Override
        public CallDetail createFromParcel(Parcel arg0) {
            // TODO Auto-generated method stub
            return new CallDetail(arg0);
        }
    };

}
