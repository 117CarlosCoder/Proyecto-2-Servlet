package com.ipc2.api.apirest.service;
import com.ipc2.api.apirest.data.PacienteDB;
import com.ipc2.api.apirest.data.UsuarioDB;
import com.ipc2.api.apirest.model.Medico.Especialidades;
import com.ipc2.api.apirest.model.Medico.MedicoEspecialidad;
import com.ipc2.api.apirest.model.Paciente.ConsultaPaciente;
import com.ipc2.api.apirest.model.Paciente.ListarMedicosNombre;
import com.ipc2.api.apirest.model.Usuario.Usuario;

import java.sql.Connection;
import java.util.List;

public class PacienteService {

    private PacienteDB pacienteDB;
    private UsuarioDB usuarioDB;
    public PacienteService(Connection conectar) {

        pacienteDB = new PacienteDB(conectar);
        usuarioDB = new UsuarioDB(conectar);
    }

    public void solicitarConsulta(ConsultaPaciente paciente) {
        pacienteDB.solicitarConsulta(paciente);
    }

    public void cargarConsulta(ConsultaPaciente paciente) {
        pacienteDB.cargarConsulta(paciente);
    }

    public List<ListarMedicosNombre> listarMedicosNombres(String med) {
        List<ListarMedicosNombre> medicos= pacienteDB.listarMedicosNombre(med);
        System.out.println("los medicos : " + medicos);
        return medicos;
    }

    public Integer obtenrId(Usuario usuario) {
        return usuarioDB.obtnerId(usuario);
    }
    public List<ListarMedicosNombre> listarMedico() {
        List<ListarMedicosNombre> medicos= pacienteDB.listarMedicos();
        System.out.println("los medicos : " + medicos);
        return medicos;
    }



}
