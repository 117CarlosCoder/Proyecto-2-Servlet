package com.ipc2.api.apirest.model.Laboratorio;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ValorExamen {
    private  int id;
    private  int cui;
    private BigDecimal costo;
}
