package com.ipc2.api.apirest.service;
import com.ipc2.api.apirest.data.UsuarioDB;
import com.ipc2.api.apirest.model.Usuario.Usuario;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class UsuarioService {
    private UsuarioDB usuarioDB;
    public UsuarioService(Connection conectar) {
        usuarioDB = new UsuarioDB(conectar);
    }
    public void crearUsuario(Usuario user) {
        usuarioDB.crear(user);
    }
    public List<Usuario> listar() {
        return usuarioDB.listar();
    }
    public Optional<Usuario> obtenerUsuario(String username, String password, String email) {
        return usuarioDB.obtenerUsuario(username, password, email);
    }
}
