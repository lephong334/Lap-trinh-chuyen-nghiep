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
	private String pathStr;
	private String fileName;
	protected ArrayList<User> listAccount;
	protected ArrayList<PublicGroup> listPublicGroup;

	public DataStorge(String pathStr, String fileName) {
		this.pathStr = pathStr;
		this.fileName = fileName;
		createFolder();
		listAccount = new ArrayList<>();
		listPublicGroup = new ArrayList<PublicGroup>();
	}

	public void createFolder() {
		File folder = new File(pathStr);
		if (folder.exists()) {
			System.out.println("Folder exist");
		} else {
			System.out.println("Folder created");
		}
	}

///////Public group : head

	public int createNewPublicGroup(List<User> listOfUsers) {
		PublicGroup temporary = new PublicGroup(listOfUsers);
		listPublicGroup.add(temporary);
		return listPublicGroup.size() - 1;
	}

	public String generateCode(int groupID) {
		return listPublicGroup.get(groupID).createJoinCode();
	}

	public int joinPublicGroupByCode(String code, User user) {
		for (int i = 0; i < listPublicGroup.size(); i++) {
			if (listPublicGroup.get(i).joinByCode(user, code)) {
				return i;
			}
		}
		return -1;
	}

	public boolean InviteUserByUsername(String usrname, int groupID) {
		User invitingUsser = checkAccountWithoutPassword(usrname);
		if (invitingUsser != null) {
			return listPublicGroup.get(groupID).inviteByMember(invitingUsser);
		}
		return false;

	}
	/////// Public group : end

	/// Account : head

	//// Account :end

	/// store : head

	public DataStorge readListAccountasByte() {

	}

	public void saveDatapasByte() {

	}
	/// store : end

}
