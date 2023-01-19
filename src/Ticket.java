import java.util.ArrayList;

public class Ticket {
    public double contadorPavonado;
    public double contadorTemplado;
    public double costoPavonado;
    public double costoTemplado;
    public double costoTotal;
    public ArrayList<Elemento> pformsT;

    public Ticket(double contadorPavonado,
    double contadorTemplado,
    double costoPavonado,
    double costoTemplado,
    double costoTotal,
    ArrayList<Elemento> pformsT){
        this.contadorPavonado = contadorPavonado;
        this.contadorTemplado = contadorTemplado;
        this.costoPavonado = costoPavonado;
        this.costoTemplado = costoTemplado;
        this.costoTotal = costoTotal;
        this.pformsT = pformsT;
    }
}
