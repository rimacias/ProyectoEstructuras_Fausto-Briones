/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoeddprimerparcial;

import Modelo.Juego;
import Modelo.Resenia;
import Modelo.Usuario;
import com.mycompany.proyectoeddprimerparcial.App;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 *
 * @author AVGla
 */
public class VentanaDetalleController implements Initializable{
    
    @FXML
    ScrollPane mainScroll;
    @FXML
    VBox mainVbox;
    @FXML
    HBox buttonHbox;
    @FXML
    Button backButton;
    @FXML
    Text gameTitle;
    @FXML
    HBox hboxGameImg;
    @FXML
    HBox hboxImgSS;
    @FXML
    ScrollPane scrollSS;
    @FXML
    VBox vboxSS;
    @FXML
    Text textReview;
    @FXML
    VBox vboxReviews;
    @FXML
    HBox hboxTitlePrice;
    @FXML
    Text textPrice;
    @FXML
    VBox vboxInfoGame;
    @FXML
    HBox hboxgenero;
    @FXML
    HBox hboxDev;
    @FXML
    Text textGen;
    @FXML
    Text textGameGen;
    @FXML
    Text textDev;
    @FXML
    Text textGameDev;
    @FXML
    Text textDescripcionG;
    @FXML
    Text textDescripcion;
    @FXML
    ComboBox comboOrden;
    @FXML
    HBox hboxAnio;
    @FXML
    Text textAnio;
    @FXML
    Text textLanz;
    @FXML
    VBox vboxOrderReviews;
    @FXML
    Text textanioBusqueda;
    @FXML
    TextField textFieldAnio;
    @FXML
    Button buscarAnio;
    @FXML
    Button botonLuz;
    @FXML
    Button botonNoche;
    @FXML
    Text textOrdenar;
    @FXML
    HBox hboxModo;
    @FXML
    Button botonSalir;
    @FXML
    Button botonWishlist;
    @FXML
    HBox hboxEscribir;
    @FXML
    Text textDeja;
    @FXML
    ToggleGroup valoracion;
    @FXML
    TextArea textAreaEscribe;
    @FXML
    Button botonEnviar;
    @FXML
    HBox hboxRadios;
    @FXML
    Text textUsuario;
    @FXML
    Button botonCatalogo;
    @FXML
    Button botonExplorar;
    
    
    
    public static String modo;
    public static String modocontrario;
    public static Juego selected;
    //public String modo="white";
    public static Usuario usr;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        llenarVentana();
        vboxOrderReviews.setSpacing(20);
        comboOrden.getItems().addAll("Mas reciente", "Mas antiguo", "Mejor calificado", "Peor calificado");
        cargarImagenesModos();
        cambiarModo(modo,modocontrario);
        textUsuario.setText(usr.getId());
        setearSalir();
        //backButton.setVisible(false);
        eventsUsuario(textUsuario);
        eventsUsuario(botonCatalogo);
        eventsUsuario(botonExplorar);
        if(usr.getWishlist().find(new Comparator<Juego>(){
            @Override
            public int compare(Juego j1, Juego j2) {
                if(j1.getTitulo().equals(j2.getTitulo())){
                return 0;
                }else{
                return 1;
                }
            }
        }, selected)!=null){
        botonWishlist.setDisable(true);
        botonWishlist.setText("Ya está en la Wishlist");
        
        }
        
        
    }
    @FXML
    public void filtro(ActionEvent a){
        if(((String)comboOrden.getValue()).equals("Mas reciente")){
        filtrarNuevos();
        }else if(((String)comboOrden.getValue()).equals("Mas antiguo")){
        filtrarViejos();
        }else if(((String)comboOrden.getValue()).equals("Mejor calificado")){
        filtrarMejores();
        }else{
        filtrarPeores();
        }
    
    }
    
    public void llenarVentana(){
    gameTitle.setText(selected.getTitulo());
    textPrice.setText("USD "+selected.getPrecio());
    textGameGen.setText(selected.getGenero());
    textGameDev.setText(selected.getDesarrolladora());
    textDescripcion.setText(selected.getDescripcion());
    textAnio.setText(selected.getAnio());
    ImageView imgv=new ImageView();
    imgv.setFitHeight(395);
    imgv.setFitWidth(686);
    imgv.setImage(Juego.cargarImagenes(selected.getId()).getFirst());
    hboxImgSS.getChildren().add(imgv);
    hboxImgSS.setEffect(new Reflection());
    HBox.setMargin(imgv,new Insets(25,100,0,100));
    setearSS();
    reseniasIniciales();
        
    //imgvSS.setImage(selected.getImages().get(1));
    }
    
//    public void llenarVboxSS(){
//    for(Image img:selected.getImages()){
//    ImageView imgv= new ImageView(img,236,175);
//    imgv.setFocusTraversable(true);
//    imgv.setSmooth(true);
//    imgv.setOnMouseClicked(new EventHandler<MouseEvent>(){
//    @Override
//    public void handle(MouseEvent e){
//    imgvSS.setImage(img);
//    }
//    });
//    vboxSS.getChildren().add(imgv);
//    }
//    }
    public void anadirClickEventSS(ImageView imgv){
    imgv.setFocusTraversable(true);
    imgv.setOnMouseClicked(new EventHandler<MouseEvent>(){
    @Override
    public void handle(MouseEvent click1){
    cambiarImagen(imgv);
    }
    
    });
    
    }
    public void anadirEventSeleccion(VBox vimgv){
    vimgv.setOnMouseEntered(new EventHandler<MouseEvent>(){
    @Override
    public void handle(MouseEvent mouse1){
    vimgv.setStyle("-fx-border-color:#ada5a5;-fx-border-width:4");
    }
    
    
    });
    vimgv.setOnMouseExited(new EventHandler<MouseEvent>(){
    @Override
    public void handle(MouseEvent mouse1){
    vimgv.setStyle("-fx-border-color:null");
    }
    
    
    });
    
    }
    
    
    public void setearSS(){
    for(Image img:Juego.cargarImagenes(selected.getId())){
    VBox vimgv=new VBox();
    vimgv.setStyle("-fx-background-color:black");
    ImageView imgv=new ImageView();
    imgv.setPreserveRatio(false);
    imgv.setFitHeight(200);
    imgv.setFitWidth(270);
    imgv.setImage(img);
    anadirClickEventSS(imgv);
    anadirEventSeleccion(vimgv);
    vimgv.getChildren().add(imgv);
    vboxSS.getChildren().add(vimgv);
    }
    }
    public void setLight(){    
    modo="white";
    modocontrario="#121212";
    cambiarModo(modo,modocontrario);
    //reseniasIniciales();
    
            
    }
    public void setNight(){  
    modo="#121212";
    modocontrario="white";
    cambiarModo(modo,modocontrario);
    //reseniasIniciales();
    }
    public void cambiarImagen(ImageView imgv2) {
    FadeTransition transition=new FadeTransition();
    transition.setDuration(new Duration(2000));
    transition.setCycleCount(1);
    transition.setFromValue(1.0);
    transition.setToValue(0.0);
    transition.setAutoReverse(true);
    transition.setNode(hboxImgSS.getChildren().get(0));
    transition.play();
    transition.setOnFinished(new EventHandler<ActionEvent>(){
      public void handle(ActionEvent e){
                ImageView imgv1=new ImageView();
                imgv1.setFitHeight(395);
                imgv1.setFitWidth(686);
                imgv1.setImage(imgv2.getImage());
                hboxImgSS.getChildren().remove(0);
                hboxImgSS.getChildren().add(imgv1);
                HBox.setMargin(imgv1,new Insets(25,100,0,100));
                //System.out.println(transition.getFromValue()+" "+transition.getToValue());
            
        }
        
        });
    
    }
    private void reseniasIniciales(){
    vboxOrderReviews.getChildren().clear();
    for(Resenia r:selected.getResenias()){
        //System.out.println(r.getAnio());
        anadirVboxResenia(r,modocontrario);
        }
    
    }
    
    
    
    private void filtrarNuevos(){
        vboxOrderReviews.getChildren().clear();
        PriorityQueue<Resenia> resenias=new PriorityQueue(selected.getResenias().getLength(),new Comparator<Resenia>(){
        
        @Override
        public int compare(Resenia r1,Resenia r2){
        if(r1.getAnio()>r2.getAnio()){
        return -1;    
        }else if(r1.getAnio()==r2.getAnio()){
        return 0;
        }else{
        return 1;
        }
        }
        
        });
        for(Resenia r:selected.getResenias()){
        resenias.add(r);
        }
        //System.out.println(resenias.size());
        Resenia r3=resenias.poll();
        while(r3!=null){
            //System.out.println(r3.anio);
        anadirVboxResenia(r3,modocontrario);
        r3=resenias.poll();
        }
    }
    private void filtrarViejos(){
        vboxOrderReviews.getChildren().clear();
        PriorityQueue<Resenia> resenias=new PriorityQueue(selected.getResenias().getLength(),new Comparator<Resenia>(){
        
        @Override
        public int compare(Resenia r1,Resenia r2){
        if(r1.getAnio()>r2.getAnio()){
        return 1;    
        }else if(r1.getAnio()==r2.getAnio()){
        return 0;
        }else{
        return -1;
        }
        }
        
        });
        for(Resenia r:selected.getResenias()){
        resenias.add(r);
        }
        Resenia r3=resenias.poll();
        while(r3!=null){
            //System.out.println(r3.anio);
        anadirVboxResenia(r3,modocontrario);
        r3=resenias.poll();
        }
    }
    
    private void filtrarMejores(){
        vboxOrderReviews.getChildren().clear();
        PriorityQueue<Resenia> resenias=new PriorityQueue(selected.getResenias().getLength(),new Comparator<Resenia>(){
        
        @Override
        public int compare(Resenia r1,Resenia r2){
            return r2.getValoracion()-r1.getValoracion();
        }
        
        
        });
        for(Resenia r:selected.getResenias()){
        resenias.add(r);
        }
        Resenia r3=resenias.poll();
        while(r3!=null){
            //System.out.println(r3.anio);
        anadirVboxResenia(r3,modocontrario);
        r3=resenias.poll();
        }
    }
    private void filtrarPeores(){
        vboxOrderReviews.getChildren().clear();
        PriorityQueue<Resenia> resenias=new PriorityQueue(selected.getResenias().getLength(),new Comparator<Resenia>(){
        
        @Override
        public int compare(Resenia r1,Resenia r2){
            return r1.getValoracion()-r2.getValoracion();
        }
        
        
        });
        for(Resenia r:selected.getResenias()){
        resenias.add(r);
        }
        Resenia r3=resenias.poll();
        while(r3!=null){
            //System.out.println(r3.anio);
        anadirVboxResenia(r3,modocontrario);
        r3=resenias.poll();
        }
    }
    @FXML
    public void regresarPrincipal()throws IOException{
    VentanaExplorarController.modo=modo;
    VentanaExplorarController.modocontrario=modocontrario;
    VentanaPrincipalDemoController.modo = modo;
    VentanaPrincipalDemoController.modocontrario = modocontrario;    
        
        
    FXMLLoader fxmloader = new FXMLLoader(App.class.getResource(App.pilaVentanas.pop()+".fxml"));
    Parent root1 = fxmloader.load();
    Stage s=(Stage)mainScroll.getScene().getWindow();
    Scene scene=new Scene(root1,1280,720);
    s.setScene(scene);
    s.show();
    }
    public void anadirVboxResenia(Resenia r3,String modocontrario){
    VBox vbresenia=new VBox();
        vbresenia.setSpacing(20);
        HBox hboxusvalanio=new HBox();
        hboxusvalanio.setSpacing(200);
        Text textus=new Text(r3.getUsuario());
        textus.setWrappingWidth(150);
        textus.setStyle("-fx-font-size:20;-fx-fill:"+modocontrario);
        eventsUsuario(textus);
        HBox hboxus=new HBox(textus);
        Text textanio=new Text("Año");
        textanio.setStyle("-fx-font-size:20;-fx-fill:"+modocontrario);
        Text textyear=new Text(String.valueOf(r3.getAnio()));
        textyear.setStyle("-fx-font-size:20;-fx-fill:"+modocontrario);
        HBox hboxanio=new HBox();
        hboxanio.getChildren().addAll(textanio,textyear);
        hboxanio.setSpacing(5);
        Text textval=new Text("Valoracion");
        textval.setStyle("-fx-font-size:20;-fx-fill:"+modocontrario);
        Text textvalor=new Text((String.valueOf(r3.getValoracion())+"/"+5));
        textvalor.setStyle("-fx-font-size:20;-fx-fill:"+modocontrario);
        HBox hboxvalor=new HBox();
        hboxvalor.getChildren().addAll(textval,textvalor);
        hboxvalor.setSpacing(5);
        hboxusvalanio.getChildren().addAll(hboxus,hboxanio,hboxvalor);
        HBox.setMargin(textus,new Insets(0,0,0,5));
 //       HBox.setMargin(hboxanio,new Insets(0,0,0,200));
//        HBox.setMargin(textyear,new Insets(0,0,0,20));
//        HBox.setMargin(textval,new Insets(0,0,0,400));
//        HBox.setMargin(textvalor,new Insets(0,0,0,40));
        Text textdescripcion=new Text(r3.getComment());
        //lbldescripcion.setTextAlignment(TextAlignment.JUSTIFY);
        textdescripcion.setStyle("-fx-font-size:20;-fx-fill:#8c8d8f");
        textdescripcion.setWrappingWidth(1280);
        vbresenia.getChildren().addAll(hboxusvalanio,textdescripcion);
        VBox.setMargin(textdescripcion, new Insets(0,5,0,5));
        vbresenia.setStyle("-fx-border-color:"+modocontrario);
        vboxOrderReviews.getChildren().add(vbresenia);
    
    }
    public void cargarImagenesModos(){
    try(FileInputStream input=new FileInputStream(App.pathSS+"darkmode.png");FileInputStream input2=new FileInputStream(App.pathSS+"lightmode.png");){
        Image imgnoche = new Image(input,35,35,false,true);
        ImageView viewnoche = new ImageView(imgnoche);
        viewnoche.setFitHeight(35);
        viewnoche.setPreserveRatio(true);
        botonNoche.setGraphic(viewnoche);
        Image imgluz = new Image(input2,35,35,false,true);
        ImageView viewluz = new ImageView(imgluz);
        viewnoche.setFitHeight(35);
        viewnoche.setPreserveRatio(true);
        botonLuz.setGraphic(viewluz);
        }   catch (IOException ex) {
                ex.printStackTrace();
            }
    
    }
    
    public void cambiarModo(String modo,String modocontrario){
    //String ordenprevio=(String)comboOrden.getValue();
    hboxModo.setStyle("-fx-border-color:"+modocontrario+";-fx-border-width:3;-fx-border-radius:5");
    backButton.setStyle("-fx-text-fill:"+modocontrario+";-fx-background-color:"+modo);
    mainScroll.setStyle("-fx-background-color:"+modo);        
    mainVbox.setStyle("-fx-background-color:"+modo);
    gameTitle.setStyle("-fx-fill:"+modocontrario+";-fx-font-size:36");
    textPrice.setStyle("-fx-fill:"+modocontrario);
    hboxGameImg.setStyle("-fx-background-color:"+modo);
    hboxTitlePrice.setStyle("-fx-background-color:"+modo);
    hboxImgSS.setStyle("-fx-background-color:"+modo);
    scrollSS.setStyle("-fx-background-color:"+modo);
    //scrollSS.setStyle("-fx-border-color:"+modo);
    vboxSS.setStyle("-fx-background-color:"+modo);
    textGen.setStyle("-fx-fill:"+modocontrario);
    //textGameGen.setStyle("-fx-fill:"+modocontrario);
    textDev.setStyle("-fx-fill:"+modocontrario);
    //textGameDev.setStyle("-fx-fill:"+modocontrario);
    textLanz.setStyle("-fx-fill:"+modocontrario);
    //textAnio.setStyle("-fx-fill:"+modocontrario);
    textDescripcionG.setStyle("-fx-fill:"+modocontrario);
    textReview.setStyle("-fx-fill:"+modocontrario);
    textOrdenar.setStyle("-fx-fill:"+modocontrario);
    textDeja.setStyle("-fx-fill:"+modocontrario);
    textUsuario.setStyle("-fx-fill:"+modocontrario);
    botonCatalogo.setStyle("-fx-text-fill:"+modocontrario+";-fx-background-color:"+modo);
    botonExplorar.setStyle("-fx-text-fill:"+modocontrario+";-fx-background-color:"+modo);
    botonSalir.setStyle("-fx-background-color:red;-fx-background-radius:24;-fx-border-radius:20;-fx-border-width:3;-fx-border-color:"+modocontrario);
    for(Node r:hboxRadios.getChildren()){
    ((RadioButton)r).setStyle("-fx-text-fill:"+modocontrario);
    }
    reseniasIniciales();
    //comboOrden.setValue(ordenprevio);
    }
    public void setearSalir(){
    try(FileInputStream input=new FileInputStream(App.pathSS+"door5.png")){
        Image img= new Image(input,35,35,false,true);
        ImageView imgv=new ImageView(img);
        botonSalir.setGraphic(imgv);
        }   catch (IOException ex) {
                ex.printStackTrace();
            }
    
    }
    public void eventsUsuario(Text Textusuario){
    Textusuario.setFocusTraversable(true);
    Textusuario.setOnMouseEntered(new EventHandler<MouseEvent>(){
    @Override
    public void handle(MouseEvent e){
    Textusuario.setUnderline(true);
    }
    });
    Textusuario.setOnMouseExited(new EventHandler<MouseEvent>(){
    @Override
    public void handle(MouseEvent e){
    Textusuario.setUnderline(false);
    }
    });
    Textusuario.setOnMouseClicked(new EventHandler<MouseEvent>(){
    @Override
    public void handle(MouseEvent e){
        try {
            abrirVentanaUsuario(Textusuario);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    });
    }
    public void eventsUsuario(Button boton){
    
    boton.setOnMouseEntered(new EventHandler<MouseEvent>(){
    @Override
    public void handle(MouseEvent e){
    boton.setUnderline(true);
    }
    });
    boton.setOnMouseExited(new EventHandler<MouseEvent>(){
    @Override
    public void handle(MouseEvent e){
    boton.setUnderline(false);
    }
    });
    
    
    }
    public void abrirVentanaUsuario(Text textusuario)throws IOException{
    VentanaUsuarioController.modo=modo;
    VentanaUsuarioController.modocontrario=modocontrario;
    VentanaUsuarioController.setearUsuario(textusuario);
    FXMLLoader fxmloader = new FXMLLoader(App.class.getResource("VentanaUsuario.fxml"));
    Parent root1 = fxmloader.load();
    Stage s=(Stage)textusuario.getScene().getWindow();
    Scene scene=new Scene(root1,1280,720);
    s.setScene(scene);
    App.pilaVentanas.push("VentanaDetalle");
    s.show();
    }
    
    @FXML
    public void anadirWishlist(ActionEvent e){
    botonWishlist.setText("Agregado exitosamente");
    botonWishlist.setDisable(true);
    App.usr.anadirJuegoWish(selected);
    VentanaDetalleController.usr=App.usr;
    
    }
    @FXML
    public void desloggear(ActionEvent e) throws IOException{
    Alert conf_desloggear = new Alert(Alert.AlertType.CONFIRMATION);
        conf_desloggear.setHeaderText(null);
        conf_desloggear.setContentText("¿Está seguro de que desea cerrar sesion?");
        Optional<ButtonType> confirmacion = conf_desloggear.showAndWait();
    if (confirmacion.get() == ButtonType.OK) {
    App.serializarUsuario(App.usr);
    App.usr=null;
    FXMLLoader fxmloader = new FXMLLoader(App.class.getResource("Login.fxml"));
    Parent root1 = fxmloader.load();
    Stage s=(Stage)mainScroll.getScene().getWindow();
    Scene scene=new Scene(root1,1280,720);
    s.setScene(scene);
    App.pilaVentanas.clear();
    s.show();
    }
    }
    @FXML
    public void guardarResenia(ActionEvent e){
    if(valoracion.getSelectedToggle()==null || (textAreaEscribe.getText().equals("")==true)){
    Alert elegir = new Alert(Alert.AlertType.INFORMATION);
    elegir.setHeaderText(null);
    elegir.setContentText("Asegurese de haber escogido un puntaje y de haber escrito un comentario");
    elegir.show();
    }else{
    String puntaje=((RadioButton)valoracion.getSelectedToggle()).getText();
    String comentario= textAreaEscribe.getText();
    try(BufferedWriter bfw=new BufferedWriter(new FileWriter(App.pathReviews+selected.getId()+"/reviews.txt",true))){
    bfw.newLine();
    bfw.write(usr.getId()+";"+comentario+";"+puntaje+";"+2022);
    textAreaEscribe.clear();
    //textFieldEscribe.setText("Escribe un comentario...");
    selected.getResenias().addLast(new Resenia(usr.getId(),comentario,Integer.parseInt(puntaje),2022));
    reseniasIniciales();
    }catch(IOException ex2){
        System.out.println("No se encontro el archivo");
    }
    
    }
    }
    
    @FXML
    public void cargarCatalogo(ActionEvent e) throws IOException{
    VentanaPrincipalDemoController.modo = modo;
    VentanaPrincipalDemoController.modocontrario = modocontrario;
    FXMLLoader fxmloader = new FXMLLoader(App.class.getResource("VentanaPrincipalDemo.fxml"));
    Parent root1 = fxmloader.load();
    Stage s=(Stage)mainScroll.getScene().getWindow();
    Scene scene=new Scene(root1,1280,720);
    s.setScene(scene);
    App.pilaVentanas.clear();
    s.show();
    
    }
    @FXML
    public void cargarExplorar(ActionEvent e) throws IOException{
    VentanaExplorarController.modo=modo;
    VentanaExplorarController.modocontrario=modocontrario;    
    FXMLLoader fxmloader = new FXMLLoader(App.class.getResource("VentanaExplorar.fxml"));
    Parent root1 = fxmloader.load();
    Stage s=(Stage)mainScroll.getScene().getWindow();
    Scene scene=new Scene(root1,1280,720);
    s.setScene(scene);
    App.pilaVentanas.clear();
    s.show();
    }
}
