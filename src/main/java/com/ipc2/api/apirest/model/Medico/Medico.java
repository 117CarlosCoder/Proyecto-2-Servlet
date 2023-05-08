package com.ipc2.api.apirest.model.Medico;

import com.ipc2.api.apirest.model.Usuario.Usuario;

import java.util.List;

public class Medico extends Usuario {

    private List<String> horarios;
    private List<CargaEspecialidad> especialidades;

    public List<String> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<String> horarios) {
        this.horarios = horarios;
    }

    public List<CargaEspecialidad> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<CargaEspecialidad> especialidades) {
        this.especialidades = especialidades;
    }
}
