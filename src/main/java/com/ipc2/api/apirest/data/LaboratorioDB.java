package com.ipc2.api.apirest.data;

import com.ipc2.api.apirest.model.Laboratorio.LaboratorioInfoExamen;
import com.ipc2.api.apirest.model.Laboratorio.TipoExamen;
import com.ipc2.api.apirest.model.Laboratorio.ValorExamen;
import com.ipc2.api.apirest.model.Usuario.Usuario;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class LaboratorioDB {
    private Connection conexion;
    public LaboratorioDB(Connection conexion) {
        this.conexion = conexion;
    };

    public void crearInfoExam(LaboratorioInfoExamen infoexamen,Usuario usuario) {
        int cui = usuario.getCui();
        String query = "INSERT INTO EXAMENLABORATORIO (id, cui, nombre, costo, descripcion) VALUES (?, ?, ?, ?, ?)";
        try (var preparedStatement = conexion.prepareStatement(query)) {
            preparedStatement.setInt(1, infoexamen.getId());
            preparedStatement.setInt(2, cui);
            preparedStatement.setString(3, infoexamen.getNombre());
            preparedStatement.setBigDecimal(4, infoexamen.getCosto());
            preparedStatement.setString(5, infoexamen.getDescripcion());
            preparedStatement.executeUpdate();
            System.out.println("Tipo de examen creado");
        } catch (SQLException e) {
            System.out.println("Error al crear tipo de examen: " + e);
        }
    }

    public void cargarTipoExam(TipoExamen infoexamen) {
        String query = "INSERT INTO TIPOEXAMEN (id, nombre, descripcion) VALUES (?, ?, ?)";
        try (var preparedStatement = conexion.prepareStatement(query)) {
            preparedStatement.setInt(1, infoexamen.getId());
            preparedStatement.setString(2, infoexamen.getNombre());
            preparedStatement.setString(3, infoexamen.getDescripcion());
            preparedStatement.executeUpdate();
            System.out.println("Tipo de examen creado");
        } catch (SQLException e) {
            System.out.println("Error al crear tipo de examen: " + e);
        }
    }

    public void cargarValorExam(ValorExamen infoexamen) {
        String query = "INSERT INTO EXAMENCOSTO (id, cui, costo) VALUES (?, ?, ?)";
        try (var preparedStatement = conexion.prepareStatement(query)) {
            preparedStatement.setInt(1, infoexamen.getId());
            preparedStatement.setInt(2, infoexamen.getCui());
            preparedStatement.setBigDecimal(3, infoexamen.getCosto());
            preparedStatement.executeUpdate();
            System.out.println("Tipo de examen creado");
        } catch (SQLException e) {
            System.out.println("Error al crear tipo de examen: " + e);
        }
    }

    public Optional<LaboratorioInfoExamen> listarInfoExamen(Usuario usuario) {
        int cui = usuario.getCui();
        String query =  "SELECT * FROM EXAMENLABORATORIO WHERE cui = ?";
        LaboratorioInfoExamen labinfo = null;
        try (var preparedStatement = conexion.prepareStatement(query)) {

            preparedStatement.setInt(1, cui);


            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    var id = resultSet.getInt("id");
                    var costo = resultSet.getBigDecimal("costo");
                    var nombre = resultSet.getString("nombre");
                    var descripcion = resultSet.getString("descripcion");
                    labinfo = new LaboratorioInfoExamen(id, cui, nombre,costo, descripcion);
                }
            }
        }catch (SQLException e) {
            System.out.println("Error al listar especialidades: " + e);
        }

        return Optional.ofNullable(labinfo);
    }

}
