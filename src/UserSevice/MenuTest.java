package UserSevice;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import Model.User;

class MenuTest {
	Menu menu;

	@BeforeEach
	void setUp() throws Exception {
		menu = new Menu();
	}

	@ParameterizedTest(name = "lastName= {0},firstName ={1},gender= {2},dateOfBirth = {3},userName = {4}, password = {5},expected ={6}")
	@CsvSource({ "no,yes,noGender,113,idk,123,true", "1,2,3,4,5,6,true", "ichi,ni,san,yon,check,roku,true" })
	void addNewUser(String lastName, String firstName, String gender, String dateOfBirth, String userName,
			String password, boolean expected) {
		boolean result = menu.addAccount(lastName, firstName, password, gender, dateOfBirth, userName);
		assertEquals(expected, result);

	}

	@ParameterizedTest(name = "username= {0},password ={1},expexted={2}")
	@CsvSource({ "banana,123,banana", "mongo,456,null" })
	void login(String username, String password, String expected) {
		menu.addAccount("lastname", "first", "123", "no", "2021", "banana");
		User result = menu.login(username, password);
		if (expected.endsWith("null")) {
			assertNull(result);
		} else {
			assertEquals(expected, result.getUserName());
		}

	}

	@ParameterizedTest(name = "keyword= {0},expexted={1}")
	@CsvSource({ "first,first lastname", "mongol,null" })
	void findFriend(String keyword, String expected) {
		menu.addAccount("lastname", "first", "123", "no", "2021", "banana");
		menu.addAccount("lastname", "second", "123", "no", "2021", "nodachi");
		List<User> user = menu.findFriendByName(keyword);
		if (expected.endsWith("null")) {
			assertNull(user);
		} else {
			assertEquals(expected, user.get(0).getFullName());
		}

	}

	@Test
	void createPublicGroup00() {
		menu.addAccount("lastname", "first", "123", "no", "2021", "banana");
		menu.login("banana", "123");
		boolean result = menu.createPublicGroup("banana");
		assertTrue(result);

	}

	@Test
	void createGroup01() {
		menu.addAccount("lastname", "first", "123", "no", "2021", "banana");
		menu.login("banana", "123");
		menu.createPublicGroup("banana");
		boolean result = menu.createPublicGroup("banana");
		assertFalse(result);

	}

	@Test
	void createPrivateGroup00() {
		menu.addAccount("lastname", "first", "123", "no", "2021", "banana");
		menu.login("banana", "123");
		boolean result = menu.createPrivateGroup("banana");
		assertTrue(result);

	}

	@Test
	void createPrivateGroup01() {
		menu.addAccount("lastname", "first", "123", "no", "2021", "banana");
		menu.login("banana", "123");
		menu.createPrivateGroup("banana");
		boolean result = menu.createPrivateGroup("banana");
		assertFalse(result);

	}

	@Test
	void invitePublicGroup() {
		menu.addAccount("lastname", "first", "123", "no", "2021", "banana");
		menu.addAccount("lastname", "first", "123", "no", "2021", "nonono");
		menu.login("banana", "123");
		menu.createPublicGroup("NoOne");
		menu.inviteUserPublicGroup("nonono", "NoOne");
		menu.logout();
		menu.login("nonono", "123");
		String result = menu.getManagermentGroup().getListPublicGroup().get(0).getListOfUsers().get(1).getUserName();
		assertEquals("nonono", result);

	}

	@Test
	void generateAndJoinByCodeInPublicGroup() {
		menu.addAccount("lastname", "first", "123", "no", "2021", "banana");
		menu.addAccount("lastname", "first", "123", "no", "2021", "nonono");
		menu.login("banana", "123");
		menu.createPublicGroup("NoOne");
		String code = menu.generateCodeForPublicGroup("NoOne");
		menu.logout();
		menu.login("nonono", "123");
		menu.joinPublicGroupByCode(code, "NoOne");
		String result = menu.getManagermentGroup().getListPublicGroup().get(0).getListOfUsers().get(1).getUserName();
		assertEquals("nonono", result);
	}

	@Test
	void invitePrivateGroup() {
		menu.addAccount("lastname", "first", "123", "no", "2021", "banana");
		menu.addAccount("lastname", "first", "123", "no", "2021", "nonono");
		menu.login("banana", "123");
		menu.createPrivateGroup("NoOne");
		menu.inviteUserPrivateGroup("nonono", "NoOne");
		menu.logout();
		menu.login("nonono", "123");
		String result = menu.getManagermentGroup().getListPrivateGroup().get(0).getListOfUsers().get(1).getUserName();
		assertEquals("nonono", result);

	}

	@Test
	void sendMessageUser() {
		menu.addAccount("lastname", "first", "123", "no", "2021", "banana");
		menu.addAccount("lastname", "first", "123", "no", "2021", "nonono");
		menu.login("banana", "123");
		boolean res = menu.sendMessageToUser("nonono", "the winter is comming");
		assertTrue(res);

	}

	@Test
	void showAllMessageUser() {
		menu.addAccount("lastname", "first", "123", "no", "2021", "banana");
		menu.addAccount("lastname", "first", "123", "no", "2021", "nonono");
		menu.login("banana", "123");
		menu.sendMessageToUser("nonono", "the winter is comming");
		menu.logout();
		menu.login("nonono", "123");
		menu.sendMessageToUser("banana", "The summer has begun!");
		String result = menu.showAllMessageUser("banana");
		assertEquals("banana: the winter is comming\n" + 
				"nonono: The summer has begun!\n", result);
	}

}
