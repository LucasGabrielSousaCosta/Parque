package com.javabd.aula_conexao.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AtracaoCliente {
    private long id;
    private long id_atracao;
    private long id_ingresso;
    private Timestamp horario_uso;
}
