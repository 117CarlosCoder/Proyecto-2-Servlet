package com.ipc2.api.apirest.controller;

import com.google.gson.JsonObject;
import com.ipc2.api.apirest.data.Conexion;
import com.ipc2.api.apirest.model.Usuario.Usuario;
import com.ipc2.api.apirest.service.UsuarioService;
import com.ipc2.api.apirest.utils.GsonUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/usuarios/*")

public class UsuarioController extends HttpServlet {

    Conexion conexion = new Conexion();

    private GsonUtils<Usuario> gsonUsuario;
    private UsuarioService usuarioService;
    private Usuario usuarioLogin;

    public UsuarioController() {
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession(true);
        session.setAttribute("user", usuarioLogin);
        getServletContext().setAttribute("userSession", session);
        session.setMaxInactiveInterval(3600);
        session.setAttribute("conexion", conexion.obtenerConexion());
        String contentType = request.getContentType();
        String uri = request.getRequestURI();
        Usuario usuario = null;
        System.out.println(uri);
        System.out.println("Sesion en usuariocontroller : " + session);

        if (contentType != null && contentType.startsWith("application/json")){
           usuario = gsonUsuario.readFromJson(request, Usuario.class);
            System.out.println("Es json");
        }
        else {
            System.out.println("No es json");
        }
        System.out.println(usuario);

        if (uri.endsWith("/iniciar")) {
            System.out.println("iniciando sesion");
            iniciarUsuario(request,response,session,usuario);
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        if (uri.endsWith("/cerrar")) {
            System.out.println("cerrando sesion");
            cerrarUsuario(request,response);
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        if (uri.endsWith("/crear")) {
            System.out.println("creando usuario");
            crearUsuario(usuario,response);

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
            response.setStatus(HttpServletResponse.SC_OK);
            System.out.println("Sesion Iniciada");
        } else {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(String.valueOf(crearJson(false)));
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    public JsonObject crearJson(boolean valor){
        if (valor){
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("tipo", usuarioLogin.getTipo());
            jsonObject.addProperty("valor",true);
            return jsonObject;
        }
        if(valor == false){
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("tipo", false);
            jsonObject.addProperty("valor",false);
            return jsonObject;
        }

        return null;
    }
    public boolean crearUsuario(Usuario user, HttpServletResponse response) throws IOException {
        System.out.println(user);
        if (user == null) return false;
        usuarioService.crearUsuario(user);
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("Usuario Creado");
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
