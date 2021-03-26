package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Model.Account;
import Service.GroupService;
import Storage.DataStorge;

class GroupServiceTest {
	DataStorge dataStorge;
	GroupService groupService;

	@BeforeEach
	void setUp() throws Exception {
		dataStorge = DataStorge.getInstance();
		groupService = new GroupService(dataStorge);

	}

	@Test
	void testInvitePublicGroup() {
		Account user1 = new Account("0001", "last", "first", "123456", "M", "20/03", "fristy1");
		Account user2 = new Account("0002", "last", "first", "123456", "M", "20/03", "fristy2");
		groupService.createPublicGroup("group1", user1);
		boolean result = groupService.invitePublicGroup(0, user2);
		assertTrue(result);
	}

	@Test
	void testInvitePrivateGroup() {
		Account user1 = new Account("0001", "last", "first", "123456", "M", "20/03", "admin1");
		Account member1 = new Account("0001", "last", "first", "123456", "M", "20/03", "member1");
		groupService.createPrivateGroup("pvgroup1", user1);
		boolean result = groupService.invitePrivateGroup(0, user1, member1);
		assertTrue(result);
	}

	@Test
	void testJoinPublicGroupByCode() {
		Account user1 = new Account("0001", "last", "first", "123456", "M", "20/03", "admin1");
		Account user2 = new Account("0002", "last", "first", "123456", "M", "20/03", "fristy2");
		groupService.createPublicGroup("group1", user1);
		String code = groupService.getListPublicGroup().get(0).createJoinCode();
		boolean result = groupService.joinPublicGroupByCode(code, user2, 0);
		assertTrue(result);
	}

	@Test
	void testCreatePublicGroup() {
		Account user1 = new Account("0001", "last", "first", "123456", "M", "20/03", "admin1");
		boolean result = groupService.createPublicGroup("pbGroup1", user1);
		assertTrue(result);
	}

	@Test
	void testCreatePrivateGroup() {
		Account user1 = new Account("0001", "last", "first", "123456", "M", "20/03", "admin1");
		boolean result = groupService.createPrivateGroup("pvGroup1", user1);
		assertTrue(result);
	}

	// Has public groups
	@Test
	void testGetPublicGroupIdByGroupName1() {
		Account user1 = new Account("0001", "last", "first", "123456", "M", "20/03", "admin1");
		groupService.createPublicGroup("group1", user1);
		int expected;
		if (groupService.getListPublicGroup().isEmpty()) {
			expected = -1;
		} else
			expected = 0;
		int result = groupService.getPublicGroupIdByName("group1");
		assertEquals(expected, result);
	}

	// Has no public group
	@Test
	void testGetPublicGroupIdByGroupName2() {
		Account user1 = new Account("0001", "last", "first", "123456", "M", "20/03", "admin1");
		// groupService.createPublicGroup("group1", user1);
		int expected;
		if (groupService.getListPublicGroup().isEmpty()) {
			expected = -1;
		} else
			expected = 0;
		int result = groupService.getPublicGroupIdByName("group1");
		assertEquals(expected, result);
	}

	// Has private groups
	@Test
	void testGetprivateGroupIdByGroupName1() {
		Account user1 = new Account("0001", "last", "first", "123456", "M", "20/03", "admin1");
		groupService.createPrivateGroup("group1", user1);
		int expected;
		if (groupService.getListPrivateGroup().isEmpty()) {
			expected = -1;
		} else
			expected = 0;
		int result = groupService.getprivateGroupIdByName("group1");
		assertEquals(expected, result);
	}

	// Has no private group
	@Test
	void testGetprivateGroupIdByGroupName2() {
		Account user1 = new Account("0001", "last", "first", "123456", "M", "20/03", "admin1");
		// groupService.createPublicGroup("group1", user1);
		int expected;
		if (groupService.getListPrivateGroup().isEmpty()) {
			expected = -1;
		} else
			expected = 0;
		int result = groupService.getprivateGroupIdByName("group1");
		assertEquals(expected, result);
	}
}
