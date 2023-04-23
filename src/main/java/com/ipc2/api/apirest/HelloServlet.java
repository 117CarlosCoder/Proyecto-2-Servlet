package com.ipc2.api.apirest;

import com.ipc2.api.apirest.data.Conexion;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Conexion conexion = new Conexion();
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(3600);
        System.out.println("Sesion en heloservlet : " + session);
        session.setAttribute("conexion", conexion.obtenerConexion());
    }
}
