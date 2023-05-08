package com.ipc2.api.apirest.model.CargarDatos;

import com.ipc2.api.apirest.model.Laboratorio.TipoExamen;
import com.ipc2.api.apirest.model.Medico.Especialidades;
import com.ipc2.api.apirest.model.Medico.Medico;
import com.ipc2.api.apirest.model.Paciente.ConsultaPaciente;
import com.ipc2.api.apirest.model.Paciente.Paciente;

import java.util.List;

public class Carga {
    private List<Especialidades> especialidades;
    private List<TipoExamen> tipos_examenes;
    private List<ConsultaPaciente> consultas;

    public List<Especialidades> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<Especialidades> especialidades) {
        this.especialidades = especialidades;
    }

    public List<TipoExamen> getTipos_examenes() {
        return tipos_examenes;
    }

    public void setTipos_examenes(List<TipoExamen> tipos_examenes) {
        this.tipos_examenes = tipos_examenes;
    }

    public List<ConsultaPaciente> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<ConsultaPaciente> consultas) {
        this.consultas = consultas;
    }
}
