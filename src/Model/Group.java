package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Group {
	protected String name;
	protected Account owner;

	protected List<String> listOfMessages;
	protected List<String> listOfMedias;
	protected List<Account> listOfUsers;
	protected int[] showLimitedMessage;

	public boolean leaveTheGroup(Account user) {
		return listOfUsers.remove((user));
	}

	public List<String> showLimitedMessageGroup(int lastestMessage, int oldMessage) {
		int[] temporaryInteger = { 0, lastestMessage, oldMessage };
		showLimitedMessage = temporaryInteger;

		List<String> temporary = new ArrayList<String>();
		int limitLoop = lastestMessage < listOfMessages.size() ? lastestMessage : listOfMessages.size();
		for (int i = 0; i < limitLoop; i++) {
			temporary.add(listOfMessages.get(i));
		}

		return temporary;

	}

	public List<String> showNextLimitedMessageGroup() {

		int[] temporaryInteger = showLimitedMessage;
		temporaryInteger[0] = temporaryInteger[1];
		temporaryInteger[1] = temporaryInteger[1] + temporaryInteger[2];
		List<String> temporary = new ArrayList<String>();

		int limitLoop = temporaryInteger[1] < listOfMessages.size() ? temporaryInteger[1] : listOfMessages.size();
		for (int i = temporaryInteger[0]; i < limitLoop; i++) {
			temporary.add(listOfMessages.get(i));
		}

		return temporary;
	}

	public String findText(String keyword) {
		for (int i = 0; i < listOfMessages.size(); i++) {
			if (listOfMessages.get(i).contains(keyword)) {
				return listOfMessages.get(i);
			}
		}
		return null;
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

	public int receiveMessageGroup(String message, Account user) {
		listOfMessages.add(user.getUserName() + ": " + message);
		return listOfMessages.size() - 1;
	}

	public List<String> showAllMessageGroup() {
		return listOfMessages;
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

	public Account getOwner() {
		return owner;
	}

	public void setOwner(Account owner) {
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

	public List<Account> getListOfUsers() {
		return listOfUsers;
	}

	public void setListOfUsers(List<Account> listOfUsers) {
		this.listOfUsers = listOfUsers;
	}

}
