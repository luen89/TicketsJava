public class SubElementoEntrega {
    public ElementoEntrega padre;
    private Servicio objetoServicio;
    private double costo;

    public SubElementoEntrega(ElementoEntrega padre, Servicio objetoServicio, double costo){
        this.padre=padre;
        this.objetoServicio=objetoServicio;
        this.costo=costo;
    }

    public void setServicio(Servicio objetoServicio){this.objetoServicio= objetoServicio;}

    public Servicio getServicio(){return objetoServicio;}

    public void setCosto(double costo){this.costo=costo;}

    public double getCosto(){return costo;}
}
