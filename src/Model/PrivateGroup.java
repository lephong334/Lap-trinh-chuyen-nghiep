package Model;

import java.util.ArrayList;
import java.util.List;

public class PrivateGroup extends Group {

	public PrivateGroup(String name, User user) {
		listOfUsers = new ArrayList<User>();
		listOfMessages = new ArrayList<String>();
		listOfMedias = new ArrayList<String>();
		this.name = name;
		listOfUsers.add(user);
	}

	public boolean inviteUser(User user, User member) {
		if (user != null && listOfUsers.get(0).getId().endsWith(member.getId())) {

			this.listOfUsers.add(user);
			return true;
		}

		return false;
	}
}
