package com.ipc2.api.apirest.data;

import com.google.protobuf.NullValue;
import com.ipc2.api.apirest.model.Medico.Especialidades;
import com.ipc2.api.apirest.model.Medico.MedicoEspecialidad;
import com.ipc2.api.apirest.model.Medico.MedicoHorario;
import com.ipc2.api.apirest.model.Usuario.Usuario;

import javax.lang.model.type.NullType;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MedicoDB {

    private Connection conexion;
    public MedicoDB(Connection conexion) {
        this.conexion = conexion;
    };
    public void crearEspecialidad(MedicoEspecialidad especialidad, Usuario usuario) {
        int cui = usuario.getCui();
        String query = "INSERT INTO ESPECIALIDADMEDICO (id, cui, costo, nombre, descripcion) VALUES (?, ?, ?, ?, ?)";
        try (var preparedStatement = conexion.prepareStatement(query)) {
            preparedStatement.setInt(1, especialidad.getId());
            preparedStatement.setInt(2, cui);
            preparedStatement.setBigDecimal(3, especialidad.getCosto());
            preparedStatement.setString(4, especialidad.getNombre());
            preparedStatement.setString(5, especialidad.getDescripcion());
            preparedStatement.executeUpdate();
            System.out.println("Especialidad creada");
        } catch (SQLException e) {
            System.out.println("Error al crear especialidad: " + e);
        }
    }

    public void crearEspecialidadAdmin(MedicoEspecialidad especialidades, Usuario usuario) {
        int cui = usuario.getCui();
        String query = "INSERT INTO ESPECIALIDADADMIN (id, cui, costo, nombre, descripcion) VALUES (?, ?, ?, ?, ?)";
        try (var preparedStatement = conexion.prepareStatement(query)) {
            preparedStatement.setNull(1, Types.INTEGER);
            preparedStatement.setInt(2, cui);
            preparedStatement.setBigDecimal(3, especialidades.getCosto());
            preparedStatement.setString(4, especialidades.getNombre());
            preparedStatement.setString(5, especialidades.getDescripcion());
            preparedStatement.executeUpdate();
            System.out.println("Especialidad creada");
        } catch (SQLException e) {
            System.out.println("Error al crear especialidad: " + e);
        }
    }

    public void crearHorario(MedicoHorario horario, Usuario usuario) {
        int cui = usuario.getCui();
        String query = "INSERT INTO HORARIO (id, cui, hora) VALUES (?, ?, ?)";
        try (var preparedStatement = conexion.prepareStatement(query)) {
            preparedStatement.setInt(1, horario.getId());
            preparedStatement.setInt(2, cui);
            preparedStatement.setString(3, horario.getHora());
            preparedStatement.executeUpdate();
            System.out.println("Horario creado");
        } catch (SQLException e) {
            System.out.println("Error al crear horario: " + e);
        }
    }

    public Optional<MedicoEspecialidad> listarEspecialidad(Usuario usuario) {
        int cui = usuario.getCui();
        String query =  "SELECT * FROM ESPECIALIDADMEDICO WHERE cui = ?";
        MedicoEspecialidad medico = null;
        try (var preparedStatement = conexion.prepareStatement(query)) {

            preparedStatement.setInt(1, cui);


            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    var id = resultSet.getInt("id");
                    var costo = resultSet.getBigDecimal("costo");
                    var nombre = resultSet.getString("nombre");
                    var descripcion = resultSet.getString("descripcion");
                    medico = new MedicoEspecialidad(id, cui, costo,nombre, descripcion);
                }
            }
        }catch (SQLException e) {
            System.out.println("Error al listar especialidades: " + e);
        }

        return Optional.ofNullable(medico);
    }

    public List<Especialidades> listarEspecialidades() {
        var especialidades = new ArrayList<Especialidades>();
        try (var stmt = conexion.createStatement(); var resultSet = stmt.executeQuery("SELECT * FROM ESPECIALIDAD")) {

            while (resultSet.next()) {

                var id = resultSet.getInt("id");
                var nombre  = resultSet.getString("nombre") ;
                var descripcion = resultSet.getString("descripcion");

                var especialidad = new Especialidades(id,nombre,descripcion);

                especialidades.add(especialidad);
            }
        }catch (SQLException e) {
            System.out.println("Error al consultar: " + e);
        }
        return especialidades;
    }

    public void cambiarCosto(MedicoEspecialidad especialidad, Usuario usuario) {
        int cui = usuario.getCui();
        String query = "UPDATE ESPECIALIDADMEDICO SET costo = ? WHERE cui = ?";
        try (var preparedStatement = conexion.prepareStatement(query)) {
            preparedStatement.setBigDecimal(1, especialidad.getCosto());
            preparedStatement.setInt(2, cui);
            preparedStatement.executeUpdate();
            System.out.println("Costo cambiado");
        } catch (SQLException e) {
            System.out.println("Error al cambiar costo: " + e);
        }
    }
}
