package it.polimi.tiw.projects.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.polimi.tiw.projects.beans.Exam;

public class ExamDAO {

	private Connection con;
	
	public ExamDAO(Connection connection) {
		this.con = connection;
	}
	
	public Exam findEsitoById(int appelloId, int studentId) throws SQLException {
		Exam exam = null;
		
		String query = "SELECT matricola, S.nome as sname, S.cognome as ssurname, email, corsoLaurea, voto, stato, C.name as cname, U.name as tname, U.surname as tsurname, date "
				+ "from iscrizioniappello I JOIN student S ON I.idStud = S.id JOIN appello A ON I.id = A.id JOIN course C ON A.idCorso = C.id JOIN user U ON C.teacherId = U.id where I.id= ? and I.idStud = ?";
		try (PreparedStatement pstatement = con.prepareStatement(query);) {
			pstatement.setInt(1, appelloId);
			pstatement.setInt(2, studentId);
			try (ResultSet result = pstatement.executeQuery();) {
				while(result.next()) {
					exam = new Exam();
					exam.setMatricola(result.getString("matricola"));
					exam.setName(result.getString("sname"));
					exam.setSurname(result.getString("ssurname"));
					exam.setEmail(result.getString("email"));
					exam.setCdl(result.getString("corsoLaurea"));
					exam.setGrade(result.getString("voto"));
					exam.setState(result.getInt("stato"));
					exam.setCourseName(result.getString("cname"));
					exam.setTeacherName(result.getString("tname"));
					exam.setTeacherSurname(result.getString("tsurname"));
					exam.setDate(result.getDate("date"));
				}
			}
		}
		return exam;
	}
	
	public void rifiuta(int appelloId, int studId) throws SQLException {
		String query = "UPDATE iscrizioniappello SET stato = 3 WHERE id = ? and idStud = ?";
		try (PreparedStatement pstatement = con.prepareStatement(query);) {
			pstatement.setInt(1, appelloId);
			pstatement.setInt(2, studId);
			pstatement.executeUpdate();
		}
	}
	
	
}
