package com.android.zukut.bo;

public class User {

	private String fullName;
	private String mobile;
	private long id;
    /**
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }
    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    /**
     * @return the mobile
     */
    public String getMobile() {
        return mobile;
    }
    /**
     * @param mobile the mobile to set
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    /**
     * @return the id
     */
    public long getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

	
	/**
	 * {"st":1,"rs":{"fullName":"gaurav","mobile":"07627191837",
	 * "primaryAccount":"WECAM",
	 * "checkSignUpSt":false,
	 * "id":1,"createdAt":1427862914937,
	 * "createdBy":1,"lastUpdatedBy":1,"lastUpdatedAt":1427862914937,"statusCode":1}}

	 * 
	 * 
	 */
	
	
}
