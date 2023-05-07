package com.ipc2.api.apirest.model.Usuario;

import com.ipc2.api.apirest.model.Admin.Admin;
import com.ipc2.api.apirest.model.Laboratorio.Laborartorio;
import com.ipc2.api.apirest.model.Medico.Medico;
import com.ipc2.api.apirest.model.Paciente.Paciente;

import java.util.List;


public class User {
        private List<Admin> admins;
        private List<Laborartorio> laboratorios;
        private List<Medico> medicos;
        private List<Paciente> pacientes;


    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public void setPacientes(List<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    public List<Admin> getAdmins() {
        return admins;
    }

    public void setAdmins(List<Admin> admins) {
        this.admins = admins;
    }

    public List<Laborartorio> getLaborartorios() {
        return laboratorios;
    }

    public void setLaborartorios(List<Laborartorio> laborartorios) {
        this.laboratorios = laborartorios;
    }

    public List<Medico> getMedicos() {
        return medicos;
    }

    public void setMedicos(List<Medico> medicos) {
        this.medicos = medicos;
    }
// getters y setters

}
