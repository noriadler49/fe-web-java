package com.mycompany.maven.mvc.project.controller;

import com.mycompany.maven.mvc.project.dao.OrderDAO;
import com.mycompany.maven.mvc.project.model.Order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "MyOrdersServlet", urlPatterns = "/myOrders")
public class MyOrdersServlet extends HttpServlet {
    private OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("username");

        if (username == null) {
            response.sendRedirect("jsp/account/login.jsp");
            return;
        }

        String status = request.getParameter("status");
        if (status == null) status = "All";

        List<Order> orders = orderDAO.getOrdersByUsernameAndStatus(username, status);
        request.setAttribute("orders", orders);
        request.setAttribute("selectedStatus", status);

        request.getRequestDispatcher("jsp/order/my_orders.jsp").forward(request, response);
    }
}
