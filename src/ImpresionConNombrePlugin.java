/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.ArrayList;

/**
 *
 * @author parzibyte
 */
public class ImpresionConNombrePlugin {

    public ArrayList<OperacionPlugin> operaciones;
    public String nombreImpresora;
    public String serial;

    public ImpresionConNombrePlugin(ArrayList<OperacionPlugin> operaciones, String nombreImpresora, String serial) {
        this.operaciones = operaciones;
        this.nombreImpresora = nombreImpresora;
        this.serial = serial;
    }
}