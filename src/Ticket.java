import java.util.ArrayList;
import java.util.Date;

public class Ticket {
    public int nOrden;
    public String nameCliente;
    public double subtotal;
    public double costoTotal;
    public Date today;
    public boolean iva;
    public double montoPagado;
    public ArrayList<Elemento> servicios;

    public Ticket(int nOrden,String nameCliente,
    double costoTotal,
    Date today,
    boolean iva,
    double montoPagado,
    ArrayList<Elemento> pformsT){
        this.nOrden=nOrden;
        this.nameCliente=nameCliente;
        this.costoTotal = costoTotal;
        this.today = today;
        this.iva=iva;
        this.montoPagado=montoPagado;
        this.servicios = pformsT;
    }

    public void validarTicket () throws ExcepcionVacio {
        for(int i=0; i<servicios.size(); i++){
            if(!servicios.get(i).Validar()){
            throw new ExcepcionVacio("Campos Incorrectos");
            }
        }
    }
}
