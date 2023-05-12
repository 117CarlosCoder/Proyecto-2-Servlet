package com.ipc2.api.apirest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.ipc2.api.apirest.data.Conexion;
import com.ipc2.api.apirest.data.MedicoDB;
import com.ipc2.api.apirest.model.Medico.Especialidades;
import com.ipc2.api.apirest.model.Medico.MedicoEspecialidad;
import com.ipc2.api.apirest.model.Medico.MedicoHorario;
import com.ipc2.api.apirest.model.Usuario.Usuario;
import com.ipc2.api.apirest.service.MedicoService;
import com.ipc2.api.apirest.utils.GsonUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/medicos/*")

public class MedicoController extends HttpServlet {
    Conexion conexion = new Conexion();
    private GsonUtils<MedicoEspecialidad> gsonEspecialidad;
    private GsonUtils<MedicoHorario> gsonHorario;
    private MedicoService medicoService;

    public MedicoController() {
        gsonHorario = new GsonUtils<>();
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
        if (Valor == null) {
            return;
        }

        String uri = request.getRequestURI();
        if (uri.endsWith("/especialidad")) {
            if (listarEspecialidad(Valor)) {
                System.out.println("Si tiene especialidad");
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(String.valueOf(listarEspecialidad(Valor)));
                response.setStatus(HttpServletResponse.SC_OK);
            }
            return;
        }

        if (uri.endsWith("/cargaespecialidad-especialidades")) {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonclass = objectMapper.writeValueAsString(medicoService.listarEspecialidades());
            System.out.println("Especialidades");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonclass);
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        if (uri.endsWith("/cargaespecialidad-especialidad")) {

            if (Valor == null) {
                return;
            }

            if (listarEspecialidadAgregada(Valor).isEmpty() || listarEspecialidadAgregada(Valor) == null) {
                return;
            }

            ObjectMapper objectMappeCLr = new ObjectMapper();
            //String jsonclassCL = objectMappeCLr.writeValueAsString(medicoService.listarEspecialidad(Valor));
            System.out.println("Valor de json : " + medicoService.listarEspecialidad(Valor));
            List<MedicoEspecialidad> especialidades = medicoService.listarEspecialidad(Valor);
            String jsonclassCL = objectMappeCLr.writeValueAsString(especialidades);
            //String jsonclassCL = medicoService.listarEspecialidad(Valor).toString();
            System.out.println("Especialidad");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            System.out.println("Especialidad para cargar : " + jsonclassCL);
            response.getWriter().write(jsonclassCL);
            response.setStatus(HttpServletResponse.SC_OK);




            return;
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
        MedicoHorario horas = null;
        String contentType = request.getContentType();
        String uri = request.getRequestURI();
        System.out.println("Sesion en medicocontroller : " + session);
        System.out.println("valor de usuario : " + Valor);
        String json = request.getReader().readLine(); // Lee el archivo JSON enviado desde Angula
        System.out.println("valor de angular : " + json);
        Gson gson = new Gson();
        JsonElement jsonElement = gson.fromJson(json, JsonElement.class);


        if (contentType != null && jsonElement.isJsonObject()){
            //JsonElement jsonElement = JsonParser.parseString(json);
            horas = gson.fromJson( jsonElement, MedicoHorario.class);
            especialidad = gson.fromJson( jsonElement, MedicoEspecialidad.class);
            System.out.println("valor de especialidad : " + especialidad);
            System.out.println("valor de horario : " + horas);
            System.out.println("Es json");
        }
        else {
            System.out.println("No es json");
        }
        if (uri.endsWith("/cargaconsulta")) {
            System.out.println("Entrando a carga de consulta");
            String Datos = request.getReader().readLine();
            System.out.println("Datos : " +Datos );
            if (Datos == null){
                Datos = json;
            }
            System.out.println("Datos : " + json );
            ObjectMapper objectMappeCLr = new ObjectMapper();
            String jsonclass = medicoService.listarHistorialConsulta(Integer.parseInt(Datos)).isEmpty() ? "" : objectMappeCLr.writeValueAsString(medicoService.listarHistorialConsulta(Integer.parseInt(Datos)));

            System.out.println("conversion de clase : " + jsonclass );
            System.out.println("Especialidades");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonclass);
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        if (uri.endsWith("/crearconsulta")) {
            System.out.println("Entrando a crear de consulta");
            String myString = request.getParameter("valor");
            String Datos = request.getReader().readLine();
            String guardar = json;
            System.out.println("Datos : " + guardar);
            if (guardar != null && !guardar.isEmpty()){
                response.getWriter().write(String.valueOf(true));
            }

            System.out.println("Consultas");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        if (uri.endsWith("/cargaexamenes")) {
            System.out.println("Entrando a carga de examenes");
            String Datos = request.getReader().readLine();
            System.out.println("Datos : " +Datos );

            System.out.println("Datos : " + json );
            if (Datos == null){
                Datos = json;
            }
            ObjectMapper objectMappeCLr = new ObjectMapper();
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonclass = medicoService.listarExamenesPaciente(Integer.parseInt(Datos)).isEmpty() ? "" : objectMappeCLr.writeValueAsString(medicoService.listarExamenesPaciente(Integer.parseInt(Datos)));

            System.out.println("conversion de clase : " + jsonclass );
            System.out.println("Examenes");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonclass);
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        if (uri.endsWith("/especialidad")) {
            if (listarEspecialidad( Valor) == false){
                System.out.println("Creando especialidad");
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                System.out.println(horas);
                crearHorario(horas,Valor);
                response.getWriter().write(String.valueOf(crearEspecialidad(especialidad,Valor)));
                response.setStatus(HttpServletResponse.SC_OK);
                return;

            }



        }

        if (uri.endsWith("/cargaespecialidad")) {
            System.out.println("Solicitud para crear especialidad");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(String.valueOf(crearEspecialidadAdmin(especialidad, Valor)));
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        if (uri.endsWith("/cargaespecialidad-cambiarcosto")) {
            System.out.println("Solicitud para cambiar especialidad");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            System.out.println(cambiarCosto(especialidad, Valor));
            response.getWriter().write(String.valueOf(cambiarCosto(especialidad, Valor)));
            response.setStatus(HttpServletResponse.SC_OK);
            return;
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

    public boolean crearEspecialidadAdmin(MedicoEspecialidad especialidad, Usuario usuario) throws IOException {
        if (especialidad == null || usuario == null){
            return false;
        }
        medicoService.crearEspecialidadAdmin(especialidad, usuario);
        return true;
    }

    public boolean crearHorario(MedicoHorario horario, Usuario usuario) throws IOException {
        if (horario == null || usuario == null){
            return false;
        }
        medicoService.crearHorario(horario, usuario);
        return true;
    }

    public boolean cambiarCosto(MedicoEspecialidad especialidad, Usuario usuario) throws IOException {
        if (especialidad == null || usuario == null){
            return false;
        }
        medicoService.cambiarCosto(especialidad, usuario);
        return true;
    }

    public boolean listarEspecialidad( Usuario usuario) throws IOException {
        if (usuario == null){
            return false;
        }
        List<MedicoEspecialidad> especialidad = medicoService.listarEspecialidad(usuario);
        if (especialidad.isEmpty() || especialidad == null){
            return false;
        }
        return true;
    }

    public List<MedicoEspecialidad> listarEspecialidadAgregada( Usuario usuario) throws IOException {
        List<MedicoEspecialidad> especialidad = medicoService.listarEspecialidad(usuario);
        return especialidad;
    }

}
