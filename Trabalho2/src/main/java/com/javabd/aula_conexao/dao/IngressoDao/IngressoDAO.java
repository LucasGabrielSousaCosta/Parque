package com.javabd.aula_conexao.dao.IngressoDao;

import com.javabd.aula_conexao.config.ConnectionFactory;
import com.javabd.aula_conexao.model.Ingresso;
import com.javabd.aula_conexao.model.FormaPagamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IngressoDAO implements iIngressoDAO {

    @Override
    public Ingresso create(Ingresso ingresso) {
        String query = "INSERT INTO ingresso (id_cliente, id_bilheteria, data_venda, pagamento) VALUES (?, ?, ?, ?::forma_pagamento)";

        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, ingresso.getId_cliente());
            ps.setLong(2, ingresso.getId_bilheteria());
            ps.setDate(3, ingresso.getData_venda());
            ps.setString(4, ingresso.getPagamento().name());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                ingresso.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ingresso;
    }

    @Override
    public void update(Ingresso ingresso) {
        String query = "UPDATE ingresso SET id_cliente = ?, id_bilheteria = ?, data_venda = ?, pagamento = ? WHERE id = ?";

        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setLong(1, ingresso.getId_cliente());
            ps.setLong(2, ingresso.getId_bilheteria());
            ps.setDate(3, ingresso.getData_venda());
            ps.setString(4, ingresso.getPagamento().name());
            ps.setLong(5, ingresso.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Ingresso ingresso) {
        String query = "DELETE FROM ingresso WHERE id = ?";

        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setLong(1, ingresso.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Ingresso> findById(int id) {
        String query = "SELECT * FROM ingresso WHERE id = ?";
        Ingresso ingresso = new Ingresso();

        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                ingresso.setId(rs.getLong("id"));
                ingresso.setId_cliente(rs.getLong("id_cliente"));
                ingresso.setId_bilheteria(rs.getLong("id_bilheteria"));
                ingresso.setData_venda(rs.getDate("data_venda"));
                ingresso.setPagamento(FormaPagamento.valueOf(rs.getString("pagamento")));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.of(ingresso);
    }

    @Override
    public List<Ingresso> findByClienteId(int idCliente) {
        List<Ingresso> ingressos = new ArrayList<>();
        String query = "SELECT * FROM ingresso WHERE id_cliente = ?";

        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Ingresso ingresso = new Ingresso();
                ingresso.setId(rs.getLong("id"));
                ingresso.setId_cliente(rs.getLong("id_cliente"));
                ingresso.setId_bilheteria(rs.getLong("id_bilheteria"));
                ingresso.setData_venda(rs.getDate("data_venda"));
                ingresso.setPagamento(FormaPagamento.valueOf(rs.getString("pagamento")));

                ingressos.add(ingresso);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ingressos;
    }

    @Override
    public List<Ingresso> findAll() {
        List<Ingresso> ingressos = new ArrayList<>();
        String query = "SELECT * FROM ingresso";

        try (Connection connection = ConnectionFactory.getConnection()) {
            ResultSet rs = connection.prepareStatement(query).executeQuery();

            while (rs.next()) {
                Ingresso ingresso = new Ingresso();
                ingresso.setId(rs.getLong("id"));
                ingresso.setId_cliente(rs.getLong("id_cliente"));
                ingresso.setId_bilheteria(rs.getLong("id_bilheteria"));
                ingresso.setData_venda(rs.getDate("data_venda"));
                ingresso.setPagamento(FormaPagamento.valueOf(rs.getString("pagamento")));

                ingressos.add(ingresso);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ingressos;
    }
}
