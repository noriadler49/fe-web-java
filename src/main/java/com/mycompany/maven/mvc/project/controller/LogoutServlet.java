package com.mycompany.maven.mvc.project.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "LogoutServlet", urlPatterns = "/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Hủy session
        HttpSession session = request.getSession(false); // false để không tạo mới nếu chưa có
        if (session != null) {
            session.invalidate();
        }
        // Quay về trang chủ
        response.sendRedirect(request.getContextPath() + "/");

    }
}
