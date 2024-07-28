package com.mycompany.proyectoeddprimerparcial;

import Modelo.Juego;
import Modelo.TDAArraylist;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Modelo.LinkedListDobleCircular;
import Modelo.Usuario;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Stack;
import javafx.scene.image.Image;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    public static Usuario usr;
    public static String pathSS = "Images/";
    public static String pathReviews = "reviews/";
    public static String pathUsuarios = "Usuarios/";
    public static Stack<String> pilaVentanas = new Stack<>();

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("Login"), 1280, 720);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Game Store");
        stage.getIcons().add(getImage("Images/icon.png"));
        //System.out.println("Anyamemby");
        stage.show();

    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    public static Image getImage(String name) {
        Image img = null;
        try ( FileInputStream f = new FileInputStream(name)) {
            img = new Image(f);
        } catch (FileNotFoundException f) {
           
        } catch (IOException i) {
            System.out.println("Hubo un error, inténtalo más tarde");
        }
        return img;
    }
    public static Image getImage(String name,int width,int height) {
        Image img = null;
        try ( FileInputStream f = new FileInputStream(name)) {
            img = new Image(f, width, height, false, true);
        } catch (FileNotFoundException f) {
            
        } catch (IOException i) {
            System.out.println("Hubo un error, inténtalo más tarde");
        }
        return img;
    }

    public static Image getImage(String name, boolean destacado) {
        Image img = null;
        try ( FileInputStream f = new FileInputStream(name)) {
            img = new Image(f, 640, 360, false, true);
        } catch (FileNotFoundException f) {
            
        } catch (IOException i) {
            System.out.println("Hubo un error, inténtalo más tarde");
        }
        return img;
    }

    public static TDAArraylist<String[]> cargarDatos(String ruta) {
        TDAArraylist<String[]> retorno = new TDAArraylist<>();
        try ( BufferedReader bf = new BufferedReader(new FileReader(ruta,StandardCharsets.UTF_8))) {
            //Se lee la cabecera del archivo
            bf.readLine();
            String linea;
            while ((linea = bf.readLine()) != null) {
                retorno.add(linea.split(","));
            }
        } catch (FileNotFoundException fe) {

        } catch (IOException ioEx) {

        }
        return retorno;
    }

    public static LinkedListDobleCircular<Juego> cargarJuegos() {
        LinkedListDobleCircular<Juego> juegos = new LinkedListDobleCircular<>();
        TDAArraylist<String[]> datos = cargarDatos("Files/games.csv");
        for (int i = 0; i < datos.size(); i++) {
            String[] arr = datos.get(i);
            Juego juego = new Juego(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6]);
            juegos.addLast(juego);
        }
        return juegos;
    }

    public static void abrirVentana(String ventana) {
        try {
            Scene scene1 = new Scene(loadFXML(ventana), 1280, 720);
            Stage s = new Stage();
            s.setScene(scene1);
            s.setResizable(false);
            s.setTitle("Game Store");
            s.getIcons().add(getImage("Images/icon.png"));
            s.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void serializarUsuario(Usuario usr1) {
        try ( ObjectOutputStream fos = new ObjectOutputStream(new FileOutputStream(App.pathUsuarios + usr1.getId() + ".bin",false))) {
            fos.writeObject(usr1);
        } catch (IOException ex1) {
            ex1.printStackTrace();
        }
    }

    public static Usuario leerUsuario(String usuario) {
        try ( ObjectInputStream ros = new ObjectInputStream(new FileInputStream(App.pathUsuarios + usuario + ".bin"))) {
            //System.out.println(usuario);
            Usuario usr2 = (Usuario) ros.readObject();

            return usr2;
        } catch (IOException | ClassNotFoundException ex1) {
        }
        return null;
    }
}
