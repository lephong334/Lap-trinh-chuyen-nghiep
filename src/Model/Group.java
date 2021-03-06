package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Group {
	private String id;
	private String name;
	private User owner;
	private List<String> listOfMessages;
	private List<Media> listOfMedias;
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

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public List<String> getListOfMessages() {
		return listOfMessages;
	}

	public void setListOfMessages(List<String> listOfMessages) {
		this.listOfMessages = listOfMessages;
	}

	public List<Media> getListOfMedias() {
		return listOfMedias;
	}

	public void setListOfMedias(List<Media> listOfMedias) {
		this.listOfMedias = listOfMedias;
	}

	public List<User> getListOfUsers() {
		return listOfUsers;
	}

	public void setListOfUsers(List<User> listOfUsers) {
		this.listOfUsers = listOfUsers;
	}

}
