package it.polimi.tiw.projects.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.polimi.tiw.projects.beans.Appello;
import it.polimi.tiw.projects.beans.User;
import it.polimi.tiw.projects.dao.AppelloDAO;
import it.polimi.tiw.projects.dao.CourseDAO;
import it.polimi.tiw.projects.utils.ConnectionHandler;

@WebServlet("/GetAppelli")
public class GetAppelli extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
       
   
    public GetAppelli() {
        super();
    }

    public void init() throws ServletException {
    	connection = ConnectionHandler.getConnection(getServletContext());
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int courseId;
		try {
			courseId = Integer.parseInt(request.getParameter("courseid"));
		} catch (NumberFormatException | NullPointerException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
		AppelloDAO aDao = new AppelloDAO(connection);
		CourseDAO  cDao = new CourseDAO(connection);
		List<Appello> appelli = null;
		User u = null;
		HttpSession s = request.getSession();
		u = (User) s.getAttribute("user");
		try {
			if((u.getRole().equals("teacher") && !cDao.checksProfessor(courseId, u.getId())) || (u.getRole().equals("student") && !cDao.checksStudent(courseId, u.getId()))) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
			appelli = (u.getRole().equals("teacher")) ? aDao.findAppelliById(courseId) : aDao.findAppelliByStudId(courseId, u.getId());
		} catch (SQLException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
		Gson gson = new GsonBuilder()
				   .setDateFormat("yyyy MMM dd").create();
		String json = gson.toJson(appelli);
		
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}
	
	public void destroy() {
		try {
			ConnectionHandler.closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
