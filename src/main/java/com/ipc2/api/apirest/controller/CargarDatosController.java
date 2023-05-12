package com.ipc2.api.apirest.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.ipc2.api.apirest.data.Conexion;
import com.ipc2.api.apirest.model.Admin.Admin;
import com.ipc2.api.apirest.model.CargarDatos.Carga;
import com.ipc2.api.apirest.model.Laboratorio.TipoExamen;
import com.ipc2.api.apirest.model.Medico.CargaEspecialidad;
import com.ipc2.api.apirest.model.Medico.Especialidades;
import com.ipc2.api.apirest.model.Medico.MedicoEspecialidad;
import com.ipc2.api.apirest.model.Medico.MedicoHorario;
import com.ipc2.api.apirest.model.Paciente.ConsultaPaciente;
import com.ipc2.api.apirest.model.Paciente.Paciente;
import com.ipc2.api.apirest.model.Usuario.Usuario;
import com.ipc2.api.apirest.service.LaboratorioService;
import com.ipc2.api.apirest.service.MedicoService;
import com.ipc2.api.apirest.service.PacienteService;
import com.ipc2.api.apirest.utils.GsonUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/cargas/*")


public class CargarDatosController extends HttpServlet {
    Conexion conexion = new Conexion();
    private GsonUtils<MedicoEspecialidad> gsonEspecialidad;
    private GsonUtils<MedicoHorario> gsonHorario;
    private MedicoService medicoService;
    private PacienteService pacienteService;
    private LaboratorioService laboratorioService;

    public CargarDatosController() {
        medicoService = new MedicoService(conexion.obtenerConexion());
        pacienteService = new PacienteService(conexion.obtenerConexion());
        laboratorioService = new LaboratorioService(conexion.obtenerConexion());

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Usuario Valor = null;
        HttpSession session = (HttpSession) getServletContext().getAttribute("userSession");

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

        if (uri.endsWith("/servicios")) {
            System.out.println("Cargando Servicios");

            try{
                Especialidades especialidades = datos.getEspecialidades().get(0);
                TipoExamen tipoExamenes = datos.getTipos_examenes().get(0);
                ConsultaPaciente consultaPacientes = datos.getConsultas().get(0);
                cargarEspecilidad(especialidades);
                cargarTipoExam(tipoExamenes);
                cargarConsulta(consultaPacientes);

                System.out.println(especialidades);
            }
            catch (Exception e){
                System.out.println("Error al cargar datos: " + e.getMessage());
                e.printStackTrace();
                System.out.println("error");
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }


            response.getWriter().write("Especialidades = " );
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

    }

    public boolean cargarEspecilidad(Especialidades especialidad) throws IOException {
        if (especialidad == null ){
            return false;
        }
        medicoService.cargarEspecialidades(especialidad);
        return true;
    }

    public boolean cargarTipoExam(TipoExamen infoexam) throws IOException {
        if (infoexam == null ){
            return false;
        }
        laboratorioService.cargarTipoExam(infoexam);
        return true;
    }

    public boolean cargarConsulta(ConsultaPaciente paciente) throws IOException {
        if (paciente == null ){
            return false;
        }
        pacienteService.cargarConsulta(paciente);
        return true;
    }



}
