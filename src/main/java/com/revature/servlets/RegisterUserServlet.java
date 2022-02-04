package com.revature.servlets;

import com.revature.models.User;
import com.revature.repositories.UserDAO;
import com.revature.services.AuthService;
import com.revature.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterUserServlet extends HttpServlet {

    UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        // Add user to database
        User user = UserDAO.getByUsername(username);
        if(user == null) {
            try {
                User newUser = new User(0, firstName, lastName, username, password, role);
                AuthService.register(newUser);
            } catch (Exception e) {
                System.out.println("User creation unsuccessful");
            }
        }
        System.out.println("Username already exists");
        // Redirect to login page
        response.sendRedirect("login.html");

    }
}
