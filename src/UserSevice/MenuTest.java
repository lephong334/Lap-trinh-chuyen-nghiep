package UserSevice;

import static org.junit.jupiter.api.Assertions.*;

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
			String password, String expected) {
		boolean result = menu.addAccount(lastName, firstName, password, gender, dateOfBirth, userName);

		boolean expect;
		if (expected.length() == 4) {
			expect = true;
		} else {
			expect = false;
		}
		assertEquals(expect, result);

	}

	@ParameterizedTest(name = "username= {0},password ={1},expexted={2}")
	@CsvSource({ "banana,123,banana", "mongo,456,null" })
	void login(String username, String password, String expected) {
		menu.addAccount("lastname", "first", "123", "no", "2021", "banana");
		User result = menu.login(username, password);
		String expect;
		if (expected.endsWith("null")) {
			expect=null;
		}else {
			expect=expected;
		}
		
		assertEquals(expect, result.getUserName());

	}
}
