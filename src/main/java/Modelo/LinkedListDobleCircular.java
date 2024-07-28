/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import javafx.scene.image.Image;

/**
 *
 * @author CltControl
 */
public class LinkedListDobleCircular<E> {
    private Node<E> first;
    private Node<E> last;
    private int longitud;
    
    public LinkedListDobleCircular(){
        first=null;
        last=null;
        longitud=0;
    }
    private class Node<E>{
        E contenido;
        Node<E> siguiente;
        Node<E> anterior;
        
        public Node(E contenido){
            this.contenido=contenido;
            siguiente=null;
            anterior=null;
        }
    }
    public int size(){
        return longitud;
    }
    public boolean isEmpty(){
        return longitud==0;
    }
    public E getFirst(){
      if(first==null){
        throw new IndexOutOfBoundsException();
      }else{
        return first.contenido;
      }
    }
    public E getLast(){
      if(last==null){
        throw new IndexOutOfBoundsException();
      }else{
        return last.contenido;
      }
    }
    public E get(int index){
        if(index<0||index>=longitud){
            throw new IndexOutOfBoundsException();
        }else if(index==0){
            return getFirst();
        }else if(index==longitud-1){
            return getLast();
        }else{
            return recorrer(index).contenido;
        }
    }
    public void addFirst(E contenido){
        Node<E> node=new Node(contenido);
        if(!isEmpty()){
            node.siguiente=first;
            node.anterior=first.anterior;
            first.anterior=node;
            first=node;
            last.siguiente=first;
        }else{
            first=node;
            last=node;
            node.siguiente=node;
            node.anterior=node;
        }
        longitud+=1;
    }
    public void addLast(E contenido){
        if(isEmpty()){
            addFirst(contenido);
        }else{
            Node<E> node=new Node(contenido);
            node.siguiente=first;
            node.anterior=last;
            last.siguiente=node;
            last=node;
            first.anterior=last;
            longitud+=1;
        }
    }
    public void add(int index,E contenido){
        if(index<0||index>longitud){
            throw new IndexOutOfBoundsException();
        }else if(index==0){
            addFirst(contenido);
        }else if(index==longitud){
            addLast(contenido);
        }else{
            Node<E> nuevo=new Node(contenido);
            Node<E> referencia=recorrer(index);
            nuevo.siguiente=referencia;
            nuevo.anterior=referencia.anterior;
            referencia.anterior=nuevo;
            nuevo.anterior.siguiente=nuevo;
            longitud+=1;
        }
    }
    public void removeFirst(){
        if(first==null){
            throw new IndexOutOfBoundsException();
        }else if(longitud==1){
            first=null;
            last=null;
            longitud-=1;
        }else{
            first=first.siguiente;
            first.anterior=last;
            last.siguiente=first;
            longitud-=1;
        }
        
    }
    public void removeLast(){
        if(isEmpty()){
            throw new IndexOutOfBoundsException();
        }else if(longitud==1){
            removeFirst();
        }else{
            last=last.anterior;
            first.anterior=last;
            last.siguiente=first;
            longitud-=1;
        }
    }
    public void remove(int index){
        if(index<0||index>=longitud){
            throw new IndexOutOfBoundsException();
        }else if(index==0){
            removeFirst();
        }else if(index+1==longitud){
            removeLast();
        }else{
            //Se toma el nodo anterior al nodo que será removido
            Node<E> anterior=recorrer(index-1);
            anterior.siguiente=anterior.siguiente.siguiente;
            anterior.siguiente.anterior=anterior;
            longitud-=1;
        }
    }
    //retorna el nodo del indíce ingresado
    private Node<E> recorrer(int index){
        Node <E> retorno;
        int distanciaFin=longitud-index-1;
        int distanciaInicio=index;
        if(distanciaInicio<=distanciaFin){
            retorno=first;
            for(int i=0;i<distanciaInicio;i++){
                retorno=retorno.siguiente;
            }
        }else{
            retorno=last;
            for(int i=0;i<distanciaFin;i++){
                retorno=retorno.anterior;
            }
        }
        return retorno;
    }
    
    public E getAnterior(E j){
        Node<E> tmp = first;
        for(int i=0;i<this.size();i++){
            if(tmp.contenido.equals(j)){
                return tmp.anterior.contenido;
            }
            tmp=tmp.siguiente;
        }    //notar que si el elemento no se encuentra el for no se rompe asi que hay que implementar iterable
        return null;
        
    }
    
    public E getSiguiente(E j){
        Node<E> tmp = first;
        for(int i=0;i<this.size();i++){
            if(tmp.contenido.equals(j)){
                return tmp.siguiente.contenido;
                
            }
            tmp=tmp.siguiente;
            //notar que si el elemento no se encuentra el for no se rompe asi que hay que implementar iterable
            
        }
        return null;
    }
    
}
