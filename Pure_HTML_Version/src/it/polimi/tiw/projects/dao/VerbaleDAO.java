package it.polimi.tiw.projects.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.Calendar;

import it.polimi.tiw.projects.beans.User;
import it.polimi.tiw.projects.beans.Verbale;

public class VerbaleDAO {
	private Connection con;

	public VerbaleDAO(Connection connection) {
		this.con = connection;
	}
	
	public Verbale createVerbale(int appelloId, User teacher) throws SQLException{
		Verbale verbale = new Verbale();
		
		verbale.setDate(new Date(Calendar.getInstance().getTime().getTime()));
		verbale.setTime(new Time(Calendar.getInstance().getTime().getTime()));
		
		verbale.setTeacherName(teacher.getName());
		verbale.setTeacherSurname(teacher.getSurname());
		
		StudentDAO sDao = new StudentDAO(con);
		
		String query = "SELECT * FROM appello A JOIN course C on A.idCorso = C.id WHERE A.id = ?";
		try (PreparedStatement pstatement = con.prepareStatement(query);) {
			pstatement.setInt(1, appelloId);
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					verbale.setAppelloDate(result.getDate("date"));
					verbale.setCourseName(result.getString("name"));
				}
			}
		}
	
		
		query = "INSERT into verbale (data, time) VALUES(?, ?)";
		con.setAutoCommit(false);
		try (PreparedStatement pstatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
			pstatement.setDate(1, verbale.getDate());
			pstatement.setTime(2, verbale.getTime());
			pstatement.executeUpdate();
			ResultSet rs = pstatement.getGeneratedKeys();
			if(rs.next()) {
				sDao.updateRefused(appelloId);
				verbale.setStudents(sDao.findVerbalized(appelloId));
				sDao.verbalizza(appelloId, rs.getInt(1));
			}
			con.commit();
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} finally {
			con.setAutoCommit(true);
		}
	
		
		
		return verbale;
	}
}
