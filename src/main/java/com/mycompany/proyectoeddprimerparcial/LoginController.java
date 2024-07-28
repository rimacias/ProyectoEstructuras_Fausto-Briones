/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.proyectoeddprimerparcial;

import Modelo.Usuario;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jexa1
 */
public class LoginController implements Initializable {
    
    @FXML
    private VBox vboxRoot;
    @FXML
    private TextField txfUsuario;
    @FXML
    private PasswordField txfClave;
    @FXML
    private Button btnIngresar;
    @FXML
    private Label lblAviso;
    @FXML
    private Button btnRegistrar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btnIngresar.setOnAction(e -> {
            lblAviso.setText("");
            verificarIngreso();
        });
        btnRegistrar.setOnAction(e -> {
            lblAviso.setText("");
            registrar();
        });
    }

    private void verificarIngreso() {
        lblAviso.setTextFill(Color.web("red"));
        try ( ObjectInputStream input = new ObjectInputStream(new FileInputStream("Usuarios/" + txfUsuario.getText()+".bin"))) {
            Usuario user = (Usuario) input.readObject();
            if (user.getContrasenia().equals(txfClave.getText())) {
                txfUsuario.setEditable(false);
                txfClave.setEditable(false);
                lblAviso.setTextFill(Color.web("green"));
                lblAviso.setText("Ingreso correcto");
                App.usr=user;
                abrirVistaPrincipal();
            } else {
                lblAviso.setText("Los datos ingresados son incorrectos");
                limpiarCampos();
            }
        } catch (FileNotFoundException f) {
            lblAviso.setText("Los datos ingresados son incorrectos");
            limpiarCampos();
        } catch (IOException io) {
            lblAviso.setText("Hubo un error, intenta otra vez");
        } catch (ClassNotFoundException ex) {

        }
    }

    private void registrar() {
        lblAviso.setTextFill(Color.web("red"));
        try ( ObjectInputStream input = new ObjectInputStream(new FileInputStream("Usuarios/" + txfUsuario.getText()+".bin"))) {
            lblAviso.setText("Nombre de usuario no disponible");
            limpiarCampos();
        } catch (FileNotFoundException f) {
            if (txfUsuario.getText() == null || txfUsuario.getText().length() < 4) {
                lblAviso.setText("El nombre de usuario debe contener 4 caracteres o más");
                limpiarCampos();
            } else if (txfClave.getText() == null || txfClave.getText().length() < 8) {
                lblAviso.setText("La contraseña debe contener 8 caracteres o más");
                limpiarCampos();
            } else {
                try ( ObjectOutputStream ob = new ObjectOutputStream(new FileOutputStream("Usuarios/" + txfUsuario.getText()+".bin"))) {
                    ob.writeObject(new Usuario(txfUsuario.getText(), txfClave.getText()));
                    lblAviso.setTextFill(Color.web("green"));
                    App.usr=App.leerUsuario(txfUsuario.getText());
                    lblAviso.setText("Se ha registrado exitosamente");
                    txfUsuario.setEditable(false);
                    txfClave.setEditable(false);
                    abrirVistaPrincipal();
                } catch (IOException io) {
                    lblAviso.setText("Hubo un error, intenta otra vez");
                }
            }
        } catch (IOException io) {
            lblAviso.setText("Hubo un error, intenta otra vez");
        }
    }
    private void limpiarCampos() {
        txfUsuario.setText("");
        txfClave.setText("");
    }
    private void abrirVistaPrincipal(){
        btnRegistrar.setDisable(true);
        btnIngresar.setDisable(true);
        
         Platform.runLater(()->{
            try {
                Thread.sleep(300);
                App.abrirVentana("VentanaPrincipalDemo");
                Stage s=(Stage)btnRegistrar.getScene().getWindow();
                s.close();
            } catch (InterruptedException ex) {
               
            }
        });
            
        
    }
}
