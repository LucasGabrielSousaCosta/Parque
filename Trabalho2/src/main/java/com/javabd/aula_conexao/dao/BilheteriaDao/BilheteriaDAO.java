package com.javabd.aula_conexao.dao.BilheteriaDao;

import com.javabd.aula_conexao.config.ConnectionFactory;
import com.javabd.aula_conexao.model.Bilheteria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BilheteriaDAO implements iBilheteriaDAO {

    @Override
    public Bilheteria create(Bilheteria bilheteria) {
        String query = "INSERT INTO bilheteria " +
                "(preco, horario_fechamento, funcionamento, quantidade_disponivel) " +
                "VALUES (?, ?, ?, ?)";
        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setBigDecimal(1, bilheteria.getPreco());
            ps.setTime(2, bilheteria.getHorarioFechamento());
            ps.setDate(3, bilheteria.getFuncionamento());
            ps.setLong(4, bilheteria.getQuantidadeDisponivel());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                bilheteria.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bilheteria;
    }

    @Override
    public void update(Bilheteria bilheteria) {
        String query = "UPDATE bilheteria SET " +
                "preco = ?, horario_fechamento = ?, funcionamento = ?, quantidade_disponivel = ? " +
                "WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setBigDecimal(1, bilheteria.getPreco());
            ps.setTime(2, bilheteria.getHorarioFechamento());
            ps.setDate(3, bilheteria.getFuncionamento());
            ps.setInt(4, bilheteria.getQuantidadeDisponivel());
            ps.setLong(5, bilheteria.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Bilheteria bilheteria) {
        String query = "DELETE FROM bilheteria WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setLong(1, bilheteria.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Bilheteria> findById(int id) {
        String query = "SELECT * FROM bilheteria WHERE id = ?";
        Bilheteria bilheteria = new Bilheteria();
        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                bilheteria.setId(rs.getLong("id"));
                bilheteria.setPreco(rs.getBigDecimal("preco"));
                bilheteria.setHorarioFechamento(rs.getTime("horario_fechamento"));
                bilheteria.setFuncionamento(rs.getDate("funcionamento"));
                bilheteria.setQuantidadeDisponivel(rs.getInt("quantidade_disponivel"));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.of(bilheteria);
    }

    @Override
    public List<Bilheteria> findAll() {
        List<Bilheteria> bilheterias = new ArrayList<>();
        String query = "SELECT * FROM bilheteria";
        try (Connection connection = ConnectionFactory.getConnection()) {
            ResultSet rs = connection.prepareStatement(query).executeQuery();

            while (rs.next()) {
                Bilheteria bilheteria = new Bilheteria();
                bilheteria.setId(rs.getLong("id"));
                bilheteria.setPreco(rs.getBigDecimal("preco"));
                bilheteria.setHorarioFechamento(rs.getTime("horario_fechamento"));
                bilheteria.setFuncionamento(rs.getDate("funcionamento"));
                bilheteria.setQuantidadeDisponivel(rs.getInt("quantidade_disponivel"));

                bilheterias.add(bilheteria);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bilheterias;
    }
}
