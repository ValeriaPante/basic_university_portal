package it.polimi.tiw.projects.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

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

import it.polimi.tiw.projects.beans.Appello;
import it.polimi.tiw.projects.beans.User;
import it.polimi.tiw.projects.dao.AppelloDAO;
import it.polimi.tiw.projects.dao.CourseDAO;
import it.polimi.tiw.projects.utils.ConnectionHandler;

@WebServlet("/GetAppelli")
public class GetAppelli extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
	private TemplateEngine templateEngine;
       
   
    public GetAppelli() {
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
		
		int courseId;
		try {
			courseId = Integer.parseInt(request.getParameter("courseid"));
		} catch (NumberFormatException | NullPointerException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Incorrect param values");
			return;
		}
		
		AppelloDAO aDao = new AppelloDAO(connection);
		CourseDAO  cDao = new CourseDAO(connection);
		List<Appello> appelli = null;
		User u = null;
		u = (User) session.getAttribute("user");
		try {
			if((u.getRole().equals("teacher") && !cDao.checksProfessor(courseId, u.getId())) || (u.getRole().equals("student") && !cDao.checksStudent(courseId, u.getId()))) {
				String path = getServletContext().getContextPath() + "/GoToHome";
				response.sendRedirect(path);
				return;
			}
			appelli = (u.getRole().equals("teacher")) ? aDao.findAppelliById(courseId) : aDao.findAppelliByStudId(courseId, u.getId());
		} catch (SQLException e) {
			response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Failure in worker's project database extraction");
		}
		String path = u.getRole().equals("teacher") ? "/WEB-INF/Appelli.html" : "/WEB-INF/AppelliStud.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("appelli", appelli);
		templateEngine.process(path, ctx, response.getWriter());
	}
	
	public void destroy() {
		try {
			ConnectionHandler.closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
