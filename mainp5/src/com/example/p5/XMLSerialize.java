package com.example.p5;

import java.io.File;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class XMLSerialize {
	static File file;

	public static void write(LessonData lessonData) throws Exception {
		Serializer serializer = new Persister();
		file = new File("/mnt/sdcard2/test2.xml");
		serializer.write(lessonData, file);
	}

	public static LessonData read() throws Exception {
		Serializer serializer = new Persister();
		file = new File("/mnt/sdcard2/test2.xml");
		LessonData lessonData = serializer.read(LessonData.class, file);
		return lessonData;
	}
}
