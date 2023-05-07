package com.ipc2.api.apirest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.ipc2.api.apirest.data.Conexion;
import com.ipc2.api.apirest.model.Admin.Admin;
import com.ipc2.api.apirest.model.Laboratorio.Laborartorio;
import com.ipc2.api.apirest.model.Medico.Medico;
import com.ipc2.api.apirest.model.Medico.MedicoEspecialidad;
import com.ipc2.api.apirest.model.Medico.MedicoHorario;
import com.ipc2.api.apirest.model.Paciente.Paciente;
import com.ipc2.api.apirest.model.Usuario.User;
import com.ipc2.api.apirest.model.Usuario.Usuario;
import com.ipc2.api.apirest.reportes.GenerarReportes.GenerarReportes;
import com.ipc2.api.apirest.service.UsuarioService;
import com.ipc2.api.apirest.utils.GsonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/usuarios/*")

public class UsuarioController extends HttpServlet {

    private GsonUtils<MedicoEspecialidad> gsonEspecialidad;
    private GsonUtils<MedicoHorario> gsonHorario;

    Conexion conexion = new Conexion();

    private GenerarReportes generarPDF;
    private GsonUtils<Usuario> gsonUsuario;
    private UsuarioService usuarioService;
    private Usuario usuarioLogin;

    public UsuarioController() {
        gsonHorario = new GsonUtils<>();
        gsonEspecialidad = new GsonUtils<>();
        generarPDF = new GenerarReportes();
        gsonUsuario = new GsonUtils<>();
        usuarioService = new UsuarioService(conexion.obtenerConexion());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /*String pathInfo = request.getPathInfo();

        if(pathInfo == null || pathInfo.equals("/")){

            var usuarios = usuarioService.listar();

            gsonUsuario.sendAsJson(response, usuarios);
            return;
        }

        response.setStatus(HttpServletResponse.SC_OK);
        gsonUsuario.sendAsJson(response, usuarioService.listar());*/

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession(true);
        session.setAttribute("user", usuarioLogin);
        getServletContext().setAttribute("userSession", session);
        session.setMaxInactiveInterval(3600);
        session.setAttribute("conexion", conexion.obtenerConexion());


        System.out.println("Calculando error");
        String json = request.getReader().readLine();
        Gson gson = new Gson();
        MedicoEspecialidad especialidad = null;
        MedicoHorario horas = null;
        JsonElement jsonElement = null;
        try {
            jsonElement =gson.fromJson(json, JsonElement.class);
        } catch (JsonSyntaxException e) {

        }
        String contentType = request.getContentType();
        String uri = request.getRequestURI();
        String Datos = "";
        Usuario usuario = null;
        User usuariocargado = null;
        System.out.println("Calculando error2");
        //Part filePart = request.getPart("file"); // Obtiene la parte del archivo de la solicitud
        //InputStream fileContent = filePart.getInputStream(); // Obtiene el flujo de entrada del archivo

        System.out.println("Archivo entrante : " + json);
        System.out.println(uri);
        System.out.println("Sesion en usuariocontroller : " + session);

        if (uri.endsWith("/cerrar")) {
            System.out.println("cerrando sesion");
            cerrarUsuario(request,response);
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        if (contentType != null && jsonElement.isJsonObject()){
            //JsonElement jsonElement = JsonParser.parseString(json);


            //usuariocargado = gson.fromJson( jsonElement, Usuario.class);


            try {
                System.out.println("carga1");
                usuario = gson.fromJson(jsonElement, Usuario.class);
                horas = gson.fromJson( jsonElement, MedicoHorario.class);
                System.out.println("carga2");
                usuariocargado = gson.fromJson(jsonElement, User.class);
                System.out.println("Usuario cargado: " + usuariocargado);
                System.out.println("carga3");
                especialidad = gson.fromJson( jsonElement, MedicoEspecialidad.class);
            } catch (Exception e) {

            }


            System.out.println("valor de especialidad : " + especialidad);
            System.out.println("valor de horario : " + horas);
            System.out.println("Es json");
        }

        System.out.println("Entrando a los procesos");

        if (contentType != null && contentType.startsWith("application/json")){
           //usuario = gsonUsuario.readFromJson(request, Usuario.class);

            System.out.println("Es json");
        }
        else {
            Datos = request.getReader().readLine();

            System.out.println("Esto es : " + Datos);
            System.out.println("No es json");
        }
        System.out.println("valor de usuario : " + usuario);

        if (uri.endsWith("/iniciar")) {
            System.out.println("iniciando sesion");
            iniciarUsuario(request,response,session,usuario);
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }


        if (uri.endsWith("/crear")) {
            System.out.println("creando usuario");
            crearUsuario(usuario,response);
            return;

        }
        if (uri.endsWith("/cargardatos")) {
            System.out.println("cargando datos");
            System.out.println("cargando datos : " + json);
            ObjectMapper objectMapper = new ObjectMapper();
            Paciente pruebausuario = usuariocargado.getPacientes().get(0);
            try{
                System.out.println(usuariocargado.getAdmins());

            }
            catch (Exception e){
                System.out.println("Error al cargar el admins: " + e.getMessage());
                e.printStackTrace();
            }

            Admin admins = usuariocargado.getAdmins().get(0);
            Laborartorio laboratorios = usuariocargado.getLaborartorios().get(0);
            Medico medicos = usuariocargado.getMedicos().get(0);

            Usuario probando = new Usuario(pruebausuario.getCui(), pruebausuario.getTipo(), pruebausuario.getNombre(), pruebausuario.getNombre_usuario(), pruebausuario.getContraseña(), pruebausuario.getDireccion(), pruebausuario.getCorreo(), pruebausuario.getFecha_nacimiento(), pruebausuario.getSaldo());
            Usuario adm = new Usuario(admins.getCui(), admins.getTipo(), admins.getNombre(), admins.getNombre_usuario(), admins.getContraseña(), admins.getDireccion(), admins.getCorreo(), admins.getFecha_nacimiento(), admins.getSaldo());
            Usuario med = new Usuario(medicos.getCui(), medicos.getTipo(), medicos.getNombre(), medicos.getNombre_usuario(), medicos.getContraseña(), medicos.getDireccion(), medicos.getCorreo(), medicos.getFecha_nacimiento(), medicos.getSaldo());
            Usuario lab = new Usuario(laboratorios.getCui(), laboratorios.getTipo(), laboratorios.getNombre(), laboratorios.getNombre_usuario(), laboratorios.getContraseña(), laboratorios.getDireccion(), laboratorios.getCorreo(), laboratorios.getFecha_nacimiento(), laboratorios.getSaldo());

            //Usuario probando = pruebausuario;
            //usuariocargado = objectMapper.readValue(request.getInputStream(), Usuario.class);
            response.getWriter().write(String.valueOf(crearUsuario(probando,response)));
            response.getWriter().write(String.valueOf(crearUsuario(adm,response)));
            response.getWriter().write(String.valueOf(crearUsuario(med,response)));
            response.getWriter().write(String.valueOf(crearUsuario(lab,response)));

            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        else {
            System.out.println("error");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    public void iniciarUsuario(HttpServletRequest request, HttpServletResponse response,HttpSession session, Usuario usuario) throws IOException {

        String username = usuario.getCorreo();
        String email = usuario.getCorreo();
        String password = usuario.getContraseña();

        if (validarUsuario(username, password, email)) {
            session.setAttribute("user", usuarioLogin);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(String.valueOf(crearJson(true)));
            System.out.println("Sesion Iniciada");
        } else {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(String.valueOf(crearJson(false)));
        }
    }

    public JsonObject crearJson(boolean valor){
        if (valor){
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("tipo", usuarioLogin.getTipo());
            jsonObject.addProperty("valor",true);
            return jsonObject;
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("tipo", false);
        jsonObject.addProperty("valor",false);
        return jsonObject;

    }
    public boolean crearUsuario(Usuario user, HttpServletResponse response) throws IOException {
        System.out.println(user);
        if (user == null) return false;
        usuarioService.crearUsuario(user);
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("Usuario Creado");
        response.setStatus(HttpServletResponse.SC_OK);
        return true;
    }
    public boolean validarUsuario(String username, String password, String email) {
        var oUsuario = usuarioService.obtenerUsuario(username, password, email);
        if (oUsuario.isEmpty()) return false;
        usuarioLogin = oUsuario.get();
        return true;
    }

    public boolean cerrarUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute("user");
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        System.out.println("Sesion Cerrada");

        response.getWriter().write(String.valueOf(false));
        return false;
    }

    /*private int processPath(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        String httpMethod = request.getMethod();

        if(httpMethod.equals("PUT") || httpMethod.equals("DELETE")) {
            if(pathInfo == null || pathInfo.equals("/")){

                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return -1;
            }
        }

        return -1;

    }*/
}
