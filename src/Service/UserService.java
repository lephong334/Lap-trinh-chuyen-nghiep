package Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Model.Group;
import Model.PrivateGroup;
import Model.PublicGroup;
import Model.Account;
import Storage.DataStorge;

public class UserService {

	private AccountService managementUser;
	private GroupService managementGroup;
	private DataStorge dataStorage;
	private Account user;

	public UserService() {
		user = null;
		dataStorage = DataStorge.getInstance();
		managementUser = new AccountService(dataStorage);
		managementGroup = new GroupService(dataStorage);

	}

	public boolean addAccount(String lastName, String firstName, String password, String gender, String dateOfBirth,
			String userName) {

		if (managementUser.addNewAccount(managementUser.getListAccount().size() + "", lastName, firstName, password,
				gender, dateOfBirth, userName)) {
			return true;
		}
		return false;

	}

	public Account login(String username, String password) {
		Account result = managementUser.checkAccount(username, password);
		user = result;
		if (result != null) {
			return result;
		}
		return null;
	}

	public boolean logout() {
		if (isLogin()) {
			return false;
		}
		user = null;
		return true;
	}

	public List<Account> findFriendByName(String keyword) {
		List<Account> result = managementUser.findFriendByName(keyword);
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
		Account invitingUser = managementUser.checkAccountWithoutPassword(username);
		if (isLogin() && invitingUser != null) {
			int id = user.getPublicGroupIdByGroupName(clubName);
			if (id > -1 && managementGroup.invitePublicGroup(id, invitingUser)) {
				invitingUser.storePublicGroup(managementGroup.getListPublicGroup().get(id), id);
				return true;
			}
		}
		return false;
	}

	public String generateCodePublicGroup(String clubName) {
		if (isLogin()) {
			int id = user.getPublicGroupIdByGroupName(clubName);
			if (id > -1) {
				String code = managementGroup.getListPublicGroup().get(id).createJoinCode();
				return code;
			}
		}
		return null;
	}

	public boolean joinPublicGroupByCode(String code, String clubName) {
		if (isLogin()) {
			int id = managementGroup.getPublicGroupIdByName(clubName);
			if (managementGroup.joinPublicGroupByCode(code, user, id)) {
				user.storePublicGroup(managementGroup.getListPublicGroup().get(id), id);
				return true;
			}
		}
		return false;

	}

	public boolean inviteUserPrivateGroup(String username, String clubName) {
		Account invitingUser = managementUser.checkAccountWithoutPassword(username);
		if (isLogin() && invitingUser != null) {
			int id = user.getprivateGroupIdByGroupName(clubName);
			if (id > -1 && managementGroup.invitePrivateGroup(id, invitingUser, user)) {
				invitingUser.storePrivateGroup(managementGroup.getListPrivateGroup().get(id), id);
				return true;
			}
		}
		return false;
	}

	public boolean setAlias(String username, String alias) {
		if (isLogin()) {
			user.setAlias(username, alias);
			return true;
		}
		return false;
	}

	// send file
	public boolean sendFileToUser(String username, String address) {
		File file = new File(address);
		Account receiver = managementUser.checkAccountWithoutPassword(username);
		if (isLogin() && file.exists()) {
			String filename = file.getName();
			String idFile = user.sendFile(filename, username);
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
			String idFile = user.sendFile(filename, clubname);
			managementGroup.sendFileToGroup(clubname, filename);
			dataStorage.copyANewFileUsingBufferedInputOutputStream(address, idFile, filename);
			return true;
		}

		return false;
	}

	public String showAllFileHasSent(String receiver) {
		if (isLogin()) {
			return user.showListFileHasSentUserOrGroup(receiver);
		}
		return null;
	}

	public boolean deleteFile(String filename) {
		String receiver = user.removeFileHasSent(filename);
		if (isLogin() && receiver != null && dataStorage.DeleteFile(filename)) {
			Account user = managementUser.checkAccountWithoutPassword(receiver);
			if (user != null) {
				user.removeFileHasReceive(filename);
				return true;
			}
			return managementGroup.deleteFile(receiver, filename);
		}
		return false;
	}

//Send message
	public boolean leaveTheGroup(String clubname) {
		if (isLogin()) {
			int id = user.leaveThePublicGroup(clubname);
			if (id != -1) {
				return managementGroup.getListPublicGroup().get(id).leaveTheGroup(user);
			}
			id = user.leaveThePrivateGroup(clubname);
			if (id != -1) {
				return managementGroup.getListPrivateGroup().get(id).leaveTheGroup(user);
			}
		}
		return false;
	}

	public List<String> showLimitedMessageGroup(int lastestMessage, int oldMessage, String clubname) {
		if (isLogin() && user.showAllMessageGroup(clubname)) {
			return managementGroup.showLimitedMessageToGroup(clubname, lastestMessage, oldMessage);
		}
		return null;

	}

	public List<String> showNextLimitedMessageGroup(String clubname) {
		if (isLogin() && user.showAllMessageGroup(clubname)) {
			return managementGroup.showNextLimitedMessageToGroup(clubname);
		}
		return null;

	}

	public List<String> showLimitedMessageUser(int lastestMessage, int oldMessage, String username) {
		return user.showLimitedMessageUser(lastestMessage, oldMessage, username);
	}

	public List<String> showNextLimitedMessageToUser(String username) {
		return user.showNextLimitedMessageUser(username);
	}

	public String findTextMessage(String keyword) {

		if (!isLogin()) {
			return null;
		}

		String result = new String();
		result = user.findTextMessageUser(keyword);
		if (result == null) {
			List<Integer> temporary = user.getListPublicGroupId();
			for (int i = 0; i < temporary.size(); i++) {
				result = managementGroup.getListPublicGroup().get(temporary.get(i)).findText(keyword);
			}
		}
		if (result == null) {
			List<Integer> temporary = user.getListPrivateGroupId();
			for (int i = 0; i < temporary.size(); i++) {
				result = managementGroup.getListPrivateGroup().get(temporary.get(i)).findText(keyword);
			}
		}
		return result;
	}

	public boolean sendMessageToGroup(String clubname, String message) {

		int idMessage;
		if (isLogin() == false) {
			return false;
		}
		idMessage = managementGroup.sendMessageToGroup(clubname, message, this.user);
		if (idMessage > -1) {
			user.sentMessageToGroup(clubname, message, idMessage);
			return true;
		}

		return false;
	}

	public List<String> showAllMessageGroup(String clubname) {

		if (isLogin() && user.showAllMessageGroup(clubname)) {
			return managementGroup.showAllMessageGroup(clubname);
		}
		return null;
	}

	public boolean deleteMessageGroup(String clubname, int idMessage) {

		if (isLogin() && user.deleteMessageGroup(clubname, idMessage)) {
			return managementGroup.deleteMessageGroup(clubname, idMessage);
		}
		return false;
	}

	public boolean sendMessageToUser(String username, String message) {
		Account receiver = managementUser.checkAccountWithoutPassword(username);
		if (isLogin()) {
			user.sentMessageUser(username, message);
			receiver.receiveMessageUser(this.user.getUserName(), message);
			return true;
		}

		return false;
	}

	public List<String> showAllMessageUser(String username) {
		return user.showAllTheMessageUser(username);

	}

	public boolean deleteMessage(String username, int idMessage) {
		Account receiver = managementUser.checkAccountWithoutPassword(username);
		if (isLogin() && this.user.deleteMessageSenderInUser(username, idMessage)) {
			receiver.deleteMessageReceiverInUser(this.user.getUserName(), idMessage);
			return true;
		}
		return false;
	}

	private boolean isLogin() {
		if (this.user == null) {
			return false;
		}
		return true;
	}

	public AccountService getManagementUser() {
		return managementUser;
	}

	public void setManagementUser(AccountService managementUser) {
		this.managementUser = managementUser;
	}

	public GroupService getManagementGroup() {
		return managementGroup;
	}

	public void setManagementGroup(GroupService managermentGroup) {
		this.managementGroup = managermentGroup;
	}

}
