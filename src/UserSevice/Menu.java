package UserSevice;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Model.DataStorge;
import Model.PublicGroup;
import Model.User;

public class Menu {

	private Scanner sc;
	private UserManager mngUser;
	private DataStorge dateStorage;
	private User user = null;
	private int groupID = -1;

	public Menu() {
		dateStorage = new DataStorge("D:\\SavePoint", "BackupData.dat");
		mngUser = new UserManager(dateStorage);
		sc = new Scanner(System.in);
	}

	public void doTask() {

		int get;
		do {
			printMenu();
			get = sc.nextInt();
			sc.nextLine();
			switch (get) {

			case 1:
				doAddAccount();
				break;
			case 2:
				doLogin();
				break;
			case 3:
				doFindFriendByName();
				break;
			case 4:
				doCreatePublicGroup();
				break;
			case 5:
				inviteUserByUsername();
				break;
			case 6:
				generateCodeForPublicGroup();
				break;
			case 7:
				joinPublicGroupByCode();
				break;
			default:
				System.out.println("Wrong input");

			}
			System.out.println("");
		} while (get != 10);
	}

	private void printMenu() {
		if (user != null) {
			System.out.println("Welcome " + user.getUserName());
		}
		System.out.println("----------------\n1.Add User" + "\n2.Login" + "\n3.Search"
				+ "\n4.Create-Pulic-Group" + "\n5.Invite-to-Public-Group" + "\n6.Generate code for public group"
				+ "\n7.Join-By-code-in-public-group" + "\n10.Exit");
	}

	public void doAddAccount() {
		String lastName, firstName, password, gender, dateOfBirth, userName;
		System.out.print("Enter username: ");
		userName = sc.nextLine();
		System.out.print("Enter password: ");
		password = sc.nextLine();
		System.out.print("Enter First Name: ");
		firstName = sc.nextLine();
		System.out.print("Enter Last Name: ");
		lastName = sc.nextLine();
		System.out.print("Enter Gender : ");
		gender = sc.nextLine();
		System.out.print("Enter Date of Birth: ");
		dateOfBirth = sc.nextLine();
		if (mngUser.addNewUser(lastName, firstName, password, gender, dateOfBirth, userName)) {
			System.out.println("Add Account successful");
		} else {
			System.out.println("Add Account unsuccessful");
		}

	}

	public void doLogin() {
		String userName, password;
		System.out.print("Enter username: ");
		userName = sc.nextLine();
		System.out.print("Enter password: ");
		password = sc.nextLine();
		User temporary = mngUser.login(userName, password);
		if (temporary != null) {
			this.user = temporary;
			System.out.println("Login successful");
		} else {
			System.out.println("Login unsuccessful");
		}

	}

	public void doFindFriendByName() {
		String keyword;
		System.out.print("Enter keyword: ");
		keyword = sc.nextLine();
		String result = mngUser.Search(keyword);
		if (result.length() > 0) {
			System.out.println(result);
		} else {
			System.out.println("Cannot Find");
		}

	}

	public void doCreatePublicGroup() {
		if (isLogin()) {
			List<User> listOfUsers = new ArrayList<User>();
			listOfUsers.add(this.user);
			groupID = dateStorage.createNewPublicGroup(listOfUsers);
			System.out.println("create group successful");
		} else {
			System.out.println("create group unsuccessful");
		}
	}

	public void inviteUserByUsername() {
		String usename;
		System.out.print("Enter usename: ");
		usename = sc.nextLine();
		if (isLogin() && isInGroup()) {
			if (dateStorage.InviteUserByUsername(usename, groupID)) {
				System.out.println("Invite successful");
			} else {
				System.out.println("Inviting user are already in this group");
			}
		} else {
			System.out.println("You are not login or not in any group");
		}

	}

	public void generateCodeForPublicGroup() {
		System.out.println("Code :" + dateStorage.generateCode(groupID));
	}

	public void joinPublicGroupByCode() {
		if (isLogin()) {
			String code;
			System.out.print("Enter code: ");
			code = sc.nextLine();
			int id = dateStorage.joinPublicGroupByCode(code, user);
			if (id == -1) {
				System.out.println("Join unsuccuessful");
			} else {
				this.groupID = id;
				System.out.println("Join succuessful");
			}
		} else {
			System.out.println("You are not login");
		}

	}

	private boolean isLogin() {
		if (this.user == null) {
			return false;
		}
		return true;
	}

	private boolean isInGroup() {
		if (this.groupID == -1) {
			return false;
		}
		return true;
	}

}
