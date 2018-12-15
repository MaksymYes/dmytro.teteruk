package ua.nure.kn.teteruk.usermanagment.web;

import ua.nure.kn.teteruk.usermanagment.User;
import ua.nure.kn.teteruk.usermanagment.db.DAOFactory;
import ua.nure.kn.teteruk.usermanagment.db.exception.DatabaseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class EditServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (nonNull(req.getParameter("okButton"))) {
            doOk(req, resp);
        } else if (nonNull(req.getParameter("cancelButton"))) {
            doCancel(req, resp);
        } else {
            showPage(req, resp);
        }
    }

    private void doOk(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = null;
        try {
            user = getUser(req);
        } catch (ValidationException e) {
            req.setAttribute("error", e.getMessage());
            showPage(req, resp);
            return;
        }
        try {
            processUser(user);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
        req.getRequestDispatcher("/browse").forward(req, resp);
    }

    protected void processUser(User user) throws DatabaseException {
        DAOFactory.getInstance().getUserDao().update(user);
    }

    private User getUser(HttpServletRequest req) throws ValidationException {
        User user = new User();
        String idStr = req.getParameter("id");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String dateStr = req.getParameter("date");

        if (isNull(firstName)) {
            throw new ValidationException("First name is empty");
        }
        if (isNull(lastName)) {
            throw new ValidationException("Last name is empty");
        }
        if (isNull(dateStr)) {
            throw new ValidationException("Date is empty");
        }

        if (nonNull(idStr)) {
            user.setId(new Long(idStr));
        }
        user.setFirstName(firstName);
        user.setLastName(lastName);
        try {
            user.setDateOfBirth(DateFormat.getDateInstance().parse(dateStr));
        } catch (ParseException e) {
            throw new ValidationException("Date format is incorrect");
        }
        return user;
    }

    private void doCancel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/browse").forward(req, resp);
    }

    protected void showPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/edit.jsp").forward(req, resp);
    }
}
