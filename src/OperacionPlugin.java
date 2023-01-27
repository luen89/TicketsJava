/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.ArrayList;

/**
 *
 * @author parzibyte
 */
public class OperacionPlugin {
    
    
    public String nombre;
    public ArrayList<Object> argumentos;

    public OperacionPlugin(String nombre, ArrayList<Object> argumentos) {
        this.nombre = nombre;
        this.argumentos = argumentos;
    }
}