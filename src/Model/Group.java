package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Group {
	private String name;
	private User owner;

	private List<String> listOfMessages;
	private List<String> listOfMedias;
	protected List<User> listOfUsers;

	public void createGroup(String name, User user) {
		this.listOfUsers = new ArrayList<User>();
		this.listOfMessages = new ArrayList<String>();
		this.listOfMedias = new ArrayList<String>();
		this.name = name;
		this.listOfUsers.add(user);
	}

	public void receiveFileUser(String filename) {
		this.listOfMedias.add(filename);
	}

	public void deleteFile(String filename) {
		for (int i = 0; i < listOfMedias.size(); i++) {
			if (listOfMedias.get(i).endsWith(filename)) {
				listOfMedias.remove(i);
				break;
			}
		}
	}

	public int receiveMessageGroup(String message, User user) {
		listOfMessages.add(user.getUserName() + ": " + message);
		return listOfMessages.size() - 1;
	}

	public String showAllMessageGroup() {
		String out = "";
		for (int i = 0; i < listOfMessages.size(); i++) {
			out += listOfMessages.get(i) + "\n";
		}
		return out;
	}

	public boolean deleteMessageGroup(int idMessage) {
		if (idMessage < listOfMessages.size()) {
			listOfMessages.set(idMessage, "This message has been deleted");
			return true;
		}
		return false;
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

	public List<String> getListOfMedias() {
		return listOfMedias;
	}

	public void setListOfMedias(List<String> listOfMedias) {
		this.listOfMedias = listOfMedias;
	}

	public List<User> getListOfUsers() {
		return listOfUsers;
	}

	public void setListOfUsers(List<User> listOfUsers) {
		this.listOfUsers = listOfUsers;
	}

}
