package gr.aueb.cf.schoolapp.controller;

import gr.aueb.cf.schoolapp.dao.ITeacherDAO;
import gr.aueb.cf.schoolapp.dao.TeacherDAOImpl;
import gr.aueb.cf.schoolapp.dao.exceptions.TeacherDAOException;
import gr.aueb.cf.schoolapp.model.Teacher;
import gr.aueb.cf.schoolapp.service.ITeacherService;
import gr.aueb.cf.schoolapp.service.TeacherServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SearchTeachersController", value = "/schoolapp/search")
public class SearchTeachersController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    ITeacherDAO teacherDAO = new TeacherDAOImpl();
    ITeacherService teacherService = new TeacherServiceImpl(teacherDAO);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//        request.setAttribute("isError", false);
//        request.setAttribute("error", "");
//        request.setAttribute("teachersNotFound", false);
        request.getRequestDispatcher("/schoolapp/menu")
                .forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String lastname = request.getParameter("lastname").trim();

        try {
            List<Teacher> teachers = teacherService.getTeachersByLastname(lastname);
            if (teachers.size() == 0) {
                request.setAttribute("teachersNotFound", true);
            } else {
                request.setAttribute("teachersNotFound", false);
            }
            request.setAttribute("teachers", teachers);
            request.getRequestDispatcher("/school/static/templates/teachers.jsp").forward(request, response);
        } catch (TeacherDAOException e) {
            String message = e.getMessage();
            request.setAttribute("isError", true);
            request.setAttribute("errorMessage", message);
            request.getRequestDispatcher("/school/static/templates/teachersmenu.jsp").forward(request, response);
        }
    }

}
