import java.util.List;


public class Runner {
	public static void main(String[] args) throws Exception
	{
		//MainParser test = new MainParser("D:/Temp/2_kurs_2015-2016g.xls");
		MainParser test = new MainParser("D:/Temp/4.xls");
		//MainParser test = new MainParser("D:/Temp/3.xls");
		/*String[] arr = test.getSheetNames();
		for (String string : arr) {
			System.out.println(string);
		}*/
		//List<CellLesson> list = test.getContentByGroup(7, "10701114");
		//List<CellLesson> list = test.getContentByGroup(5, "10504114"); 
		
		//List<CellLesson> list = test.getContentByGroup(0, "107212");
		List<CellLesson> list = test.getContentByGroup(0, "107412");
		//List<CellLesson> list = test.getContentByGroup(1, "10702313");
		
		//List<CellLesson> list = test.getContentByGroup(0, "10109113");
		
		for(CellLesson i : list)
		{
			//if (i.getTypeOfCell() == 0 && i.getLesson() != null)				
			//System.out.print("День " + i.getLesson().getTime().getDay() + "  ");
			if (!i.isSorted())
			{
				System.out.print("День " + i.getNotSortedLesson().getTime().getDay() + " Время " + i.getNotSortedLesson().getTime().getHour() + "." + i.getNotSortedLesson().getTime().getMinute());
				System.out.println();
			}
			else
			{
				switch (i.getTypeOfCell())
				{
				case 0:
					Lesson les0 = i.getLesson();
					System.out.print("День " + les0.getTime().getDay() + " Время " + les0.getTime().getHour() + "." + les0.getTime().getMinute() + " Тип " + i.getTypeOfCell());
					System.out.println(" subj= \"" + les0.getSubject() + "\"");
					System.out.println("Аудитория " + les0.getClassroom() + " Корпус " + les0.getHousing());
					System.out.println("----------------");
					break;
				case 1:
				case 2:
					Lesson les12[] = i.getDoubleLesson();
					for(Lesson les : les12)
					{
						if (les != null)
						{
							System.out.print("День " + les.getTime().getDay() + " Время " + les.getTime().getHour() + "." + les.getTime().getMinute() + " Тип " + i.getTypeOfCell());
							System.out.println(" subj= \"" + les.getSubject() + "\"");
							System.out.println("Аудитория " + les.getClassroom() + " Корпус " + les.getHousing());
							System.out.println("----------------");
						}
					}
					break;
				case 3:
				case 4:
				case 6:
				case 7:
					Lesson les3467[] = i.getTripleLesson();
					for(Lesson les : les3467)
					{
						if (les != null)
						{
							System.out.print("День " + les.getTime().getDay() + " Время " + les.getTime().getHour() + "." + les.getTime().getMinute() + " Тип " + i.getTypeOfCell());
							System.out.println(" subj= \"" + les.getSubject() + "\"");
							System.out.println("Аудитория " + les.getClassroom() + " Корпус " + les.getHousing());
							System.out.println("----------------");
						}
					}
					break;
				case 5:
					Lesson les5[] = i.getFourLesson();
					for(Lesson les : les5)
					{
						if (les != null)
						{
							System.out.print("День " + les.getTime().getDay() + " Время " + les.getTime().getHour() + "." + les.getTime().getMinute() + " Тип " + i.getTypeOfCell());
							System.out.println(" subj= \"" + les.getSubject() + "\"");
							System.out.println("Аудитория " + les.getClassroom() + " Корпус " + les.getHousing());
							System.out.println("----------------");
						}
					}
					break;
				default:
					break;
				}
			}				
		}
	}
}
