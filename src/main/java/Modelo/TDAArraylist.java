/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author CltControl
 */
public class TDAArraylist<E> {
    private E[] arreglo;
    private int ultimo,capacidad;
    
    public TDAArraylist(){
        arreglo=(E[])new Object[10];
        capacidad=10;
        ultimo=-1;
    }
    public boolean isEmpty(){
        return (ultimo==-1);
    }
    private void crecerArreglo(){
        E[] arregloTemp=(E[])new Object[capacidad+5];
        for(int i=0;i<capacidad;i++){
            arregloTemp[i]=arreglo[i];
        }
        arreglo=arregloTemp;
        capacidad+=5;
    }
    public void add(E e){
        if(ultimo==capacidad-1){
            crecerArreglo();
            //Se imprime para probar que funciona
        }
        //Se imprime para probar que funciona
        ultimo+=1;
        arreglo[ultimo]=e;
        
    }
    public void add(int indice,E e){
        //Se propone esta primera condición (ultimo-indice<=-2) para no añadir  
        //después del final+1 
        if(ultimo-indice<=-2||indice<0){
            throw new IndexOutOfBoundsException();
        }else{
            if(ultimo==capacidad-1){
                crecerArreglo();
            }
            for(int i=ultimo;i>indice;i--){
                arreglo[i+1]=arreglo[i];
            }
            arreglo[indice]=e;
            ultimo+=1;
        }
    }
    public void remove(int indice){
        if(indice>ultimo||indice<0){
            throw new IndexOutOfBoundsException();
        }
        for(int i=indice;i<ultimo;i++){
            arreglo[i]=arreglo[i+1];
        }
        ultimo-=1;
    }
    public int size(){
        return ultimo+1;
    }
    public E get(int indice){
        if(indice<0||indice>ultimo){
            throw new IndexOutOfBoundsException();
        }else{
            return arreglo[indice];
        }
    }
}
