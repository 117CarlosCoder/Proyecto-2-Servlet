package com.ipc2.api.apirest.service;

import com.ipc2.api.apirest.data.LaboratorioDB;
import com.ipc2.api.apirest.model.Laboratorio.LaboratorioInfoExamen;
import com.ipc2.api.apirest.model.Usuario.Usuario;

import java.sql.Connection;
import java.util.Optional;

public class LaboratorioService {

    private LaboratorioDB laboratorioDB;
    public LaboratorioService(Connection conectar) {
        laboratorioDB = new LaboratorioDB(conectar);
    }
    public void crearExamLab(LaboratorioInfoExamen lab, Usuario usuario) {
        laboratorioDB.crearInfoExam(lab,usuario);
    }

    public Optional<LaboratorioInfoExamen> listarExamenInfo(Usuario usuario) {

        return laboratorioDB.listarInfoExamen(usuario);
    }
}
