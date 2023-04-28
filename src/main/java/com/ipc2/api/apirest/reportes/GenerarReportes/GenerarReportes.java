package com.ipc2.api.apirest.reportes.GenerarReportes;

import com.ipc2.api.apirest.reportes.Conexion.ConexionDatos;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Enumeration;

public class GenerarReportes extends HttpServlet {

    public void generarRep(HttpServletResponse response,String Ruta) throws ServletException, IOException {

            // Generar el informe con JasperReports
            JasperReport informe = null;
            JasperPrint jasperPrint = null;
            String rutaRelativa = "";

        //String archivoJasper = "/Users/calin10/Documents/Cunoc/IPC2/Proyectosanteriores/apirest/src/main/java/com/ipc2/api/apirest/reportes/reporte-medico.jasper";
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // Código para generar el informe...
            System.out.println("Cargar informe");
        System.out.println("Valor de la ruta: " + Ruta);

            switch (Ruta){
                case "Porcentaje":
                    rutaRelativa = "/Users/calin10/Documents/Cunoc/IPC2/Proyectosanteriores/apirest/src/main/webapp/WEB-INF/Reportes/Admin/Reporte-porcentajes.jasper";
                    break;

                case "Top5Laboratorios":
                    rutaRelativa = "/Users/calin10/Documents/Cunoc/IPC2/Proyectosanteriores/apirest/src/main/webapp/WEB-INF/Reportes/Admin/Top5Laboratorios.jasper";
                    break;

                case  "Top5Medicos":
                    rutaRelativa = "/Users/calin10/Documents/Cunoc/IPC2/Proyectosanteriores/apirest/src/main/webapp/WEB-INF/Reportes/Admin/Top5Medicos.jasper";
                    break;
                case "Totalingresos":
                    rutaRelativa = "/Users/calin10/Documents/Cunoc/IPC2/Proyectosanteriores/apirest/src/main/webapp/WEB-INF/Reportes/Admin/Totalingresos.jasper";
                    break;

            }

            if (Ruta == "Porcentaje"){
                rutaRelativa = "/Users/calin10/Documents/Cunoc/IPC2/Proyectosanteriores/apirest/src/main/webapp/WEB-INF/Reportes/Admin/Reporte-porcentajes.jasper";
                System.out.println("Funciona");
            }
            if (Ruta == "Top5Laboratorios"){
            rutaRelativa = "/Users/calin10/Documents/Cunoc/IPC2/Proyectosanteriores/apirest/src/main/webapp/WEB-INF/Reportes/Admin/Top5Laboratorios.jasper";
            }
            if (Ruta == "Top5Medicos"){
            rutaRelativa = "/Users/calin10/Documents/Cunoc/IPC2/Proyectosanteriores/apirest/src/main/webapp/WEB-INF/Reportes/Admin/Top5Medicos.jasper";
            }
            if (Ruta == "Totalingresos"){
            rutaRelativa = "/Users/calin10/Documents/Cunoc/IPC2/Proyectosanteriores/apirest/src/main/webapp/WEB-INF/Reportes/Admin/Totalingresos.jasper";
            }

        // Escribir el informe en memoria
        System.out.println("Ruta relativa : " + rutaRelativa);
        System.out.println("Escribir informe");

            try {


                System.out.println(rutaRelativa);
                //ServletContext servletContext = getServletContext();
                //System.out.println(servletContext);
                //String fullPath = servletContext.getRealPath(rutaRelativa);
                //File archivo = new File(fullPath);
                //File archivo = new File(rutaRelativa);
                //String rutaAbsoluta = archivo.getPath();
                //System.out.println(rutaAbsoluta);
                String archivoJasper = rutaRelativa;
                informe = (JasperReport) JRLoader.loadObjectFromFile(archivoJasper);
                System.out.println(informe);
                jasperPrint = JasperFillManager.fillReport(informe, null, ConexionDatos.getInstance().getConnection());
                JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
                System.out.println(baos);

            } catch (JRException | SQLException e) {
                System.out.println(e);
                throw new RuntimeException(e);

            }

        System.out.println("Informe Inscrito");


        byte[] bytes = baos.toByteArray();

            // Configurar la respuesta HTTP
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "attachment; filename=reporte.pdf");
            response.setContentLength(bytes.length);

        System.out.println("subiendo informe");


        // Enviar el archivo al cliente
            OutputStream out = response.getOutputStream();
            out.write(bytes);
            out.flush();
        }
    }



    /*public static void main(String[] args) {
        genrarReporte();
    }
    public static byte[] genrarReporte(){

        // Generar el informe con JasperReports

        JasperReport informe = null;
        JasperPrint jasperPrint = null;
        String archivoJasper = "src/main/java/com/ipc2/api/apirest/reportes/practica_paises.jasper";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            informe = (JasperReport) JRLoader.loadObjectFromFile(archivoJasper);
            jasperPrint = JasperFillManager.fillReport(informe, null, ConexionDatos.getInstance().getConnection());
            JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
            System.out.println(baos);

        } catch (JRException | SQLException e) {
            throw new RuntimeException(e);
        }


        byte[] bytes = baos.toByteArray();
        System.out.println(bytes);
        return bytes;
        // Configurar la respuesta HTTP
        /*response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename=informe.pdf");
        response.setContentLength(bytes.length);

        // Enviar el archivo al cliente
        OutputStream out = response.getOutputStream();
        out.write(bytes);
        out.flush();
    }*/



    /*public static void main(String[] args) {

        try {
            String archivoJasper = "src/main/java/com/ipc2/api/apirest/reportes/practica_paises.jasper";
            JasperReport informe = (JasperReport) JRLoader.loadObjectFromFile(archivoJasper);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            System.out.println("Informe cargado correctamente.");
            JasperPrint jasperPrint = JasperFillManager.fillReport(informe, null, ConexionDatos.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
            JasperExportManager.exportReportToPdfFile(jasperPrint,baos);
            byte[] bytes = baos.toByteArray();
        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/
