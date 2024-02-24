package Dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import db.DbConnect;
import model.Course;

public class CourseDao {
	Connection connection;

	public CourseDao() throws ClassNotFoundException, SQLException {
		connection = DbConnect.getConnection();
	}

	public void addCourse(Course course) {
		try {
			PreparedStatement pst = connection
					.prepareStatement("insert into course(courseName, fees, category, duration) values(?,?,?,?)");
			pst.setString(1, course.getCourseName());
			pst.setInt(2, course.getFees());
			pst.setString(3, course.getCategory());
			pst.setInt(4, course.getDuration());

			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Course> getAllCourses() {
		List<Course> courses = new ArrayList<Course>();
		try {
			java.sql.Statement statement = connection.createStatement();

			ResultSet rs = statement.executeQuery("select * from course");

			while (rs.next()) {
				Course course = new Course();

				course.setCategory(rs.getString("category"));
				course.setCourseId(rs.getInt("courseId"));
				course.setFees(rs.getInt("fees"));
				course.setDuration(rs.getInt("fees"));
				course.setCourseName(rs.getString("courseName"));

				courses.add(course);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courses;
	}

	public void deleteCourse(int courseId) {
		try {
			PreparedStatement pst = connection.prepareStatement("DELETE FROM course WHERE courseId = ?");
			pst.setInt(1, courseId);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public Course getCourseById(int courseId) {
		Course course = new Course();
		try {
			PreparedStatement pst = connection.prepareStatement("SELECT * FROM course WHERE courseId = ?");
			pst.setInt(1, courseId);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				course.setCategory(rs.getString("category"));
				course.setCourseId(rs.getInt("courseId"));
				course.setFees(rs.getInt("fees"));
				course.setDuration(rs.getInt("fees"));
				course.setCourseName(rs.getString("courseName"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return course;
	}

	public void updateCourse(Course course) {

		try {
			PreparedStatement pst = connection.prepareStatement(
					"UPDATE course SET courseName=?, category=?, fees=?, duration=?  WHERE courseId = ?");
			pst.setString(1, course.getCourseName());
			pst.setInt(2, course.getFees());
			pst.setString(3, course.getCategory());
			pst.setInt(4, course.getDuration());
			pst.setInt(5, course.getCourseId());

			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
