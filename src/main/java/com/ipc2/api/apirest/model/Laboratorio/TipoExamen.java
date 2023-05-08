package com.ipc2.api.apirest.model.Laboratorio;

import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class TipoExamen {
    private  int id;
    private  String nombre;
    private  String descripcion;
}
