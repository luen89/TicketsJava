import java.util.ArrayList;

public class ControladorPE {
    private GestorArchivos fileGestor;
    public MatrizValidacion matrix;

    

    private boolean iva=false;
    private boolean autoCalculo=true;

    private String[] serviciosNames;
    private String[] acerosNames;
    
    ArrayList<Servicio> arrayServicios;
    ArrayList<Acero> arrayAceros;
    //ArrayList<Elemento> arrayElementos = new ArrayList<Elemento>();

    public ControladorPE(ArrayList<Servicio> arrayServicios, ArrayList<Acero> arrayAceros){
        this.arrayServicios=arrayServicios;
        this.arrayAceros=arrayAceros;
        matrix= new MatrizValidacion(arrayServicios.size(), arrayAceros.size());

        serviciosNames= new String[arrayServicios.size()];
        acerosNames= new String[arrayAceros.size()];

        for(int i=0;i<arrayServicios.size();i++){serviciosNames[i]=arrayServicios.get(i).name;}
        for(int i=0;i<arrayAceros.size();i++){acerosNames[i]=arrayAceros.get(i).name;}
    }

    public String[] getServiciosNames(){return serviciosNames;}

    public String[] getAcerosNames(){return acerosNames;}

    public Servicio getServicioFromGeneralArray(int index){return arrayServicios.get(index);}

    public Acero getAceroFromGeneralArray(int index){return arrayAceros.get(index);}

    public void setIVA(boolean iva){this.iva=iva;}

    public boolean getIVA(){return iva;}

    public void setAutoCalculo(boolean autoCalculo){this.autoCalculo=autoCalculo;}

    public boolean getAutoCalculo(){return autoCalculo;}

}
