package com.javabd.aula_conexao.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Atracao {
    private long id;
    private String nome;
    private String descricao;
    private Time horario_inicio;
    private Time horario_fim;
    private int capacidade;
}
