package ca.mcgill.ecse223.block.persistence;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PersistenceObjectStream {

	private static String filename = "output.txt";

	public static void serialize(Object object) {
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(filename);
			//System.out.println("1");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			//System.out.println("2");
			out.writeObject(object);
			//System.out.println("3");

			out.close();
			//System.out.println("4");

			fileOut.close();
			//System.out.println("5");

		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new RuntimeException("Could not save data to file '" + filename + "'.");
		}

	}

	public static Object deserialize() {
		Object o = null;
		ObjectInputStream in;
		try {
			FileInputStream fileIn = new FileInputStream(filename);
			in = new ObjectInputStream(fileIn);
			o = in.readObject();
			in.close();
			fileIn.close();
		} catch (Exception e) {
			o = null;
		}
		return o;
	}
	
	public static void setFilename(String newFilename) {
		filename = newFilename;
	}

}
