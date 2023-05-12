package com.ipc2.api.apirest.service;

import com.ipc2.api.apirest.data.LaboratorioDB;
import com.ipc2.api.apirest.model.Laboratorio.LaboratorioInfoExamen;
import com.ipc2.api.apirest.model.Laboratorio.TipoExamen;
import com.ipc2.api.apirest.model.Laboratorio.ValorExamen;
import com.ipc2.api.apirest.model.Usuario.Usuario;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class LaboratorioService {

    private LaboratorioDB laboratorioDB;
    public LaboratorioService(Connection conectar) {
        laboratorioDB = new LaboratorioDB(conectar);
    }
    public void crearExamLab(LaboratorioInfoExamen lab, Usuario usuario) {
        laboratorioDB.crearInfoExam(lab,usuario);
    }

    public void cargarTipoExam(TipoExamen lab) {
        laboratorioDB.cargarTipoExam(lab);
    }

    public void cargarValorExam(ValorExamen lab) {
        laboratorioDB.cargarValorExam(lab);
    }
    public Optional<LaboratorioInfoExamen> listarExamenInfo(Usuario usuario) {

        return laboratorioDB.listarInfoExamen(usuario);
    }

    public List<LaboratorioInfoExamen> listarExamenAdmin() {
        System.out.println("Cargando examenes");
        return laboratorioDB.listarExamenAdmin();
    }
}
