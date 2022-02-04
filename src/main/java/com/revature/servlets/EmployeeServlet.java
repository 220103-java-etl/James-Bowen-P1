package com.revature.servlets;

import com.revature.models.Reimbursement;
import com.revature.models.Status;
import com.revature.models.User;
import com.revature.repositories.ReimbursementDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class EmployeeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);


        Double amount = Double.parseDouble(request.getParameter("amount"));
        String date = request.getParameter("date");
        String time = request.getParameter("time");
        String location = request.getParameter("location");
        String description = request.getParameter("description");
        String justify = request.getParameter("justify");
        Status status = Status.PENDING;
        User author = (User) session.getAttribute("user");
        User resolver = new User();



        try {
            Reimbursement r = new Reimbursement(0, status, author, resolver, amount, date, time, location, description, justify);
            ReimbursementDAO.createReimbursement(r);
            ReimbursementDAO.updateAllowance(r);
            response.sendRedirect("employee.html");
        } catch (Exception e) {
            e.printStackTrace();
        }
        }

    }
