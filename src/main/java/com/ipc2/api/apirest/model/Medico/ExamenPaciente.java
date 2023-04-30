package com.ipc2.api.apirest.model.Medico;
import lombok.*;
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class ExamenPaciente {
    private int paciente;
    private String fecha_solicitud;
    private String fecha_fin;
    private String laboratorio;
    private String estado;

}
