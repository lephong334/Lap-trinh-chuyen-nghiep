package UserSevice;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Model.DataStorge;
import Model.User;

class UserManagerTest {
	DataStorge dataStorge;
	UserManager manager;
	
	@BeforeEach
	void setUp() throws Exception {
		dataStorge = new DataStorge();
		manager = new UserManager(dataStorge);
	}

	@Test
	void testFindFriendByName() {
		manager.addNewAccount("0001", "last", "first", "123456", "M", "20/03", "admin1");
		List<User> result = new ArrayList<User>();
		result = manager.findFriendByName("last");
		assertNotNull(result);
	}

	@Test
	void testAddNewAccount() {
		boolean result = manager.addNewAccount("0001", "last", "first", "123456", "M", "20/03", "admin1");
		assertTrue(result);
	}

	@Test
	void testCheckAccount() {
		manager.addNewAccount("0001", "last", "first", "123456", "M", "20/03", "admin1");
		User expected;
		expected = manager.listAccount.get(0);
		User result;
		result = manager.checkAccount("admin1", "123456");
		assertEquals(expected, result);
	}

	@Test
	void testCheckAccountWithoutPassword() {
		manager.addNewAccount("0001", "last", "first", "123456", "M", "20/03", "admin1");
		User expected;
		expected = manager.listAccount.get(0);
		User result;
		result = manager.checkAccountWithoutPassword("admin1");
		assertEquals(expected, result);
	}

	

}
