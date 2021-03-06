package Model;

import java.util.List;

public class PrivateGroup extends Group {
	
	public PrivateGroup(List<User> listOfUsers) {
		super(listOfUsers);
		// TODO Auto-generated constructor stub
	}
	
	public boolean inviteUser(User user) {
		if (user != null) {
			this.listOfUsers.add(user);
			return true;
		}
		return false;
	}
}
