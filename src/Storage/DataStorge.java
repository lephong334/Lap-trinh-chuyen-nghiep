package Storage;

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

import Model.PrivateGroup;
import Model.PublicGroup;
import Model.Account;

public class DataStorge {
	private static DataStorge instance;
	private List<Account> listAccount;
	private List<PublicGroup> listPublicGroup;
	private List<PrivateGroup> listPrivateGroup;
	private HashMap<String, String> listFile;

	private DataStorge() {
		listAccount = new ArrayList<>();
		listPublicGroup = new ArrayList<PublicGroup>();
		listPrivateGroup = new ArrayList<PrivateGroup>();
		listFile = new HashMap<String, String>();
		createFolder("D:\\all_study\\coding practice\\Test send file\\DataStore");
	}

	public static DataStorge getInstance() {
		if (instance == null) {
			instance = new DataStorge();
		}
		return instance;
	}

	public boolean copyANewFileUsingBufferedInputOutputStream(String filePathIn, String idFile, String filename) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		String temporary = "D:\\all_study\\coding practice\\Test send file\\DataStore\\" + idFile;
		listFile.put(filename, temporary);

		try {
			fis = new FileInputStream(filePathIn);
			fos = new FileOutputStream(temporary);
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
				return true;
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
		return false;

	}

	public boolean DeleteFile(String fileName) {
		File myObj = new File(listFile.get(fileName));
		if (myObj.delete()) {
			return true;
		}
		return false;
	}

	private void createFolder(String pathOut) {
		File folder = new File(pathOut);
		if (folder.exists()) {
		} else {
			folder.mkdirs();
		}
	}

	/// getter and setter
	public List<Account> getListAccount() {
		return listAccount;
	}

	public void setListAccount(ArrayList<Account> listAccount) {
		this.listAccount = listAccount;
	}

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
