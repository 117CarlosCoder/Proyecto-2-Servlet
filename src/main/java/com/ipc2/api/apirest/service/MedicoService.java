package com.ipc2.api.apirest.service;

import com.ipc2.api.apirest.data.MedicoDB;
import com.ipc2.api.apirest.model.Medico.MedicoEspecialidad;
import com.ipc2.api.apirest.model.Usuario.Usuario;
import com.sun.tools.jconsole.JConsoleContext;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Optional;

public class MedicoService {
    private MedicoDB medicoDB;
    public MedicoService(Connection conectar) {
        medicoDB = new MedicoDB(conectar);
    }

    public void crearEspecialidad(MedicoEspecialidad especialidad, Usuario usuario) {
        medicoDB.crearEspecialidad(especialidad,usuario);
    }

    public Optional<MedicoEspecialidad> listarEspecialidad(Usuario usuario) {
        Optional<MedicoEspecialidad> especialidad = medicoDB.listarEspecialidad(usuario);
        System.out.println("la especialidad da: " + especialidad);
        return especialidad;
    }

}
