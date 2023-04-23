package com.ipc2.api.apirest.model.Laboratorio;

import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class LaboratorioInfoExamen {
    private  int id;
    private  int cui;
    private  String nombre;
    private BigDecimal costo;
    private  String descripcion;

}
