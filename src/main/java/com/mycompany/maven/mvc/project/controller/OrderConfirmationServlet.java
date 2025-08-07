package com.mycompany.maven.mvc.project.controller;

import com.mycompany.maven.mvc.project.dao.OrderDAO;
import com.mycompany.maven.mvc.project.model.Order;
import com.mycompany.maven.mvc.project.model.OrderItem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "OrderConfirmationServlet", urlPatterns = "/orderConfirmation")
public class OrderConfirmationServlet extends HttpServlet {
    private OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("username");
        String orderIdParam = request.getParameter("orderId");

        if (username == null || orderIdParam == null) {
            response.sendRedirect("jsp/account/login.jsp");
            return;
        }

        String orderId = orderIdParam;

        Order order = orderDAO.getOrderByIdAndUsername(orderId, username);
        List<OrderItem> items = orderDAO.getOrderItemsByOrderId(orderId);

        if (order == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Order not found");
            return;
        }
        request.setAttribute("order", order);
        request.setAttribute("items", items);
        request.getRequestDispatcher("jsp/order/orderConfirmation.jsp").forward(request, response);
    }
}
