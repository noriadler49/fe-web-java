package com.mycompany.maven.mvc.project.controller;

import java.io.IOException;
import java.util.List;

import com.mycompany.maven.mvc.project.dao.OrderDAO;
import com.mycompany.maven.mvc.project.model.Order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "AdminOrderController", urlPatterns = "/admin/orders")
public class AdminOrderController extends HttpServlet {
    private OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String status = request.getParameter("status");
        if (status == null) status = "All";

        List<Order> orders = orderDAO.getAllOrders(status);
        request.setAttribute("orders", orders);
        request.setAttribute("selectedStatus", status);
        request.getRequestDispatcher("/jsp/admin/admin_order.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String orderIdParam = request.getParameter("orderId");

        if ("confirm".equals(action) && orderIdParam != null) {
            int orderId = Integer.parseInt(orderIdParam);
            orderDAO.updateOrderStatus(orderId, "Completed");
        }

        response.sendRedirect(request.getContextPath() + "/admin/orders");
    }
}
