package com.javabd.aula_conexao.dao.IngressoDao;

import com.javabd.aula_conexao.model.Ingresso;

import java.util.List;
import java.util.Optional;

public interface iIngressoDAO {
    Ingresso create(Ingresso ingresso);
    void update(Ingresso ingresso);
    void delete(Ingresso ingresso);
    Optional<Ingresso> findById(int id);
    List<Ingresso> findByClienteId(int id);
    List<Ingresso> findAll();
}
