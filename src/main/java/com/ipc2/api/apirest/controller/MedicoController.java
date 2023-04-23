package com.ipc2.api.apirest.controller;

import com.ipc2.api.apirest.data.Conexion;
import com.ipc2.api.apirest.model.Medico.MedicoEspecialidad;
import com.ipc2.api.apirest.model.Usuario.Usuario;
import com.ipc2.api.apirest.service.MedicoService;
import com.ipc2.api.apirest.utils.GsonUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/medicos/*")

public class MedicoController extends HttpServlet {
    Conexion conexion = new Conexion();
    private GsonUtils<MedicoEspecialidad> gsonEspecialidad;
    private MedicoService medicoService;
    public MedicoController() {
        gsonEspecialidad = new GsonUtils<>();
        medicoService = new MedicoService(conexion.obtenerConexion());
    }
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Usuario Valor = null;
        HttpSession session = (HttpSession) getServletContext().getAttribute("userSession");
        if (session != null) {
            Valor = (Usuario) session.getAttribute("user");
        }

        String uri = request.getRequestURI();
        if (uri.endsWith("/especialidad")) {
            System.out.println("Si tiene especialidad");
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(String.valueOf(listarEspecialidad(Valor)));
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else {
            System.out.println("error");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //HttpSession session = request.getSession();
        Usuario Valor = null;
        HttpSession session = (HttpSession) getServletContext().getAttribute("userSession");
        if (session != null) {
            Valor = (Usuario) session.getAttribute("user");

        }

        MedicoEspecialidad especialidad = null;
        String contentType = request.getContentType();
        String uri = request.getRequestURI();
        System.out.println("Sesion en medicocontroller : " + session);
        System.out.println("valor de usuario : " + Valor);

        if (contentType != null && contentType.startsWith("application/json")){
            especialidad = gsonEspecialidad.readFromJson(request, MedicoEspecialidad.class);
            System.out.println("Es json");
        }
        else {
            System.out.println("No es json");
        }

        if (uri.endsWith("/especialidad")) {
            if (listarEspecialidad(Valor) == false){
                System.out.println("Creando especialidad");
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(String.valueOf(crearEspecialidad(especialidad,Valor)));
                response.setStatus(HttpServletResponse.SC_OK);

            }



        }

        else {
            System.out.println("error");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    private HttpServletResponse crearRespuesta(HttpServletResponse response,boolean valor) throws IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        //return response.getWriter().write(String.valueOf(valor));
        return response;
    }
    public boolean crearEspecialidad(MedicoEspecialidad especialidad, Usuario usuario) throws IOException {
        if (especialidad == null || usuario == null){
            return false;
        }
        medicoService.crearEspecialidad(especialidad, usuario);
        return true;
    }

    public boolean listarEspecialidad( Usuario usuario) throws IOException {
        if (usuario == null){
            return false;
        }
        Optional<MedicoEspecialidad> especialidad = medicoService.listarEspecialidad(usuario);
        if (especialidad.isEmpty() || especialidad == null){
            return false;
        }
        return true;
    }
}
