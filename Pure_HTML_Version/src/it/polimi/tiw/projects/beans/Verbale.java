package it.polimi.tiw.projects.beans;

import java.sql.Date;
import java.sql.Time;

import java.util.List;

public class Verbale {
	
	private Date date;
	private Time time;
	private String courseName;
	private String teacherName;
	private String teacherSurname;
	private Date appelloDate;
	private List<Student> students;
	

	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Time getTime() {
		return time;
	}
	public void setTime(Time time) {
		this.time = time;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getTeacherSurname() {
		return teacherSurname;
	}
	public void setTeacherSurname(String teacherSurname) {
		this.teacherSurname = teacherSurname;
	}
	public Date getAppelloDate() {
		return appelloDate;
	}
	public void setAppelloDate(Date appelloDate) {
		this.appelloDate = appelloDate;
	}
	public List<Student> getStudents() {
		return students;
	}
	public void setStudents(List<Student> students) {
		this.students = students;
	}
	
}
