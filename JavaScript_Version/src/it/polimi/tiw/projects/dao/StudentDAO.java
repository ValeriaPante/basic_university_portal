package it.polimi.tiw.projects.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polimi.tiw.projects.beans.Student;

public class StudentDAO {

private Connection con;
	
	public StudentDAO(Connection connection) {
		this.con = connection;
	}
	
	public List<Student> findIscrittiById(int appelloId) throws SQLException {
		List<Student> iscritti = new ArrayList<Student>();
		
		
		String query = "SELECT * from iscrizioniappello I JOIN student S ON I.idStud = S.id where I.id = ? ORDER BY matricola";
			
		
		try (PreparedStatement pstatement = con.prepareStatement(query);) {
			pstatement.setInt(1, appelloId);
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					Student iscritto = new Student();
					iscritto.setId(result.getInt("idStud"));
					iscritto.setMatricola(result.getString("matricola"));
					iscritto.setName(result.getString("nome"));
					iscritto.setSurname(result.getString("cognome"));
					iscritto.setEmail(result.getString("email"));
					iscritto.setCdl(result.getString("corsoLaurea"));
					iscritto.setGrade(result.getString("voto"));
					iscritto.setState(result.getInt("stato"));
					iscritto.setAppelloid(appelloId);
					iscritti.add(iscritto);
				}
			}
		}
		return iscritti;
	}
	
	public List<Student> findVerbalized(int appelloId) throws SQLException {
		List<Student> verbalizzati = new ArrayList<Student>();
		
		String query = "SELECT * from iscrizioniappello I JOIN student S ON I.idStud = S.id where I.id = ? and (I.stato = 2 or I.stato = 3) ORDER BY matricola";
		
		
		try (PreparedStatement pstatement = con.prepareStatement(query);) {
			pstatement.setInt(1, appelloId);
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					Student iscritto = new Student();
					iscritto.setId(result.getInt("idStud"));
					iscritto.setMatricola(result.getString("matricola"));
					iscritto.setName(result.getString("nome"));
					iscritto.setSurname(result.getString("cognome"));
					iscritto.setEmail(result.getString("email"));
					iscritto.setCdl(result.getString("corsoLaurea"));
					iscritto.setGrade(result.getString("voto"));
					iscritto.setState(result.getInt("stato"));
					verbalizzati.add(iscritto);
				}
			}
		}
		return verbalizzati;
	}
	
	public Student findIscritto(int appelloId, int studId) throws SQLException{
		Student iscritto = new Student();
		String query = "SELECT * from iscrizioniappello I JOIN student S ON I.idStud = S.id where I.id = ? and I.idStud = ?";
		try (PreparedStatement pstatement = con.prepareStatement(query);) {
			pstatement.setInt(1, appelloId);
			pstatement.setInt(2, studId);
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					if(result.getInt("stato") > 1) return null;
					iscritto.setId(result.getInt("idStud"));
					iscritto.setMatricola(result.getString("matricola"));
					iscritto.setName(result.getString("nome"));
					iscritto.setSurname(result.getString("cognome"));
					iscritto.setEmail(result.getString("email"));
					iscritto.setCdl(result.getString("corsoLaurea"));
					iscritto.setGrade(result.getString("voto"));
					iscritto.setState(result.getInt("stato"));		
					iscritto.setAppelloid(appelloId);
				}
			}
		}
		return iscritto;
	}
	
public boolean checksEditableGrade(int appelloid, int studId) throws SQLException{
		
		boolean exit = false;
		
		String query = "SELECT * from iscrizioniappello where id = ? and idStud = ?";
		try (PreparedStatement pstatement = con.prepareStatement(query);) {
			pstatement.setInt(1, appelloid);
			pstatement.setInt(2, studId);
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					if(result.getInt("stato") == 1 || result.getInt("stato") == 0) exit = true;
				}
			}
		}
		
		return exit;
		
	}
	
	public void pubblica(int appelloId) throws SQLException {
		String query = "UPDATE iscrizioniappello SET stato = 2 WHERE id = ? and stato = 1";
		try (PreparedStatement pstatement = con.prepareStatement(query);) {
			pstatement.setInt(1, appelloId);
			pstatement.executeUpdate();
		}
	}
	
	public void editGrade(int appelloId, int studId, String grade) throws SQLException {
		String query = "UPDATE iscrizioniappello SET stato = 1, voto = ? WHERE id = ? and idStud = ?";
		try (PreparedStatement pstatement = con.prepareStatement(query);) { 
			pstatement.setString(1, grade);
			pstatement.setInt(2, appelloId);
			pstatement.setInt(3, studId);
			pstatement.executeUpdate();
		}
	}
	
	public void updateRefused(int appelloId) throws SQLException{
		String query = "UPDATE iscrizioniappello SET voto = 'rimandato' WHERE id = ? and stato = 3";
		try (PreparedStatement pstatement = con.prepareStatement(query);) { 
			pstatement.setInt(1, appelloId);
			pstatement.executeUpdate();
		}
	}
	
	public void verbalizza(int appelloId, int verbaleid) throws SQLException {
		
		String query = "UPDATE iscrizioniappello SET stato = 4, idverbale = ? WHERE id = ? and (stato = 2 or stato = 3)";
		try (PreparedStatement pstatement = con.prepareStatement(query);) { 
			pstatement.setInt(1, verbaleid);
			pstatement.setInt(2, appelloId);
			pstatement.executeUpdate();
		}
	}
	
}
