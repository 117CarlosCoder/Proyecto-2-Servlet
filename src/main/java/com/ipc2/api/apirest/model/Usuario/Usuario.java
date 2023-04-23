package com.ipc2.api.apirest.model.Usuario;

import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class Usuario {

    private  int cui;
    private  String tipo;
    private  String nombre;
    private  String nombre_usuario;
    private  String contraseña;
    private  String direccion;
    private  String correo;
    private  String fecha_nacimiento;
    private BigDecimal saldo;



}
