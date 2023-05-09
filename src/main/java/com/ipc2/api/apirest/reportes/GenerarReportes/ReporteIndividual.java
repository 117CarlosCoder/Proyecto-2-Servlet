package com.ipc2.api.apirest.reportes.GenerarReportes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


import com.ipc2.api.apirest.reportes.Conexion.ConexionDatos;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;





public class ReporteIndividual {

        public static void main(String[] args) throws JRException, SQLException {
                ReporteIndividual reporte = new ReporteIndividual();
                reporte.reporteCarga();
        }

        private ConexionDatos conexionDatos;

        public ReporteIndividual() throws JRException {
            conexionDatos = ConexionDatos.getInstance();
        }

        public void reporteCarga() throws JRException, SQLException {

                JasperReport jasperReport = JasperCompileManager.compileReport("/Users/calin10/Documents/Cunoc/IPC2/Proyectosanteriores/apirest/src/main/webapp/WEB-INF/Reportes/Medico/Top5Especialidades.jrxml");
                Map<String, Object> parameters = new HashMap<>() {{
                        put("medico", "Med2");
                }};

                System.out.println(parameters);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conexionDatos.getConnection());
                System.out.println(jasperPrint);
                //JasperExportManager.exportReportToPdfFile(jasperPrint, "/Users/calin10/Documents/Cunoc/IPC2/Proyectosanteriores/apirest/src/main/webapp/WEB-INF/Reportes/Medico/Top5Especialidades.pdf");
                byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);


        }


}
