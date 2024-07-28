/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import com.mycompany.proyectoeddprimerparcial.App;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import javafx.scene.image.Image;

/**
 *
 * @author jexa1
 */
public class Juego implements Serializable{
    private String id;
    private String titulo;
    private String descripcion;
    private String genero;
    private String desarrolladora;
    private String anio;
    private String precio;
    //private LDEC<Image> images;
    private LDEC<Resenia> resenias;
    
    public Juego(String id, String titulo, String descripcion,String genero,String desarrolladora, String anio, String precio) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.genero=genero;
        this.desarrolladora = desarrolladora;
        this.anio = anio;
        this.precio = precio;
        //images=cargarImagenes(id);
        resenias=cargarResenias(id);
    }

    public Juego(String titulo, String anio) {
        this.titulo = titulo;
        this.anio = anio;
    }
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
    
    public String getDesarrolladora() {
        return desarrolladora;
    }

    public void setDesarrolladora(String desarrolladora) {
        this.desarrolladora = desarrolladora;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    
    
    public LDEC<Resenia> getResenias() {
        return resenias;
    }

    public void setResenias(LDEC<Resenia> resenias) {
        this.resenias = resenias;
    }
    
    
    
    
    public static LDEC<Image> cargarImagenes(String id){
        LDEC<Image> result=new LDEC<>();
        for(int i=1;i<6;i++){
        try(FileInputStream input=new FileInputStream(App.pathSS+id+"/"+"ss"+i+".jpg")){
        Image img=new Image(input,686,395,false,true);
        result.addLast(img);
        }   catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
    
    private LDEC<Resenia> cargarResenias(String id){
        LDEC<Resenia> result=new LDEC<>();
        try ( BufferedReader bfr = new BufferedReader(new FileReader(App.pathReviews+id+"/reviews.txt",StandardCharsets.UTF_8))) {
            bfr.readLine();
            String linea=bfr.readLine();
            while (linea != null) {
                String[] datoslinea = linea.split(";");
                Resenia resenia=new Resenia(datoslinea[0],datoslinea[1],Integer.parseInt(datoslinea[2]),Integer.parseInt(datoslinea[3]));
                result.addLast(resenia);
                linea=bfr.readLine();
            }
        }catch(IOException ex1){
            System.out.println("No se encontro el archivo");
        }
        return result;
    }
    
    
    
    
    @Override
    public boolean equals(Object o){
        if (o == null) {
            return false;
        }

        if (o.getClass() != this.getClass()) {
            return false;
        }
        Juego j = (Juego)o;
        if ((this.titulo == null) ? (j.titulo != null) : !this.titulo.equals(j.titulo)) {
            return false;
        }
        return true;
    }
    
}
