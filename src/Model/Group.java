package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Group {
	private String id;
	private String groupName;
	private User groupOwner;
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

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public User getGroupOwner() {
		return groupOwner;
	}

	public void setGroupOwner(User groupOwner) {
		this.groupOwner = groupOwner;
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
