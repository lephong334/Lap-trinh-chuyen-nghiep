package Service;

import java.util.ArrayList;
import java.util.List;

import Model.Account;
import Storage.DataStorge;

public class AccountService {
	DataStorge dataStorge;

	public AccountService(DataStorge dataStorge) {
		this.dataStorge = dataStorge;
	}

	public List<Account> findFriendByName(String keyword) {
		List<Account> result = new ArrayList<Account>();
		for (int i = 0; i < dataStorge.getListAccount().size(); i++) {
			if (dataStorge.getListAccount().get(i).getFullName().contains(keyword)) {
				result.add(dataStorge.getListAccount().get(i));
			}
		}
		if (result.size() < 1) {
			result = null;
		}
		return result;

	}

	public boolean addNewAccount(String id, String lastName, String firstName, String password, String gender,
			String dateOfBirth, String userName) {
		if (checkUsername(userName) != -1) {
			return false;
		}
		Account user = new Account(id, lastName, firstName, doMD5(password), gender, dateOfBirth, userName);
		dataStorge.getListAccount().add(user);
		return true;
	}

	public Account checkAccount(String username, String password) {
		int id = checkUsername(username);
		if (id > -1) {
			if (dataStorge.getListAccount().get(id).getPassword().compareTo(doMD5(password)) == 0) {
				return dataStorge.getListAccount().get(id);
			}
			return null;
		}
		return null;
	}

	public Account checkAccountWithoutPassword(String username) {
		int id = checkUsername(username);
		if (id > -1) {
			return dataStorge.getListAccount().get(id);
		}
		return null;
	}

	private String doMD5(String md5) {
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
		for (int i = 0; i < dataStorge.getListAccount().size(); i++) {
			if (this.dataStorge.getListAccount().get(i).getUserName().compareTo(username) == 0) {
				return i;
			}
		}
		return -1;
	}

	public List<Account> getListAccount() {
		return dataStorge.getListAccount();
	}

}
