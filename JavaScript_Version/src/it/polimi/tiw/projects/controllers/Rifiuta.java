package it.polimi.tiw.projects.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.polimi.tiw.projects.beans.Exam;
import it.polimi.tiw.projects.beans.User;
import it.polimi.tiw.projects.dao.ExamDAO;
import it.polimi.tiw.projects.utils.ConnectionHandler;


@WebServlet("/Rifiuta")
@MultipartConfig
public class Rifiuta extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
       
    
    public Rifiuta() {
        super();
    }
    
    public void init() throws ServletException {
    	connection = ConnectionHandler.getConnection(getServletContext());
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User u = null;
		HttpSession s = request.getSession();
		u = (User) s.getAttribute("user");
		
		Integer appelloId = null;
		try {
			appelloId = Integer.parseInt(request.getParameter("appelloid"));
		} catch (NumberFormatException | NullPointerException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("Incorrect param values");
			return;
		}

		
		ExamDAO eDao = new ExamDAO(connection);
		Exam esito = null;
		
		try {
			esito = eDao.findEsitoById(appelloId, u.getId());
		} catch (SQLException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
		if(esito.getState().getValue() == 2 && !esito.getGrade().equals("riprovato") && !esito.getGrade().equals("rimandato") && !esito.getGrade().equals("assente") && (esito.getGrade().equals("30L") || esito.getGradeAsInt() > 17 )) {
			
			try {
				eDao.rifiuta(appelloId, u.getId());
			} catch (SQLException e) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			}
		} else {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		
	}
	
	public void destroy() {
		try {
			ConnectionHandler.closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
