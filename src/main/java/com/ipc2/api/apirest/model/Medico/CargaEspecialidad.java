package com.ipc2.api.apirest.model.Medico;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CargaEspecialidad {
    private  int id;
    private int cui;
    private BigDecimal costo;
}
