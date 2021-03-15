package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Group {
	private String name;
	private User owner;

	private List<String> listOfMessages;
	private List<Media> listOfMedias;
	protected List<User> listOfUsers;

	public void createGroup(String name, User user) {
		this.listOfUsers = new ArrayList<User>();
		this.name = name;
		this.listOfUsers.add(user);
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
