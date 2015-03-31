package com.android.zukut.bo;

import android.os.Parcel;
import android.os.Parcelable;

public class GCMResponse implements Parcelable {

    private String message;
    private String ids;
    private int akey;
    private String roomId;

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message
     *            the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the ids
     */
    public String getIds() {
        return ids;
    }

    /**
     * @param ids
     *            the ids to set
     */
    public void setIds(String ids) {
        this.ids = ids;
    }

    /**
     * @return the akey
     */
    public int getAkey() {
        return akey;
    }

    /**
     * @param akey
     *            the akey to set
     */
    public void setAkey(int akey) {
        this.akey = akey;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "GCMResponse [message=" + message + ", ids=" + ids + ", akey="
                + akey + "]";
    }

    public GCMResponse() {
    }

    public GCMResponse(Parcel source) {
        message = source.readString();
        ids = source.readString();
        akey = source.readInt();
    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(message);
        dest.writeString(ids);
        dest.writeInt(akey);
    }

    public static final Parcelable.Creator<GCMResponse> CREATOR = new Creator<GCMResponse>() {
        @Override
        public GCMResponse[] newArray(int size) {
            // TODO Auto-generated method stub
            return new GCMResponse[size];
        }

        @Override
        public GCMResponse createFromParcel(Parcel source) {
            // TODO Auto-generated method stub
            return new GCMResponse(source);
        }
    };

}
