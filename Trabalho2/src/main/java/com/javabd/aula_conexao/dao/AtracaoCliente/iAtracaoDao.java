package com.javabd.aula_conexao.dao.AtracaoCliente;

import com.javabd.aula_conexao.model.Atracao;

import java.util.List;
import java.util.Optional;

public interface iAtracaoDao {
    Atracao create(Atracao atracao);
    void update(Atracao atracao);
    void delete(Atracao atracao);
    Optional<Atracao> findById(int id);
    List<Atracao> findAll();
    Optional<Atracao> findByNome(String nome);
}
