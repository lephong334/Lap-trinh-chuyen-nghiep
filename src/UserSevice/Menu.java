package UserSevice;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Model.DataStorge;
import Model.Group;
import Model.PublicGroup;
import Model.User;

public class Menu {

	private UserManager managementUser;
	private DataStorge dataStorage;
	private User user;
	private List<Group> group;

	public Menu() {
		user = null;
		dataStorage = new DataStorge();
		managementUser = new UserManager(dataStorage);
		group = new ArrayList<Group>();

	}

	public boolean addAccount(String lastName, String firstName, String password, String gender, String dateOfBirth,
			String userName) {

		if (managementUser.addNewAccount(lastName, firstName, password, gender, dateOfBirth, userName)) {
			return true;
		}
		return false;

	}

	public User login(String username, String password) {
		User result = managementUser.checkAccount(username, password);
		this.user = result;
		if (result != null) {
			return result;
		}
		return null;
	}

	public List<User> findFriendByName(String keyword) {
		List<User> result = managementUser.findFriendByName(keyword);
		if (result != null) {
			return result;

		}
		return null;
	}

	public boolean createGroup(String name) {
		if (isLogin()) {
			Group newGroup = new Group(dataStorage);
			if (newGroup.createGroup(name, user)) {
				this.group.add(newGroup);
				return true;
			}
		}
		return false;
	}

//	public boolean createPublicGroup() {
//		if (isLogin()) {
//			List<User> listOfUsers = new ArrayList<User>();
//			listOfUsers.add(this.user);
//			groupID = dataStorage.createNewPublicGroup(listOfUsers);
//			return true;
//		}
//		return false;
//
//	}
//
//	public boolean inviteUserByUsername(String usename) {
//
//		if (isLogin() && isInGroup() && dataStorage.InviteUserByUsername(usename, groupID)) {
//
//			return true;
//
//		}
//		return false;
//
//	}
//
//	public String generateCodeForPublicGroup() {
//		if (groupID != -1) {
//			return "Code :" + dataStorage.generateCode(groupID);
//		}
//		return null;
//	}
//
//// user have to login and not in the club to be able to join
//	public boolean joinPublicGroupByCode(String code) {
//		if (isLogin()) {
//			int id = dataStorage.joinPublicGroupByCode(code, user);
//			if (id == -1) {
//				return false;
//			} else {
//				this.groupID = id;
//				return true;
//			}
//		}
//		return false;
//
//	}
//
	private boolean isLogin() {
		if (this.user == null) {
			return false;
		}
		return true;
	}

}
