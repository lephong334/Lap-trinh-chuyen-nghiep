package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
	private String id;
	private String lastName;
	private String firstName;
	private String fullName;
	private String password;
	private String gender;
	private String dateOfBirth;
	private String userName;

	private HashMap<String, Integer> listPublicGroup;
	private HashMap<String, Integer> listPrivateGroup;
	private HashMap<String, List<String>> listMessageUser;
	private HashMap<String, List<Integer>> listMessageHasSentToUser;
	private HashMap<String, List<Integer>> listMessageHasSentToGroup;
	private HashMap<String, String> listFileHasSent;
	private List<String> listFileHasReceive;
	private HashMap<String, int[]> showLimitedMessage;
	private HashMap<String, String> aliasList;

	public User(String id, String lastName, String firstName, String password, String gender, String dateOfBirth,
			String userName) {
		this.id = id;
		this.lastName = lastName;
		this.firstName = firstName;
		this.fullName = firstName + " " + lastName;
		this.password = password;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.userName = userName;
		this.listPublicGroup = new HashMap<>();
		this.listPrivateGroup = new HashMap<>();
		this.listMessageUser = new HashMap<>();
		this.listMessageHasSentToUser = new HashMap<>();
		this.listMessageHasSentToGroup = new HashMap<>();
		this.listFileHasSent = new HashMap<>();
		this.listFileHasReceive = new ArrayList<String>();
		this.showLimitedMessage = new HashMap<String, int[]>();
		this.aliasList = new HashMap<String, String>();
	}

	public void setAlias(String username, String alias) {
		this.aliasList.put(username, alias);
	}

	private String changeUsernameToAlias(String content) {
		int index = content.indexOf(" ");
		String firstWord = content.substring(0, index - 1);
		if (this.aliasList.get(firstWord) != null) {
			return this.aliasList.get(firstWord) + ":" + content.substring(index);
		}
		return content;
	}

	public Integer leaveThePublicGroup(String clubname) {
		if (listPublicGroup.get(clubname) != null) {
			return listPublicGroup.remove(clubname);
		}
		return -1;
	}

	public Integer leaveThePrivateGroup(String clubname) {
		if (listPrivateGroup.get(clubname) != null) {
			return listPrivateGroup.remove(clubname);
		}
		return -1;
	}

	public String showLimitedMessageUser(int lastestMessage, int oldMessage, String username) {
		int[] temporaryInteger = { 0, lastestMessage, oldMessage };
		showLimitedMessage.put(username, temporaryInteger);

		List<String> temporaryList = listMessageUser.get(username);

		String out = new String();
		int limitLoop = lastestMessage < temporaryList.size() ? lastestMessage : temporaryList.size();
		for (int i = 0; i < limitLoop; i++) {
			out += changeUsernameToAlias(temporaryList.get(i)) + "\n";
		}
		
		return out;

	}

	public String showNextLimitedMessageUser(String username) {
		if (showLimitedMessage.get(username) != null) {
			int[] temporaryInteger = showLimitedMessage.get(username);
			temporaryInteger[0] = temporaryInteger[1];
			temporaryInteger[1] = temporaryInteger[1] + temporaryInteger[2];
			List<String> temporaryList = listMessageUser.get(username);

			String out = new String();
			int limitLoop = temporaryInteger[1] < temporaryList.size() ? temporaryInteger[1] : temporaryList.size();
			for (int i = temporaryInteger[0]; i < limitLoop; i++) {
				out +=  changeUsernameToAlias(temporaryList.get(i)) + "\n";
			}
			showLimitedMessage.put(username, temporaryInteger);
			
			return out;

		}
		return null;
	}

	public List<Integer> getListPublicGroupId() {
		List<Integer> temporary = new ArrayList<Integer>();
		for (Entry<String, Integer> entry : listPublicGroup.entrySet()) {
			temporary.add(entry.getValue());
		}
		return temporary;
	}

	public List<Integer> getListPrivateGroupId() {
		List<Integer> temporary = new ArrayList<Integer>();
		for (Entry<String, Integer> entry : listPrivateGroup.entrySet()) {
			temporary.add(entry.getValue());
		}
		return temporary;
	}

	public String findTextMessageToUser(String keyword) {
		for (Entry<String, List<String>> entry : listMessageUser.entrySet()) {
			List<String> temporary = entry.getValue();
			for (int i = 0; i < temporary.size(); i++) {
				String pattern = "\\b" + keyword + "\\b";
				Pattern p = Pattern.compile(pattern);
				Matcher m = p.matcher(temporary.get(i));
				if (m.find()) {
					return temporary.get(i);
				}
			}
		}
		return null;
	}

	// file
	public String sendFile(String filename, String receiver) {
		this.listFileHasSent.put(filename, receiver);
		return this.userName + listFileHasSent.size();
	}

	public void receiveFileUser(String filename) {
		this.listFileHasReceive.add(filename);
	}

	public String showListFileHasSentToUserOrGroup(String receiver) {
		String out = new String();
		for (HashMap.Entry<String, String> entry : listFileHasSent.entrySet()) {
			if (entry.getValue().endsWith(receiver)) {
				out += entry.getKey() + "\n";
			}
		}
		return out;
	}

	public String removeFilewhichHasSent(String filename) {
		String out = this.listFileHasSent.get(filename);
		this.listFileHasSent.remove(filename);
		return out;
	}

	public void removeFileWhichHasReceive(String filename) {
		for (int i = 0; i < listFileHasReceive.size(); i++) {
			if (listFileHasReceive.get(i).endsWith(filename)) {
				listFileHasReceive.remove(i);
				break;
			}
		}
	}

	// message
	public boolean sentMessageToGroup(String clubname, String message, int idMessage) {
		if (listPublicGroup.get(clubname) != null || listPrivateGroup.get(clubname) != null) {
			if (listMessageHasSentToGroup.get(clubname) == null) {
				listMessageHasSentToGroup.put(clubname, new ArrayList<Integer>());
				listMessageHasSentToGroup.get(clubname).add(idMessage);
			} else {
				listMessageHasSentToGroup.get(clubname).add(idMessage);
			}
			return true;
		}

		return false;
	}

	public boolean showAllMessageGroup(String clubname) {
		if (listPublicGroup.get(clubname) != null || listPrivateGroup.get(clubname) != null) {
			return true;
		}
		return false;
	}

	public boolean deleteMessageGroup(String clubname, int id) {
		if (listPublicGroup.get(clubname) != null || listPrivateGroup.get(clubname) != null) {
			List<Integer> temporary = listMessageHasSentToGroup.get(clubname);
			if (temporary.contains(id)) {
				return true;
			}

		}
		return false;
	}

	public void sentMessageToUser(String username, String message) {
		if (listMessageUser.get(username) == null) {
			listMessageUser.put(username, new ArrayList<String>());
			listMessageHasSentToUser.put(username, new ArrayList<Integer>());
			listMessageUser.get(username).add(this.userName + ": " + message);
			listMessageHasSentToUser.get(username).add(0);
		} else {
			listMessageUser.get(username).add(this.userName + ": " + message);
			listMessageHasSentToUser.get(username).add(listMessageUser.get(username).size() - 1);
		}

	}

	public void receiveMessagetoUser(String username, String message) {
		if (listMessageUser.get(username) == null) {
			listMessageUser.put(username, new ArrayList<String>());
			listMessageHasSentToUser.put(username, new ArrayList<Integer>());
			listMessageUser.get(username).add(username + ": " + message);
		} else {
			listMessageUser.get(username).add(username + ": " + message);
		}

	}

	public boolean deleteMessageSenderInUser(String username, int idMessage) {
		if (checkContainTheIdMessageUser(username, idMessage)) {
			listMessageUser.get(username).set(idMessage, "This message has been deleted");
			return true;

		}
		return false;
	}

	public void deleteMessageReceiverInUser(String username, int idMessage) {
		listMessageUser.get(username).set(idMessage, "This message has been deleted");
	}

	private boolean checkContainTheIdMessageUser(String username, int idMessage) {
		List<Integer> temporary = listMessageHasSentToUser.get(username);
		for (int i = 0; i < temporary.size(); i++) {
			if (temporary.get(i) == idMessage) {
				return true;
			}
		}

		return false;
	}

	public String showAllTheMessageUser(String username) {
		List<String> temporary = listMessageUser.get(username);
		if (temporary != null) {
			String out = new String();
			for (int i = 0; i < temporary.size(); i++) {
				out += changeUsernameToAlias(temporary.get(i)) + "\n";
			}
			return out;
		}
		return null;
	}
	// group

	public void storePublicGroup(PublicGroup group, int id) {

		this.listPublicGroup.put(group.getName(), id);
	}

	public void storePrivateGroup(PrivateGroup group, int id) {
		this.listPrivateGroup.put(group.getName(), id);
	}

	public int getPublicGroupIdByGroupName(String name) {
		return this.listPublicGroup.get(name);
	}

	public int getprivateGroupIdByGroupName(String name) {
		return this.listPrivateGroup.get(name);
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public HashMap<String, Integer> getListPublicGroup() {
		return listPublicGroup;
	}

	public void setListPublicGroup(HashMap<String, Integer> listPublicGroup) {
		this.listPublicGroup = listPublicGroup;
	}

	public HashMap<String, Integer> getListPrivateGroup() {
		return listPrivateGroup;
	}

	public void setListPrivateGroup(HashMap<String, Integer> listPrivateGroup) {
		this.listPrivateGroup = listPrivateGroup;
	}

	public HashMap<String, String> getAliasList() {
		return aliasList;
	}

	public void setAliasList(HashMap<String, String> aliasList) {
		this.aliasList = aliasList;
	}

}
