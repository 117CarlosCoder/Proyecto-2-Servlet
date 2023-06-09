package com.ipc2.api.apirest.data;

import com.ipc2.api.apirest.model.Laboratorio.LaboratorioInfoExamen;
import com.ipc2.api.apirest.model.Medico.Especialidades;
import com.ipc2.api.apirest.model.Paciente.ConsultaPaciente;
import com.ipc2.api.apirest.model.Paciente.ListarMedicosNombre;
import com.ipc2.api.apirest.model.Usuario.Usuario;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PacienteDB {
    private Connection conexion;
    public PacienteDB(Connection conexion) {
        this.conexion = conexion;
    }

    public void solicitarConsulta(ConsultaPaciente paciente) {
        String query = "INSERT INTO CONSULTA (id, fecha_inicio, estado, paciente, medico) VALUES (?, ?, ?, ?, ?)";
        try (var preparedStatement = conexion.prepareStatement(query)) {
            preparedStatement.setNull(1, Types.INTEGER);
            preparedStatement.setString(2, String.valueOf(LocalDate.now()));
            preparedStatement.setString(3, "AGENDADA");
            preparedStatement.setInt(4, paciente.getPaciente());
            preparedStatement.setString(5, paciente.getMedico());
            preparedStatement.executeUpdate();
            System.out.println("Consulta creada");
        } catch (SQLException e) {
            System.out.println("Error al crear consulta: " + e);
        }
    }

    public void recargarSaldo(Usuario usuario, int saldonuevo) {
        String query = "UPDATE USUARIO SET saldo = saldo + ? WHERE cui = ?";
        try (var preparedStatement = conexion.prepareStatement(query)) {

            preparedStatement.setInt(1, saldonuevo);
            preparedStatement.setInt(2, usuario.getCui());
            preparedStatement.executeUpdate();

        }catch (SQLException e) {
            System.out.println("Error al meter saldo: " + e);
        }
    }
    public void cargarConsulta(ConsultaPaciente paciente) {

        Double porcentaje = null;

        String cambio = "SELECT porcentaje FROM PORCENTAJE WHERE id = (SELECT MAX(id) FROM PORCENTAJE)";
        try (var stmt = conexion.createStatement(); var resultSet = stmt.executeQuery(cambio)){
            porcentaje = resultSet.getDouble("porcentaje");
        }catch (SQLException e) {
            System.out.println("Error al crear consulta: " + e);
        }

        String query = "INSERT INTO CONSULTA (id, fecha_inicio,fecha_fin, estado,porcentaje, costo, paciente, medico, especialidad) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (var preparedStatement = conexion.prepareStatement(query)) {
            preparedStatement.setNull(1, Types.INTEGER);
            preparedStatement.setString(2, paciente.getFecha_inicio());
            preparedStatement.setString(3, paciente.getFecha_fin());
            preparedStatement.setString(4, paciente.getEstado());
            preparedStatement.setDouble(5, porcentaje);
            preparedStatement.setBigDecimal(6, BigDecimal.valueOf(0));
            preparedStatement.setInt(7, paciente.getPaciente());
            preparedStatement.setString(8, paciente.getMedico());
            preparedStatement.setString(9, paciente.getEspecialidad());
            preparedStatement.executeUpdate();
            System.out.println("Consulta creada");
        } catch (SQLException e) {
            System.out.println("Error al crear consulta: " + e);
        }
    }

    public List<ListarMedicosNombre> listarMedicosNombre(String med) {
        var medicos = new ArrayList<ListarMedicosNombre>();
        String query = "SELECT u.nombre_usuario AS medico, e.nombre AS especialidad  FROM USUARIO u  INNER JOIN ESPECIALIDADMEDICO e ON u.cui = e.cui  WHERE u.tipo = 'Medico' AND u.nombre_usuario = ?";
        try (var preparedStatement = conexion.prepareStatement(query) ){

            preparedStatement.setString(1, med);

            try (var resultSet = preparedStatement.executeQuery()) {


            while (resultSet.next()) {
                var nombre  = resultSet.getString("nombre") ;
                var especialidad = resultSet.getString("descripcion");

                var medico = new ListarMedicosNombre(nombre,especialidad);

                medicos.add(medico);
            }
        }
            }catch (SQLException e) {
            System.out.println("Error al listar medico: " + e);
        }
        return medicos;
    }

    public List<ListarMedicosNombre> listarMedicos() {
        var medicos = new ArrayList<ListarMedicosNombre>();
        String query = "SELECT u.nombre_usuario AS medico, e.nombre AS especialidad  FROM USUARIO u  INNER JOIN ESPECIALIDADMEDICO e ON u.cui = e.cui  WHERE u.tipo = 'Medico'";
        try (var stmt = conexion.createStatement(); var resultSet = stmt.executeQuery(query)) {

            while (resultSet.next()) {
                var nombre  = resultSet.getString("medico") ;
                var especialidad = resultSet.getString("especialidad");

                var medico = new ListarMedicosNombre(nombre,especialidad);

                medicos.add(medico);
            }
        }catch (SQLException e) {
            System.out.println("Error al listar medico: " + e);
        }
        return medicos;
    }

}
