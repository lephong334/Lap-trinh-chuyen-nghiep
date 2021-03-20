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
	private GroupManager managementGroup;
	private DataStorge dataStorage;
	private User user;

	public Menu() {
		user = null;
		dataStorage = new DataStorge();
		managementUser = new UserManager(dataStorage);
		managementGroup = new GroupManager(dataStorage);

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
			if (managementGroup.createPublicGroup(name, user)) {
				return true;
			}
		}
		return false;
	}

	public boolean createPrivateGroup(String name) {
		if (isLogin()) {
			if (managementGroup.createPrivateGroup(name, user)) {
				return true;
			}
		}
		return false;
	}

	public boolean inviteUserPublicGroup(String username, String clubName) {
		User invitingUser = managementUser.checkAccountWithoutPassword(username);
		if (isLogin() && invitingUser != null) {
			int id = user.getPublicGroupIdByGroupName(clubName);
			if (id > -1 && managementGroup.invitePublicGroup(id, invitingUser)) {
				invitingUser.storePublicGroup(managementGroup.getListPublicGroup().get(id), id);
				return true;
			}
		}
		return false;
	}

	public String generateCodeForPublicGroup(String clubName) {
		if (isLogin()) {
			int id = this.user.getPublicGroupIdByGroupName(clubName);
			if (id > -1) {
				String code = managementGroup.getListPublicGroup().get(id).createJoinCode();
				return code;
			}
		}
		return null;
	}

	public boolean joinPublicGroupByCode(String code, String clubName) {
		if (isLogin()) {
			int id = managementGroup.getPublicGroupIdByGroupName(clubName);
			if (managementGroup.joinPublicGroupByCode(code, this.user, id)) {
				user.storePublicGroup(managementGroup.getListPublicGroup().get(id), id);
				return true;
			}
		}
		return false;

	}

	public boolean inviteUserPrivateGroup(String username, String clubName) {
		User invitingUser = managementUser.checkAccountWithoutPassword(username);
		if (isLogin() && invitingUser != null) {
			int id = user.getprivateGroupIdByGroupName(clubName);
			if (id > -1 && managementGroup.invitePrivateGroup(id, invitingUser, user)) {
				invitingUser.storePrivateGroup(managementGroup.getListPrivateGroup().get(id), id);
				return true;
			}
		}
		return false;
	}

	// send file
	public boolean sendFileToUser(String username, String address) {
		File file = new File(address);
		User receiver = managementUser.checkAccountWithoutPassword(username);
		if (isLogin() && file.exists()) {
			String filename = file.getName();
			String idFile = this.user.sendFile(filename, username);
			receiver.receiveFileUser(filename);
			dataStorage.copyANewFileUsingBufferedInputOutputStream(address, idFile, filename);
			return true;
		}

		return false;
	}

	public boolean sendFileToGroup(String clubname, String address) {
		File file = new File(address);
		if (isLogin() && file.exists()) {
			String filename = file.getName();
			String idFile = this.user.sendFile(filename, clubname);
			int id = managementGroup.getPublicGroupIdByGroupName(clubname);
			if (id == -1) {
				id = managementGroup.getprivateGroupIdByGroupName(clubname);
				managementGroup.getListPrivateGroup().get(id).receiveFileUser(filename);
			} else {
				managementGroup.getListPublicGroup().get(id).receiveFileUser(filename);
			}
			dataStorage.copyANewFileUsingBufferedInputOutputStream(address, idFile, filename);
			return true;
		}

		return false;
	}

	public String showAllFileHasSent(String receiver) {
		if (isLogin()) {
			return this.user.showListFileHasSentToUserOrGroup(receiver);
		}
		return null;
	}

	public boolean deleteFile(String filename) {
		String receiver = this.user.removeFilewhichHasSent(filename);
		if (isLogin() && receiver != null && dataStorage.DeleteFile(filename)) {
			User user = managementUser.checkAccountWithoutPassword(receiver);
			if (user != null) {
				user.removeFileWhichHasReceive(filename);
				return true;
			}
			int id = managementGroup.getPublicGroupIdByGroupName(receiver);
			if (id == -1) {
				id = managementGroup.getprivateGroupIdByGroupName(receiver);
				managementGroup.getListPrivateGroup().get(id).deleteFile(filename);
			} else {
				managementGroup.getListPublicGroup().get(id).deleteFile(filename);
			}
			return true;
		}
		return false;
	}

//Send message
	public String showLimitedMessageToUser(int lastestMessage, int oldMessage, String username) {
		return this.user.showLimitedMessageUser(lastestMessage, oldMessage, username);
	}

	public String showNextLimitedMessageToUser(String username) {
		return this.user.showNextLimitedMessageUser(username);
	}

	public String findTextMessage(String keyword) {

		if (!isLogin()) {
			return null;
		}

		String result = new String();
		result = this.user.findTextMessageToUser(keyword);
		if (result == null) {
			List<Integer> temporary = this.user.getListPublicGroupId();
			for (int i = 0; i < temporary.size(); i++) {
				result = managementGroup.getListPublicGroup().get(temporary.get(i)).findText(keyword);
			}
		}
		if (result == null) {
			List<Integer> temporary = this.user.getListPrivateGroupId();
			for (int i = 0; i < temporary.size(); i++) {
				result = managementGroup.getListPrivateGroup().get(temporary.get(i)).findText(keyword);
			}
		}
		return result;
	}

	public boolean sendMessageToGroup(String clubname, String message) {
		int id = managementGroup.getPublicGroupIdByGroupName(clubname);
		int idMessage;
		if (isLogin() == false) {
			return false;
		}
		if (id == -1) {
			id = managementGroup.getprivateGroupIdByGroupName(clubname);
			if (id != -1) {
				idMessage = managementGroup.getListPrivateGroup().get(id).receiveMessageGroup(message, this.user);
				this.user.sentMessageToGroup(clubname, message, idMessage);
				return true;
			}
		} else {
			idMessage = managementGroup.getListPublicGroup().get(id).receiveMessageGroup(message, this.user);
			this.user.sentMessageToGroup(clubname, message, idMessage);
			return true;
		}
		return false;
	}

	public String showAllMessageGroup(String clubname) {
		String out = null;
		if (isLogin() && this.user.showAllMessageGroup(clubname)) {
			int id = managementGroup.getPublicGroupIdByGroupName(clubname);
			if (id == -1) {
				id = managementGroup.getprivateGroupIdByGroupName(clubname);
				out = managementGroup.getListPrivateGroup().get(id).showAllMessageGroup();
			} else {
				out = managementGroup.getListPublicGroup().get(id).showAllMessageGroup();
			}
			return out;
		}
		return out;
	}

	public boolean deleteMessageGroup(String clubname, int idMessage) {
		String out = null;
		if (isLogin() && this.user.deleteMessageGroup(clubname, idMessage)) {
			int id = managementGroup.getPublicGroupIdByGroupName(clubname);
			if (id == -1) {
				id = managementGroup.getprivateGroupIdByGroupName(clubname);
				return managementGroup.getListPrivateGroup().get(id).deleteMessageGroup(idMessage);
			} else {
				return managementGroup.getListPublicGroup().get(id).deleteMessageGroup(idMessage);
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

	public GroupManager getManagementGroup() {
		return managementGroup;
	}

	public void setManagementGroup(GroupManager managermentGroup) {
		this.managementGroup = managermentGroup;
	}

}
