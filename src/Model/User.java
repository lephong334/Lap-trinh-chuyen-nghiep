package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
				out += temporary.get(i) + "\n";
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

}
