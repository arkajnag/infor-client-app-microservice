package io.demoClient.model;

import java.util.List;

public class UserDTO {
	
	private List<User> users;

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public UserDTO(List<User> users) {
		this.users = users;
	}

	public UserDTO() {
		
	}
	
}
