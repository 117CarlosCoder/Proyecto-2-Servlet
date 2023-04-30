package com.ipc2.api.apirest.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.ipc2.api.apirest.data.Conexion;
import com.ipc2.api.apirest.model.Medico.MedicoEspecialidad;
import com.ipc2.api.apirest.model.Medico.MedicoHorario;
import com.ipc2.api.apirest.model.Usuario.Usuario;
import com.ipc2.api.apirest.reportes.GenerarReportes.GenerarReportes;
import com.ipc2.api.apirest.service.MedicoService;
import com.ipc2.api.apirest.utils.GsonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/reportes/*")
public class ReportesController extends HttpServlet {

    Conexion conexion = new Conexion();;
    private GenerarReportes generarPDF;

    public ReportesController() {
        generarPDF = new GenerarReportes();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //HttpSession session = request.getSession();
        Usuario Valor = null;
        String uri = request.getRequestURI();
        String Datos = "";
        HttpSession session = (HttpSession) getServletContext().getAttribute("userSession");
        if (session != null) {
            Valor = (Usuario) session.getAttribute("user");
            System.out.println("Este usuario ingresa a reportes : " + Valor);
        }
        if (Valor == null) {
            System.out.println("No hay usuario en registro");
           return;
        }

        Datos = request.getReader().readLine();

        System.out.println("Cargando Datos : " + Datos);

        if (uri.endsWith("/reportes-administrador")) {
            System.out.println("Entro a reportes admin");
            generarPDF.generarRep(response,Datos,"Administrador");
            response.setStatus(HttpServletResponse.SC_OK);
        }
        if (uri.endsWith("/reportes-pacientes")) {
            generarPDF.generarRep(response,Datos, "Paciente");
            response.setStatus(HttpServletResponse.SC_OK);
        }
        if (uri.endsWith("/reportes-medicos")) {
            generarPDF.generarRep(response,Datos, "Medicos");
            response.setStatus(HttpServletResponse.SC_OK);
        }
        if (uri.endsWith("/reportes-laboratorios")) {
            generarPDF.generarRep(response,Datos, "Laboratorios");
            response.setStatus(HttpServletResponse.SC_OK);
        }





    }


}
