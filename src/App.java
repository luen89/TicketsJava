
import javax.swing.*;

import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Compa's Team
 */
public class App {
    public static void main(String args[]){
        FlatArcOrangeIJTheme.setup();
        GestorArchivos ga= new GestorArchivos();
        // ga.leerArchivo();

        Ventana miVentana=new Ventana(ga);                
        miVentana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        miVentana.setVisible(true);        
    }        
}