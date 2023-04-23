package com.ipc2.api.apirest.controller;

import com.ipc2.api.apirest.data.Conexion;
import com.ipc2.api.apirest.model.Laboratorio.LaboratorioInfoExamen;
import com.ipc2.api.apirest.model.Usuario.Usuario;
import com.ipc2.api.apirest.service.LaboratorioService;
import com.ipc2.api.apirest.utils.GsonUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/laboratorio/*")
public class LaboratorioController extends HttpServlet {

    Conexion conexion = new Conexion();
    private LaboratorioService laboratorioService;
    private GsonUtils<LaboratorioInfoExamen> gsonLaboratorio;

    public LaboratorioController(){
        this.gsonLaboratorio = new GsonUtils<>();
        this.laboratorioService = new LaboratorioService(conexion.obtenerConexion());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Usuario Valor = null;
        HttpSession session = (HttpSession) getServletContext().getAttribute("userSession");
        if (session != null) {
            Valor = (Usuario) session.getAttribute("user");
        }

        String uri = request.getRequestURI();
        if (uri.endsWith("/tipoexamen")) {
            System.out.println("Si tiene tipo examen");
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(String.valueOf(listarTipoExamen(Valor)));
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

        LaboratorioInfoExamen laboratorio = null;
        String contentType = request.getContentType();
        String uri = request.getRequestURI();
        System.out.println("Sesion en medicocontroller : " + session);
        System.out.println("valor de usuario : " + Valor);

        if (contentType != null && contentType.startsWith("application/json")){
            laboratorio = gsonLaboratorio.readFromJson(request, LaboratorioInfoExamen.class);
            System.out.println("Es json");
        }
        else {
            System.out.println("No es json");
        }

        if (uri.endsWith("/tipoexamen")) {
            if (listarTipoExamen(Valor) == false){
                System.out.println("Creando examenlaboratorio");
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(String.valueOf(crearInfoExamen(laboratorio,Valor)));
                response.setStatus(HttpServletResponse.SC_OK);
            }
        }

        else {
            System.out.println("error");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    public boolean crearInfoExamen(LaboratorioInfoExamen labinfoexamen, Usuario usuario) {
        if (labinfoexamen == null || usuario == null){
            return false;
        }
        laboratorioService.crearExamLab(labinfoexamen,usuario);
        return true;
    }

    public boolean listarTipoExamen( Usuario usuario) {
        if (usuario == null){
            return false;
        }
        Optional<LaboratorioInfoExamen> labinfoexamen = laboratorioService.listarExamenInfo(usuario);
        if (labinfoexamen.isEmpty() || labinfoexamen == null){
            return false;
        }
        return true;
    }

}
