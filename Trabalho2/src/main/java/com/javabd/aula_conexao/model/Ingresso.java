package com.javabd.aula_conexao.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ingresso {
    private long id;
    private long id_cliente;
    private long id_bilheteria;
    private Date data_venda;
    private FormaPagamento pagamento;
}
