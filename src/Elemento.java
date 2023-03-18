import java.util.ArrayList;

public class Elemento {
    public ControladorPE padre;
    private String acero;
    private String servicio;
    private Integer piezas;
    private Double kilos;
    private String dureza;
    private String descripcion;
    public Double precioCustom;
    private Acero aceroObject;
    public Servicio servicioObjeto;
    public ArrayList<SubElemento> subelementoArray = new ArrayList<SubElemento>();  
    private int piezasEntregadas;
  

    public Elemento(ControladorPE padre,String acero, String servicio, int piezas, Double kilos, String dureza, String descripcion, int piezasEntregadas){
        super();
        this.padre=padre;
        this.servicio=servicio;
        this.acero = acero;
        precioCustom=0.0;
        this.piezas = piezas;
        this.kilos = kilos;

        this.dureza = dureza;
        this.descripcion = descripcion;
        this.piezasEntregadas=piezasEntregadas;
    }

    public void removerDeMatriz(){
        padre.matrix.restarAcelda(servicioObjeto.id, aceroObject.id, kilos);
   }

    protected void agregarAMatriz(){
        padre.matrix.sumarAcelda(servicioObjeto.id, aceroObject.id, kilos);
    }

    public void removerTodoDeMatriz(){
             System.out.println("Entre a borrar todo de la matriz de validacion");
            removerDeMatriz();
            for (SubElemento sub : subelementoArray){sub.removerDeMatriz();}
    }

    public void agregarTodoAMatriz(){
        System.out.println("Entre a borrar todo de la matriz de validacion");
       agregarAMatriz();
       for (SubElemento sub : subelementoArray){sub.agregarAMatriz();}
}

    public double calcularCosto(){
        precioCustom=servicioObjeto.obtenerCosto(padre.matrix.getCelda(servicioObjeto.id, aceroObject.id), kilos);
        return precioCustom;
    }

    public void setAceroObject(Acero ac){
        aceroObject=ac;

    }

    public Acero getAceroObject(){
        return aceroObject;
    }

    public String getServicio(){
        return servicio;
    }
    
    public int getPiezas(){
        return piezas;
    }

    public double getKilos(){
        return kilos;
    }
    
    public String getAcero(){
        return acero;
    }

    public String getDureza(){
        return dureza;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public void addSubElemento(Servicio servicio, double costo){
        subelementoArray.add(new SubElemento(this, servicio,costo));
    }

    public void removeSubElemento(SubElemento s){
        subelementoArray.remove(s);
    }

    public SubElemento getSubElemento(int i){return subelementoArray.get(i);}

    public int getSubElementoSize(){return subelementoArray.size();}

    public Double getPrecioCustom(){
        return precioCustom;
    }

    public int getPiezasEntregadas(){
        return piezasEntregadas;
    }

    public void setPiezasEntregadas(int piezasEntregadas){
        this.piezasEntregadas=piezasEntregadas;
    }

    public void setServicio(String servicio){
        this.servicio = servicio;
    }
    
    public void setPiezas(Integer piezas){
        this.piezas = piezas;
    }

    public void setKilos(Double kilos){
        this.kilos = kilos;
    }
    
    public void setAcero(String acero){
        this.acero = acero;
    }
    
    public void setDureza(String dureza){
        this.dureza = dureza;
    }

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public void setPrecioCustom(Double precioC){
        precioCustom=precioC;
    }

    public boolean Validar(){
        if(acero == "" || servicio == "" || descripcion == "" || dureza == "" || piezas==0 || kilos==0.0 || servicio=="Seleccionar" || acero=="Seleccionar")
        return false;
        else
        return true;
    }
    
}