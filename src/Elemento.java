import java.util.ArrayList;

public class Elemento {
    private String acero;
    private String servicio;
    private String servAbr;
    private String acerAbr;
    private Integer piezas;
    private Double kilos;
    private String dureza;
    private String descripcion;
    public ArrayList <Double> precioCustom = new ArrayList<Double>();
    private Acero aceroObject;
    public ArrayList<Servicio> servArray = new ArrayList<Servicio>(); 
    private int piezasEntregadas;
  

    public Elemento(String acero, String servicio, int piezas, Double kilos, String dureza, String descripcion, int piezasEntregadas){
        super();
        this.acero = acero;
        precioCustom.add(0.0);
        if (acero=="Colled Rolled"){acerAbr="C.R.";}
        else{acerAbr=acero;}
        this.servicio = servicio;
        switch(servicio){
            case "Pavonado" : 
                            servAbr = "PAV";
                            break;
            case "Templado y Pavonado" :
                            System.out.println("Si marca el abreviado");
                            servAbr = "PA_TE";
                            break;
            case "Templado" : 
                            servAbr = "TEMP";
                            break;
            
            default         :      
                            servAbr = "";
                            break;
        }
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

    public String getServicioAbreviado(){
        return servAbr;
    }

    public String getAceroAbreviado(){
        return acerAbr;
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

    public void addPrecioCustom(){
        precioCustom.add(0.0);
    }

    public void removePrecioCustom(int i){
        precioCustom.remove(i);
    }

    public Double getPrecioCustom(int i){
        return precioCustom.get(i);
    }

    public int getPiezasEntregadas(){
        return piezasEntregadas;
    }

    public void setPiezasEntregadas(int piezasEntregadas){
        this.piezasEntregadas=piezasEntregadas;
    }

    public void setServicio(String servicio){
        this.servicio = servicio;
        switch(servicio){
            case "Pavonado" : 
                            servAbr = "PAV";
                            break;
            case "Templado" : 
                            servAbr = "TEMP";
                            break;
            case "Templado y Pavonado"      : 
                            servAbr = "PA_TE";
                            break;
            default         :      
                            servAbr = "";
                            break;
        }
    }
    
    public void setPiezas(Integer piezas){
        this.piezas = piezas;
    }

    public void setKilos(Double kilos){
        this.kilos = kilos;
    }
    
    public void setAcero(String acero){
        this.acero = acero;
        if (acero=="Colled Rolled"){acerAbr="C.R.";}
        else{acerAbr=acero;}
    }
    
    public void setDureza(String dureza){
        this.dureza = dureza;
    }

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public void setPrecioCustom(Double precioC, int i){
        precioCustom.set(i,precioC);
    }

    public boolean Validar(){
        if(acero == "" || servicio == "" || piezas==0 || kilos==0.0 || servicio=="Seleccionar" || acero=="Seleccionar")
        return false;
        else
        return true;
    }
    
}