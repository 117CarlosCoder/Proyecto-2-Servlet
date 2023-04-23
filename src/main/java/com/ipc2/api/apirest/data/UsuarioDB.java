package com.ipc2.api.apirest.data;

import com.ipc2.api.apirest.model.Usuario.Usuario;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioDB {
    private Connection conexion;
    public UsuarioDB(Connection conexion) {
        this.conexion = conexion;
    };

    public void crear(Usuario user) {
        String query = "INSERT INTO USUARIO (cui, tipo, nombre, nombre_usuario, contraseña, direccion, correo_electronico, fecha_de_nacimiento, saldo) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";
        try (var preparedStatement = conexion.prepareStatement(query)) {
            preparedStatement.setInt(1, user.getCui());
            preparedStatement.setString(2, user.getTipo());
            preparedStatement.setString(3, user.getNombre());
            preparedStatement.setString(4, user.getNombre_usuario());
            preparedStatement.setString(5, user.getContraseña());
            preparedStatement.setString(6, user.getDireccion());
            preparedStatement.setString(7, user.getCorreo());
            preparedStatement.setString(8, user.getFecha_nacimiento());
            preparedStatement.setBigDecimal(9, user.getSaldo());
            preparedStatement.executeUpdate();
            System.out.println("Usuario creado");
        } catch (SQLException e) {
            System.out.println("Error al crear usuario: " + e);
        }
    }
    public List<Usuario> listar() {
        var usuarios = new ArrayList<Usuario>();
        try (var stmt = conexion.createStatement(); var resultSet = stmt.executeQuery("SELECT * FROM USUARIO")) {

            while (resultSet.next()) {

                var cui = resultSet.getInt("cui");
                var tipo = resultSet.getString("tipo");
                var nombre  = resultSet.getString("nombre") ;
                var nombre_usuario = resultSet.getString("nombre_usuario");
                var contraseña = resultSet.getString("contraseña");
                var direccion = resultSet.getString("direccion");
                var correo = resultSet.getString("correo_electronico");
                var fecha_nacimiento = resultSet.getString("fecha_de_nacimiento");
                var saldo = resultSet.getBigDecimal("saldo");

                var usuario = new Usuario(cui,tipo,nombre, nombre_usuario, contraseña, direccion, correo, fecha_nacimiento,saldo);

                usuarios.add(usuario);
            }
        }catch (SQLException e) {
            System.out.println("Error al consultar: " + e);
        }
        return usuarios;
    }

    public Optional<Usuario> obtenerUsuario(String nombre_usuario, String contraseña, String correo_electronico) {
        String query = "SELECT * FROM USUARIO WHERE nombre_usuario = ? AND contraseña = ? OR correo_electronico = ? AND contraseña = ? ";
        Usuario usuario = null;

        try (var preparedStatement = conexion.prepareStatement(query)) {

            preparedStatement.setString(1, nombre_usuario);
            preparedStatement.setString(2, contraseña);
            preparedStatement.setString(3, correo_electronico);
            preparedStatement.setString(4, contraseña);


            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    var cui = resultSet.getInt("cui");
                    var tipo = resultSet.getString("tipo");
                    var nombre = resultSet.getString("nombre");
                    var direccion = resultSet.getString("direccion");
                    var fecha_de_nacimiento = resultSet.getString("fecha_de_nacimiento");
                    var saldo = resultSet.getBigDecimal("saldo");
                    usuario = new Usuario(cui, tipo, nombre, nombre_usuario, contraseña,direccion,correo_electronico,fecha_de_nacimiento,saldo);
                }
            }
        }catch (SQLException e) {
            System.out.println("Error al consultar: " + e);
        }

        return Optional.ofNullable(usuario);
    }



    /*public Optional<Usuario> obtenerUsuario(String username, String password, String email) {
        String query = "SELECT * FROM USUARIOTIENDA_N WHERE username = ? AND password = ? OR email = ? AND password = ? ";
        Usuario usuario = null;

        try (var preparedStatement = conexion.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);


            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    var codigo = resultSet.getInt("codigo");
                    var nombre = resultSet.getString("nombre");
                    var tienda = resultSet.getInt("tienda");
                    usuario = new Usuario(codigo, nombre, tienda, username, password,email);
                }
            }
        }catch (SQLException e) {
            System.out.println("Error al consultar: " + e);
        }

        return Optional.ofNullable(usuario);
    }*/
}
