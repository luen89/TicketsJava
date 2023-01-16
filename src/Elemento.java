public class Elemento {
    private String acero;
    private String servicio;
    private Integer piezas;
    private Double kilos;

    public Elemento(String acero, String servicio, Integer piezas, Double kilos){
        super();
        this.acero = acero;
        this.servicio = servicio;
        this.piezas = piezas;
        this.kilos = kilos;
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
    
    
}