package web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Course;

import java.io.IOException;
import java.sql.SQLException;

import Dao.CourseDao;

/**
 * Servlet implementation class CourseServlet
 */
public class CourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	CourseDao dao;

	public CourseServlet() throws ClassNotFoundException, SQLException {
		super();
		// TODO Auto-generated constructor stub

		dao = new CourseDao();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String action = request.getParameter("action") != null ? request.getParameter("action") : "default";
		String forward ="";
		switch (action) {
		case "delete": {
			int courseId = Integer.parseInt(request.getParameter("courseId"));
			dao.deleteCourse(courseId);
			RequestDispatcher view = request.getRequestDispatcher("view.jsp");
			request.setAttribute("courses", dao.getAllCourses());
			view.forward(request, response);
			break;
		}
		case "update": {
			int courseId = Integer.parseInt(request.getParameter("courseId"));
			RequestDispatcher add = request.getRequestDispatcher("add.jsp");
			request.setAttribute("course", dao.getCourseById(courseId));
			add.forward(request, response);
			break;
		}
		default: {
			RequestDispatcher view = request.getRequestDispatcher("view.jsp");
			request.setAttribute("courses", dao.getAllCourses());
			view.forward(request, response);
		}
		


		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(request, response);
		Course course = new Course();
		course.setCourseName(request.getParameter("courseName"));
		course.setDuration(Integer.parseInt(request.getParameter("duration")));
		course.setFees(Integer.parseInt(request.getParameter("fees")));
		course.setCategory(request.getParameter("category"));
		
		String courseId = request.getParameter("courseId");
		if(courseId==null || courseId.isEmpty()) {
			dao.addCourse(course);
		}else {
			course.setCourseId(Integer.parseInt(courseId));
			dao.updateCourse(course);
		}
		RequestDispatcher view = request.getRequestDispatcher("view.jsp");
		request.setAttribute("courses", dao.getAllCourses());
		view.forward(request, response);
	}

}
