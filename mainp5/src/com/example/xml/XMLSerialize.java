package com.example.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.content.Context;

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
}
