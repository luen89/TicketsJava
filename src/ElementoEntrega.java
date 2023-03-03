import java.util.ArrayList;

public class ElementoEntrega {

    private String acero;
    private String servicio;
    private Integer piezas;
    private Double kilos;
    private String dureza;
    private String descripcion;
    public Double precioCustom;
    private Acero aceroObject;
    public Servicio servicioObjeto;
    public ArrayList<SubElementoEntrega> subelementoArray = new ArrayList<SubElementoEntrega>();  
    private int piezasEntregadas;
  

    public ElementoEntrega (String acero, String servicio, int piezas, Double kilos, String dureza, String descripcion, int piezasEntregadas){
        super();
        this.servicio=servicio;
        this.acero = acero;
        precioCustom=0.0;
        this.piezas = piezas;
        this.kilos = kilos;

        this.dureza = dureza;
        this.descripcion = descripcion;
        this.piezasEntregadas=piezasEntregadas;
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
        subelementoArray.add(new SubElementoEntrega(this, servicio,costo));
    }

    public void removeSubElemento(SubElemento s){
        subelementoArray.remove(s);
    }

    public SubElementoEntrega getSubElemento(int i){return subelementoArray.get(i);}

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
        if(acero == "" || servicio == "" || piezas==0 || kilos==0.0 || servicio=="Seleccionar" || acero=="Seleccionar")
        return false;
        else
        return true;
    }
    
}