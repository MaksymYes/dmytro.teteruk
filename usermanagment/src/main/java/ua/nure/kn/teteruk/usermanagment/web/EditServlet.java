package ua.nure.kn.teteruk.usermanagment.web;

import ua.nure.kn.teteruk.usermanagment.User;
import ua.nure.kn.teteruk.usermanagment.db.DAOFactory;
import ua.nure.kn.teteruk.usermanagment.db.exception.DatabaseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;

import static java.util.Objects.nonNull;

public class EditServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (nonNull(req.getParameter("okButton"))) {
            doOk(req, resp);
        }else if (nonNull(req.getParameter("cancelButton"))) {
            doCancel(req, resp);
        }else{
            showPage();
        }
    }

    private void doOk(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getUser(req);
        try {
            processUser(user);
        } catch (DatabaseException e) {
            e.printStackTrace();
            new ServletException(e);
        }
        req.getRequestDispatcher("/browse").forward(req, resp);
    }

    private void processUser(User user) throws DatabaseException {
        DAOFactory.getInstance().getUserDao().update(user);
    }

    private User getUser(HttpServletRequest req) {
        User user = new User();
        String idStr = req.getParameter("id");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String dateStr = req.getParameter("date");

        if(nonNull(idStr)) {
            user.setId(new Long(idStr));
        }
        user.setFirstName(firstName);
        user.setLastName(lastName);
        try {
            user.setDateOfBirth(DateFormat.getDateInstance().parse(dateStr));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    private void doCancel(HttpServletRequest req, HttpServletResponse resp) {
    }

    private void showPage() {
    }
}
