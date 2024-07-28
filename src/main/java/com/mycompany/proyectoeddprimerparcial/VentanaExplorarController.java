/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.proyectoeddprimerparcial;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import Modelo.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jexa1
 */
public class VentanaExplorarController implements Initializable {

    @FXML
    private TextField barra_busqueda;
    @FXML
    private TextField barra_busquedaAnio;
    @FXML
    private Button btnBuscar;
    @FXML
    private Label lblResultados;
    @FXML
    private HBox hboxJuegos;
    @FXML
    private Button btnAnteriores;
    @FXML
    private Button btnSiguientes;
    @FXML
    private Button botonSalir;
    @FXML
    private Button backButton;
    @FXML
    private Button botonLuz;
    @FXML
    private Button botonNoche;
    @FXML
    private Text textUsuario;
    @FXML
    private ScrollPane root;
    @FXML
    private Button botonCatalogo;
    @FXML
    private Button botonExplorar;
    @FXML
    private VBox vboxPrincipal;
    @FXML
    private HBox hboxModo;

    public static String modo;
    public static String modocontrario;
    private LinkedListDobleCircular<Juego> juegos = App.cargarJuegos();
    private static LinkedListDobleCircular<VBox> vboxes = new LinkedListDobleCircular<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnAnteriores.setVisible(false);
        btnSiguientes.setVisible(false);
        textUsuario.setText(App.usr.getId());
        eventsUsuario(textUsuario);
        eventsUsuario(botonCatalogo);
        eventsUsuario(botonExplorar);
        buscarJuego();
        mover();
        setearSalir();
        cargarImagenesModos();
        backButton.setVisible(false);
        //System.out.println(modo+modocontrario);
        cambiarModo(modo, modocontrario);
        cambiarModoBarra(modo, modocontrario);
    }

    public LDEC<Juego> buscarJ() {
        LDEC<Juego> tmp = new LDEC<>();
        for (int i = 0; i < juegos.size(); i++) {
            tmp.addLast(juegos.get(i));
        }
        Comparator comparador = new Comparator<Juego>() {
            @Override
            public int compare(Juego j1, Juego j2) {
                if (escogerCondicion(j1, j2)) {
                    return 0;
                } else {
                    return 1;
                }
            }
        };
        LDEC<Juego> resultado = tmp.findAll(comparador, new Juego(barra_busqueda.getText(), barra_busquedaAnio.getText()));
        return resultado;
    }

    private boolean escogerCondicion(Juego j1, Juego j2) {
        String titulo = barra_busqueda.getText();
        String anio = barra_busquedaAnio.getText();
        boolean condicion = false;
        if (!titulo.equals("") && !anio.equals("")) {
            condicion = j1.getTitulo().toLowerCase().contains(j2.getTitulo().toLowerCase()) && j1.getAnio().equals(j2.getAnio());
        } else if (!titulo.equals("") && anio.equals("")) {
            condicion = j1.getTitulo().toLowerCase().contains(j2.getTitulo().toLowerCase());
        } else if (titulo.equals("") && !anio.equals("")) {
            condicion = j1.getAnio().equals(j2.getAnio());
        }
        return condicion;
    }

    private LinkedListDobleCircular<VBox> llenarCatalogo2(LDEC<Juego> juegosl) {
        LinkedListDobleCircular<VBox> tmp = new LinkedListDobleCircular<>();
        for (int i = 0; i < juegosl.getLength(); i++) {
            Juego actual = juegosl.getI(i);
            VBox vbJuego = new VBox();
            vbJuego.setAlignment(Pos.TOP_CENTER);
            ImageView imgvJuego = new ImageView();
            Image img = App.getImage("Images/" + actual.getTitulo() + ".jpg", 170, 227);
            imgvJuego.setImage(img);
            imgvJuego.setFitHeight(227);
            imgvJuego.setPreserveRatio(true);
            Rectangle clip = new Rectangle(170, 227);
            clip.setArcWidth(30);
            clip.setArcHeight(30);
            imgvJuego.setClip(clip);
            SnapshotParameters parameters = new SnapshotParameters();
            parameters.setFill(Color.TRANSPARENT);
            WritableImage image = imgvJuego.snapshot(parameters, null);
            imgvJuego.setClip(null);
            imgvJuego.setImage(image);

            imgvJuego.setOnMouseClicked(e -> {

                try {
                    abrirVentanaJuego(actual);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            });
            vbJuego.getChildren().add(imgvJuego);
            Label titulo = new Label(actual.getTitulo());
            titulo.setPadding(new Insets(5, 5, 5, 5));
            titulo.setTextFill(Color.web(modocontrario));
            vbJuego.getChildren().add(titulo);
            Label precio = new Label(actual.getPrecio());
            precio.setTextFill(Color.web(modocontrario));
            precio.setPadding(new Insets(5, 5, 5, 5));
            vbJuego.getChildren().add(precio);
            vbJuego.setSpacing(5);
            vbJuego.setPrefSize(170, 227);
            tmp.addLast(vbJuego);

            imgvJuego.setOnMouseEntered(e -> {
                vbJuego.setCursor(Cursor.HAND);
                vbJuego.setStyle("-fx-background-color:#343434;-fx-background-radius:15;");
                titulo.setTextFill(Color.web("white"));
                precio.setTextFill(Color.web("white"));
            });

            imgvJuego.setOnMouseExited(e -> {
                vbJuego.setCursor(Cursor.DEFAULT);
                vbJuego.setStyle("-fx-background-color:" + modo + ";-fx-background-radius:15;");
                titulo.setTextFill(Color.web(modocontrario));
                precio.setTextFill(Color.web(modocontrario));
            });
        }
        return tmp;
    }

    private void abrirVentanaJuego(Juego actual) throws IOException {
        VentanaUsuarioController.modo = modo;
        VentanaUsuarioController.modocontrario = modocontrario;
        VentanaDetalleController.usr = App.usr;
        VentanaDetalleController.selected = actual;
        VentanaDetalleController.modo = modo;
        VentanaDetalleController.modocontrario = modocontrario;
        FXMLLoader fxmloader = new FXMLLoader(App.class.getResource("VentanaDetalle.fxml"));
        Parent root1 = fxmloader.load();
        Stage s = (Stage) root.getScene().getWindow();
        Scene scene = new Scene(root1, 1280, 720);
        s.setScene(scene);
        App.pilaVentanas.push("VentanaExplorar");
        s.show();
    }

    public void mover() {
        moverIzq();
        moverDer();
    }

    public void moverIzq() {
        btnAnteriores.setOnAction(e -> {
            VBox tmp = (VBox) hboxJuegos.getChildren().get(0);
            hboxJuegos.getChildren().clear();
            for (int i = 0; i < 5; i++) {
                tmp = vboxes.getAnterior(tmp);
            }
            for (int i = 0; i < 5; i++) {
                hboxJuegos.getChildren().add(tmp);
                tmp = vboxes.getSiguiente(tmp);
            }
        });
    }

    public void moverDer() {
        btnSiguientes.setOnAction(e -> {
            VBox tmp = (VBox) hboxJuegos.getChildren().get(4);
            hboxJuegos.getChildren().clear();
            for (int i = 0; i < 5; i++) {
                hboxJuegos.getChildren().add(vboxes.getSiguiente(tmp));
                tmp = vboxes.getSiguiente(tmp);
            }
        });
    }

    public void buscarJuego() {
        btnBuscar.setOnAction(e -> {
            hboxJuegos.getChildren().clear();
            vboxes = llenarCatalogo2(buscarJ());
            if (vboxes.size() <= 5) {
                btnAnteriores.setVisible(false);
                btnSiguientes.setVisible(false);
                for (int i = 0; i < vboxes.size(); i++) {
                    hboxJuegos.getChildren().add(vboxes.get(i));
                }
            } else {
                btnAnteriores.setVisible(true);
                btnSiguientes.setVisible(true);
                for (int i = 0; i < 5; i++) {
                    hboxJuegos.getChildren().add(vboxes.get(i));
                }
            }
            lblResultados.setVisible(true);
        });
    }

    public void setLight() {
        modo = "white";
        modocontrario = "#121212";
        cambiarModo(modo, modocontrario);
        cambiarModoBarra(modo, modocontrario);

        //reseniasIniciales();
    }

    public void setNight() {
        modo = "#121212";
        modocontrario = "white";
        cambiarModo(modo, modocontrario);
        cambiarModoBarra(modo, modocontrario);
        //reseniasIniciales();
    }

    public void setearSalir() {
        try ( FileInputStream input = new FileInputStream(App.pathSS + "door5.png")) {
            Image img = new Image(input, 35, 35, false, true);
            ImageView imgv = new ImageView(img);
            botonSalir.setGraphic(imgv);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    public void desloggear(ActionEvent e) throws IOException {
        Alert conf_desloggear = new Alert(Alert.AlertType.CONFIRMATION);
        conf_desloggear.setHeaderText(null);
        conf_desloggear.setContentText("¿Está seguro de que desea cerrar sesion?");
        Optional<ButtonType> confirmacion = conf_desloggear.showAndWait();
        if (confirmacion.get() == ButtonType.OK) {
            App.serializarUsuario(App.usr);
            App.usr = null;
            FXMLLoader fxmloader = new FXMLLoader(App.class.getResource("Login.fxml"));
            Parent root1 = fxmloader.load();
            Stage s = (Stage) root.getScene().getWindow();
            Scene scene = new Scene(root1, 1280, 720);
            s.setScene(scene);
            App.pilaVentanas.clear();
            //App.pilaVentanas.push("VentanaDetalle");
            s.show();
        }
    }

    @FXML
    public void cargarCatalogo(ActionEvent e) throws IOException {
        VentanaPrincipalDemoController.modo = modo;
        VentanaPrincipalDemoController.modocontrario = modocontrario;
        FXMLLoader fxmloader = new FXMLLoader(App.class.getResource("VentanaPrincipalDemo.fxml"));
        Parent root1 = fxmloader.load();
        Stage s = (Stage) root.getScene().getWindow();
        Scene scene = new Scene(root1, 1280, 720);
        s.setScene(scene);
        App.pilaVentanas.clear();
        s.show();

    }

    @FXML
    public void cargarExplorar(ActionEvent e) throws IOException {
        VentanaExplorarController.modo = modo;
        VentanaExplorarController.modocontrario = modocontrario;
        FXMLLoader fxmloader = new FXMLLoader(App.class.getResource("VentanaExplorar.fxml"));
        Parent root1 = fxmloader.load();
        Stage s = (Stage) root.getScene().getWindow();
        Scene scene = new Scene(root1, 1280, 720);
        s.setScene(scene);
        App.pilaVentanas.clear();
        s.show();

    }

    public void cargarImagenesModos() {
        try ( FileInputStream input = new FileInputStream(App.pathSS + "darkmode.png");  FileInputStream input2 = new FileInputStream(App.pathSS + "lightmode.png");) {
            Image imgnoche = new Image(input, 35, 35, false, true);
            ImageView viewnoche = new ImageView(imgnoche);
            viewnoche.setFitHeight(35);
            viewnoche.setPreserveRatio(true);
            botonNoche.setGraphic(viewnoche);
            Image imgluz = new Image(input2, 35, 35, false, true);
            ImageView viewluz = new ImageView(imgluz);
            viewnoche.setFitHeight(35);
            viewnoche.setPreserveRatio(true);
            botonLuz.setGraphic(viewluz);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void eventsUsuario(Text Textusuario) {
        Textusuario.setFocusTraversable(true);
        Textusuario.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Textusuario.setUnderline(true);
            }
        });
        Textusuario.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Textusuario.setUnderline(false);
            }
        });
        Textusuario.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                try {
                    abrirVentanaUsuario(Textusuario);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void abrirVentanaUsuario(Text textusuario) throws IOException {

        VentanaUsuarioController.modo = modo;
        VentanaUsuarioController.modocontrario = modocontrario;

        VentanaUsuarioController.setearUsuario(textusuario);
        FXMLLoader fxmloader = new FXMLLoader(App.class.getResource("VentanaUsuario.fxml"));
        Parent root1 = fxmloader.load();
        Stage s = (Stage) textusuario.getScene().getWindow();
        Scene scene = new Scene(root1, 1280, 720);
        s.setScene(scene);
        App.pilaVentanas.push("VentanaExplorar");
        s.show();
    }

    public void eventsUsuario(Button boton) {

        boton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                boton.setUnderline(true);
            }
        });
        boton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                boton.setUnderline(false);
            }
        });
    }

    private void cambiarModo(String modo, String modocontrario) {
        vboxPrincipal.setStyle("-fx-background-color:" + modo);
        lblResultados.setTextFill(Color.web(modocontrario));
        if (vboxes != null) {
            for (int i = 0; i < vboxes.size(); i++) {
                VBox actual = (VBox) vboxes.get(i);
                actual.setStyle("-fx-background-color:" + modo);
                if (!actual.getChildren().isEmpty()) {
                    Label titulo = (Label) actual.getChildren().get(1);
                    titulo.setTextFill(Color.web(modocontrario));
                    Label precio = (Label) actual.getChildren().get(2);
                    precio.setTextFill(Color.web(modocontrario));
                }
            }
        }

    }

    public void cambiarModoBarra(String modo, String modocontrario) {
        hboxModo.setStyle("-fx-border-color:" + modocontrario + ";-fx-border-width:3;-fx-border-radius:5");
        backButton.setStyle("-fx-text-fill:" + modocontrario + ";-fx-background-color:" + modo);
        textUsuario.setStyle("-fx-fill:" + modocontrario);
        botonCatalogo.setStyle("-fx-text-fill:" + modocontrario + ";-fx-background-color:" + modo);
        botonExplorar.setStyle("-fx-text-fill:" + modocontrario + ";-fx-background-color:" + modo);
        botonSalir.setStyle("-fx-background-color:red;-fx-background-radius:24;-fx-border-radius:20;-fx-border-width:3;-fx-border-color:" + modocontrario);
    }
}
