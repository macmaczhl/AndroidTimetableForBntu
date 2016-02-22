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

public class XMLSerialize {
	static File file;
	static String filename = "raspisanie.xml";

	public static void write(LessonData lessonData, Context context)
			throws Exception {
		Serializer serializer = new Persister();
		// file = new File("/mnt/sdcard2/test2.xml");

		FileOutputStream fos = context.openFileOutput(filename,
				Context.MODE_WORLD_WRITEABLE);
		OutputStreamWriter os = new OutputStreamWriter(fos);
		serializer.write(lessonData, os);
		os.close();
		fos.flush();
		fos.close();
	}

	public static LessonData read(Context context) throws Exception {
		Serializer serializer = new Persister();
		// file = new File("/mnt/sdcard2/test2.xml");
		// LessonData lessonData = serializer.read(LessonData.class, file);

		FileInputStream fis = context.openFileInput(filename);
		InputStreamReader is = new InputStreamReader(fis);
		LessonData lessonData = serializer.read(LessonData.class, is);

		is.close();
		fis.close();

		return lessonData;
	}

	public static void write(LessonData lessonData, String fileName)
			throws Exception {
		File file = new File(Environment.getExternalStorageDirectory()
				.getPath() + "/Download/" + XMLSerialize.filename);
		if (file.exists()) {
			file.delete();
		}
		file.createNewFile();

		Serializer serializer = new Persister();
		serializer.write(lessonData, file);
	}

	public static LessonData read(Context context, String fileName)
			throws Exception {
		Serializer serializer = new Persister();
		File file = new File(Environment.getExternalStorageDirectory()
				.getPath() + "/Download/" + XMLSerialize.filename);
		if (!file.exists()) {
			return null;
		}
		LessonData lessonData = serializer.read(LessonData.class, file);
		return lessonData;
	}
}
