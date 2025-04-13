package com.javabd.aula_conexao.dao.AtracaoCliente;

import com.javabd.aula_conexao.config.ConnectionFactory;
import com.javabd.aula_conexao.model.Atracao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AtracaoDao implements iAtracaoDao {

    @Override
    public Atracao create(Atracao atracao){
        String query = "INSERT INTO atracao "+
        "(nome, descricao, horario_inicio, horario_fim, capacidade) "+
        "VALUES (?, ?, ?, ?, ?)";
        try(Connection connection = ConnectionFactory.getConnection()){
            PreparedStatement ps = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, atracao.getNome());
            ps.setString(2, atracao.getDescricao());
            ps.setTime(3, atracao.getHorario_inicio());
            ps.setTime(4, atracao.getHorario_fim());
            ps.setInt(5, atracao.getCapacidade());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                atracao.setId(rs.getLong(1));
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return atracao;
    }

    @Override
    public void update(Atracao atracao) {
        String query = "UPDATE atracao SET "+
            "nome = ?, descricao = ?, horario_inicio = ?, horario_fim = ?, capacidade = ?"+
            "WHERE id = ?";
        try(Connection connection = ConnectionFactory.getConnection()){
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, atracao.getNome());
            ps.setString(2, atracao.getDescricao());
            ps.setTime(3,atracao.getHorario_inicio());
            ps.setTime(4,atracao.getHorario_fim());
            ps.setInt(5,atracao.getCapacidade());
            ps.setLong(6,atracao.getId());

            ps.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Atracao atracao) {
        String query = "DELETE FROM atracao WHERE id = ?";
        try(Connection connection = ConnectionFactory.getConnection()){
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setLong(1, atracao.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Atracao> findById(int id) {
        String query = "SELECT * FROM atracao where id = ?";
        Atracao atracao = new Atracao();
        try(Connection connection = ConnectionFactory.getConnection()){
           PreparedStatement ps =  connection.prepareStatement(query);
           ps.setInt(1, id);
           ResultSet rs = ps.executeQuery();

           if (rs.next()){
                atracao.setId(rs.getLong("id"));
                atracao.setNome(rs.getString("nome"));
                atracao.setDescricao(rs.getString("descricao"));
                atracao.setHorario_inicio(rs.getTime("horario_inicio"));
                atracao.setHorario_fim(rs.getTime("horario_fim"));
                atracao.setCapacidade(rs.getInt("capacidade"));
           } else {
                return Optional.empty();
           }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.of(atracao);
    }

    @Override
    public Optional<Atracao> findByNome(String nome) {
        String query = "SELECT * FROM atracao where nome = ?";
        Atracao atracao = new Atracao();
        try(Connection connection = ConnectionFactory.getConnection()){
            PreparedStatement ps =  connection.prepareStatement(query);
            ps.setString(1, nome);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                atracao.setId(rs.getLong("id"));
                atracao.setNome(rs.getString("nome"));
                atracao.setDescricao(rs.getString("descricao"));
                atracao.setHorario_inicio(rs.getTime("horario_inicio"));
                atracao.setHorario_fim(rs.getTime("horario_fim"));
                atracao.setCapacidade(rs.getInt("capacidade"));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.of(atracao);
    }

    @Override
    public List<Atracao> findAll() {
        List<Atracao> atracoes = new ArrayList<>();
        String query = "SELECT * FROM atracao";
        try(Connection connection = ConnectionFactory.getConnection()) {
            ResultSet rs = connection.prepareStatement(query).executeQuery();

            while(rs.next()){
                Atracao atracao = new Atracao();
                atracao.setId(rs.getLong("id"));
                atracao.setNome(rs.getString("nome"));
                atracao.setDescricao(rs.getString("descricao"));
                atracao.setCapacidade(rs.getInt("capacidade"));
                atracao.setHorario_inicio(rs.getTime("horario_inicio"));
                atracao.setHorario_fim(rs.getTime("horario_fim"));

                atracoes.add(atracao);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return atracoes;
    }
}
