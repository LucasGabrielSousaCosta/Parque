package com.javabd.aula_conexao.dao.AtracaoClienteDao;

import com.javabd.aula_conexao.config.ConnectionFactory;
import com.javabd.aula_conexao.model.AtracaoCliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AtracaoClienteDao implements iAtracaoClienteDao {

    @Override
    public AtracaoCliente create(AtracaoCliente atracaoCliente) {
        String query = "INSERT INTO atracao_cliente (id_atracao, id_ingresso, horario_uso) VALUES (?, ?, ?)";
        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, atracaoCliente.getId_atracao());
            ps.setLong(2, atracaoCliente.getId_ingresso());
            ps.setTimestamp(3, atracaoCliente.getHorario_uso());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                atracaoCliente.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return atracaoCliente;
    }

    @Override
    public void update(AtracaoCliente atracaoCliente) {
        String query = "UPDATE atracao_cliente SET id_atracao = ?, id_ingresso = ?, horario_uso = ? WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setLong(1, atracaoCliente.getId_atracao());
            ps.setLong(2, atracaoCliente.getId_ingresso());
            ps.setTimestamp(3, atracaoCliente.getHorario_uso());
            ps.setLong(4, atracaoCliente.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(AtracaoCliente atracaoCliente) {
        String query = "DELETE FROM atracao_cliente WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setLong(1, atracaoCliente.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<AtracaoCliente> findById(int id) {
        String query = "SELECT * FROM atracao_cliente WHERE id = ?";
        AtracaoCliente atracaoCliente = new AtracaoCliente();
        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                atracaoCliente.setId(rs.getLong("id"));
                atracaoCliente.setId_atracao(rs.getLong("id_atracao"));
                atracaoCliente.setId_ingresso(rs.getLong("id_ingresso"));
                atracaoCliente.setHorario_uso(rs.getTimestamp("horario_uso"));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.of(atracaoCliente);
    }

    @Override
    public Optional<AtracaoCliente> findByIngressoId(int id) {
        String query = "SELECT * FROM atracao_cliente WHERE id_ingresso = ?";
        AtracaoCliente atracaoCliente = new AtracaoCliente();
        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                atracaoCliente.setId(rs.getLong("id"));
                atracaoCliente.setId_atracao(rs.getLong("id_atracao"));
                atracaoCliente.setId_ingresso(rs.getLong("id_ingresso"));
                atracaoCliente.setHorario_uso(rs.getTimestamp("horario_uso"));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.of(atracaoCliente);
    }

    @Override
    public List<AtracaoCliente> findAll() {
        List<AtracaoCliente> lista = new ArrayList<>();
        String query = "SELECT * FROM atracao_cliente";
        try (Connection connection = ConnectionFactory.getConnection()) {
            ResultSet rs = connection.prepareStatement(query).executeQuery();

            while (rs.next()) {
                AtracaoCliente atracaoCliente = new AtracaoCliente();
                atracaoCliente.setId(rs.getLong("id"));
                atracaoCliente.setId_atracao(rs.getLong("id_atracao"));
                atracaoCliente.setId_ingresso(rs.getLong("id_ingresso"));
                atracaoCliente.setHorario_uso(rs.getTimestamp("horario_uso"));

                lista.add(atracaoCliente);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }
}
