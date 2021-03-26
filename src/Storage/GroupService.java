package Storage;

import java.util.HashMap;
import java.util.List;

import Model.Group;
import Model.PrivateGroup;
import Model.PublicGroup;
import Model.User;

public class GroupService {
	DataStorge dataStorge;
	private List<PublicGroup> listPublicGroup;
	private List<PrivateGroup> listPrivateGroup;

	public GroupService(DataStorge dataStorge) {
		this.dataStorge = dataStorge;
		this.listPublicGroup = dataStorge.getListPublicGroup();
		this.listPrivateGroup = dataStorge.getListPrivateGroup();
	}

	public int sendMessageToGroup(String clubname, String message, User user) {
		int id = this.getPublicGroupIdByGroupName(clubname);
		if (id != -1) {
			return this.listPublicGroup.get(id).receiveMessageGroup(message, user);

		}
		id = this.getprivateGroupIdByGroupName(clubname);
		if (id != -1) {
			return this.listPrivateGroup.get(id).receiveMessageGroup(message, user);

		}
		return -1;
	}

	public boolean deleteMessageGroup(String clubname, int idMessage) {
		int id = this.getPublicGroupIdByGroupName(clubname);
		if (id != -1) {
			this.listPublicGroup.get(id).deleteMessageGroup(idMessage);
			return true;
		}
		id = this.getprivateGroupIdByGroupName(clubname);
		if (id != -1) {
			this.listPrivateGroup.get(id).deleteMessageGroup(idMessage);
			return true;
		}
		return false;
	}

	public String showAllMessageGroup(String clubname, HashMap<String, String> aliasList) {
		int id = this.getPublicGroupIdByGroupName(clubname);
		if (id != -1) {
			return this.listPublicGroup.get(id).showAllMessageGroup(aliasList);

		}
		id = this.getprivateGroupIdByGroupName(clubname);
		if (id != -1) {
			return this.listPrivateGroup.get(id).showAllMessageGroup(aliasList);

		}
		return null;
	}

	public String showNextLimitedMessageToGroup(String clubname, HashMap<String, String> aliasList) {
		int id = this.getPublicGroupIdByGroupName(clubname);
		if (id != -1) {
			return this.listPublicGroup.get(id).showNextLimitedMessageGroup(aliasList);

		}
		id = this.getprivateGroupIdByGroupName(clubname);
		if (id != -1) {
			return this.listPrivateGroup.get(id).showNextLimitedMessageGroup(aliasList);

		}
		return null;
	}

	public String showLimitedMessageToGroup(String clubname, int lastestMessage, int oldMessage,
			HashMap<String, String> aliasList) {
		int id = this.getPublicGroupIdByGroupName(clubname);
		if (id != -1) {
			return this.listPublicGroup.get(id).showLimitedMessageGroup(lastestMessage, oldMessage, aliasList);

		}
		id = this.getprivateGroupIdByGroupName(clubname);
		if (id != -1) {
			return this.listPrivateGroup.get(id).showLimitedMessageGroup(lastestMessage, oldMessage, aliasList);

		}
		return null;
	}

	public boolean sendFileToGroup(String clubname, String filename) {
		int id = this.getPublicGroupIdByGroupName(clubname);
		if (id != -1) {
			this.listPublicGroup.get(id).receiveFileUser(filename);
			return true;
		}
		id = this.getprivateGroupIdByGroupName(clubname);
		if (id != -1) {
			this.listPrivateGroup.get(id).receiveFileUser(filename);
			return true;
		}
		return false;
	}

	public boolean deleteFile(String clubname, String filename) {
		int id = this.getPublicGroupIdByGroupName(clubname);
		if (id != -1) {
			this.listPublicGroup.get(id).deleteFile(filename);
			return true;
		}
		id = this.getprivateGroupIdByGroupName(clubname);
		if (id != -1) {
			this.listPrivateGroup.get(id).deleteFile(filename);
			return true;
		}
		return false;
	}

	public boolean invitePublicGroup(int id, User user) {
		if (listPublicGroup.get(id).inviteUser(user)) {
			return true;
		}
		return false;
	}

	public boolean invitePrivateGroup(int id, User user, User member) {
		if (listPrivateGroup.get(id).inviteUser(user, member)) {
			return true;
		}
		return false;
	}

	public boolean joinPublicGroupByCode(String code, User user, int id) {

		if (id > -1) {
			listPublicGroup.get(id).joinByCode(user, code);
			return true;
		}
		return false;
	}

	public boolean createPublicGroup(String name, User user) {
		if (checkNameOfPublicGroup(name)) {
			PublicGroup group = new PublicGroup();
			group.createGroup(name, user);
			this.listPublicGroup.add(group);
			user.storePublicGroup(group, this.listPublicGroup.size() - 1);
			return true;
		}
		return false;

	}

	public boolean createPrivateGroup(String name, User user) {
		if (checkNameOfPrivateGroup(name)) {
			PrivateGroup group = new PrivateGroup();
			group.createGroup(name, user);
			this.listPrivateGroup.add(group);
			user.storePrivateGroup(group, this.listPrivateGroup.size() - 1);
			return true;
		}
		return false;

	}

	private boolean checkNameOfPublicGroup(String name) {
		for (int i = 0; i < listPublicGroup.size(); i++) {
			if (listPublicGroup.get(i).getName().equalsIgnoreCase(name)) {
				return false;
			}
		}
		return true;
	}

	private boolean checkNameOfPrivateGroup(String name) {
		for (int i = 0; i < listPrivateGroup.size(); i++) {
			if (listPrivateGroup.get(i).getName().equalsIgnoreCase(name)) {
				return false;
			}
		}
		return true;
	}

	public int getPublicGroupIdByGroupName(String name) {
		for (int i = 0; i < listPublicGroup.size(); i++) {
			if (listPublicGroup.get(i).getName().equalsIgnoreCase(name)) {
				return i;
			}
		}

		return -1;
	}

	public int getprivateGroupIdByGroupName(String name) {
		for (int i = 0; i < listPrivateGroup.size(); i++) {
			if (listPrivateGroup.get(i).getName().equalsIgnoreCase(name)) {
				return i;
			}
		}

		return -1;
	}

	public List<PublicGroup> getListPublicGroup() {

		return listPublicGroup;
	}

	public void setListPublicGroup(List<PublicGroup> listPublicGroup) {
		this.listPublicGroup = listPublicGroup;
	}

	public List<PrivateGroup> getListPrivateGroup() {
		return listPrivateGroup;
	}

	public void setListPrivateGroup(List<PrivateGroup> listPrivateGroup) {
		this.listPrivateGroup = listPrivateGroup;
	}

}