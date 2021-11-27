package it.polimi.tiw.projects.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.polimi.tiw.projects.beans.Student;
import it.polimi.tiw.projects.beans.User;
import it.polimi.tiw.projects.dao.AppelloDAO;
import it.polimi.tiw.projects.dao.StudentDAO;
import it.polimi.tiw.projects.utils.ConnectionHandler;


@WebServlet("/Modifica")
@MultipartConfig
public class Modifica extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
       
    public Modifica() {
        super();
    }

    public void init() throws ServletException {
    	connection = ConnectionHandler.getConnection(getServletContext());
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User u = null;
		HttpSession s = request.getSession();
		u = (User) s.getAttribute("user");
		
		int appelloId;
		int studId;
		try {
			appelloId = Integer.parseInt(request.getParameter("appelloid"));
			studId = Integer.parseInt(request.getParameter("studId"));
		} catch (NumberFormatException | NullPointerException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
		AppelloDAO aDao = new AppelloDAO(connection);
		try {
			if(!aDao.checksProfessor(appelloId, u.getId())) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
		} catch (SQLException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
		StudentDAO sDao = new StudentDAO(connection);
		Student iscritto = null;
		try {
			iscritto = sDao.findIscritto(appelloId, studId);
			if(iscritto == null || !sDao.checksEditableGrade(appelloId, studId)) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
		} catch (SQLException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		iscritto.setId(studId);

		Gson gson = new GsonBuilder()
				   .setDateFormat("yyyy MMM dd").create();
		String json = gson.toJson(iscritto);
		
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User u = null;
		HttpSession s = request.getSession();
		u = (User) s.getAttribute("user");
		if (u.getRole().equals("student")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		String grade;	
		int appelloId;
		int studId;
		try {
			grade = request.getParameter("grade");
			appelloId = Integer.parseInt(request.getParameter("appelloid"));
			studId = Integer.parseInt(request.getParameter("studid"));
		} catch (NumberFormatException | NullPointerException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		AppelloDAO aDao = new AppelloDAO(connection);
		try {
			if(!aDao.checksProfessor(appelloId, u.getId())) {
				String path = getServletContext().getContextPath() + "/index.html";
				response.sendRedirect(path);
				return;
			}
		} catch (SQLException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
		String[] correctInputs = new String[] {"assente", "rimandato", "riprovato", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "30L"};
		boolean incorrect = true;
		for(int i = 0; i < correctInputs.length; i++) {
			if(grade.equals(correctInputs[i])) incorrect = false;
		}
		
		if(incorrect) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("Incorrect parameter");
			return;
		} else {
			StudentDAO sDao = new StudentDAO(connection);
			
			try {
				if(sDao.checksEditableGrade(appelloId, studId)) sDao.editGrade(appelloId, studId, grade);
			} catch (SQLException e) {
				response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Failure in worker's project database extraction");
			}
		
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
