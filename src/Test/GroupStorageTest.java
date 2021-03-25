package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Model.User;
import Storage.DataStorge;
import Storage.GroupStorage;

class GroupStorageTest {
	DataStorge dataStorge;
	GroupStorage manager;

	
	@BeforeEach
	void setUp() throws Exception {
		dataStorge = new DataStorge();
		manager = new GroupStorage(dataStorge);

	}

	@Test
	void testInvitePublicGroup() {
		User user1 = new User("0001", "last", "first", "123456", "M", "20/03", "fristy1");
		User user2 = new User("0002", "last", "first", "123456", "M", "20/03", "fristy2");
		manager.createPublicGroup("group1", user1);
		boolean result = manager.invitePublicGroup(0, user2);
		assertTrue(result);
	}

	@Test
	void testInvitePrivateGroup() {
		User user1 = new User("0001", "last", "first", "123456", "M", "20/03", "admin1");
		User member1 = new User("0001", "last", "first", "123456", "M", "20/03", "member1");
		manager.createPrivateGroup("pvgroup1", user1);
		boolean result = manager.invitePrivateGroup(0, user1, member1);
		assertTrue(result);
	}
	
	@Test
	void testJoinPublicGroupByCode() {
		User user1 = new User("0001", "last", "first", "123456", "M", "20/03", "admin1");
		User user2 = new User("0002", "last", "first", "123456", "M", "20/03", "fristy2");
		manager.createPublicGroup("group1", user1);
		String code = manager.getListPublicGroup().get(0).createJoinCode();
		boolean result = manager.joinPublicGroupByCode(code, user2, 0);
		assertTrue(result);
	}
	
	@Test
	void testCreatePublicGroup() {
		User user1 = new User("0001", "last", "first", "123456", "M", "20/03", "admin1");
		boolean result = manager.createPublicGroup("pbGroup1", user1);
		assertTrue(result);
	}
	
	@Test
	void testCreatePrivateGroup() {
		User user1 = new User("0001", "last", "first", "123456", "M", "20/03", "admin1");
		boolean result = manager.createPrivateGroup("pvGroup1", user1);
		assertTrue(result);
	}
	
	//Has public groups
	@Test
	void testGetPublicGroupIdByGroupName1() {
		User user1 = new User("0001", "last", "first", "123456", "M", "20/03", "admin1");
		manager.createPublicGroup("group1", user1);
		int expected;
		if (manager.getListPublicGroup().isEmpty()) {
			expected = -1;
		} else expected = 0;
		int result = manager.getPublicGroupIdByGroupName("group1");
		assertEquals(expected, result);
	}
	
	//Has no public group
	@Test
	void testGetPublicGroupIdByGroupName2() {
		User user1 = new User("0001", "last", "first", "123456", "M", "20/03", "admin1");
		// manager.createPublicGroup("group1", user1);
		int expected;
		if (manager.getListPublicGroup().isEmpty()) {
			expected = -1;
		} else expected = 0;
		int result = manager.getPublicGroupIdByGroupName("group1");
		assertEquals(expected, result);
	}
	
	//Has private groups
	@Test
	void testGetprivateGroupIdByGroupName1() {
		User user1 = new User("0001", "last", "first", "123456", "M", "20/03", "admin1");
		manager.createPrivateGroup("group1", user1);
		int expected;
		if (manager.getListPrivateGroup().isEmpty()) {
			expected = -1;
		} else expected = 0;
		int result = manager.getprivateGroupIdByGroupName("group1");
		assertEquals(expected, result);
	}
	
	//Has no private group
	@Test
	void testGetprivateGroupIdByGroupName2() {
		User user1 = new User("0001", "last", "first", "123456", "M", "20/03", "admin1");
		// manager.createPublicGroup("group1", user1);
		int expected;
		if (manager.getListPrivateGroup().isEmpty()) {
			expected = -1;
		} else expected = 0;
		int result = manager.getprivateGroupIdByGroupName("group1");
		assertEquals(expected, result);
	}
}
