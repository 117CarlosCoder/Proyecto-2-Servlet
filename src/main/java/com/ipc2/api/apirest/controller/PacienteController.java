package com.ipc2.api.apirest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.ipc2.api.apirest.data.Conexion;
import com.ipc2.api.apirest.model.Laboratorio.LaboratorioInfoExamen;
import com.ipc2.api.apirest.model.Usuario.Usuario;
import com.ipc2.api.apirest.service.MedicoService;
import com.ipc2.api.apirest.service.PacienteService;
import com.ipc2.api.apirest.service.UsuarioService;
import com.ipc2.api.apirest.utils.GsonUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/paciente/*")
public class PacienteController extends HttpServlet {

    Conexion conexion = new Conexion();
    private PacienteService pacienteService;
    public PacienteController() {
        pacienteService = new PacienteService(conexion.obtenerConexion());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Usuario Valor = null;
        HttpSession session = (HttpSession) getServletContext().getAttribute("userSession");
        if (session != null) {
            Valor = (Usuario) session.getAttribute("user");

        }
        if (Valor == null) {
            return;
        }

        String uri = request.getRequestURI();

        if (uri.endsWith("/cargarmedicos")) {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonclass = objectMapper.writeValueAsString(pacienteService.listarMedico());
            System.out.println("Listando medicos");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonclass);
            response.setStatus(HttpServletResponse.SC_OK);
        }

        return;
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Usuario Valor = null;
        HttpSession session = (HttpSession) getServletContext().getAttribute("userSession");
        if (session != null) {
            Valor = (Usuario) session.getAttribute("user");

        }
        if (Valor == null) {
            return;
        }

        String contentType = request.getContentType();
        String uri = request.getRequestURI();
        System.out.println("Sesion en medicocontroller : " + session);
        System.out.println("valor de usuario : " + Valor);
        String json = request.getReader().readLine(); // Lee el archivo JSON enviado desde Angula
        Gson gson = new Gson();
        JsonElement jsonElement = gson.fromJson(json, JsonElement.class);

        if (uri.endsWith("/cargarmedico")) {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonclass = objectMapper.writeValueAsString(pacienteService.listarMedicosNombres("Med1"));
            System.out.println("Listando medicoo especifico");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonclass);
            response.setStatus(HttpServletResponse.SC_OK);
        }

        if (uri.endsWith("/crear")) {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonclass = objectMapper.writeValueAsString(pacienteService.listarMedicosNombres("Med1"));
            System.out.println("Listando medicoo especifico");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonclass);
            response.setStatus(HttpServletResponse.SC_OK);
        }

    }

}
