public class EntradaRegistro {
    public String Folio;
    public String NombreCliente;
    public double Monto;
    public String StatusPago;
    public String StatusEntrega;
    public String Fecha;

    public EntradaRegistro(String folio, String nombreCliente, double monto, String statusPago, String statusEntrega, String fecha){
        this.Folio = folio;
        this.NombreCliente = nombreCliente;
        this.Monto = monto;
        this.StatusPago = statusPago;
        this.StatusEntrega = statusEntrega;
        this.Fecha = fecha;        
    }


    public void setFolio(String Folio){
        this.Folio = Folio;
    }

    public void setNombreCliente(String NombreCliente){
        this.NombreCliente = NombreCliente;
    }

    public void setMonto(double Monto){
        this.Monto = Monto;
    }

    public void setStatusPago(String StatusPago){
        this.StatusPago = StatusPago;
    }

    public void setStatusEntrega(String StatusEntrega){
        this.StatusEntrega = StatusEntrega;
    }

    public void setFecha(String Fecha){
        this.Fecha = Fecha;
    }

    public String getFolio(){
        return this.Folio;
    }

    public String getNombreCliente(){
        return this.NombreCliente;
    }

    public double getMonto(){
        return this.Monto;
    }

    public String getStatusPago(){
        return this.StatusPago;
    }

    public String getStatusEntrega(){
        return this.StatusEntrega;
    }

    public String getFecha(){
        return this.Fecha;
    }
}