package com.javabd.aula_conexao;

import com.javabd.aula_conexao.dao.ClienteDao.ClienteDAO;
import com.javabd.aula_conexao.model.Cliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class CadastroController {

    @FXML
    private Button btn_login;

    @FXML
    private TextField tf_cpf;

    @FXML
    private TextField tf_nome;

    @FXML
    private TextField tx_endereco;

    @FXML
    private TextField tx_telefone;

    @FXML
    private Label StringEndereco;

    @FXML
    private Label StringTelefone;

    @FXML
    private Label StringCPF;

    @FXML
    private Button btn_cadastro;

    @FXML
    private Label Titulo;

    boolean logar = false;// controla se está logando ou cadastrando

    @FXML
    void btn_login_cadastro(ActionEvent event) {// alterna entre login e cadastro
        limparCampos();
        if(Objects.equals(btn_login.getText(), "Login")){
            btn_login.setText("Cadastro");
            tx_endereco.setDisable(true);
            tx_endereco.setVisible(false);
            tx_telefone.setDisable(true);
            tx_telefone.setVisible(false);
            StringTelefone.setVisible(false);
            StringEndereco.setVisible(false);
            StringCPF.setText("Senha(CPF)");
            btn_cadastro.setText("Realizar Login");
            Titulo.setText("Login");
            logar = true;
        }else{
            btn_login.setText("Login");
            tx_endereco.setDisable(false);
            tx_endereco.setVisible(true);
            tx_telefone.setDisable(false);
            tx_telefone.setVisible(true);
            StringTelefone.setVisible(true);
            StringEndereco.setVisible(true);
            StringCPF.setText("CPF");
            btn_cadastro.setText("Realizar Cadastro");
            Titulo.setText("Cadastro");
            logar = false;
        }
    }

    void limparCampos(){
        tf_cpf.setText("");
        tf_nome.setText("");
        tx_endereco.setText("");
        tx_telefone.setText("");
    }

    @FXML
    void btn_cadastrar(ActionEvent event) {
        String cpf = tf_cpf.getText();
        String nome = tf_nome.getText();
        String endereco = tx_endereco.getText();
        String telefone = tx_telefone.getText();

        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente = new Cliente();

        if(logar && !cpf.isEmpty() && !nome.isEmpty()){
            cliente.setCpf(cpf);
            cliente.setNome(nome);
            Optional<Cliente> clienteOptional = clienteDAO.findByNomeCPF(cpf,nome);
            if(clienteOptional.isPresent()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso");
                alert.setHeaderText("Login realizado");
                limparCampos();
                alert.show();
                TrocarPagina(event,clienteOptional);
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Erro");
                alert.setHeaderText("Usuário não encontrado");
                limparCampos();
                alert.show();
            }
        }else if(cpf.isEmpty() || nome.isEmpty() || endereco.isEmpty() || telefone.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Campos Vazios");
            alert.show();
        }else {
            cliente.setCpf(cpf);
            cliente.setNome(nome);
            cliente.setEndereco(endereco);
            cliente.setTelefone(telefone);
            clienteDAO.create(cliente);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Dados Cadastrados");
            limparCampos();
            alert.show();
        }
    }

    @FXML
    void TrocarPagina(ActionEvent event,Optional<Cliente> cliente) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("bilheterias.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setUserData(cliente);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
