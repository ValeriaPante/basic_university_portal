package it.polimi.tiw.projects.beans;

import java.sql.Date;

public class Exam {
	private String matricola;
	private String name;
	private String surname;
	private String email;
	private String cdl;
	private String grade;
	private ExamStatus state;
	private String courseName;
	private String teacherName;
	private String teacherSurname;
	private Date date;
	
	private int appelloid;
	private boolean rifiutabile;


	public String getMatricola() {
		return matricola;
	}

	public void setMatricola(String matricola) {
		this.matricola = matricola;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getCdl() {
		return cdl;
	}
	
	public void setCdl(String cdl) {
		this.cdl = cdl;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	public int getGradeAsInt(){
		return Integer.parseInt(this.grade);
	}

	public ExamStatus getState() {
		return state;
	}

	public void setState(int value) {
		this.state = ExamStatus.getExamStatusFromInt(value);
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
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

	public int getAppelloid() {
		return appelloid;
	}

	public void setAppelloid(int appelloid) {
		this.appelloid = appelloid;
	}

	public boolean isRifiutabile() {
		return rifiutabile;
	}

	public void setRifiutabile(boolean rifiutabile) {
		this.rifiutabile = rifiutabile;
	}
}
