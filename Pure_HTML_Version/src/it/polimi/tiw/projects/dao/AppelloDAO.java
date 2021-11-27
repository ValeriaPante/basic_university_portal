package it.polimi.tiw.projects.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polimi.tiw.projects.beans.Appello;

public class AppelloDAO {

	private Connection con;
	
	public AppelloDAO(Connection connection) {
		this.con = connection;
	}
	
	public List<Appello> findAppelliById(int courseId) throws SQLException {
		List<Appello> appelli = new ArrayList<Appello>();

		String query = "SELECT * from appello where idCorso = ? ORDER BY date DESC";
		try (PreparedStatement pstatement = con.prepareStatement(query);) {
			pstatement.setInt(1, courseId);
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					Appello appello = new Appello();
					appello.setId(result.getInt("id"));
					appello.setDate(result.getDate("date"));
					appelli.add(appello);
				}
			}
		}
		return appelli;
	}
	
	public List<Appello> findAppelliByStudId(int courseId, int studId) throws SQLException {
		List<Appello> appelli = new ArrayList<Appello>();

		String query = "SELECT * from appello as A JOIN iscrizioniappello as I on A.id = I.id where A.idCorso = ? and I.idStud = ? ORDER BY date DESC";
		try (PreparedStatement pstatement = con.prepareStatement(query);) {
			pstatement.setInt(1, courseId);
			pstatement.setInt(2, studId);
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					Appello appello = new Appello();
					appello.setId(result.getInt("id"));
					appello.setDate(result.getDate("date"));
					appelli.add(appello);
				}
			}
		}
		return appelli;
	}
	
	public boolean checksProfessor(int appelloid, int teacherId) throws SQLException{
		
		boolean exit = false;
		
		String query = "SELECT * from appello as A JOIN course as C on A.idCorso = C.id where A.id = ?";
		try (PreparedStatement pstatement = con.prepareStatement(query);) {
			pstatement.setInt(1, appelloid);
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					if(result.getInt("teacherId") == teacherId) exit = true;
				}
			}
		}
		
		return exit;
		
	}
	
}
