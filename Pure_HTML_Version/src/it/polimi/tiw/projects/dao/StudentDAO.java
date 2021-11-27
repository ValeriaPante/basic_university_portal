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
	
	public List<Student> findIscrittiById(int appelloId, int order) throws SQLException {
		List<Student> iscritti = new ArrayList<Student>();
		
		String query;
		switch(order) {
		case 0:
			query = "SELECT * from iscrizioniappello I JOIN student S ON I.idStud = S.id where I.id = ? ORDER BY matricola";
			break;
		case 1:
			query = "SELECT * from iscrizioniappello I JOIN student S ON I.idStud = S.id where I.id = ? ORDER BY matricola DESC";
			break;
		case 2:
			query = "SELECT * from iscrizioniappello I JOIN student S ON I.idStud = S.id where I.id = ? ORDER BY nome";
			break;
		case 3:
			query = "SELECT * from iscrizioniappello I JOIN student S ON I.idStud = S.id where I.id = ? ORDER BY nome DESC";
			break;
		case 4:
			query = "SELECT * from iscrizioniappello I JOIN student S ON I.idStud = S.id where I.id = ? ORDER BY cognome";
			break;
		case 5:
			query = "SELECT * from iscrizioniappello I JOIN student S ON I.idStud = S.id where I.id = ? ORDER BY cognome DESC";
			break;
		case 6:
			query = "SELECT * from iscrizioniappello I JOIN student S ON I.idStud = S.id where I.id = ? ORDER BY email";
			break;
		case 7:
			query = "SELECT * from iscrizioniappello I JOIN student S ON I.idStud = S.id where I.id = ? ORDER BY email DESC";
			break;
		case 8:
			query = "SELECT * from iscrizioniappello I JOIN student S ON I.idStud = S.id where I.id = ? ORDER BY corsoLaurea";
			break;
		case 9:
			query = "SELECT * from iscrizioniappello I JOIN student S ON I.idStud = S.id where I.id = ? ORDER BY corsoLaurea DESC";
			break;
		case 10:
			query = "SELECT * from iscrizioniappello I JOIN student S ON I.idStud = S.id where I.id = ? ORDER BY case when stato = 0 then 1"
																													+ " when voto = 'assente' then 2"
																													+ " when voto = 'rimandato' then 3"
																													+ " when voto = 'riprovato' then 4"
																													+ " when voto = '18' then 5"
																													+ " when voto = '19' then 6"
																													+ " when voto = '20' then 7"
																													+ " when voto = '21' then 8"
																													+ " when voto = '22' then 9"
																													+ " when voto = '23' then 10"
																													+ " when voto = '24' then 11"
																													+ " when voto = '25' then 12"
																													+ " when voto = '26' then 13"
																													+ " when voto = '27' then 14"
																													+ " when voto = '28' then 15"
																													+ " when voto = '29' then 16"
																													+ " when voto = '30' then 17"
																													+ " when voto = '30L' then 18"
																													+ " else 19"
																													+ " end asc";
			break;
		case 11:
			query = "SELECT * from iscrizioniappello I JOIN student S ON I.idStud = S.id where I.id = ? ORDER BY case when stato = 0 then 1"
																													+ " when voto = 'assente' then 2"
																													+ " when voto = 'rimandato' then 3"
																													+ " when voto = 'riprovato' then 4"
																													+ " when voto = '18' then 5"
																													+ " when voto = '19' then 6"
																													+ " when voto = '20' then 7"
																													+ " when voto = '21' then 8"
																													+ " when voto = '22' then 9"
																													+ " when voto = '23' then 10"
																													+ " when voto = '24' then 11"
																													+ " when voto = '25' then 12"
																													+ " when voto = '26' then 13"
																													+ " when voto = '27' then 14"
																													+ " when voto = '28' then 15"
																													+ " when voto = '29' then 16"
																													+ " when voto = '30' then 17"
																													+ " when voto = '30L' then 18"
																													+ " else 19"
																													+ " end desc";
			break;
		case 12:
			query = "SELECT * from iscrizioniappello I JOIN student S ON I.idStud = S.id where I.id = ? ORDER BY stato";
			break;
		case 13:
			query = "SELECT * from iscrizioniappello I JOIN student S ON I.idStud = S.id where I.id = ? ORDER BY stato DESC";
			break;
		default:
			query = "SELECT * from iscrizioniappello I JOIN student S ON I.idStud = S.id where I.id = ? ORDER BY matricola";
			break;
		}
		
		
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
