/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.io.Serializable;

/**
 *
 * @author AVGla
 */
public class Resenia implements Serializable{
    
    String usuario;
    String comentario;
    int valoracion;
    int anio;
    
    public Resenia(String usuario,String comentario,int valoracion,int anio){
    this.usuario=usuario;
    this.comentario=comentario;
    this.valoracion=valoracion;
    this.anio=anio;
    }
    
    public String getUsuario(){
    return usuario;
    }
    public String getComment(){
    return comentario;
    }
    public int getValoracion(){
    return valoracion;
    }
    public int getAnio(){
    return anio;
    }
}
