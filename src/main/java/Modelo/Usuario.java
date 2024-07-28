/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author AVGla
 */
public class Usuario implements Serializable{
    private String id;
    private String contrasenia;
    private LDEC<Juego>wishlist=new LDEC<>();
    
    public Usuario(String id,String contrasenia){
    this.id=id;
    this.contrasenia=contrasenia;
    }
    public String getId(){
    return this.id;
    }
    public String getContrasenia(){
    return this.contrasenia;
    }
    public LDEC<Juego> getWishlist(){
    return this.wishlist;
    }
    public void anadirJuegoWish(Juego j1){
    this.wishlist.addLast(j1);
    }
    public void setContrasenia(String contrasenia){
    this.contrasenia=contrasenia;
    }
}
