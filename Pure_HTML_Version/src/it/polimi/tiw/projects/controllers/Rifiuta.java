package it.polimi.tiw.projects.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.polimi.tiw.projects.beans.Exam;
import it.polimi.tiw.projects.beans.User;
import it.polimi.tiw.projects.dao.ExamDAO;
import it.polimi.tiw.projects.utils.ConnectionHandler;


@WebServlet("/Rifiuta")
public class Rifiuta extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
       
    
    public Rifiuta() {
        super();
    }
    
    public void init() throws ServletException {
		try {
			ServletContext context = getServletContext();
			String driver = context.getInitParameter("dbDriver");
			String url = context.getInitParameter("dbUrl");
			String user = context.getInitParameter("dbUser");
			String password = context.getInitParameter("dbPassword");
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, password);

		} catch (ClassNotFoundException e) {
			throw new UnavailableException("Can't load database driver");
		} catch (SQLException e) {
			throw new UnavailableException("Couldn't get db connection");
		}
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("user");
		
		int appelloId;
		try {
			appelloId = Integer.parseInt(request.getParameter("appelloid"));
		} catch (NumberFormatException | NullPointerException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Incorrect param values");
			return;
		}
		
		ExamDAO eDao = new ExamDAO(connection);
		Exam esito = null;
		
		try {
			esito = eDao.findEsitoById(appelloId, u.getId());
		} catch (SQLException e) {
			response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Failure in worker's project database extraction");
		}
		
		if(esito.getState().getValue() == 2 && !esito.getGrade().equals("riprovato") && !esito.getGrade().equals("rimandato") && !esito.getGrade().equals("assente") && (esito.getGrade().equals("30L") || esito.getGradeAsInt() > 17 )) {
			
			try {
				eDao.rifiuta(appelloId, u.getId());
			} catch (SQLException e) {
				response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Failure in worker's project database extraction");
			}
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		response.sendRedirect(getServletContext().getContextPath() + "/GetInfo?appelloid="+ String.valueOf(appelloId));
	}
	
	public void destroy() {
		try {
			ConnectionHandler.closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
