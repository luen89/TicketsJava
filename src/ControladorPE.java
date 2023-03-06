import java.util.ArrayList;

public class ControladorPE {
    private GestorArchivos fileGestor;
    public MatrizValidacion matrix;

    

    private boolean iva;
    private boolean autoCalculo;

    private String[] serviciosNames;
    private String[] acerosNames;
    private double total;

    
    private ArrayList<Servicio> arrayServicios;
    private ArrayList<Acero> arrayAceros;
    ArrayList<Elemento> arrayElementos;

    public ControladorPE(ArrayList<Servicio> arrayServicios, ArrayList<Acero> arrayAceros){
        this.arrayServicios=arrayServicios;
        this.arrayAceros=arrayAceros;
        initVars();
    }

    public void initVars(){
        total=0.0;
        arrayElementos = new ArrayList<Elemento>();
        autoCalculo=true;
        iva=false;
    
        matrix= new MatrizValidacion(arrayServicios.size(), arrayAceros.size());

        serviciosNames= new String[arrayServicios.size()];
        acerosNames= new String[arrayAceros.size()];

        for(int i=0;i<arrayServicios.size();i++){serviciosNames[i]=arrayServicios.get(i).name;}
        for(int i=0;i<arrayAceros.size();i++){acerosNames[i]=arrayAceros.get(i).name;}
    }

    public Elemento createElemento(){
        Elemento elemento= new Elemento(this,getAceroFromGeneralArray(0).name,getServicioFromGeneralArray(0).name,0,0.0,"","",0);
        elemento.servicioObjeto=getServicioFromGeneralArray(0);
        elemento.setAceroObject(getAceroFromGeneralArray(0));
        arrayElementos.add(elemento);
        return elemento;
    }

    public void removeElemento(Elemento elm){
        arrayElementos.remove(elm);
    }

    public double getTotal(){
        return total;
    }

    public void setTotal(double total){
        this.total=total;
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
