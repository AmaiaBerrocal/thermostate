package com.thermostate.infrastructure;

import com.thermostate.application.Users;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/validation")
public class Validation extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        Users users = new Users(new SqulitoConnection());
        if (users.isValidPassword(name, password)){
            resp.sendRedirect("/vacia.html");
        } else {
            resp.sendRedirect("/login.jsp?error=true");
        }
    }
}

