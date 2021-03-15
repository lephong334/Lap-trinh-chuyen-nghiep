package Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class DataStorge {

	protected List<User> listAccount;
	protected List<PublicGroup> listPublicGroup;
	protected List<PrivateGroup> listPrivateGroup;

	public DataStorge() {
		listAccount = new ArrayList<>();
		listPublicGroup = new ArrayList<PublicGroup>();
		listPrivateGroup = new ArrayList<PrivateGroup>();

	}

	/// Group: head

///////Public group : head

//	public String generateCode(int groupID) {
//		return listPublicGroup.get(groupID).createJoinCode();
//	}
//

	/////// Public group : end

	/////// Public group : end
//getter and setter for AccountList : head
	public List<User> getListAccount() {
		return listAccount;
	}

	public void setListAccount(ArrayList<User> listAccount) {
		this.listAccount = listAccount;
	}
	// getter and setter for AccountList : end

	/// store : head

	public DataStorge readListAccountasByte() {
		return null;

	}

	public boolean saveDatapasByte() {
		return true;

	}
	/// store : end

	public List<PublicGroup> getListPublicGroup() {
		return listPublicGroup;
	}

	public void setListPublicGroup(List<PublicGroup> listPublicGroup) {
		this.listPublicGroup = listPublicGroup;
	}

	public List<PrivateGroup> getListPrivateGroup() {
		return listPrivateGroup;
	}

	public void setListPrivateGroup(List<PrivateGroup> listPrivateGroup) {
		this.listPrivateGroup = listPrivateGroup;
	}

	

}
