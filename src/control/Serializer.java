package control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Serializer {

	@SuppressWarnings("unchecked")
	public <L> List<L> deserializeList(String fileName, boolean canBeEmpty) {
		ArrayList<L> items = new ArrayList<>();
		Object o = deserializeFile(fileName, canBeEmpty);
		if (o != null) {
			items = (ArrayList<L>) o;
		}
		return items;
	}

	public void serializeObject(String fileName, Object s) {
		try {
			FileOutputStream fileOut = new FileOutputStream(fileName);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(s);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	public Object deserializeFile(String fileName, boolean canBeEmpty) {
		if (new File(fileName).isFile()) {
			try {
				FileInputStream fileIn = new FileInputStream(fileName);
				ObjectInputStream in = new ObjectInputStream(fileIn);
				Object object = in.readObject();
				in.close();
				fileIn.close();
				return object;
			} catch (FileNotFoundException e) {
				if (!canBeEmpty) {
					System.out.println("Couldn't find file " + fileName);
				}
			} catch (IOException i) {
				i.printStackTrace();
			} catch (ClassNotFoundException c) {
				c.printStackTrace();
			}
		}

		if (!canBeEmpty) {
			System.exit(0);
		}

		return null;
	}
}
