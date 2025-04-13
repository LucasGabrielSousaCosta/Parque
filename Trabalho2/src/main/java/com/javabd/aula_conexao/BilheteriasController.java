package com.javabd.aula_conexao;

import com.javabd.aula_conexao.dao.BilheteriaDao.BilheteriaDAO;
import com.javabd.aula_conexao.dao.IngressoDao.IngressoDAO;
import com.javabd.aula_conexao.model.Bilheteria;
import com.javabd.aula_conexao.model.Cliente;
import com.javabd.aula_conexao.model.FormaPagamento;
import com.javabd.aula_conexao.model.Ingresso;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BilheteriasController {

    @FXML
    private TextField preco;

    @FXML
    private TextField horario_fechamento;

    @FXML
    private ComboBox<String> formaPagamento;

    @FXML
    private ComboBox<String> id;

    @FXML
    private TextField quantidade;

    private Cliente clienteLogado;

    @FXML
    public void initialize() {
        mostrarBilheterias(null);
        //PARA OBTER O DADO DO CLIENTE LOGADO
        Platform.runLater(() -> {
            Stage stage = (Stage) preco.getScene().getWindow();// utilizando o elemento preço ja carregado
            Optional<Cliente> clienteOptional = (Optional<Cliente>) stage.getUserData();

            if (clienteOptional.isPresent()) {
                this.clienteLogado = clienteOptional.get();
            }
        });
    }

    @FXML
    void btn_buscar(ActionEvent event) {
        String idBilheteria = id.getValue();
        BilheteriaDAO bilheteriaDAO = new BilheteriaDAO();
        if(idBilheteria == null || idBilheteria.equals("Sem bilheterias")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Nenhuma bilheteria selecionada");
            alert.show();
        }else{
            Optional<Bilheteria> bilheteriaOptional = bilheteriaDAO.findById(Integer.parseInt(idBilheteria));
            if(bilheteriaOptional.isPresent()){
                Bilheteria bilheteria = bilheteriaOptional.get();
                preco.setText(String.valueOf(bilheteria.getPreco()));
                horario_fechamento.setText(String.valueOf(bilheteria.getHorarioFechamento()));
                quantidade.setText(String.valueOf(bilheteria.getQuantidadeDisponivel()));
                formaPagamento.setItems(FXCollections.observableArrayList("pix","credito","debito","dinheiro"));
            }
        }
    }

    @FXML
    void btn_atracoes(ActionEvent event) {
        TrocarPagina(event);
    }

    @FXML
    void btn_comprar(ActionEvent event) {
        String formaPagamentoString = formaPagamento.getValue();//Obtem a forma de pagamento selecionada no front
        String idString = id.getValue();//Obtem o id da bilheteria selecionada no front

        IngressoDAO ingressoDAO = new IngressoDAO();//Para criar novo ingresso
        Ingresso ingresso = new Ingresso();

        if(idString == null || idString.equals("Sem bilheterias") || formaPagamentoString == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Campos Vazios");
            alert.show();
            System.err.println("Campos Vazios");
        }
        else{
            ingresso.setPagamento(FormaPagamento.valueOf(formaPagamentoString));
            ingresso.setId_bilheteria(Long.parseLong(idString));
            ingresso.setData_venda(Date.valueOf(LocalDate.now()));
            ingresso.setId_cliente(clienteLogado.getId());
            ingressoDAO.create(ingresso);


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Ingresso comprado");
            alert.show();

            //A QUANTIDADE DE INGRESSOS É ATUALIZADA AUTOMATICAMENTE USANDO O TRIGGER, APÓS A COMPRA

            btn_buscar(event);//Atualiza o número de ingressos
        }
    }

    @FXML
    void mostrarBilheterias(ActionEvent event) {
        BilheteriaDAO bilheteriaDAO = new BilheteriaDAO();
        try {
            List<Bilheteria> bilheterias = bilheteriaDAO.findAll();

            if (bilheterias.isEmpty()) {
                id.setItems(FXCollections.observableArrayList("Sem bilheterias"));
            } else {
                List<String> ids = new ArrayList<>();
                for (Bilheteria b : bilheterias) {
                    ids.add(String.valueOf(b.getId()));
                }
                id.setItems(FXCollections.observableArrayList(ids));
            }
        } catch (Exception e) {
            id.setItems(FXCollections.observableArrayList("Sem bilheterias"));
        }
    }

    @FXML
    void TrocarPagina(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("atracao.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setUserData(clienteLogado);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
