package it.polimi.tiw.projects.beans;

public class Student {

	private int id;
	private String matricola;
	private String name;
	private String surname;
	private String email;
	private String cdl;
	private String grade;
	private ExamStatus state;


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

	public ExamStatus getState() {
		return state;
	}

	public void setState(int value) {
		this.state = ExamStatus.getExamStatusFromInt(value);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
