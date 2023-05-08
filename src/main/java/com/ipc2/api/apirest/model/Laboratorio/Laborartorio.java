package com.ipc2.api.apirest.model.Laboratorio;

import com.ipc2.api.apirest.model.Usuario.Usuario;
import java.util.List;

public class Laborartorio extends Usuario {
    private List<ValorExamen> examenes;

    public List<ValorExamen> getValorExamenes() {
        return examenes;
    }

    public void setValorExamenes(List<ValorExamen> valorExamenes) {
        this.examenes = valorExamenes;
    }
}
