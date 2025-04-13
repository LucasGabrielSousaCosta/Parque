package com.javabd.aula_conexao.dao.ClienteDao;

import com.javabd.aula_conexao.model.Cliente;

import java.util.List;
import java.util.Optional;

public interface iClienteDAO {
    Cliente create(Cliente cliente);
    void update(Cliente cliente);
    void delete(Cliente cliente);
    Optional<Cliente> findById(int id);
    List<Cliente> findAll();
    Optional<Cliente> findByNomeCPF(String cpf, String nome);
}

