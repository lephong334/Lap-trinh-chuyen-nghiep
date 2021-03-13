package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Group {
	private String id;
	private String name;
	private User owner;
	private User admin;
	private DataStorge dataStorage;
	private List<String> listOfMessages;
	private List<Media> listOfMedias;
	protected List<User> listOfUsers;
	private String joinCode;

	public Group(DataStorge dataStorge) {
		listOfUsers = new ArrayList<>();
		this.dataStorage = dataStorge;
	}

	public boolean createGroup(String name, User user) {
		if (dataStorage.checkNameOfGroup(name)) {
			this.name = name;
			this.admin = user;
			this.listOfUsers.add(user);
			dataStorage.storeGroup(this);
			return true;
		}
		return false;
	}

	public boolean joinByCode(User user, String code) {
		if (user != null && code == this.joinCode) {
			this.listOfUsers.add(user);
			return true;
		}
		return false;
	}

	public String createJoinCode() {
		this.joinCode = getAlphaNumericString(8);
		return this.joinCode;
	}

	private String getAlphaNumericString(int n) {
		// chose a Character random from this String
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

		// create StringBuffer size of AlphaNumericString
		StringBuilder sb = new StringBuilder(n);

		for (int i = 0; i < n; i++) {
			// generate a random number between
			// 0 to AlphaNumericString variable length
			int index = (int) (AlphaNumericString.length() * Math.random());

			// add Character one by one in end of sb
			sb.append(AlphaNumericString.charAt(index));
		}
		return sb.toString();
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
