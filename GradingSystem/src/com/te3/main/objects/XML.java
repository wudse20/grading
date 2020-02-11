package com.te3.main.objects;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * XML de-/encoder
 * 
 * @author Anton Skorup
 * @param <E> The data type of the object, that is to be saved.
 */
public class XML<E> {

	public XML() {}

	/**
	 * Writes an XML-file of object
	 * 
	 * @param fileLocation The path to the file
	 * @param obj the object
	 */
	public void write(String fileLocation, E obj) {
		try {
			FileOutputStream fos = new FileOutputStream(new File(fileLocation));
			XMLEncoder encoder = new XMLEncoder(fos);

			encoder.writeObject(obj);
			encoder.close();
			fos.close();
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * Reads the XML-file from an XML-File.
	 * 
	 * @param fileLocation the file path
	 * @return the object from the XML File
	 */
	@SuppressWarnings("unchecked")
	public E read(String fileLocation) {
		Object o = null;
		try {
			FileInputStream fis = new FileInputStream(new File(fileLocation));
			XMLDecoder encoder = new XMLDecoder(fis);

			o = (E) encoder.readObject();
			fis.close();
			encoder.close();

			return (E) o;
		} catch (IOException e) {
			System.out.println(e.toString());
			return (E) o;
		}
	}
}
