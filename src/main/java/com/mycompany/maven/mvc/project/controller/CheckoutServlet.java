package com.mycompany.maven.mvc.project.controller;

import java.io.IOException;
import java.util.List;

import com.mycompany.maven.mvc.project.dao.CartDAO;
import com.mycompany.maven.mvc.project.dao.OrderDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.mycompany.maven.mvc.project.model.CartItem;
@WebServlet(name = "CheckoutServlet", urlPatterns = "/checkout")
public class CheckoutServlet extends HttpServlet {
    private OrderDAO orderDAO = new OrderDAO();
    private CartDAO cartDAO = new CartDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("username");

        if (username == null) {
            response.sendRedirect("jsp/account/login.jsp");
            return;
        }

        // Lấy giỏ hàng của user
        List<CartItem> cartItems = cartDAO.getCartItemsByUsername(username);
        double total = cartItems.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();

        request.setAttribute("cartItems", cartItems);
        request.setAttribute("totalPrice", total);
        request.getRequestDispatcher("jsp/order/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("username");

        if (username == null) {
            response.sendRedirect("jsp/account/login.jsp");
            return;
        }

        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String payment = request.getParameter("payment");
        String voucherCode = request.getParameter("usedVoucher");
        if (voucherCode != null && voucherCode.trim().isEmpty()) {
            voucherCode = null;
        }
        List<CartItem> cartItems = cartDAO.getCartItemsByUsername(username);
        double total = cartItems.stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum();


        int orderId = orderDAO.placeOrder(username, cartItems, total, voucherCode, address, payment);

        cartDAO.clearCart(username); // sau khi đặt đơn thì xóa giỏ

        response.sendRedirect("orderConfirmation?orderId=" + orderId);
    }
}

