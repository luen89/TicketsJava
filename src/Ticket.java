import java.util.ArrayList;
import java.util.Date;

public class Ticket {
    public double contadorPavonado;
    public double contadorTemplado;
    public double costoPavonado;
    public double costoTemplado;
    public double costoTotal;
    public Date today;
    public boolean iva;
    public ArrayList<Elemento> pformsT;

    public Ticket(double contadorPavonado,
    double contadorTemplado,
    double costoPavonado,
    double costoTemplado,
    double costoTotal,
    Date today,
    boolean iva,
    ArrayList<Elemento> pformsT){
        this.contadorPavonado = contadorPavonado;
        this.contadorTemplado = contadorTemplado;
        this.costoPavonado = costoPavonado;
        this.costoTemplado = costoTemplado;
        this.costoTotal = costoTotal;
        this.today = today;
        this.iva=iva;
        this.pformsT = pformsT;
    }
}
