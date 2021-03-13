package Model;

import java.util.List;

public class PublicGroup extends Group {
	public PublicGroup(DataStorge dataStorge) {
		super(dataStorge);
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
