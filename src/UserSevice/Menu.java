package UserSevice;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Model.DataStorge;
import Model.Group;
import Model.PrivateGroup;
import Model.PublicGroup;
import Model.User;

public class Menu {

	private UserManager managementUser;
	private GroupManager managermentGroup;
	private DataStorge dataStorage;
	private User user;

	public Menu() {
		user = null;
		dataStorage = new DataStorge();
		managementUser = new UserManager(dataStorage);
		managermentGroup = new GroupManager(dataStorage);
	}

	public boolean addAccount(String lastName, String firstName, String password, String gender, String dateOfBirth,
			String userName) {

		if (managementUser.addNewAccount(managementUser.getListAccount().size() + "", lastName, firstName, password,
				gender, dateOfBirth, userName)) {
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

	public boolean logout() {
		if (isLogin()) {
			return false;
		}
		this.user = null;
		return true;
	}

	public List<User> findFriendByName(String keyword) {
		List<User> result = managementUser.findFriendByName(keyword);
		if (result != null) {
			return result;

		}
		return null;
	}

	public boolean createPublicGroup(String name) {
		if (isLogin()) {
			if (managermentGroup.createPublicGroup(name, user)) {
				return true;
			}
		}
		return false;
	}

	public boolean createPrivateGroup(String name) {
		if (isLogin()) {
			if (managermentGroup.createPrivateGroup(name, user)) {
				return true;
			}
		}
		return false;
	}

	public boolean inviteUserPublicGroup(String username, String clubName) {
		User invitingUser = managementUser.checkAccountWithoutPassword(username);
		if (isLogin() && invitingUser != null) {
			int id = user.getPublicGroupIdByGroupName(clubName);
			if (id > -1 && managermentGroup.invitePublicGroup(id, invitingUser)) {
				invitingUser.storePublicGroup(managermentGroup.getListPublicGroup().get(id), id);
				return true;
			}
		}
		return false;
	}

	public String generateCodeForPublicGroup(String clubName) {
		if (isLogin()) {
			int id = this.user.getPublicGroupIdByGroupName(clubName);
			if (id > -1) {
				String code = managermentGroup.getListPublicGroup().get(id).createJoinCode();
				return code;
			}
		}
		return null;
	}

	public boolean joinPublicGroupByCode(String code, String clubName) {
		if (isLogin()) {
			int id = managermentGroup.getPublicGroupIdByGroupName(clubName);
			if (managermentGroup.joinPublicGroupByCode(code, this.user, id)) {
				user.storePublicGroup(managermentGroup.getListPublicGroup().get(id), id);
				return true;
			}
		}
		return false;

	}

	public boolean inviteUserPrivateGroup(String username, String clubName) {
		User invitingUser = managementUser.checkAccountWithoutPassword(username);
		if (isLogin() && invitingUser != null) {
			int id = user.getprivateGroupIdByGroupName(clubName);
			System.out.println(id);
			if (id > -1 && managermentGroup.invitePrivateGroup(id, invitingUser, user)) {
				invitingUser.storePrivateGroup(managermentGroup.getListPrivateGroup().get(id), id);
				return true;
			}
		}
		return false;
	}

	// send file
	public boolean sendFileToUser(String username, String address) {
		File file = new File(address);
		User receiver = managementUser.checkAccountWithoutPassword(username);
		if (isLogin() &&  file.exists()) {
			String filename = file.getName();
			String idFile = this.user.sendFileToUser(filename);
			receiver.receiveFileUser(filename);
			dataStorage.copyANewFileUsingBufferedInputOutputStream(address, idFile, filename);
			return true;
		}

		return false;
	}

//Send message
	public boolean sendMessageToGroup(String clubname, String message) {
		int id = managermentGroup.getPublicGroupIdByGroupName(clubname);
		int idMessage;
		if (isLogin() == false) {
			return false;
		}
		if (id == -1) {
			id = managermentGroup.getprivateGroupIdByGroupName(clubname);
			if (id != -1) {
				idMessage = managermentGroup.getListPrivateGroup().get(id).receiveMessageGroup(message, this.user);
				this.user.sentMessageToGroup(clubname, message, idMessage);
				return true;
			}
		} else {
			idMessage = managermentGroup.getListPublicGroup().get(id).receiveMessageGroup(message, this.user);
			this.user.sentMessageToGroup(clubname, message, idMessage);
			return true;
		}
		return false;
	}

	public String showAllMessageGroup(String clubname) {
		String out = null;
		if (isLogin() && this.user.showAllMessageGroup(clubname)) {
			int id = managermentGroup.getPublicGroupIdByGroupName(clubname);
			if (id == -1) {
				id = managermentGroup.getprivateGroupIdByGroupName(clubname);
				out = managermentGroup.getListPrivateGroup().get(id).showAllMessageGroup();
			} else {
				out = managermentGroup.getListPublicGroup().get(id).showAllMessageGroup();
			}
			return out;
		}
		return out;
	}

	public boolean deleteMessageGroup(String clubname, int idMessage) {
		String out = null;
		if (isLogin() && this.user.deleteMessageGroup(clubname, idMessage)) {
			int id = managermentGroup.getPublicGroupIdByGroupName(clubname);
			if (id == -1) {
				id = managermentGroup.getprivateGroupIdByGroupName(clubname);
				return managermentGroup.getListPrivateGroup().get(id).deleteMessageGroup(idMessage);
			} else {
				return managermentGroup.getListPublicGroup().get(id).deleteMessageGroup(idMessage);
			}

		}
		return false;
	}

	public boolean sendMessageToUser(String username, String message) {
		User receiver = managementUser.checkAccountWithoutPassword(username);
		if (isLogin()) {
			this.user.sentMessageToUser(username, message);
			receiver.receiveMessagetoUser(this.user.getUserName(), message);
			return true;
		}

		return false;
	}

	public String showAllMessageUser(String username) {
		String out = this.user.showAllTheMessageUser(username);
		return out;
	}

	public boolean deleteMessage(String username, int idMessage) {
		User receiver = managementUser.checkAccountWithoutPassword(username);
		if (isLogin() && this.user.deleteMessageSenderInUser(username, idMessage)) {
			receiver.deleteMessageReceiverInUser(this.user.getUserName(), idMessage);
			return true;
		}
		return false;
	}

	public User getUser() {
		return this.user;
	}

	private boolean isLogin() {
		if (this.user == null) {
			return false;
		}
		return true;
	}

	public UserManager getManagementUser() {
		return managementUser;
	}

	public void setManagementUser(UserManager managementUser) {
		this.managementUser = managementUser;
	}

	public GroupManager getManagermentGroup() {
		return managermentGroup;
	}

	public void setManagermentGroup(GroupManager managermentGroup) {
		this.managermentGroup = managermentGroup;
	}

}
