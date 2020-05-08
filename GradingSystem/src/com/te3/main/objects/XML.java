package com.te3.main.objects;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalTime;

/**
 * XML de-/encoder
 *
 * @param <E> The data type of the object, that is to be saved.
 */
public class XML<E> {

	/**
	 * Constructor
	 */
	public XML() {
	}

	/**
	 * Writes an XML-file of object
	 * 
	 * @param fileLocation The path to the file
	 * @param obj          the object
	 */
	public void write(String fileLocation, E obj) {
		try {
			// Opens a FileOutputStream
			FileOutputStream fos = new FileOutputStream(new File(fileLocation));

			// Ties a XMLEncoder to the FileOutputStream
			XMLEncoder encoder = new XMLEncoder(fos);

			// Writes an object as XML
			encoder.writeObject(obj);

			// Closes the encoder and FileOutputStream
			encoder.close();
			fos.close();

			// Prints debug message to the console
			System.out.println("["
					+ ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour() : LocalTime.now().getHour())
					+ ":"
					+ ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute()
							: LocalTime.now().getMinute())
					+ ":" + ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond()
							: LocalTime.now().getSecond())
					+ "] XML has successfully wrote the data.");
		} catch (IOException e) {
			// Print the error message to the console
			System.out.println("["
					+ ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour() : LocalTime.now().getHour())
					+ ":"
					+ ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute()
							: LocalTime.now().getMinute())
					+ ":"
					+ ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond()
							: LocalTime.now().getSecond())
					+ "] XML threw exception with message: " + e.getLocalizedMessage());
		}
	}

	/**
	 * Reads the XML-file from an XML-File.
	 * 
	 * @param fileLocation the file path
	 * @return the object from the XML File, if it fails to read then it will return
	 *         {@code null}.
	 */
	@SuppressWarnings("unchecked")
	public E read(String fileLocation) {
		// Creates an empty object of the generic type.
		E e = null;
		try {
			// Opens a FileInputStream
			FileInputStream fis = new FileInputStream(new File(fileLocation));

			// Ties a XMLDecoder to that FileInputStream
			XMLDecoder decoder = new XMLDecoder(fis);

			// Stores the result of the XMLDecoder to the object
			e = (E) decoder.readObject();

			// Closes the FileInputStream and the decoder.
			fis.close();
			decoder.close();

			// Prints debug message to the console.
			System.out.println("["
					+ ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour() : LocalTime.now().getHour())
					+ ":"
					+ ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute()
							: LocalTime.now().getMinute())
					+ ":" + ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond()
							: LocalTime.now().getSecond())
					+ "] XML loaded the data");

			// Returns the object
			return e;
		} catch (IOException ex) {
			// Prints a debug message to the console, with the message
			System.out.println("["
					+ ((LocalTime.now().getHour() < 10) ? "0" + LocalTime.now().getHour() : LocalTime.now().getHour())
					+ ":"
					+ ((LocalTime.now().getMinute() < 10) ? "0" + LocalTime.now().getMinute()
							: LocalTime.now().getMinute())
					+ ":"
					+ ((LocalTime.now().getSecond() < 10) ? "0" + LocalTime.now().getSecond()
							: LocalTime.now().getSecond())
					+ "] XML threw exception with message: " + ex.getLocalizedMessage());

			// Returns the object(null)
			return e;
		}
	}
}
