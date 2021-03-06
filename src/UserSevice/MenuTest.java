package UserSevice;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class MenuTest {
Menu menu;
	@BeforeEach
	void setUp() throws Exception {
		menu = new Menu();
	}
	
	@ParameterizedTest(name="lastName= {0},firstName ={1},gender= {2},dateOfBirth = {3},userName = {4}, password = {5},expected ={6}")
	@CsvSource({"no,yes,noGender,113,idk,123,true","","1,1,7,-1","1,1,8,-1"})
	void addNewUser(String lastName, String firstName, String gender, String dateOfBirth,
			String userName, String password, String expected) {
		boolean result =menu.addAccount(lastName, firstName, password, gender, dateOfBirth, userName);
		assertEquals(expected, result);
		
	}

}
