package UserSevice;

import Model.DataStorge;
import Model.User;

public class UserManager {
	DataStorge dataStorge;

	public UserManager(DataStorge dataStorge) {
		this.dataStorge = dataStorge;
	}

	public boolean addNewUser(String lastName, String firstName, String password, String gender, String dateOfBirth,
			String userName) {
		return dataStorge.addNewAccount(lastName, firstName, password, gender, dateOfBirth, userName);
	}

	public User login(String username, String password) {
		return dataStorge.checkAccount(username, password);
	}

	public String Search(String keyword) {
		return dataStorge.findFriendByName(keyword);

	}
}
