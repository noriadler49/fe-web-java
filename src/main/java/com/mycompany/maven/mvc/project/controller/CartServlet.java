package com.mycompany.maven.mvc.project.controller;

import com.mycompany.maven.mvc.project.dao.CartDAO;
import com.mycompany.maven.mvc.project.model.CartItem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private CartDAO cartDAO = new CartDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = (String) request.getSession().getAttribute("username");
        if (username == null) {
            response.sendRedirect("jsp/account/login.jsp");
            return;
        }

        List<CartItem> cartItems = cartDAO.getCartItemsByUsername(username);
        double totalPrice = cartItems.stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();

        request.setAttribute("cartItems", cartItems);
        request.setAttribute("totalPrice", totalPrice);

        request.getRequestDispatcher("jsp/order/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");

        if (username == null || !"User".equals(role)) {
            response.sendRedirect("jsp/account/login.jsp");
            return;
        }

        String dishIdStr = request.getParameter("dishId");
        int dishId = Integer.parseInt(dishIdStr);

        // Thêm vào giỏ hàng (mặc định quantity = 1)
        cartDAO.addToCart(username, dishId, 1);

        // Sau khi thêm thì redirect về trang menu (hoặc dish details tùy bạn)
        response.sendRedirect("menu?category=All");
    }
}
