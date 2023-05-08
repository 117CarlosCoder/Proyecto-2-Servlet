package com.ipc2.api.apirest.model.Paciente;
import lombok.*;
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class ConsultaPaciente {
    private int paciente;
    private String fecha_inicio;
    private String fecha_fin;
    private String estado;
    private double porcentaje;
    private String medico;
    private String especialidad;


}
