import javax.naming.LimitExceededException;

public class Servicio{
    public int id;
    public String name;
    public String nameAbr;
    public double costoMin;
    public double costoMed;
    public double costoKg;
    public double limiteMedio;
    public double limiteMinimo;

    public Servicio(int id, String name, double CostoMin, double CostoMed, double CostoKg, double limiteMinimo, double limiteMedio){
        this.id=id;
        this.name=name;
        this.costoMin=CostoMin;
        this.costoMed=CostoMed;
        this.costoKg=CostoKg;
        this.limiteMinimo=limiteMinimo;
        this.limiteMedio=limiteMedio;
        nameAbr=name.substring(0, 2).toUpperCase();
    }

    public double obtenerCosto(double kgBandera, double kg){
        double costo;
        if(kgBandera<=limiteMinimo){costo=costoMin;}
        else{
        if(kgBandera>limiteMinimo && kgBandera<=limiteMedio){costo=costoMed;}
        else{costo=kg*costoKg;}
        }
        return costo;    
    }

}