package com.javabd.aula_conexao.dao.AtracaoClienteDao;

import com.javabd.aula_conexao.model.AtracaoCliente;

import java.util.List;
import java.util.Optional;

public interface iAtracaoClienteDao {
    AtracaoCliente create(AtracaoCliente atracaoCliente);
    void update(AtracaoCliente atracaoCliente);
    void delete(AtracaoCliente atracaoCliente);
    Optional<AtracaoCliente> findById(int id);
    List<AtracaoCliente> findAll();
    Optional<AtracaoCliente> findByIngressoId(int id);
}
