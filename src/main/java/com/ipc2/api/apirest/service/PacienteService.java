package com.ipc2.api.apirest.service;
import com.ipc2.api.apirest.data.PacienteDB;
import com.ipc2.api.apirest.model.Medico.Especialidades;
import com.ipc2.api.apirest.model.Medico.MedicoEspecialidad;
import com.ipc2.api.apirest.model.Paciente.ConsultaPaciente;
import com.ipc2.api.apirest.model.Paciente.ListarMedicosNombre;
import com.ipc2.api.apirest.model.Usuario.Usuario;

import java.sql.Connection;
import java.util.List;

public class PacienteService {

    private PacienteDB pacienteDB;
    public PacienteService(Connection conectar) {
        pacienteDB = new PacienteDB(conectar);
    }

    public void solicitarConsulta(ConsultaPaciente paciente) {
        pacienteDB.solicitarConsulta(paciente);
    }

    public List<ListarMedicosNombre> listarMedicosNombres(String med) {
        List<ListarMedicosNombre> medicos= pacienteDB.listarMedicosNombre(med);
        System.out.println("los medicos : " + medicos);
        return medicos;
    }

    public List<ListarMedicosNombre> listarMedico() {
        List<ListarMedicosNombre> medicos= pacienteDB.listarMedicos();
        System.out.println("los medicos : " + medicos);
        return medicos;
    }



}
