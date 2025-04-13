package com.javabd.aula_conexao;

import com.javabd.aula_conexao.dao.AtracaoClienteDao.AtracaoClienteDao;
import com.javabd.aula_conexao.dao.AtracaoCliente.AtracaoDao;
import com.javabd.aula_conexao.dao.IngressoDao.IngressoDAO;
import com.javabd.aula_conexao.model.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AtracaoController {

    @FXML
    private TextField descricao;

    @FXML
    private TextField horario_inicio;

    @FXML
    private ComboBox<String> ingressos;

    @FXML
    private TextField capacidade;

    @FXML
    private ComboBox<String> nome;

    @FXML
    private TextField horario_fim;

    private Cliente clienteLogado;

    private Atracao atracao;

    @FXML
    public void initialize() {
        mostrarAtracoes(null);//atualiza lista de atrações
        Platform.runLater(() -> {//pega os dados do cliente logado
            Stage stage = (Stage) nome.getScene().getWindow();
            Object userData = stage.getUserData();
            if (userData instanceof Cliente) {
                this.clienteLogado = (Cliente) userData;
            }
        });
    }

    @FXML
    void btn_buscar(ActionEvent event) {
        String nomeAtracao = nome.getValue();
        AtracaoDao atracaoDao = new AtracaoDao();
        if(nomeAtracao == null || nomeAtracao.equals("Sem atrações")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Nenhuma atracao selecionada");
            alert.show();
        }else{
            Optional<Atracao> atracaoOptional = atracaoDao.findByNome(nomeAtracao);
            if(atracaoOptional.isPresent()){
                atracao = atracaoOptional.get();
                descricao.setText(String.valueOf(atracao.getDescricao()));
                horario_inicio.setText(String.valueOf(atracao.getHorario_inicio()));
                horario_fim.setText(String.valueOf(atracao.getHorario_fim()));
                capacidade.setText(String.valueOf(atracao.getCapacidade()));
                mostrarIngressos();
            }
        }
    }

    @FXML
    void btn_comprar(ActionEvent event) {
        String idIngresso = ingressos.getValue();

        AtracaoClienteDao atracaoClienteDao = new AtracaoClienteDao();
        if(atracao == null || idIngresso == null || idIngresso.equals("Sem ingressos")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Campos Vazios");
            alert.show();
            System.err.println("Campos Vazios");
            return;
        }
        Optional<AtracaoCliente> atracaoClienteOptional = atracaoClienteDao.findByIngressoId(Integer.parseInt(ingressos.getValue()));
        if(atracaoClienteOptional.isPresent()){//VERIFICA SE O INGRESSO É VALIDO
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Ingresso já utilizado");
            alert.show();
            System.err.println("Ingresso já utilizado");
        }
        else{
            AtracaoCliente atracaoCliente = new AtracaoCliente();
            atracaoCliente.setId_ingresso(Long.parseLong(idIngresso));
            atracaoCliente.setId_atracao(atracao.getId());
            atracaoCliente.setHorario_uso(Timestamp.valueOf(LocalDateTime.now()));
            atracaoClienteDao.create(atracaoCliente);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Ingresso utilizado");
            alert.show();

        }
    }

    @FXML
    void mostrarAtracoes(ActionEvent event) {
        AtracaoDao atracaoDao = new AtracaoDao();
        try {
            List<Atracao> atracoes = atracaoDao.findAll();

            if (atracoes.isEmpty()) {
                nome.setItems(FXCollections.observableArrayList("Sem atrações"));
            } else {
                List<String> nomes = new ArrayList<>();
                for (Atracao a : atracoes) {
                    nomes.add(String.valueOf(a.getNome()));
                }
                nome.setItems(FXCollections.observableArrayList(nomes));
            }
        } catch (Exception e) {
            nome.setItems(FXCollections.observableArrayList("Sem atrações"));
        }
    }

    private void mostrarIngressos(){
        IngressoDAO ingressoDao = new IngressoDAO();
        try{
            List<Ingresso> ingressosLista = ingressoDao.findByClienteId(Math.toIntExact(clienteLogado.getId()));

            if (ingressosLista.isEmpty()) {
                ingressos.setItems(FXCollections.observableArrayList("Sem ingressos"));
            } else {
                List<String> numeroIngressos = new ArrayList<>();
                for (Ingresso i : ingressosLista) {
                    numeroIngressos.add(String.valueOf(i.getId()));
                }
                ingressos.setItems(FXCollections.observableArrayList(numeroIngressos));
            }
        } catch (Exception e) {
            ingressos.setItems(FXCollections.observableArrayList("Sem ingressos"));
        }
    }
}
