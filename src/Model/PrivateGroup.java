package Model;

import java.util.List;

public class PrivateGroup extends Group {

	public boolean inviteUser(User user, User member) {
		if (user != null && listOfUsers.get(0).getId().endsWith(member.getId())) {
			
			this.listOfUsers.add(user);
			return true;
		}
		
		return false;
	}
}
