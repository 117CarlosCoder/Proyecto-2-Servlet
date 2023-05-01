package com.ipc2.api.apirest.service;

import com.ipc2.api.apirest.data.MedicoDB;
import com.ipc2.api.apirest.model.Medico.*;
import com.ipc2.api.apirest.model.Paciente.ConsultaPaciente;
import com.ipc2.api.apirest.model.Usuario.Usuario;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class MedicoService {
    private MedicoDB medicoDB;
    public MedicoService(Connection conectar) {
        medicoDB = new MedicoDB(conectar);
    }

    public void cambiarCosto(MedicoEspecialidad especialidad, Usuario usuario) {
        medicoDB.cambiarCosto(especialidad,usuario);
    }
    public void crearEspecialidad(MedicoEspecialidad especialidad, Usuario usuario) {
        medicoDB.crearEspecialidad(especialidad,usuario);
    }
    public void crearEspecialidadAdmin(MedicoEspecialidad especialidad, Usuario usuario) {
        medicoDB.crearEspecialidadAdmin(especialidad,usuario);
    }

    public Optional<MedicoEspecialidad> listarEspecialidad(Usuario usuario) {
        Optional<MedicoEspecialidad> especialidad = medicoDB.listarEspecialidad(usuario);
        System.out.println("la especialidad da: " + especialidad);
        return especialidad;
    }

    public List<ConsultaPaciente> listarHistorialConsulta(int id) {
        List<ConsultaPaciente> consultaPaciente = medicoDB.listarHistorialConsulta(id);
        System.out.println("la consultapaciente da: " + consultaPaciente);
        return consultaPaciente;
    }

    public List<ExamenPaciente> listarExamenesPaciente(int id) {
        List<ExamenPaciente> examenPacientes = medicoDB.listarHistorialExamenes(id);
        System.out.println("la examenespaciente da: " + examenPacientes);
        return examenPacientes;
    }
    public List<Especialidades> listarEspecialidades() {
        List<Especialidades> especialidades= medicoDB.listarEspecialidades();
        System.out.println("las especialidades : " + especialidades);
        return especialidades;
    }



    public void crearHorario(MedicoHorario horario, Usuario usuario) {
        medicoDB.crearHorario(horario,usuario);
    }

}
