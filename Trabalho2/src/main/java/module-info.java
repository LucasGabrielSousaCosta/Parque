module com.javabd.aula_conexao {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires static lombok;

    opens com.javabd.aula_conexao to javafx.fxml;
    exports com.javabd.aula_conexao;
}