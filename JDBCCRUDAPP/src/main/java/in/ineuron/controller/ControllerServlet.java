package in.ineuron.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.ineuron.dto.Student;
import in.ineuron.service.IStudentService;
import in.ineuron.servicefactory.StudentServiceFactory;

/**
 * Servlet implementation class ControllerServlet
 */
@WebServlet("/controller/*")//EXTENSION URL PATTERN
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doProcess(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		doProcess(request,response);
	}
	
	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		IStudentService stdService = StudentServiceFactory.getStudentService();
		
		System.out.println("Request URI ::"+request.getRequestURI());
		System.out.println("Path Info ::"+ request.getPathInfo());
		
		if(request.getRequestURI().endsWith("addform")) {
			
			String sage = request.getParameter("sage");
			String sname = request.getParameter("sname");
			String saddr = request.getParameter("saddr");
		
		Student student = new Student();
		student.setSname(sname);
		student.setSage(Integer.parseInt(sage));
		student.setSaddress(saddr);
		
		String status = stdService.addStudent(student);
		PrintWriter out = response.getWriter();
		if (status.equals("success")) {
			out.println("<h1 style='color:green; text-align:center'>REGISTRATION SUCCESSFUL</h1>");
		} else {
			out.println("<h1 style='color:red; text-align:center'>REGISTRATION FAILED</h1>");
		}
		out.close();
		}
		
		if(request.getRequestURI().endsWith("searchform")) {
			String sid = request.getParameter("sid");
			
			Student student = stdService.searchStudent(Integer.parseInt(sid));
			PrintWriter out = response.getWriter();
			if(student!= null) {
				out.println("<body>");
				out.println("<center>");
				out.println("<table border='1'>");
				out.println("<tr><th>ID</th><td>"+student.getSid()+"</td></tr>");
				out.println("<tr><th>NAME</th><td>"+student.getSname()+"</td></tr>");
				out.println("<tr><th>AGE</th><td>"+student.getSage()+"</td></tr>");
				out.println("<tr><th>ADDRESS</th><td>"+student.getSaddress()+"</td></tr>");
				out.println("</table>");
				out.println("</center>");
				out.println("</body>");
				
			}else {
				out.println("<h1 style='color:red;text-align:center;'>Record not available for given record "+sid+"</h1>");
				}
			out.close();
			
		}
		
		if(request.getRequestURI().endsWith("deleteform")) {
			String sid = request.getParameter("sid");
			
			String status = stdService.deleteStudent(Integer.parseInt(sid));
			PrintWriter out = response.getWriter();
			
			if (status.equals("success")) {
				out.println("<body>");
				out.println("<h1 style='color:green;text-align:center;'>Record deleted successfully</h1>");
				out.println("<body>");
			} else if (status.equals("failure")){
				out.println("<body>");
				out.println("<h1 style='color:red;text-align:center;'>Record deletion failed</h1>");
				out.println("<body>");
			}else {
				out.println("<body>");
				out.println("<h1 style='color:green;text-align:center;'>Record not found for given id "+sid+"</h1>");
				out.println("<body>");
			}
			out.close();
		}
		
		if(request.getRequestURI().endsWith("editform")) {
			String sid = request.getParameter("sid");
			
			Student student = stdService.searchStudent(Integer.parseInt(sid));
			PrintWriter out = response.getWriter();
			if(student!= null) {
				//display student records as a form data so it is editable
				out.println("<body>");
				out.println("<center>");
				out.println("<form method='post' action ='./controller/updateRecord'>");
				out.println("<table>");
				out.println("<tr><th>ID</th><td>"+student.getSid()+"</td></tr>");
				out.println("<input type='hidden' name='sid' value='"+student.getSid()+"'/>");
				out.println("<tr><th>NAME</th><td><input type='text' name='sname' value='"+student.getSname()+"'/></td></tr>");
				out.println("<tr><th>AGE</th><td><input type='text' name ='sage' value='"+student.getSage()+"'/></td></tr>");
				out.println("<tr><th>ADDRESS</th><td><input type='text' name='saddr' value='"+student.getSaddress()+"'</td></tr>");
				out.println("<tr><td></td><td><input type='submit' value='update'/></td></tr>");
				out.println("</table>");
				out.println("</form>");
				out.println("</center>");
				out.println("</body>");
			}else {
				//display not found message
				out.println("<body>");
				out.println("<h1 style='color:red;text-align:center;'>Record not available for the given id ::"+sid+"</h1>");
				out.println("</body>");
			}
			out.close();
		
		}
		if(request.getRequestURI().endsWith("updateRecord")) {
			String sid = request.getParameter("sid");
			String sname = request.getParameter("sname");
			String sage = request.getParameter("sage");
			String saddr = request.getParameter("saddr");
			
			System.out.println(sid);
			System.out.println(sname);
			System.out.println(sage);
			System.out.println(saddr);
			
			Student student = new Student();
			student.setSid(Integer.parseInt(sid));
			student.setSname(sname);
			student.setSage(Integer.parseInt(sage));
			student.setSaddress(saddr);
			
			
			String status =stdService.updateStudent(student);
			PrintWriter out = response.getWriter();
			if(status == "success" ) {
				out.println("<h1 style='color:green;text-align:center;'>STUDENT UPDATED SUCCESSFULLY</h1>");
			}else {
				out.println("<h1 style='color:green;text-align:center;'>STUDENT RECORD UPDATION FAILED</h1>");
			}
		}
	}
	
	

}
