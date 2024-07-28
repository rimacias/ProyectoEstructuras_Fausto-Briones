/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;

/**
 *
 * @author CltControl
 */
public class LDEC<E> implements Iterable<E>,Serializable{
    Node first;
    Node last;
    int length;
    public LDEC(){
    first=null;
    last=null;
    length=0;
    }
    
    private class Node<E> implements Serializable{
    Node ant;
    E contenido;
    Node sig;
    public Node(E content){
    ant=null;
    contenido=content;
    sig=null;
    }
    @Override
    public String toString(){
    return (String)contenido; 
    }
}
    public boolean estaVacia(){
        return length==0;
    }
    
    public void addFirst(E datos){
    Node nuevo= new Node(datos);
    if(estaVacia()){
    nuevo.sig=nuevo;
    nuevo.ant=nuevo;
    first=nuevo;
    last=nuevo;
    length++;
    }else if(length==1){
    nuevo.sig=first;
    nuevo.ant=first;
    first.sig=nuevo;
    first.ant=nuevo;
    last=first;
    first=nuevo;
    length++;
    }else{
    nuevo.sig=first;
    first.ant=nuevo;
    nuevo.ant=last;
    last.sig=nuevo;
    first=nuevo;
    length++;
    }
    
    }
    public void addI(int i,E datos){    
    if(estaVacia()){
    addFirst(datos);
    }else{
    if((i>=length)||(i<0)){
        System.out.println("No se puede anadir el elemento en esta posicion");
    }else if(i==0){
        addFirst(datos);
    }else if(i==length-1){
        addLast(datos);
    }
    else{
    Node nuevo= new Node(datos);    
    Node anadirI=first;
    for(int d=0;d<i+1;d++){
    if(d==i-1){
    nuevo.sig=anadirI.sig;
    anadirI.sig.ant=nuevo;
    nuevo.ant=anadirI;
    anadirI.sig=nuevo;
    length++;
    }
    else{
    anadirI=anadirI.sig;
    }
    
    }
    
    }
    
    
    }
    }
    public void addLast(E contenido){
    if(estaVacia()){
    addFirst(contenido);
    }else if(length==1){
    Node nuevo= new Node(contenido);
    nuevo.sig=first;
    nuevo.ant=first;
    first.sig=nuevo;
    first.ant=nuevo;
    last=nuevo;
    length++;
    }else{
    Node nuevo= new Node(contenido);
    nuevo.sig=first;
    first.ant=nuevo;
    nuevo.ant=last;
    last.sig=nuevo;
    last=nuevo;
    length++;
    }
    }
    
    public void removeFirst(){
    if(estaVacia()){
        System.out.println("La lista esta vacia");
    }else if(length==1){
    first=null;
    last=null;
    length--;
    }else{
    first.sig.ant=last;
    last.sig=first.sig;
    first=first.sig;
    
    length--;
    }
    }
    public void removeLast(){
    if(estaVacia()){
        System.out.println("La lista esta vacia");
    }else if(length==1){
    removeFirst();
    }else{
    last.ant.sig=first;
    first.ant=last.ant;
    last=last.ant;
    length--;
    
    }
    }
    
    
    public void removeI(int indice){
    if(estaVacia()){
        System.out.println("La lista esta vacia");
    }else if(indice==0){
    removeFirst();
    }else if(indice==length-1){
    removeLast();
    }
    else{
    Node removerI=first;
    for(int d=0;d<indice+1;d++){
    if(d==indice-1){
    removerI.sig=removerI.sig.sig;
    removerI.sig.ant=removerI;
    length--;
    }else{
    removerI=removerI.sig;
    }
    }
    }
    
    }
    
    
    public int getLength(){
    return length;
    }
    public E getLast(){
    return (E)last.contenido;
    }
    public E getFirst(){
    return (E)first.contenido;    
    }
    public E getI(int i){
    Node recorrerI=first;
    for(int d=0;d<i+1;d++){
    if(d==i){
    return (E)recorrerI.contenido;
    }else{
    recorrerI=recorrerI.sig;
    }
    }
    return (E)recorrerI.contenido;
    }
    public void imprimirLista(){
    for(int d=0;d<length;d++){
        System.out.print(getI(d)+" ");
    }
        System.out.println();
    }
    @Override
    public Iterator<E> iterator(){
        return  new IteradorLCD<>(this);
    }
    
    public E find(Comparator com,E elemento){
    for(E list_element:this){
    if(com.compare(list_element,elemento)==0){
    return list_element;
    }
    }
    return null;
    }
    
    public LDEC<E> findAll (Comparator cmp, E elemento) {
    LDEC<E> encontrados = new LDEC();
    for (E list_element:this) {
        if (cmp.compare(list_element, elemento) == 0) {
             encontrados.addLast(list_element);
        }
    }
    return encontrados; 
}
    
    class IteradorLCD<E>implements Iterator<E>{
    Node<E> i;
    int contador;
    
    public IteradorLCD(LDEC<E> le){
        i=(Node <E>)le.first;
        contador=0;
        
    }
    @Override
    public E next(){
    E tmp=i.contenido;
    i=i.sig;
    contador+=1;
    return tmp;
    
    }
    @Override
    public boolean hasNext(){
        if(contador==length){
        return false;
        }else{
        return true;
        }
    }
    
    
    
    }
    
    
}
