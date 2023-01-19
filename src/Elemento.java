public class Elemento {
    private String acero;
    private String servicio;
    private String servAbr;
    private Integer piezas;
    private Double kilos;

    public Elemento(String acero, String servicio, Integer piezas, Double kilos){
        super();
        this.acero = acero;
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
    }

    public String getServicio(){
        return servicio;
    }

    public String getServicioAbreviado(){
        return servAbr;
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
    }
    
    
}