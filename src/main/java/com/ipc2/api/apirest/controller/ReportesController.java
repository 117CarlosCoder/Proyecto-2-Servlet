package com.ipc2.api.apirest.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.ipc2.api.apirest.data.Conexion;
import com.ipc2.api.apirest.model.Medico.MedicoEspecialidad;
import com.ipc2.api.apirest.model.Medico.MedicoHorario;
import com.ipc2.api.apirest.model.Usuario.Usuario;
import com.ipc2.api.apirest.reportes.Conexion.ConexionDatos;
import com.ipc2.api.apirest.reportes.GenerarReportes.GenerarReportes;
import com.ipc2.api.apirest.service.MedicoService;
import com.ipc2.api.apirest.utils.GsonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/reportes/*")
public class ReportesController extends HttpServlet {

    Conexion conexion = new Conexion();
    ConexionDatos conexionDatos;
    private GenerarReportes generarPDF;

    public ReportesController() {
        generarPDF = new GenerarReportes();
        conexionDatos = ConexionDatos.getInstance();
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
            try {
                generarPDF.generarRep(response,Datos,"Administrador",conexionDatos, Valor);
            } catch (JRException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            response.setStatus(HttpServletResponse.SC_OK);
        }
        if (uri.endsWith("/reportes-pacientes")) {
            try {
                generarPDF.generarRep(response,Datos, "Paciente", conexionDatos,Valor);
            } catch (JRException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            response.setStatus(HttpServletResponse.SC_OK);
        }
        if (uri.endsWith("/reportes-medicos")) {
            try {
                generarPDF.generarRep(response,Datos, "Medicos", conexionDatos, Valor);
            } catch (JRException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            response.setStatus(HttpServletResponse.SC_OK);
        }
        if (uri.endsWith("/reportes-laboratorios")) {
            try {
                generarPDF.generarRep(response,Datos, "Laboratorios", conexionDatos, Valor);
            } catch (JRException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            response.setStatus(HttpServletResponse.SC_OK);
        }





    }


}
