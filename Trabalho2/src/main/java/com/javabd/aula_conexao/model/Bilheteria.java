package com.javabd.aula_conexao.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Bilheteria {
    private Long id;
    private BigDecimal preco;
    private Time horarioFechamento;
    private Date funcionamento;
    private Integer quantidadeDisponivel;
}
