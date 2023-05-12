package com.ipc2.api.apirest.model.Admin;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Porcentaje {
    private  int id;
    private  String fecha;
    private  BigDecimal porcentaje;;
}
