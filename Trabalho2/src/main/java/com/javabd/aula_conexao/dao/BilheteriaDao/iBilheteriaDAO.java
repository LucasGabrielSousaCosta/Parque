package com.javabd.aula_conexao.dao.BilheteriaDao;

import com.javabd.aula_conexao.model.Bilheteria;

import java.util.List;
import java.util.Optional;

public interface iBilheteriaDAO {
    Bilheteria create(Bilheteria bilheteria);
    void update(Bilheteria bilheteria);
    void delete(Bilheteria bilheteria);
    Optional<Bilheteria> findById(int id);
    List<Bilheteria> findAll();
}
