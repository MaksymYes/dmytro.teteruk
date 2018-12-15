package ua.nure.kn.teteruk.usermanagment.web;

import ua.nure.kn.teteruk.usermanagment.User;
import ua.nure.kn.teteruk.usermanagment.db.DAOFactory;
import ua.nure.kn.teteruk.usermanagment.db.exception.DatabaseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

import static java.util.Objects.nonNull;

public class BrowseServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (nonNull(req.getParameter("addButton"))) {
            add(req, resp);
        } else if (nonNull(req.getParameter("editButton"))) {
            edit(req, resp);
        } else if (nonNull(req.getParameter("deleteButton"))) {
            delete(req, resp);
        } else if (nonNull(req.getParameter("detailsButton"))) {
            details(req, resp);
        } else {
            browse(req, resp);
        }
    }

    private void details(HttpServletRequest req, HttpServletResponse resp) {

    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) {

    }

    private void edit(HttpServletRequest req, HttpServletResponse resp) {

    }

    private void add(HttpServletRequest req, HttpServletResponse resp) {

    }

    private void browse(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Collection<User> users;
        try {
            users = DAOFactory.getInstance().getUserDao().findAll();
            req.getSession().setAttribute("users", users);
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
        } catch (DatabaseException e) {
            throw new ServletException(e);
        }
    }
}
