package in.ineuron.servicefactory;

import in.ineuron.service.IStudentService;
import in.ineuron.service.StudentServiceImpl;
//Connection connection = DriverManaget.getConnection(url,username, password);
public class StudentServiceFactory {
	
//	constructor is private here no object creation
	private StudentServiceFactory() {
		
	}
	
	private static IStudentService studentService = null;
	
	public static IStudentService getStudentService() {
		
		//SINGLETON PATTERN CODE
		if(studentService == null) {
			studentService =  new StudentServiceImpl();
		}
		return studentService;
	}
}
