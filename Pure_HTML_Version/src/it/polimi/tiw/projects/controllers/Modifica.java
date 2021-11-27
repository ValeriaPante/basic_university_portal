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

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.tiw.projects.beans.Student;
import it.polimi.tiw.projects.beans.User;
import it.polimi.tiw.projects.dao.AppelloDAO;
import it.polimi.tiw.projects.dao.StudentDAO;
import it.polimi.tiw.projects.utils.ConnectionHandler;


@WebServlet("/Modifica")
public class Modifica extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
	private TemplateEngine templateEngine;
       
    public Modifica() {
        super();
    }

    public void init() throws ServletException {
		ServletContext servletContext = getServletContext();
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		this.templateEngine = new TemplateEngine();
		this.templateEngine.setTemplateResolver(templateResolver);
		templateResolver.setSuffix(".html");
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
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("user");
		
		int appelloId;
		int studId;
		try {
			appelloId = Integer.parseInt(request.getParameter("appelloid"));
			studId = Integer.parseInt(request.getParameter("studId"));
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
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failure in worker's project database extraction");
		}
		
		StudentDAO sDao = new StudentDAO(connection);
		Student iscritto = null;
		try {
			iscritto = sDao.findIscritto(appelloId, studId);
			
			if(iscritto == null) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No such student");
				return;
			}
		} catch (SQLException e) {
			response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Failure in worker's project database extraction");
		}
		String path = "/WEB-INF/Modifica.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("iscritto", iscritto);
		ctx.setVariable("appelloId", appelloId);
		templateEngine.process(path, ctx, response.getWriter());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User u = null;
		HttpSession s = request.getSession();
		u = (User) s.getAttribute("user");
		
		String grade;	
		int appelloId;
		int studId;
		try {
			grade = request.getParameter("grade");
			appelloId = Integer.parseInt(request.getParameter("appelloid"));
			studId = Integer.parseInt(request.getParameter("studid"));
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
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failure in worker's project database extraction");
		}
		
		String[] correctInputs = new String[] {"assente", "rimandato", "riprovato", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "30L"};
		boolean incorrect = true;
		for(int i = 0; i < correctInputs.length; i++) {
			if(grade.equals(correctInputs[i])) incorrect = false;
		}
		
		if(incorrect) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Incorrect param values");
			return;
		} else {
			StudentDAO sDao = new StudentDAO(connection);
			
			try {
				if(sDao.checksEditableGrade(appelloId, studId)) sDao.editGrade(appelloId, studId, grade);
			} catch (SQLException e) {
				response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Failure in worker's project database extraction");
			}
			
			response.sendRedirect(getServletContext().getContextPath() + "/GetIscritti?appelloid="+ String.valueOf(appelloId)+"&o=0");
		
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
