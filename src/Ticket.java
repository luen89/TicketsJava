import java.util.ArrayList;
import java.util.Date;

public class Ticket {
    public int nOrden;
    public String nameCliente;
    public double contadorPavonado;
    public double contadorTemplado;
    public double costoPavonado;
    public double costoTemplado;
    public double costoTotal;
    public Date today;
    public boolean iva;
    public double montoPagado;
    public ArrayList<Elemento> pformsT;

    public Ticket(int nOrden,String nameCliente, double contadorPavonado,
    double contadorTemplado,
    double costoPavonado,
    double costoTemplado,
    double costoTotal,
    Date today,
    boolean iva,
    double montoPagado,
    ArrayList<Elemento> pformsT){
        this.nOrden=nOrden;
        this.nameCliente=nameCliente;
        this.contadorPavonado = contadorPavonado;
        this.contadorTemplado = contadorTemplado;
        this.costoPavonado = costoPavonado;
        this.costoTemplado = costoTemplado;
        this.costoTotal = costoTotal;
        this.today = today;
        this.iva=iva;
        this.montoPagado=montoPagado;
        this.pformsT = pformsT;
    }
}
