package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import Model.Account;
import Service.UserService;

class UserServiceTest {
	UserService userService;

	@BeforeEach
	void setUp() throws Exception {
		userService = new UserService();

	}

	@ParameterizedTest(name = "lastName= {0},firstName ={1},gender= {2},dateOfBirth = {3},userName = {4}, password = {5},expected ={6}")
	@CsvSource({ "no,yes,noGender,113,idk,123,true", "1,2,3,4,5,6,true", "ichi,ni,san,yon,check,roku,true" })
	void addNewUser(String lastName, String firstName, String gender, String dateOfBirth, String userName,
			String password, boolean expected) {
		boolean result = userService.addAccount(lastName, firstName, password, gender, dateOfBirth, userName);
		assertEquals(expected, result);

	}

	@ParameterizedTest(name = "username= {0},password ={1},expexted={2}")
	@CsvSource({ "banana,123,banana", "mongo,456,null" })
	void login(String username, String password, String expected) {
		userService.addAccount("lastname", "first", "123", "no", "2021", "banana");
		Account result = userService.login(username, password);
		if (expected.endsWith("null")) {
			assertNull(result);
		} else {
			assertEquals(expected, result.getUserName());
		}

	}

	@ParameterizedTest(name = "keyword= {0},expexted={1}")
	@CsvSource({ "first,first lastname", "mongol,null" })
	void findFriend(String keyword, String expected) {
		userService.addAccount("lastname", "first", "123", "no", "2021", "banana");
		userService.addAccount("lastname", "second", "123", "no", "2021", "nodachi");
		List<Account> user = userService.findFriendByName(keyword);
		if (expected.endsWith("null")) {
			assertNull(user);
		} else {
			assertEquals(expected, user.get(0).getFullName());
		}

	}

	@Test
	void createPublicGroup00() {
		userService.addAccount("lastname", "first", "123", "no", "2021", "banana");
		userService.login("banana", "123");
		boolean result = userService.createPublicGroup("banana");
		assertTrue(result);

	}

	@Test
	void createGroup01() {
		userService.addAccount("lastname", "first", "123", "no", "2021", "banana");
		userService.login("banana", "123");
		userService.createPublicGroup("banana");
		boolean result = userService.createPublicGroup("banana");
		assertFalse(result);

	}

	@Test
	void createPrivateGroup00() {
		userService.addAccount("lastname", "first", "123", "no", "2021", "banana");
		userService.login("banana", "123");
		boolean result = userService.createPrivateGroup("banana");
		assertTrue(result);

	}

	@Test
	void createPrivateGroup01() {
		userService.addAccount("lastname", "first", "123", "no", "2021", "banana");
		userService.login("banana", "123");
		userService.createPrivateGroup("banana");
		boolean result = userService.createPrivateGroup("banana");
		assertFalse(result);

	}

	@Test
	void invitePublicGroup() {
		userService.addAccount("lastname", "first", "123", "no", "2021", "banana");
		userService.addAccount("lastname", "first", "123", "no", "2021", "nonono");
		userService.login("banana", "123");
		userService.createPublicGroup("NoOne");
		userService.inviteUserPublicGroup("nonono", "NoOne");
		userService.logout();
		userService.login("nonono", "123");
		String result = userService.getManagementGroup().getListPublicGroup().get(0).getListOfUsers().get(1)
				.getUserName();
		assertEquals("nonono", result);

	}

	@Test
	void generateAndJoinByCodeInPublicGroup() {
		userService.addAccount("lastname", "first", "123", "no", "2021", "banana");
		userService.addAccount("lastname", "first", "123", "no", "2021", "nonono");
		userService.login("banana", "123");
		userService.createPublicGroup("NoOne");
		String code = userService.generateCodePublicGroup("NoOne");
		userService.logout();
		userService.login("nonono", "123");
		userService.joinPublicGroupByCode(code, "NoOne");
		String result = userService.getManagementGroup().getListPublicGroup().get(0).getListOfUsers().get(1)
				.getUserName();
		assertEquals("nonono", result);
	}

	@Test
	void invitePrivateGroup() {
		userService.addAccount("lastname", "first", "123", "no", "2021", "banana");
		userService.addAccount("lastname", "first", "123", "no", "2021", "nonono");
		userService.login("banana", "123");
		userService.createPrivateGroup("NoOne");
		userService.inviteUserPrivateGroup("nonono", "NoOne");
		userService.logout();
		userService.login("nonono", "123");
		String result = userService.getManagementGroup().getListPrivateGroup().get(0).getListOfUsers().get(1)
				.getUserName();
		assertEquals("nonono", result);

	}

	@Test
	void sendMessageUser() {
		userService.addAccount("lastname", "first", "123", "no", "2021", "banana");
		userService.addAccount("lastname", "first", "123", "no", "2021", "nonono");
		userService.login("banana", "123");
		boolean res = userService.sendMessageToUser("nonono", "the winter is comming");
		assertTrue(res);

	}

	@Test
	void showAllMessageUser() {
		userService.addAccount("lastname", "first", "123", "no", "2021", "banana");
		userService.addAccount("lastname", "first", "123", "no", "2021", "nonono");
		userService.login("banana", "123");
		userService.sendMessageToUser("nonono", "the winter is comming");
		userService.logout();
		userService.login("nonono", "123");
		userService.sendMessageToUser("banana", "The summer has begun!");
		List<String> messages = userService.showAllMessageUser("banana");
		String result = new String();
		for (int i = 0; i < messages.size(); i++) {
			result += messages.get(i) + "\n";
		}

		assertEquals("banana: the winter is comming\n" + "nonono: The summer has begun!\n", result);
	}

	@Test
	void deleteMessageUser() {
		userService.addAccount("lastname", "first", "123", "no", "2021", "banana");
		userService.addAccount("lastname", "first", "123", "no", "2021", "nonono");
		userService.login("banana", "123");
		userService.sendMessageToUser("nonono", "the winter is comming");
		boolean resulDeleteMessage = userService.deleteMessage("nonono", 0);
		userService.logout();
		userService.login("nonono", "123");
		userService.sendMessageToUser("banana", "The summer has begun!");
		List<String> messages = userService.showAllMessageUser("banana");
		String resultShowMessage = new String();
		for (int i = 0; i < messages.size(); i++) {
			resultShowMessage += messages.get(i) + "\n";
		}
		assertTrue(resulDeleteMessage);
		assertEquals("This message has been deleted\n" + "nonono: The summer has begun!\n", resultShowMessage);
	}

	@Test
	void sendMessageGroup() {
		userService.addAccount("lastname", "first", "123", "no", "2021", "banana");
		userService.login("banana", "123");
		userService.createPublicGroup("NoOne");
		boolean result = userService.sendMessageToGroup("NoOne", "The winter is comming");
		assertTrue(result);
	}

	@Test
	void showMessageGroup() {
		userService.addAccount("lastname", "first", "123", "no", "2021", "banana");
		userService.addAccount("lastname", "first", "123", "no", "2021", "nonono");
		userService.login("banana", "123");
		userService.createPublicGroup("NoOne");
		userService.sendMessageToGroup("NoOne", "The winter is comming");
		userService.inviteUserPublicGroup("nonono", "NoOne");
		userService.logout();
		userService.login("nonono", "123");
		userService.sendMessageToGroup("NoOne", "The summer has begin!");
		List<String> messages = userService.showAllMessageGroup("NoOne");
		String result = new String();
		for (int i = 0; i < messages.size(); i++) {
			result += messages.get(i) + "\n";
		}
		assertEquals("banana: The winter is comming\n" + "nonono: The summer has begin!\n", result);
	}

	@Test
	void deleteMessageGroup() {
		userService.addAccount("lastname", "first", "123", "no", "2021", "banana");
		userService.addAccount("lastname", "first", "123", "no", "2021", "nonono");
		userService.login("banana", "123");
		userService.createPublicGroup("NoOne");
		userService.sendMessageToGroup("NoOne", "The winter is comming");
		boolean resulDeleteMessage = userService.deleteMessageGroup("NoOne", 0);
		userService.inviteUserPublicGroup("nonono", "NoOne");
		userService.logout();
		userService.login("nonono", "123");
		userService.sendMessageToGroup("NoOne", "The summer has begin!");
		List<String> messages = userService.showAllMessageGroup("NoOne");
		String resultShowMessage = new String();
		for (int i = 0; i < messages.size(); i++) {
			resultShowMessage += messages.get(i) + "\n";
		}
		assertTrue(resulDeleteMessage);
		assertEquals("This message has been deleted\n" + "nonono: The summer has begin!\n", resultShowMessage);
	}

	@Test
	void sendFileToUser() {
		userService.addAccount("lastname", "first", "123", "no", "2021", "banana");
		userService.addAccount("lastname", "first", "123", "no", "2021", "nonono");
		userService.login("banana", "123");
		boolean result = userService.sendFileToUser("nonono",
				"D:\\all_study\\coding practice\\Test send file\\XDSoiW2-msi-wallpaper.jpg");
		assertTrue(result);

	}

	@Test
	void sendFileToGroup() {
		userService.addAccount("lastname", "first", "123", "no", "2021", "banana");
		userService.login("banana", "123");
		userService.createPublicGroup("NoOne");
		boolean resultImage = userService.sendFileToGroup("NoOne",
				"D:\\all_study\\coding practice\\Test send file\\XDSoiW2-msi-wallpaper.jpg");
		boolean resultMp3 = userService.sendFileToGroup("NoOne",
				"D:\\all_study\\coding practice\\Test send file\\Holopsicon.mp3");
		assertTrue(resultImage);
		assertTrue(resultMp3);
	}

	@Test
	void showFileUser() {
		userService.addAccount("lastname", "first", "123", "no", "2021", "banana");
		userService.addAccount("lastname", "first", "123", "no", "2021", "nonono");
		userService.addAccount("lastname", "first", "123", "no", "2021", "Obama");
		userService.addAccount("lastname", "first", "123", "no", "2021", "noOne");
		userService.login("banana", "123");
		userService.sendFileToUser("nonono",
				"D:\\all_study\\coding practice\\Test send file\\XDSoiW2-msi-wallpaper.jpg");
		userService.sendFileToUser("nonono", "D:\\all_study\\coding practice\\Test send file\\videoplayback.mp4");
		userService.sendFileToUser("Obama", "D:\\all_study\\coding practice\\Test send file\\Holopsicon.mp3");
		String resultFirstPerson = userService.showAllFileHasSent("nonono");
		String resultSecondPerson = userService.showAllFileHasSent("Obama");
		String resultThirdPerson = userService.showAllFileHasSent("noOne");
		assertEquals("videoplayback.mp4\n" + "XDSoiW2-msi-wallpaper.jpg\n", resultFirstPerson);
		assertEquals("Holopsicon.mp3\n", resultSecondPerson);
		assertEquals("", resultThirdPerson);

	}

	@Test
	void showFileGroup() {
		userService.addAccount("lastname", "first", "123", "no", "2021", "banana");
		userService.addAccount("lastname", "first", "123", "no", "2021", "nonono");
		userService.login("banana", "123");
		userService.createPublicGroup("NoOne");
		userService.sendFileToGroup("NoOne", "D:\\all_study\\coding practice\\Test send file\\Holopsicon.mp3");
		String resultFirstPerson = userService.showAllFileHasSent("NoOne");
		userService.inviteUserPublicGroup("nonono", "NoOne");
		userService.logout();
		userService.login("nonono", "123");
		userService.sendFileToGroup("NoOne", "D:\\all_study\\coding practice\\Test send file\\videoplayback.mp4");
		String resultSecondPerson = userService.showAllFileHasSent("NoOne");
		assertEquals("Holopsicon.mp3\n", resultFirstPerson);
		assertEquals("videoplayback.mp4\n", resultSecondPerson);
	}

	@Test
	void deleteFileUser() {
		userService.addAccount("lastname", "first", "123", "no", "2021", "banana");
		userService.addAccount("lastname", "first", "123", "no", "2021", "nonono");
		userService.login("banana", "123");
		userService.sendFileToUser("nonono",
				"D:\\all_study\\coding practice\\Test send file\\XDSoiW2-msi-wallpaper.jpg");
		userService.deleteFile("XDSoiW2-msi-wallpaper.jpg");
		String result = userService.showAllFileHasSent("nonono");
		assertEquals("", result);
	}

	@Test
	void deleteFileGroup() {
		userService.addAccount("lastname", "first", "123", "no", "2021", "banana");
		userService.login("banana", "123");
		userService.createPublicGroup("NoOne");
		userService.sendFileToGroup("NoOne",
				"D:\\all_study\\coding practice\\Test send file\\XDSoiW2-msi-wallpaper.jpg");
		userService.sendFileToGroup("NoOne", "D:\\all_study\\coding practice\\Test send file\\videoplayback.mp4");
		userService.deleteFile("XDSoiW2-msi-wallpaper.jpg");
		String result = userService.showAllFileHasSent("NoOne");
		assertEquals("videoplayback.mp4\n", result);
	}

	@Test
	void findtext() {
		userService.addAccount("lastname", "first", "123", "no", "2021", "banana");
		userService.addAccount("lastname", "first", "123", "no", "2021", "nonono");
		userService.login("banana", "123");
		userService.sendMessageToUser("nonono", "the winter is comming");
		userService.createPublicGroup("NoOne");
		userService.sendMessageToGroup("NoOne", "the summer has begin");
		userService.inviteUserPublicGroup("nonono", "NoOne");
		userService.createPrivateGroup("NoTwo");
		userService.sendMessageToGroup("NoTwo", "The time has come");
		String resultUser = userService.findTextMessage("winter");
		String resultPublicGroup = userService.findTextMessage("summer");
		String resultPrivateGroup = userService.findTextMessage("time");

		assertEquals("banana: the winter is comming", resultUser);
		assertEquals("banana: the summer has begin", resultPublicGroup);
		assertEquals("banana: The time has come", resultPrivateGroup);
	}

	@Test
	void showLimitedTestUser() {
		userService.addAccount("lastname", "first", "123", "no", "2021", "banana");
		userService.addAccount("lastname", "first", "123", "no", "2021", "nonono");
		userService.login("banana", "123");
		userService.sendMessageToUser("nonono", "the winter is comming");
		userService.sendMessageToUser("nonono", "the summer is comming");
		userService.sendMessageToUser("nonono", "the night is comming");
		List<String> message1 = userService.showLimitedMessageUser(1, 1, "nonono");
		List<String> message2 = userService.showNextLimitedMessageToUser("nonono");
		List<String> message3 = userService.showNextLimitedMessageToUser("nonono");

		String showMTest = new String();
		String showNextKTest00 = new String();
		String showNextKTest01 = new String();
		for (int i = 0; i < message1.size(); i++) {
			showMTest += message1.get(i) + "\n";
		}
		for (int i = 0; i < message2.size(); i++) {
			showNextKTest00 += message2.get(i) + "\n";
		}
		for (int i = 0; i < message3.size(); i++) {
			showNextKTest01 += message3.get(i) + "\n";
		}

		assertEquals("banana: the winter is comming\n", showMTest);
		assertEquals("banana: the summer is comming\n", showNextKTest00);
		assertEquals("banana: the night is comming\n", showNextKTest01);

	}

	@Test
	void showLimitedTestGroup() {
		userService.addAccount("lastname", "first", "123", "no", "2021", "banana");
		userService.login("banana", "123");
		userService.createPrivateGroup("NoOne");
		userService.sendMessageToGroup("NoOne", "the winter is comming");
		userService.sendMessageToGroup("NoOne", "the summer is comming");
		userService.sendMessageToGroup("NoOne", "the night is comming");
		List<String> message1 = userService.showLimitedMessageGroup(1, 1, "NoOne");
		List<String> message2 = userService.showNextLimitedMessageGroup("NoOne");
		List<String> message3 = userService.showNextLimitedMessageGroup("NoOne");
		String resultShowMessage = new String();
		String showMTest = new String();
		String showNextKTest00 = new String();
		String showNextKTest01 = new String();
		for (int i = 0; i < message1.size(); i++) {
			showMTest += message1.get(i) + "\n";
		}
		for (int i = 0; i < message2.size(); i++) {
			showNextKTest00 += message2.get(i) + "\n";
		}
		for (int i = 0; i < message3.size(); i++) {
			showNextKTest01 += message3.get(i) + "\n";
		}
		assertEquals("banana: the winter is comming\n", showMTest);
		assertEquals("banana: the summer is comming\n", showNextKTest00);
		assertEquals("banana: the night is comming\n", showNextKTest01);

	}

	@Test
	void LeaveTheGroup() {
		userService.addAccount("lastname", "first", "123", "no", "2021", "banana");
		userService.login("banana", "123");
		userService.createPrivateGroup("NoOne");
		boolean res = userService.leaveTheGroup("NoOne");
		assertTrue(res);
	}

	@Test
	void LeaveTheGroupAndCantReadMessage() {
		userService.addAccount("lastname", "first", "123", "no", "2021", "banana");
		userService.addAccount("lastname", "first", "123", "no", "2021", "nonono");
		userService.login("banana", "123");
		userService.createPrivateGroup("NoOne");
		userService.inviteUserPrivateGroup("nonono", "NoOne");
		userService.sendMessageToGroup("NoOne", "the winter is comming");
		userService.sendMessageToGroup("NoOne", "the summer is comming");
		List<String> message1 = userService.showAllMessageGroup("NoOne");
		userService.leaveTheGroup("NoOne");
		List<String> leaveTheGroup = userService.showAllMessageGroup("NoOne");
		userService.logout();
		userService.login("nonono", "123");
		List<String> message3 = userService.showAllMessageGroup("NoOne");
		String notLeaveTheGroup = new String();
		String otherMember = new String();
		for (int i = 0; i < message1.size(); i++) {
			notLeaveTheGroup += message1.get(i) + "\n";
		}
		for (int i = 0; i < message3.size(); i++) {
			otherMember += message3.get(i) + "\n";
		}
		assertEquals("banana: the winter is comming\nbanana: the summer is comming\n", notLeaveTheGroup);
		assertEquals(null, leaveTheGroup);
		assertEquals("banana: the winter is comming\nbanana: the summer is comming\n", otherMember);
	}

	@Test
	void setAlias() {
		userService.addAccount("lastname", "first", "123", "no", "2021", "banana");
		userService.addAccount("lastname", "first", "123", "no", "2021", "nonono");
		userService.login("banana", "123");
		boolean result = userService.setAlias("nonono", "Obama");
		assertTrue(result);
	}

}
