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

    public void generarRep(HttpServletResponse response,String Ruta , String Tipo) throws ServletException,IOException {
        System.out.println("Cargar informe");
        JasperReport informe = null;
        JasperPrint jasperPrint = null;
        String rutaRelativa = "";

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.out.println("Cargar informe");
        System.out.println("Valor de la ruta: " + Ruta);

        rutaRelativa = reporte(Ruta,Tipo);

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

            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "attachment; filename=reporte.pdf");
            response.setContentLength(bytes.length);

        System.out.println("subiendo informe");

            OutputStream out = response.getOutputStream();
            out.write(bytes);
            out.flush();
        }


    public String reporte( String Ruta, String Tipo){
        if (Tipo == "Administrador"){
            switch (Ruta){
                case "Porcentaje":
                    System.out.println("Entro");
                    return "/Users/calin10/Documents/Cunoc/IPC2/Proyectosanteriores/apirest/src/main/webapp/WEB-INF/Reportes/Admin/Reporte-porcentajes.jasper";

                case "Top5Laboratorios":
                    return  "/Users/calin10/Documents/Cunoc/IPC2/Proyectosanteriores/apirest/src/main/webapp/WEB-INF/Reportes/Admin/Top5Laboratorios.jasper";

                case  "Top5Medicos":
                    return "/Users/calin10/Documents/Cunoc/IPC2/Proyectosanteriores/apirest/src/main/webapp/WEB-INF/Reportes/Admin/Top5Medicos.jasper";

                case "Totalingresos":
                    return "/Users/calin10/Documents/Cunoc/IPC2/Proyectosanteriores/apirest/src/main/webapp/WEB-INF/Reportes/Admin/Totalingresos.jasper";
            }
        }
        if (Tipo == "Paciente"){
            switch (Ruta){
                case "ConsultasMedicas":
                    return "/Users/calin10/Documents/Cunoc/IPC2/Proyectosanteriores/apirest/src/main/webapp/WEB-INF/Reportes/Paciente/ConsultasMedicas.jasper";

                case "ExamenesMedicos":
                    return "/Users/calin10/Documents/Cunoc/IPC2/Proyectosanteriores/apirest/src/main/webapp/WEB-INF/Reportes/Paciente/HistorialEsamenes.jasper";

                case  "HistorialMedico":
                    return "/Users/calin10/Documents/Cunoc/IPC2/Proyectosanteriores/apirest/src/main/webapp/WEB-INF/Reportes/Paciente/HistorialMedico.jasper";

                case "Saldo":
                    return "/Users/calin10/Documents/Cunoc/IPC2/Proyectosanteriores/apirest/src/main/webapp/WEB-INF/Reportes/Paciente/Saldo.jasper";
            }
        }
        if (Tipo == "Medicos"){
            switch (Ruta){
                case "Saldo":
                    return "/Users/calin10/Documents/Cunoc/IPC2/Proyectosanteriores/apirest/src/main/webapp/WEB-INF/Reportes/Medico/Saldo.jasper";

                case "Top5Especialidades":
                    return "/Users/calin10/Documents/Cunoc/IPC2/Proyectosanteriores/apirest/src/main/webapp/WEB-INF/Reportes/Medico/Top5Especialidades.jasper";

                case  "Top5Pacientes":
                    return "/Users/calin10/Documents/Cunoc/IPC2/Proyectosanteriores/apirest/src/main/webapp/WEB-INF/Reportes/Medico/Top5Pacientes.jasper";
            }
        }
        if (Tipo == "Laboratorios"){
            switch (Ruta){

                case "Top5Examenes":
                    return  "/Users/calin10/Documents/Cunoc/IPC2/Proyectosanteriores/apirest/src/main/webapp/WEB-INF/Reportes/Laboratorio/Top5Laboratorios.jasper";

                case  "Top5Pacientes":
                    return "/Users/calin10/Documents/Cunoc/IPC2/Proyectosanteriores/apirest/src/main/webapp/WEB-INF/Reportes/Laboratorio/Top5Pacientes.jasper";

                case "Saldo":
                    return "/Users/calin10/Documents/Cunoc/IPC2/Proyectosanteriores/apirest/src/main/webapp/WEB-INF/Reportes/Laboratorio/Saldo.jasper";
            }
        }

        return "";
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
