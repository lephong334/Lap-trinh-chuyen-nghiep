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
	}

	public void storePublicGroup(PublicGroup group,int id) {
		
		this.listPublicGroup.put(group.getName(), id);
	}
	public void storePrivateGroup(PrivateGroup group,int id) {
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
