package com.ipc2.api.apirest.service;

import com.ipc2.api.apirest.data.AdminDB;
import com.ipc2.api.apirest.data.LaboratorioDB;
import com.ipc2.api.apirest.model.Admin.Porcentaje;
import com.ipc2.api.apirest.model.Laboratorio.LaboratorioInfoExamen;
import com.ipc2.api.apirest.model.Paciente.ListarMedicosNombre;
import com.ipc2.api.apirest.model.Usuario.Usuario;

import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class AdminSevice {
    private AdminDB adminDB;
    public AdminSevice(Connection conectar) {
        adminDB = new AdminDB(conectar);
    }


    public void crearExamLabAdmin(LaboratorioInfoExamen lab) {
        adminDB.crearExamendAdmin(lab);
    }

    public void crearPorcentaje(Porcentaje porcentaje) {
        adminDB.crearPorcentaje(porcentaje);
    }

    public List<Porcentaje> listarPorcentaje() {
        List<Porcentaje> porcentaje= adminDB.listarPorcentaje();
        System.out.println("los porcentaje : " + porcentaje);
        return porcentaje;
    }
    public void eliminarEspecialidad(int id){
        adminDB.eliminarEspecialidad(id);
    }

    public void eliminarExamen(int id){
        adminDB.eliminarExamen(id);
    }
}
