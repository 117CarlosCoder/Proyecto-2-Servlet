package com.ipc2.api.apirest.reportes.GenerarReportes;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;

public class CompilarJasper {

    public static void main(String[] args) {

        /*try {
            String archivoJrxml = "src/main/webapp/WEB-INF/Reportes/Admin/Porcentajes.jrxml";
            JasperCompileManager.compileReportToFile(archivoJrxml);
            System.out.println("Informe compilado correctamente.");
        } catch (JRException e) {
            e.printStackTrace();
        }*/

        try {
            String[] archivosJrxml = {
                    "src/main/webapp/WEB-INF/Reportes/Admin/Porcentajes.jrxml",
                    "src/main/webapp/WEB-INF/Reportes/Admin/Top5Laboratorios.jrxml",
                    "src/main/webapp/WEB-INF/Reportes/Admin/Top5Medicos.jrxml",
                    "src/main/webapp/WEB-INF/Reportes/Admin/Totalingresos.jrxml",
                    "src/main/webapp/WEB-INF/Reportes/Laboratorio/Saldo.jrxml",
                    "src/main/webapp/WEB-INF/Reportes/Laboratorio/Top5Examenes.jrxml",
                    "src/main/webapp/WEB-INF/Reportes/Laboratorio/Top5Pacientes.jrxml",
                    "src/main/webapp/WEB-INF/Reportes/Laboratorio/Totalingresos.jrxml",
                    "src/main/webapp/WEB-INF/Reportes/Medico/Saldo.jrxml",
                    "src/main/webapp/WEB-INF/Reportes/Medico/Top5Especialidades.jrxml",
                    "src/main/webapp/WEB-INF/Reportes/Medico/Top5Pacientes.jrxml",
                    "src/main/webapp/WEB-INF/Reportes/Medico/Top5Especialidades.jrxml",
                    "src/main/webapp/WEB-INF/Reportes/Paciente/ConsultasMedicas.jrxml",
                    "src/main/webapp/WEB-INF/Reportes/Paciente/ExamenesMedicos.jrxml",
                    "src/main/webapp/WEB-INF/Reportes/Paciente/HistorialMedico.jrxml",
                    "src/main/webapp/WEB-INF/Reportes/Paciente/Saldo.jrxml"
            };

            for(String archivoJrxml : archivosJrxml) {
                JasperCompileManager.compileReportToFile(archivoJrxml);
                System.out.println("Informe " + archivoJrxml + " compilado correctamente.");
            }
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
