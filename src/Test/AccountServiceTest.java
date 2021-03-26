package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Model.Account;
import Service.AccountService;
import Storage.DataStorge;

class AccountServiceTest {
	DataStorge dataStorge;
	AccountService accountService;

	@BeforeEach
	void setUp() throws Exception {
		dataStorge = DataStorge.getInstance();
		accountService = new AccountService(dataStorge);
	}

	@Test
	void testFindFriendByName() {
		accountService.addNewAccount("0001", "last", "first", "123456", "M", "20/03", "admin1");
		List<Account> result = new ArrayList<Account>();
		result = accountService.findFriendByName("last");
		assertNotNull(result);
	}

	@Test
	void testAddNewAccount() {
		boolean result = accountService.addNewAccount("0001", "last", "first", "123456", "M", "20/03", "admin1");
		assertTrue(result);
	}

	@Test
	void testCheckAccount() {
		accountService.addNewAccount("0001", "last", "first", "123456", "M", "20/03", "admin1");
		Account expected;
		expected = accountService.getListAccount().get(0);
		Account result;
		result = accountService.checkAccount("admin1", "123456");
		assertEquals(expected, result);
	}

	@Test
	void testCheckAccountWithoutPassword() {
		accountService.addNewAccount("0001", "last", "first", "123456", "M", "20/03", "admin1");
		Account expected;
		expected = accountService.getListAccount().get(0);
		Account result;
		result = accountService.checkAccountWithoutPassword("admin1");
		assertEquals(expected, result);
	}

}
