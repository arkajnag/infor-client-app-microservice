package io.demoClient.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class User {
	
	private Integer userid;
	private String username;
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public User(Integer userid, String username) {
		this.userid = userid;
		this.username = username;
	}
	public User() {
		
	}
	@Override
	public String toString() {
		return "User [userid=" + userid + ", username=" + username + "]";
	}

}
