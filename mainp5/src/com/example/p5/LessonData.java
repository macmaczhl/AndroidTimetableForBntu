package com.example.p5;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "lessons")
public class LessonData {
	@ElementList(inline = true, name = "lesson")
	public List<Lesson> list = new ArrayList<Lesson>();

	public LessonData() {

	}
	
	public void clear() {
		list.clear();
	}
}
