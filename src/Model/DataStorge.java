package Model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataStorge {

	private List<User> listAccount;
	private List<PublicGroup> listPublicGroup;
	private List<PrivateGroup> listPrivateGroup;
	private HashMap<String, String> listFile;

	public DataStorge() {
		listAccount = new ArrayList<>();
		listPublicGroup = new ArrayList<PublicGroup>();
		listPrivateGroup = new ArrayList<PrivateGroup>();
		listFile = new HashMap<String, String>();

	}

	public void copyANewFileUsingBufferedInputOutputStream(String filePathIn, String idFile, String filename) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		String temporary = "D:\\all_study\\coding practice\\Test send file\\" + idFile;
		listFile.put(filename, temporary + "\\" + filename);
		createFolder(temporary);
		try {
			fis = new FileInputStream(filePathIn);
			fos = new FileOutputStream(temporary + "\\" + filename);
			bis = new BufferedInputStream(fis);
			bos = new BufferedOutputStream(fos);

			byte[] data = new byte[100];
			while (bis.read(data) != -1) {
				bos.write(data);
				bos.flush();
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				bos.close();
				bis.close();
				fis.close();
				fos.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}

	}

	private void createFolder(String pathOut) {
		File folder = new File(pathOut);
		if (folder.exists()) {
		} else {
			folder.mkdirs();
		}
	}

	/// Group: head

///////Public group : head

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
