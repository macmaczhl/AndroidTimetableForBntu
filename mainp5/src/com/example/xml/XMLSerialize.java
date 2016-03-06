package com.example.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class XMLSerialize {
	//static File file;
	static String filename = "raspisanie.xml";

	public static void write(DataManager dataManager, Context context)
			throws Exception {
		Serializer serializer = new Persister();
		// file = new File("/mnt/sdcard2/test2.xml");

		FileOutputStream fos = context.openFileOutput(filename,
				Context.MODE_WORLD_WRITEABLE);
		OutputStreamWriter os = new OutputStreamWriter(fos);
		serializer.write(dataManager, os);
		os.close();
		fos.flush();
		fos.close();
	}

	public static DataManager read(Context context) throws Exception {
		Serializer serializer = new Persister();
		// file = new File("/mnt/sdcard2/test2.xml");
		// LessonData lessonData = serializer.read(LessonData.class, file);

		FileInputStream fis = context.openFileInput(filename);
		InputStreamReader is = new InputStreamReader(fis);
		DataManager dataManager = serializer.read(DataManager.class, is);

		is.close();
		fis.close();

		return dataManager;
	}

	public static void write(DataManager dataManager, String fileName)
			throws Exception {
		//TODO œ≈–≈«¿œ»—‹
		File file = new File(Environment.getExternalStorageDirectory()
				.getPath() + "/Download/" + XMLSerialize.filename);
		if (file.exists()) {
			file.delete();
		}
		file.createNewFile();

		Serializer serializer = new Persister();
		serializer.write(dataManager, file);
		Log.d("azaza22", file.getPath());
	}

	public static DataManager read(Context context, String fileName)
			throws Exception {
		Serializer serializer = new Persister();
		File file = new File(Environment.getExternalStorageDirectory()
				.getPath() + "/Download/" + XMLSerialize.filename);
		if (!file.exists()) {
			return null;
		}
		DataManager dataManager = serializer.read(DataManager.class, file);
		return dataManager;
	}
}
