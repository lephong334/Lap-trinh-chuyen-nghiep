package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Group {
	private String id;
	private String name;
	private User owner;
	protected List<User> listOfUsers;

	public Group(List<User> listOfUsers) {
		this.listOfUsers = listOfUsers;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getListOfUsers() {
		return listOfUsers;
	}

	public void setListOfUsers(List<User> listOfUsers) {
		this.listOfUsers = listOfUsers;
	}

}
