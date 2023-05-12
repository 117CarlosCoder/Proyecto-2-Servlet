package com.ipc2.api.apirest.data;

import com.ipc2.api.apirest.model.Medico.*;
import com.ipc2.api.apirest.model.Paciente.ConsultaPaciente;
import com.ipc2.api.apirest.model.Usuario.Usuario;

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
            preparedStatement.setNull(1, Types.INTEGER);
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

    public void crearEspecialidadAdmin(MedicoEspecialidad especialidad) {

        String query = "INSERT INTO ESPECIALIDADMEDICO (id, cui, costo, nombre, descripcion) VALUES (?, ?, ?, ?, ?)";
        try (var preparedStatement = conexion.prepareStatement(query)) {
            preparedStatement.setNull(1, Types.INTEGER);
            preparedStatement.setInt(2, especialidad.getCui());
            preparedStatement.setBigDecimal(3, especialidad.getCosto());
            preparedStatement.setString(4, especialidad.getNombre());
            preparedStatement.setString(5, especialidad.getDescripcion());
            preparedStatement.executeUpdate();
            System.out.println("Especialidad creada admin");
        } catch (SQLException e) {
            System.out.println("Error al crear especialidad admin: " + e);
        }
    }

    public void cargarEspecialidades(Especialidades especialidad) {
        String query = "INSERT INTO ESPECIALIDAD (id, nombre, descripcion) VALUES (?, ?, ?)";
        try (var preparedStatement = conexion.prepareStatement(query)) {
            preparedStatement.setInt(1, especialidad.getId());
            preparedStatement.setString(2, especialidad.getNombre());
            preparedStatement.setString(3, especialidad.getDescripcion());
            preparedStatement.executeUpdate();
            System.out.println("Especialidad cargada");
        } catch (SQLException e) {
            System.out.println("Error al crear especialidad: " + e);
        }
    }

    public void cargarEspecialidadesMedico(CargaEspecialidad especialidad) {
        String query = "INSERT INTO ESPECIALIDADCOSTO (id,cui, costo) VALUES (?, ?, ?)";
        try (var preparedStatement = conexion.prepareStatement(query)) {
            preparedStatement.setInt(1, especialidad.getId());
            preparedStatement.setInt(2, especialidad.getCui());
            preparedStatement.setBigDecimal(3, especialidad.getCosto());
            preparedStatement.executeUpdate();
            System.out.println("EspecialidadCosto cargada");
        } catch (SQLException e) {
            System.out.println("Error al crear especialidadcosto: " + e);
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
            preparedStatement.setNull(1, Types.INTEGER);
            preparedStatement.setInt(2, cui);
            preparedStatement.setString(3, horario.getHora());
            preparedStatement.executeUpdate();
            System.out.println("Horario creado");
        } catch (SQLException e) {
            System.out.println("Error al crear horario: " + e);
        }
    }

    public List<MedicoEspecialidad> listarEspecialidadesAdmin() {
        var especialidades = new ArrayList<MedicoEspecialidad>();
        try (var stmt = conexion.createStatement(); var resultSet = stmt.executeQuery("SELECT * FROM ESPECIALIDADADMIN")) {

            while (resultSet.next()) {

                var id = resultSet.getInt("id");
                var cui = resultSet.getInt("cui");
                var costo  = resultSet.getBigDecimal("costo") ;
                var nombre  = resultSet.getString("nombre") ;
                var descripcion = resultSet.getString("descripcion");

                var medicoEspecialidad = new MedicoEspecialidad(id, cui, costo,nombre, descripcion);

                especialidades.add(medicoEspecialidad);
            }
        }catch (SQLException e) {
            System.out.println("Error al consultar: " + e);
        }
        return especialidades;
    }

    public List<MedicoEspecialidad> listarEspecialidad(Usuario usuario) {
        int cui = usuario.getCui();
        var especialidades = new ArrayList<MedicoEspecialidad>();
        String query =  "SELECT * FROM ESPECIALIDADMEDICO WHERE cui = ?";
        try (var preparedStatement = conexion.prepareStatement(query)) {

            preparedStatement.setInt(1, cui);


            try (var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    var id = resultSet.getInt("id");
                    var costo = resultSet.getBigDecimal("costo");
                    var nombre = resultSet.getString("nombre");
                    var descripcion = resultSet.getString("descripcion");
                    var medicoEspecialidad = new MedicoEspecialidad(id, cui, costo,nombre, descripcion);
                    especialidades.add(medicoEspecialidad);
                }
            }
        }catch (SQLException e) {
            System.out.println("Error al listar especialidades: " + e);
        }

        return especialidades;
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

    public List<ConsultaPaciente> listarHistorialConsulta(int bid) {
        var consultaPacientes = new ArrayList<ConsultaPaciente>();
        String query =  "SELECT paciente, fecha_inicio, fecha_fin,porcentaje, estado, medico, especialidad  FROM CONSULTA  WHERE paciente = ? ORDER BY fecha_inicio ASC";
            try (var preparedStatement = conexion.prepareStatement(query) ){

                preparedStatement.setInt(1, bid);


                try (var resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        var paciente = resultSet.getInt("paciente");
                        var fecha_incio = resultSet.getString("fecha_inicio");
                        var fecha_fin = resultSet.getString("fecha_fin");
                        var porcentaje = resultSet.getInt("porcentaje");
                        var estado = resultSet.getString("estado");
                        var medico = resultSet.getString("medico");
                        var especialidad = resultSet.getString("especialidad");
                        var consultar = new ConsultaPaciente(paciente, fecha_incio, fecha_fin, estado,porcentaje,medico, especialidad);


                        consultaPacientes.add(consultar);
                    }
                }
            }catch (SQLException e) {
                System.out.println("Error al listar especialidades: " + e);
            }

            return consultaPacientes;
    }

    public List<ExamenPaciente> listarHistorialExamenes(int bid) {
        var examenPacientes = new ArrayList<ExamenPaciente>();
        String query =  "SELECT paciente, fecha_solicitud, fecha_fin, laboratorio, estado  FROM EXAMEN  WHERE paciente = ? ORDER BY fecha_solicitud ASC";
        try (var preparedStatement = conexion.prepareStatement(query) ){

            preparedStatement.setInt(1, bid);


            try (var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    var paciente = resultSet.getInt("paciente");
                    var fecha_solicitud = resultSet.getString("fecha_solicitud");
                    var fecha_fin = resultSet.getString("fecha_fin");
                    var estado = resultSet.getString("estado");
                    var laboratorio = resultSet.getString("laboratorio");
                    var examenes = new ExamenPaciente(paciente, fecha_solicitud, fecha_fin,laboratorio,estado);


                    examenPacientes.add(examenes);
                }
            }
        }catch (SQLException e) {
            System.out.println("Error al listar examenes: " + e);
        }

        return examenPacientes;
    }

    public void cambiarCosto(MedicoEspecialidad especialidad, Usuario usuario) {
        int cui = usuario.getCui();
        String query = "UPDATE ESPECIALIDADMEDICO SET costo = ? WHERE cui = ? AND id = ?";
        try (var preparedStatement = conexion.prepareStatement(query)) {
            preparedStatement.setBigDecimal(1, especialidad.getCosto());
            preparedStatement.setInt(2, cui);
            preparedStatement.setInt(3, especialidad.getId());
            preparedStatement.executeUpdate();
            System.out.println("Costo cambiado");
        } catch (SQLException e) {
            System.out.println("Error al cambiar costo: " + e);
        }
    }
}
