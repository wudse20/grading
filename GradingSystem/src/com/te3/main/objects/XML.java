package com.te3.main.objects;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class XML {

	public XML() {}

	@SuppressWarnings("unused")
	private void write(String fileLocation, ArrayList<Data> al) {
		try {
			FileOutputStream fos = new FileOutputStream(new File(fileLocation));
			XMLEncoder encoder = new XMLEncoder(fos);

			encoder.writeObject(al);
			encoder.close();
			fos.close();
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<Data> read(String fileLocation) {
		ArrayList<Data> al = new ArrayList<Data>();

		try {
			FileInputStream fis = new FileInputStream(new File(fileLocation));
			XMLDecoder encoder = new XMLDecoder(fis);

			al = (ArrayList<Data>) encoder.readObject();
			fis.close();
			encoder.close();

			return al;
		} catch (IOException e) {
			System.out.println(e.toString());
			return al;
		}
	}
}
