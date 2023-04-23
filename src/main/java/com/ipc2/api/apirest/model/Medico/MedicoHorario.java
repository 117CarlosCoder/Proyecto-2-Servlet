package com.ipc2.api.apirest.model.Medico;
import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class MedicoHorario {
    private  int id;
    private  int cui;
    private String hora;
}
