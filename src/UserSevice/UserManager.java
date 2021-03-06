package UserSevice;

import java.util.ArrayList;

import Model.DataStorge;
import Model.User;

public class UserManager {
	DataStorge dataStorge;
	protected ArrayList<User> listAccount;

	public UserManager(DataStorge dataStorge) {
		this.dataStorge = dataStorge;
		this.listAccount = dataStorge.getListAccount();
	}

	public ArrayList<User> findFriendByName(String keyword) {
		ArrayList<User> result = null;
		for (int i = 0; i < listAccount.size(); i++) {
			if (listAccount.get(i).getFullName().contains(keyword)) {
				result.add(listAccount.get(i));
			}
		}
		return result;

	}

	public boolean addNewAccount(String lastName, String firstName, String password, String gender, String dateOfBirth,
			String userName) {
		if (checkUsername(userName) != -1) {
			return false;
		}
		User user = new User(lastName, firstName, doMD5(password), gender, dateOfBirth, userName);
		this.listAccount.add(user);
		return true;
	}

	

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

	private User checkAccountWithoutPassword(String username) {
		int id = checkUsername(username);
		if (id > -1) {
			return listAccount.get(id);
		}
		return null;
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

	private int checkUsername(String username) {
		for (int i = 0; i < listAccount.size(); i++) {
			if (this.listAccount.get(i).getUserName().compareTo(username) == 0) {
				return i;
			}
		}
		return -1;
	}
}
