package Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataStorge {

	protected List<User> listAccount;
	protected List<Group> listGroup;

	public DataStorge() {
		listAccount = new ArrayList<>();
		listGroup = new ArrayList<Group>();
	}

	/// Group: head
	public boolean checkNameOfGroup(String name) {
		for (int i = 0; i < listGroup.size(); i++) {
			if (listGroup.get(i).getName().equalsIgnoreCase(name)) {
				return false;
			}
		}
		return true;
	}

	public void storeGroup(Group group) {
		this.listGroup.add(group);
	}
///////Public group : head

//	public int createNewPublicGroup(List<User> listOfUsers) {
//		PublicGroup temporary = new PublicGroup(listOfUsers);
//		listPublicGroup.add(temporary);
//		return listPublicGroup.size() - 1;
//	}
//
//	public String generateCode(int groupID) {
//		return listPublicGroup.get(groupID).createJoinCode();
//	}
//
//	public int joinPublicGroupByCode(String code, User user) {
//		for (int i = 0; i < listPublicGroup.size(); i++) {
//			if (listPublicGroup.get(i).joinByCode(user, code)) {
//				return i;
//			}
//		}
//		return -1;
//	}
//
//	public boolean InviteUserByUsername(String usrname, int groupID) {
//		User invitingUsser = checkAccountWithoutPassword(usrname);
//		if (invitingUsser != null) {
//			return listPublicGroup.get(groupID).inviteByMember(invitingUsser);
//		}
//		return false;
//
//	}
	/////// Public group : end

	/////// Public group : end
//getter and setter for AccountList : head
	public List<User> getListAccount() {
		return listAccount;
	}

	public void setListAccount(ArrayList<User> listAccount) {
		this.listAccount = listAccount;
	}
	// getter and setter for AccountList : end

	/// store : head

	public DataStorge readListAccountasByte() {
		return null;

	}

	public boolean saveDatapasByte() {
		return true;

	}
	/// store : end

	public List<Group> getListGroup() {
		return listGroup;
	}

	public void setListGroup(List<Group> listGroup) {
		this.listGroup = listGroup;
	}

}
