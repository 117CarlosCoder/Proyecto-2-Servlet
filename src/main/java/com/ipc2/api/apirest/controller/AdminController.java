package com.ipc2.api.apirest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import com.ipc2.api.apirest.data.Conexion;
import com.ipc2.api.apirest.model.Admin.Porcentaje;
import com.ipc2.api.apirest.model.CargarDatos.Carga;
import com.ipc2.api.apirest.model.Laboratorio.LaboratorioInfoExamen;
import com.ipc2.api.apirest.model.Laboratorio.TipoExamen;
import com.ipc2.api.apirest.model.Medico.Especialidades;
import com.ipc2.api.apirest.model.Medico.MedicoEspecialidad;
import com.ipc2.api.apirest.model.Medico.MedicoHorario;
import com.ipc2.api.apirest.model.Paciente.ConsultaPaciente;
import com.ipc2.api.apirest.model.Usuario.Usuario;
import com.ipc2.api.apirest.service.AdminSevice;
import com.ipc2.api.apirest.service.LaboratorioService;
import com.ipc2.api.apirest.service.MedicoService;
import com.ipc2.api.apirest.service.PacienteService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.sf.jasperreports.chrome.PageOptions;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet("/admin/*")
public class AdminController extends HttpServlet {

    Conexion conexion = new Conexion();

    private MedicoService medicoService;
    private AdminSevice adminSevice;
    private LaboratorioService laboratorioService;

    public AdminController() {
        medicoService = new MedicoService(conexion.obtenerConexion());
        adminSevice = new AdminSevice(conexion.obtenerConexion());
        laboratorioService = new LaboratorioService(conexion.obtenerConexion());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Usuario Valor = null;
        HttpSession session = (HttpSession) getServletContext().getAttribute("userSession");
        if (session != null) {
            Valor = (Usuario) session.getAttribute("user");
        }

        System.out.println("Calculando error");
        String json = request.getReader().readLine();
        Gson gson = new Gson();
        Carga datos = null;
        JsonElement jsonElement = null;
        try {
            jsonElement = gson.fromJson(json, JsonElement.class);
        } catch (JsonSyntaxException e) {

        }
        String contentType = request.getContentType();
        String uri = request.getRequestURI();
        Usuario usuario = null;

        System.out.println("Calculando error2");

        try {
            System.out.println("carga1");
            System.out.println("carga2");
            System.out.println("carga3");
            datos = gson.fromJson( jsonElement, Carga.class);
        } catch (Exception e) {

        }

        if (uri.endsWith("/solicitud-especialidad")) {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonclass = objectMapper.writeValueAsString(listarEspecialidadAdmin());
            System.out.println("Solicitude de especialidad");
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonclass);
            response.setStatus(HttpServletResponse.SC_OK);

        }

        if (uri.endsWith("/solicitud-examenes")) {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonclass = objectMapper.writeValueAsString(listarExamenAdmin());
            System.out.println("Solicitud de examenes : " + jsonclass );
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonclass);
            response.setStatus(HttpServletResponse.SC_OK);

        }

        if (uri.endsWith("/cargaporcentaje")) {
            System.out.println("Entrando a porcentaje");
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonclass = objectMapper.writeValueAsString(listarPorcentaje());
            System.out.println("Solicitud de porcentaje : " + jsonclass );
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonclass);
            response.setStatus(HttpServletResponse.SC_OK);

        }

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Usuario Valor = null;
        HttpSession session = (HttpSession) getServletContext().getAttribute("userSession");
        if (session != null) {
            Valor = (Usuario) session.getAttribute("user");
        }
        System.out.println("Calculando error");
        String json = request.getReader().lines().collect(Collectors.joining());
        Gson gson = new Gson();
        Carga datos = null;
        JsonElement jsonElement = null;
        MedicoEspecialidad especialidad = null;
        LaboratorioInfoExamen laboratorio = null;
        MedicoHorario horas = null;
        Porcentaje porcentaje = null;
        String contentType = request.getContentType();
        //JsonArray jsonArray = jsonElement.getAsJsonArray();
        //JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();


        System.out.println("valor General : " + json);
        try {
            jsonElement = gson.fromJson(json, JsonElement.class);
            //jsonElement = JsonParser.parseString(json);

            System.out.println("conviertiendo jso: " + jsonElement + " valor json es : " + jsonElement.isJsonObject());

        } catch (JsonSyntaxException e) {
            System.out.println("Existe algun problema : " + e);
        }

        if (contentType != null && request.getContentType().contains("application/json")){
            //JsonElement jsonElement = JsonParser.parseString(json);
            horas = gson.fromJson( jsonElement, MedicoHorario.class);
            especialidad = gson.fromJson( jsonElement, MedicoEspecialidad.class);
            laboratorio = gson.fromJson( jsonElement, LaboratorioInfoExamen.class);
            porcentaje = gson.fromJson( jsonElement, Porcentaje.class);
            System.out.println("valor de especialidad : " + especialidad);
            System.out.println("valor de horario : " + horas);
            System.out.println("Es json");
        }

        String uri = request.getRequestURI();

        System.out.println("Calculando error2");

        try {
            System.out.println("carga1");
            System.out.println("carga2");
            System.out.println("carga3");
            datos = gson.fromJson( jsonElement, Carga.class);
        } catch (Exception e) {

        }

        if (uri.endsWith("/especialidad")) {

                System.out.println("Creando especialidad");
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(String.valueOf(crearEspecialidadAdmin(especialidad)));
                response.setStatus(HttpServletResponse.SC_OK);
                return;

        }

        if (uri.endsWith("/examen")) {

            System.out.println("Creando examen");
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(String.valueOf(crearExamenAdmin(laboratorio)));
            response.setStatus(HttpServletResponse.SC_OK);
            return;

        }

        if (uri.endsWith("/porcentaje")) {

            System.out.println("Creando porcentaje");
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(String.valueOf(crearPorcentaje(porcentaje)));
            response.setStatus(HttpServletResponse.SC_OK);
            return;

        }

    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Usuario Valor = null;
        HttpSession session = (HttpSession) getServletContext().getAttribute("userSession");
        if (session != null) {
            Valor = (Usuario) session.getAttribute("user");
        }

        String uri = request.getRequestURI();
        String json = request.getReader().readLine();


        if (uri.endsWith("/eliminar")) {

            //System.out.println("Eliminando especialidad");
            //response.setContentType("text/plain");
            //response.setCharacterEncoding("UTF-8");
            //System.out.println("valor de json : " + json);
            //eliminarEspecialidad(Integer.parseInt(json));
            //response.getWriter().write("Eliminado");
            //response.setStatus(HttpServletResponse.SC_OK);
            String idStr = request.getParameter("id");
            int id = Integer.parseInt(idStr);
            System.out.println("Eliminando especialidad con ID: " + id);
            eliminarEspecialidad(id);
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("Eliminado");
            response.setStatus(HttpServletResponse.SC_OK);
            return;

        }

        if (uri.endsWith("/eliminar-examenes")) {

            //System.out.println("Eliminando especialidad");
            //response.setContentType("text/plain");
            //response.setCharacterEncoding("UTF-8");
            //System.out.println("valor de json : " + json);
            //eliminarEspecialidad(Integer.parseInt(json));
            //response.getWriter().write("Eliminado");
            //response.setStatus(HttpServletResponse.SC_OK);
            String idStr = request.getParameter("id");
            int id = Integer.parseInt(idStr);
            System.out.println("Eliminando examen con ID: " + id);
            eliminarExamne(id);
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("Eliminado");
            response.setStatus(HttpServletResponse.SC_OK);
            return;

        }

    }

    public List<MedicoEspecialidad> listarEspecialidadAdmin() throws IOException {
        List<MedicoEspecialidad> especialidad = medicoService.listarEspecialidadesAdmin();
        return especialidad;
    }

    public List<LaboratorioInfoExamen> listarExamenAdmin() throws IOException {
        List<LaboratorioInfoExamen> labexamen = laboratorioService.listarExamenAdmin();
        return labexamen;
    }

    public List<Porcentaje> listarPorcentaje() throws IOException {
        List<Porcentaje> porcentajes = adminSevice.listarPorcentaje();
        return porcentajes;
    }

    public boolean crearEspecialidadAdmin(MedicoEspecialidad especialidad) throws IOException {

        System.out.println("Especialidad creandose en el metodo : " + especialidad);
        if (especialidad == null){
            return false;
        }
        medicoService.crearEspecialidadAdmin(especialidad);
        return true;
    }

    public boolean crearExamenAdmin(LaboratorioInfoExamen lab) throws IOException {

        System.out.println("Examen creandose en el metodo : " + lab);
        if (lab == null){
            return false;
        }
        adminSevice.crearExamLabAdmin(lab);
        return true;
    }

    public boolean crearPorcentaje(Porcentaje porcentaje) throws IOException {

        System.out.println("Porcentajes creandose en el metodo : " + porcentaje);
        if (porcentaje == null){
            return false;
        }
        adminSevice.crearPorcentaje(porcentaje);
        return true;
    }

    public boolean eliminarEspecialidad(int id) throws IOException {

        if (id == 0){
            return false;
        }
        adminSevice.eliminarEspecialidad(id);
        System.out.println("Especialidad Eliminada");
        return true;
    }

    public boolean eliminarExamne(int id) throws IOException {

        if (id == 0){
            return false;
        }
        adminSevice.eliminarExamen(id);
        System.out.println("Examen Eliminado");
        return true;
    }

}
