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

import it.polimi.tiw.projects.beans.User;
import it.polimi.tiw.projects.dao.AppelloDAO;
import it.polimi.tiw.projects.dao.StudentDAO;
import it.polimi.tiw.projects.utils.ConnectionHandler;


@WebServlet("/Pubblica")
public class Pubblica extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
       
    
    public Pubblica() {
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
		int o;
		try {
			appelloId = Integer.parseInt(request.getParameter("appelloid"));
			o = Integer.parseInt(request.getParameter("o"));
		} catch (NumberFormatException | NullPointerException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Incorrect param values");
			return;
		}
		
		AppelloDAO aDao = new AppelloDAO(connection);
		try {
			if(!aDao.checksProfessor(appelloId, u.getId())) {
				String path = getServletContext().getContextPath() + "/GoToHome";
				response.sendRedirect(path);
				return;
			}
		} catch (SQLException e) {
			response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Failure in worker's project database extraction");
		}
		
		StudentDAO sDao = new StudentDAO(connection);
		
		try {
			sDao.pubblica(appelloId);
		} catch (SQLException e) {
			response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Failure in worker's project database extraction");
		}
		
		response.sendRedirect(getServletContext().getContextPath() + "/GetIscritti?appelloid="+ String.valueOf(appelloId)+"&o="+String.valueOf(o));
	}
	
	public void destroy() {
		try {
			ConnectionHandler.closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
