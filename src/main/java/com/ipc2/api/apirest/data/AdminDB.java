package com.ipc2.api.apirest.data;

import com.ipc2.api.apirest.model.Admin.Porcentaje;
import com.ipc2.api.apirest.model.Laboratorio.LaboratorioInfoExamen;
import com.ipc2.api.apirest.model.Medico.MedicoEspecialidad;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class AdminDB {

    private Connection conexion;
    public AdminDB(Connection conexion) {
        this.conexion = conexion;
    };

    public void crearExamendAdmin(LaboratorioInfoExamen labexamen) {

        String query = "INSERT INTO TIPOEXAMENADMIN (id, cui, costo, nombre, descripcion) VALUES (?, ?, ?, ?, ?)";
        try (var preparedStatement = conexion.prepareStatement(query)) {
            preparedStatement.setNull(1, Types.INTEGER);
            preparedStatement.setInt(2, labexamen.getCui());
            preparedStatement.setBigDecimal(3, labexamen.getCosto());
            preparedStatement.setString(4, labexamen.getNombre());
            preparedStatement.setString(5, labexamen.getDescripcion());
            preparedStatement.executeUpdate();
            System.out.println("Especialidad creada admin");
        } catch (SQLException e) {
            System.out.println("Error al crear especialidad admin: " + e);
        }
    }

    public void crearPorcentaje(Porcentaje porcentaje) {

        String query = "INSERT INTO Porcentaje (id,fecha, porcentaje) VALUES (?, ?, ?)";
        try (var preparedStatement = conexion.prepareStatement(query)) {
            preparedStatement.setInt(1, porcentaje.getId());
            preparedStatement.setString(2, porcentaje.getFecha());
            preparedStatement.setBigDecimal(3, porcentaje.getPorcentaje());
            preparedStatement.executeUpdate();
            System.out.println("Porcentaje creado admin");
        } catch (SQLException e) {
            System.out.println("Error al crear porcentaje admin: " + e);
        }
    }

    public List<Porcentaje> listarPorcentaje() {
        var porcentajes = new ArrayList<Porcentaje>();
        try (var stmt = conexion.createStatement(); var resultSet = stmt.executeQuery("SELECT * FROM PORCENTAJE WHERE id = (SELECT MAX(id) FROM PORCENTAJE)")) {

            while (resultSet.next()) {

                var id = resultSet.getInt("id");
                var fecha  = resultSet.getString("fecha") ;
                var porcentaje = resultSet.getBigDecimal("porcentaje");

                var porcentajeadmin = new Porcentaje(id, fecha, porcentaje);

                porcentajes.add(porcentajeadmin);
            }
        }catch (SQLException e) {
            System.out.println("Error al consultar porcentaje: " + e);
        }
        return porcentajes;
    }
    public void eliminarEspecialidad(int id) {
        try (var stmt = conexion.prepareStatement("DELETE FROM ESPECIALIDADADMIN WHERE id = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e);
        }
    }

    public void eliminarExamen(int id) {
        try (var stmt = conexion.prepareStatement("DELETE FROM TIPOEXAMENADMIN WHERE id = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e);
        }
    }

}
