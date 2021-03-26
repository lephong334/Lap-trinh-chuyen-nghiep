package Service;

import java.util.HashMap;
import java.util.List;

import Model.Group;
import Model.PrivateGroup;
import Model.PublicGroup;
import Model.User;
import Storage.DataStorge;

public class GroupService {
	DataStorge dataStorge;

	public GroupService(DataStorge dataStorge) {
		this.dataStorge = dataStorge;

	}

	public int sendMessageToGroup(String clubname, String message, User user) {
		int id = this.getPublicGroupIdByGroupName(clubname);
		if (id != -1) {
			return dataStorge.getListPublicGroup().get(id).receiveMessageGroup(message, user);

		}
		id = this.getprivateGroupIdByGroupName(clubname);
		if (id != -1) {
			return dataStorge.getListPrivateGroup().get(id).receiveMessageGroup(message, user);

		}
		return -1;
	}

	public boolean deleteMessageGroup(String clubname, int idMessage) {
		int id = this.getPublicGroupIdByGroupName(clubname);
		if (id != -1) {
			dataStorge.getListPublicGroup().get(id).deleteMessageGroup(idMessage);
			return true;
		}
		id = this.getprivateGroupIdByGroupName(clubname);
		if (id != -1) {
			dataStorge.getListPrivateGroup().get(id).deleteMessageGroup(idMessage);
			return true;
		}
		return false;
	}

	public String showAllMessageGroup(String clubname) {
		int id = this.getPublicGroupIdByGroupName(clubname);
		if (id != -1) {
			return dataStorge.getListPublicGroup().get(id).showAllMessageGroup();

		}
		id = this.getprivateGroupIdByGroupName(clubname);
		if (id != -1) {
			return dataStorge.getListPrivateGroup().get(id).showAllMessageGroup();

		}
		return null;
	}

	public String showNextLimitedMessageToGroup(String clubname) {
		int id = this.getPublicGroupIdByGroupName(clubname);
		if (id != -1) {
			return dataStorge.getListPublicGroup().get(id).showNextLimitedMessageGroup();

		}
		id = this.getprivateGroupIdByGroupName(clubname);
		if (id != -1) {
			return dataStorge.getListPrivateGroup().get(id).showNextLimitedMessageGroup();

		}
		return null;
	}

	public String showLimitedMessageToGroup(String clubname, int lastestMessage, int oldMessage) {
		int id = this.getPublicGroupIdByGroupName(clubname);
		if (id != -1) {
			return dataStorge.getListPublicGroup().get(id).showLimitedMessageGroup(lastestMessage, oldMessage);

		}
		id = this.getprivateGroupIdByGroupName(clubname);
		if (id != -1) {
			return dataStorge.getListPrivateGroup().get(id).showLimitedMessageGroup(lastestMessage, oldMessage);

		}
		return null;
	}

	public boolean sendFileToGroup(String clubname, String filename) {
		int id = this.getPublicGroupIdByGroupName(clubname);
		if (id != -1) {
			dataStorge.getListPublicGroup().get(id).receiveFileUser(filename);
			return true;
		}
		id = this.getprivateGroupIdByGroupName(clubname);
		if (id != -1) {
			dataStorge.getListPrivateGroup().get(id).receiveFileUser(filename);
			return true;
		}
		return false;
	}

	public boolean deleteFile(String clubname, String filename) {
		int id = this.getPublicGroupIdByGroupName(clubname);
		if (id != -1) {
			dataStorge.getListPublicGroup().get(id).deleteFile(filename);
			return true;
		}
		id = this.getprivateGroupIdByGroupName(clubname);
		if (id != -1) {
			dataStorge.getListPrivateGroup().get(id).deleteFile(filename);
			return true;
		}
		return false;
	}

	public boolean invitePublicGroup(int id, User user) {
		if (dataStorge.getListPublicGroup().get(id).inviteUser(user)) {
			return true;
		}
		return false;
	}

	public boolean invitePrivateGroup(int id, User user, User member) {
		if (dataStorge.getListPrivateGroup().get(id).inviteUser(user, member)) {
			return true;
		}
		return false;
	}

	public boolean joinPublicGroupByCode(String code, User user, int id) {

		if (id > -1) {
			dataStorge.getListPublicGroup().get(id).joinByCode(user, code);
			return true;
		}
		return false;
	}

	public boolean createPublicGroup(String name, User user) {
		if (checkNameOfPublicGroup(name)) {
			PublicGroup group = new PublicGroup(name, user);
			dataStorge.getListPublicGroup().add(group);
			user.storePublicGroup(group, dataStorge.getListPublicGroup().size() - 1);
			return true;
		}
		return false;

	}

	public boolean createPrivateGroup(String name, User user) {
		if (checkNameOfPrivateGroup(name)) {
			PrivateGroup group = new PrivateGroup(name, user);
			dataStorge.getListPrivateGroup().add(group);
			user.storePrivateGroup(group, dataStorge.getListPrivateGroup().size() - 1);
			return true;
		}
		return false;

	}

	private boolean checkNameOfPublicGroup(String name) {
		for (int i = 0; i < dataStorge.getListPublicGroup().size(); i++) {
			if (dataStorge.getListPublicGroup().get(i).getName().equalsIgnoreCase(name)) {
				return false;
			}
		}
		return true;
	}

	private boolean checkNameOfPrivateGroup(String name) {
		for (int i = 0; i < dataStorge.getListPrivateGroup().size(); i++) {
			if (dataStorge.getListPrivateGroup().get(i).getName().equalsIgnoreCase(name)) {
				return false;
			}
		}
		return true;
	}

	public int getPublicGroupIdByGroupName(String name) {
		for (int i = 0; i < dataStorge.getListPublicGroup().size(); i++) {
			if (dataStorge.getListPublicGroup().get(i).getName().equalsIgnoreCase(name)) {
				return i;
			}
		}

		return -1;
	}

	public int getprivateGroupIdByGroupName(String name) {
		for (int i = 0; i < dataStorge.getListPrivateGroup().size(); i++) {
			if (dataStorge.getListPrivateGroup().get(i).getName().equalsIgnoreCase(name)) {
				return i;
			}
		}

		return -1;
	}

	public List<PublicGroup> getListPublicGroup() {

		return dataStorge.getListPublicGroup();
	}

	public List<PrivateGroup> getListPrivateGroup() {
		return dataStorge.getListPrivateGroup();
	}

}
