package com.javabd.aula_conexao.dao.ClienteDao;

import com.javabd.aula_conexao.config.ConnectionFactory;
import com.javabd.aula_conexao.model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClienteDAO implements iClienteDAO {

    @Override
    public Cliente create(Cliente cliente) {
        try (Connection connection
                     = ConnectionFactory.getConnection()){
            String query = "INSERT INTO cliente " +
                    "(nome, cpf, telefone, endereco) " +
                    "VALUES (?,?,?,?)";
            PreparedStatement ps =
                    connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCpf());
            ps.setString(3, cliente.getTelefone());
            ps.setString(4, cliente.getEndereco());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                cliente.setId((long) id);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cliente;
    }

    @Override
    public void update(Cliente cliente) {
        try (Connection connection
                     = ConnectionFactory.getConnection()) {
            String query = "UPDATE cliente SET "+
                    "nome = ?, cpf = ?, telefone = ?, endereco = ? "+
                    "WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCpf());
            ps.setString(3, cliente.getTelefone());
            ps.setString(4, cliente.getEndereco());
            ps.setLong(5, cliente.getId());
            ps.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Cliente cliente) {
        try (Connection connection
                     = ConnectionFactory.getConnection()) {
            String query = "DELETE FROM cliente " +
                    "WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setLong(1, cliente.getId());
            ps.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Cliente> findById(int id) {
        Cliente cliente = new Cliente();
        String query = "SELECT * FROM cliente " +
                "WHERE id = ?";
        try (Connection connection
                     = ConnectionFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            rs.next();
            cliente.setNome(rs.getString("nome"));
            cliente.setCpf(rs.getString("cpf"));
            cliente.setTelefone(rs.getString("telefone"));
            cliente.setEndereco(rs.getString("endereco"));
            cliente.setId(rs.getLong("id"));
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.of(cliente);
    }
    @Override
    public Optional<Cliente> findByNomeCPF(String cpf, String nome) {
        String query = "SELECT * FROM cliente " +
                "WHERE cpf = ? AND nome = ?";
        try (Connection connection
                     = ConnectionFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, cpf);
            ps.setString(2, nome);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setId(rs.getLong("id"));
                return Optional.of(cliente);
            } else {
                return Optional.empty(); // Nenhum cliente encontrado
            }

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Cliente> findAll() {
        List<Cliente> clientes = new ArrayList<>();
        String query = "SELECT * FROM cliente";
        try (Connection connection
                     = ConnectionFactory.getConnection()) {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setId(rs.getLong("id"));
                clientes.add(cliente);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clientes;
    }
}
