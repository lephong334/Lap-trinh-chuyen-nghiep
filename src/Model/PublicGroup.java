package Model;

import java.util.List;

public class PublicGroup extends Group {
	private String admin;
	private String joinCode;

	public PublicGroup(List<User> listOfUsers) {
		super(listOfUsers);
		admin = listOfUsers.get(0).getUserName();
		// TODO Auto-generated constructor stub
	}

	public boolean inviteByMember(User invitingMember) {
		if (!isMember(invitingMember)) {
			listOfUsers.add(invitingMember);
			return true;
		}
		return false;
	}

	public String createJoinCode() {
		this.joinCode = getAlphaNumericString(8);
		return this.joinCode;

	}

	public boolean joinByCode(User user, String code) {
		if (!isMember(user)) {
			if (code == joinCode && joinCode != null) {
				listOfUsers.add(user);
				joinCode = null;
				return true;
			}

		}
		return false;

	}

	private boolean isMember(User user) {
		return listOfUsers.contains(user);
	}

	private String getAlphaNumericString(int n) {

		// chose a Character random from this String
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

		// create StringBuffer size of AlphaNumericString
		StringBuilder sb = new StringBuilder(n);

		for (int i = 0; i < n; i++) {

			// generate a random number between
			// 0 to AlphaNumericString variable length
			int index = (int) (AlphaNumericString.length() * Math.random());

			// add Character one by one in end of sb
			sb.append(AlphaNumericString.charAt(index));
		}

		return sb.toString();
	}

}
