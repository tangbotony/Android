package com.fanxiaotong.client.bean;

import java.io.Serializable;

public class FakeVersion implements Serializable {
	private static final long serialVersionUID = 1L;
	private String ulr = "";
	private String version = "";
	private String releasetime = "";
	private String describe = "";

	public FakeVersion() {
	}

	

	public FakeVersion(String ulr, String version, String releasetime,
			String describe) {
		super();
		this.ulr = ulr;
		this.version = version;
		this.releasetime = releasetime;
		this.describe = describe;
	}



	public String getUlr() {
		return ulr;
	}



	public void setUlr(String ulr) {
		this.ulr = ulr;
	}



	public String getVersion() {
		return version;
	}



	public void setVersion(String version) {
		this.version = version;
	}



	public String getReleasetime() {
		return releasetime;
	}



	public void setReleasetime(String releasetime) {
		this.releasetime = releasetime;
	}



	public String getDescribe() {
		return describe;
	}



	public void setDescribe(String describe) {
		this.describe = describe;
	}



	@Override
	public String toString() {
		return "Version [describe=" + describe + ", releasetime=" + releasetime
				+ ", ulr=" + ulr + ", version=" + version + "]";
	}

}
