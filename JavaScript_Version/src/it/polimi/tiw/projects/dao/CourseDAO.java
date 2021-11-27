package it.polimi.tiw.projects.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polimi.tiw.projects.beans.Course;

public class CourseDAO {
	private Connection con;

	public CourseDAO(Connection connection) {
		this.con = connection;
	}
	
	public List<Course> findCoursesByTeacherId(int userId) throws SQLException {
		List<Course> courses = new ArrayList<Course>();

		String query = "SELECT * from course where teacherId = ? ORDER BY name DESC";
		try (PreparedStatement pstatement = con.prepareStatement(query);) {
			pstatement.setInt(1, userId);
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					Course course = new Course();
					course.setId(result.getInt("id"));
					course.setName(result.getString("name"));
					course.setTeacherId(result.getInt("teacherId"));
					courses.add(course);
				}
			}
		}
		return courses;
	}
	
	public List<Course> findCoursesByStudId(int userId) throws SQLException {
		List<Course> courses = new ArrayList<Course>();

		String query = "SELECT * from iscrizionicorso as I JOIN course as C ON C.id = I.idCorso where idStud = ? ORDER BY name DESC";
		try (PreparedStatement pstatement = con.prepareStatement(query);) {
			pstatement.setInt(1, userId);
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					Course course = new Course();
					course.setId(result.getInt("id"));
					course.setName(result.getString("name"));
					course.setTeacherId(result.getInt("teacherId"));
					courses.add(course);
				}
			}
		}
		return courses;
	}
	
public boolean checksProfessor(int courseid, int teacherId) throws SQLException{
		
		boolean exit = false;
		
		String query = "SELECT * from course WHERE id = ?";
		try (PreparedStatement pstatement = con.prepareStatement(query);) {
			pstatement.setInt(1, courseid);
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					if(result.getInt("teacherId") == teacherId) exit = true;
				}
			}
		}
		
		return exit;
		
	}
	
	public boolean checksStudent(int courseid, int studId) throws SQLException{
		
		boolean exit = false;
		
		String query = "SELECT * from iscrizionicorso WHERE idcORSO = ?";
		try (PreparedStatement pstatement = con.prepareStatement(query);) {
			pstatement.setInt(1, courseid);
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					if(result.getInt("idStud") == studId) exit = true;
				}
			}
		}
		
		return exit;
		
	}
}
