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

	public User checkAccount(String username, String password) {
		int id = checkUsername(username);
		if (id > -1) {
			if (listAccount.get(id).getPassword().compareTo(doMD5(password)) == 0) {
				return listAccount.get(id);
			}
			return null;
		}
		return null;
	}

	public String findFriendByName(String keyword) {
		String result = "";
		for (int i = 0; i < listAccount.size(); i++) {
			if (listAccount.get(i).getFullName().contains(keyword)) {
				result += listAccount.get(i).getFullName() + "\n";
			}
		}
		return result;

	}

	private User checkAccountWithoutPassword(String username) {
		int id = checkUsername(username);
		if (id > -1) {
			return listAccount.get(id);
		}
		return null;
	}

	public boolean addNewAccount(String lastName, String firstName, String password, String gender, String dateOfBirth,
			String userName) {
		if (checkUsername(userName) > -1) {
			return false;
		}
		User user = new User(lastName, firstName, doMD5(password), gender, dateOfBirth, userName);
		listAccount.add(user);
		return true;
	}

	public String doMD5(String md5) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			byte[] array = md.digest(md5.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
		}
		return null;
	}

	public int checkUsername(String username) {
		for (int i = 0; i < listAccount.size(); i++) {
			if (this.listAccount.get(i).getUserName().compareTo(username) == 0) {
				return i;
			}
		}
		return -1;
	}
	//// Account :end

	/// store : head

	public DataStorge readListAccountasByte() {
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		DataStorge dataStorage = this;
		try {
			fis = new FileInputStream(pathStr + "\\" + fileName);
			ois = new ObjectInputStream(fis);
			dataStorage = (DataStorge) ois.readObject();
		} catch (FileNotFoundException ex) {
			System.out.println(ex.getMessage());
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		} catch (ClassNotFoundException ex) {
			System.out.println(ex.getMessage());
		} finally {
			try {
				fis.close();
				ois.close();
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			}
		}
		return dataStorage;
	}

	public void saveDatapasByte() {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(pathStr + "\\" + fileName);
			oos = new ObjectOutputStream(fos);

			oos.writeObject(this);
			oos.flush();
		} catch (FileNotFoundException ex) {
			System.out.println(ex.getMessage());
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		} finally {
			try {
				fos.close();
				oos.close();
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}
	/// store : end

}
