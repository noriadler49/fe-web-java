package com.mycompany.maven.mvc.project.controller;

import com.mycompany.maven.mvc.project.dao.CartDAO;
import com.mycompany.maven.mvc.project.model.CartItem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "CartServlet", urlPatterns = "/cart")
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
        double total = cartItems.stream().mapToDouble(item -> item.getDish().getDishPrice() * item.getQuantity()).sum();

        request.setAttribute("cartItems", cartItems);
        request.setAttribute("totalPrice", total);

        request.getRequestDispatcher("jsp/order/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String username = (String) request.getSession().getAttribute("username");

        if (username == null) {
            response.sendRedirect("jsp/account/login.jsp");
            return;
        }
        
        if ("updateQuantity".equals(action)) {
            int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            cartDAO.updateCartQuantity(username, cartItemId, quantity);
        } else if ("removeItem".equals(action)) {
            int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
            cartDAO.removeCartItem(username, cartItemId);
        } else if ("addToCart".equals(action)) {
            int dishId = Integer.parseInt(request.getParameter("dishId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            cartDAO.addToCart(username, dishId, quantity);
        }

        response.sendRedirect("cart");
    }
}
