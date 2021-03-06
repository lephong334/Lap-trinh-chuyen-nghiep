package UserSevice;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Model.DataStorge;
import Model.PublicGroup;
import Model.User;

public class Menu {

	private UserManager mngUser;
	private DataStorge dateStorage;
	private User user = null;
	private int groupID = -1;

	public Menu() {
		dateStorage = new DataStorge("D:\\SavePoint", "BackupData.dat");
		mngUser = new UserManager(dateStorage);

	}

	public boolean addAccount(String lastName, String firstName, String password, String gender, String dateOfBirth,
			String userName) {

		if (mngUser.addNewAccount(lastName, firstName, password, gender, dateOfBirth, userName)) {
			return true;
		}
		return false;

	}
	public User login(String username, String password) {
		User result = mngUser.checkAccount(username, password);
		if (result != null) {
			return result;
		}
		return null;
	}

	public ArrayList<User> findFriendByName(String keyword) {
		ArrayList<User> result = (ArrayList<User>) mngUser.findFriendByName(keyword);
		if (result != null) {
			return result;

		}
		return null;
	}

	public boolean createPublicGroup() {
		if (isLogin()) {
			List<User> listOfUsers = new ArrayList<User>();
			listOfUsers.add(this.user);
			groupID = dateStorage.createNewPublicGroup(listOfUsers);
			return true;
		}
		return false;

	}

	public boolean inviteUserByUsername(String usename) {

		if (isLogin() && isInGroup() && dateStorage.InviteUserByUsername(usename, groupID)) {

			return true;

		}
		return false;

	}

	public String generateCodeForPublicGroup() {
		if (groupID != -1) {
			return "Code :" + dateStorage.generateCode(groupID);
		}
		return null;
	}

// user have to login and not in the club to be able to join
	public boolean joinPublicGroupByCode(String code) {
		if (isLogin()) {
			int id = dateStorage.joinPublicGroupByCode(code, user);
			if (id == -1) {
				return false;
			} else {
				this.groupID = id;
				return true;
			}
		}
		return false;

	}

	private boolean isLogin() {
		if (this.user == null) {
			return false;
		}
		return true;
	}

	private boolean isInGroup() {
		if (this.groupID == -1) {
			return false;
		}
		return true;
	}

}
