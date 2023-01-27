
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.FileInputStream;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author LuisEnrique
 */
public class GestorArchivos {
    ArrayList <String> instruccion=new ArrayList <>();
    int index=0;
    
    public GestorArchivos(){
        
    }



    public void saveInstruction(String aux){
        //Se agrega la instruccion a un arreglo de instruccionoes
        instruccion.add(aux);
        System.out.println(instruccion.get(index));
        index++;
    }

    /*public void reiniciar(Foto f, boolean limpiar){
        //Se borran las instrucciones y se obtiene una nuevo objeto foto
        fot=f;
        if(limpiar){
        index=0;
        instruccion.clear();
        }
        
    }*/
    
    /*public void saveImage(Foto f){ 
        BufferedImage img = f.getImagen();
        if(img == null){
            System.out.println("Imposible guardar imagen");
            return;
        }
        try {
            String fn = f.getFileName();            
            String[] name = fn.split("\\.");            
            File outputfile = new File("src/Imagenes/ROI's/Generadas por el programa/"+name[0]+"_ROI.jpg");
            if (outputfile.createNewFile()) {
                System.out.println("Imagen creada: " + outputfile.getName());
            } else {
                System.out.println("Imagen ya existente sobreescrita.");
            }
            ImageIO.write(img, "jpg", outputfile);
        } catch (IOException ex) {
            System.out.println("Error al escribir la imagen.");
        }                
    }*/

    public void writeFile(EntradaRegistro entRe){
        try {
            //Se crea un nuevo archivo
            File saveF = new File("src/Registros/pruebas.csv");
            if(!saveF.exists()){
                if (saveF.createNewFile()) {
                    System.out.println("Archivo creado: " + saveF.getName());
                  } else {
                    System.out.println("Archivo ya existente sobreescrito.");
                  }
            }          
            //Se crea un writer
            FileWriter myWriter;
                 myWriter = new FileWriter(saveF,true);
            //Se escriben todas las instrucciones en el archivo
            myWriter.write("\n");
            myWriter.write(entRe.getFolio()+","+entRe.getNombreCliente()+","+entRe.getMonto()+","+entRe.getStatusPago()+","+ entRe.getStatusEntrega()+","+entRe.getFecha());
            
            //Se cierra el writer
            myWriter.close();
            System.out.println("Escrito correctamente.");
        } catch (IOException e) {
            System.out.println("Error no se escribio el archivo.");
        }
    }
    
    public void writeFileOrder(Ticket ticket){
        try {
            //Se crea un nuevo archivo
            File saveF = new File("src/Registros/Ordenes/"+ticket.nOrden+".txt");
            if(!saveF.exists()){
                if (saveF.createNewFile()) {
                    System.out.println("Archivo creado: " + saveF.getName());
                  } else {
                    System.out.println("Archivo ya existente sobreescrito.");
                  }
            }          
            //Se crea un writer
            FileWriter myWriter;
                 myWriter = new FileWriter(saveF);
                    myWriter.write("NUMERO DE ORDEN");
                    myWriter.write("\n");
                    myWriter.write(ticket.nOrden+"");
                    myWriter.write("\n");
                    myWriter.write("NOMBRE DEL CLIENTE");
                    myWriter.write("\n");
                    myWriter.write(ticket.nameCliente+"");
                    myWriter.write("\n");
                    myWriter.write("FECHA DE OPERACION");
                    myWriter.write("\n");
                    myWriter.write(ticket.today+"");
                    myWriter.write("\n");
                    myWriter.write("COSTO TOTAL");
                    myWriter.write("\n");
                    myWriter.write(ticket.costoTotal+"");
                    myWriter.write("\n");
                    myWriter.write("IVA");
                    myWriter.write("\n");
                    myWriter.write(ticket.iva ? "SI" : "NO");
                    myWriter.write("\n");
                    myWriter.write("MONTO PAGADO");
                    myWriter.write("\n");
                    myWriter.write(ticket.montoPagado+"");
                    myWriter.write("\n");
                    myWriter.write("DETALLE DE ORDEN");
                    myWriter.write("\n");
            ticket.pformsT.forEach(elemento -> {
                try {
                    myWriter.write(elemento.getPiezas()+","+elemento.getKilos()+","+elemento.getServicio()+","+elemento.getDescripcion()+","+elemento.getAcero()+","+elemento.getPiezasEntregadas());
                    myWriter.write("\n");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            });
                        
            //Se cierra el writer
            myWriter.close();
            System.out.println("Escrito correctamente.");
        } catch (IOException e) {
            System.out.println("Error no se escribio el archivo.");
        }
    }
    
    public Ticket readFileOrder(int nOrden){
        
        File f = new File("src/Registros/Ordenes/"+nOrden+".txt");
        String[] st;
        String nameCliente="";
        Date opDate= new Date();
        boolean ivab=false;
        double costoTotalTicket=0.0;
        double mPagado=0.0;
        Ticket ticketsito;
        ArrayList<Elemento> elementosOrden = new ArrayList<Elemento>();
        System.out.println("eNTRE A LEER LA ORDEN "+nOrden);
        //BufferedReader in = new BufferedReader(new FileReader(f));    
        try{            
            DataInputStream in = new DataInputStream(new FileInputStream(f));   

            String line;
            while ((line = in.readLine()) != null){
                System.out.println(line);
                if(line.equals("NUMERO DE ORDEN")){
                    System.out.println("LEI EL NUMERO DE ORDEN");
                    line=in.readLine();
                }
                if(line.equals("NOMBRE DEL CLIENTE")){
                    line=in.readLine();
                    nameCliente=line;
                }
                if(line.equals("FECHA DE OPERACION")){
                    line=in.readLine();
                    opDate = new Date(line);
                }
                if(line.equals("IVA")){
                    line=in.readLine();
                    ivab = (line=="SI") ? true : false;
                }
                if(line.equals("COSTO TOTAL")){
                    line=in.readLine();
                    costoTotalTicket=Double.parseDouble(line);
                }
                if(line.equals("MONTO PAGADO")){
                    line=in.readLine();
                    mPagado=Double.parseDouble(line);
                }
                if(line.equals("DETALLE DE ORDEN")){
                    System.out.println("ANDO LEYENDO EL DETALLE DE ORDEN");
                    while ((line = in.readLine()) != null){
                        st=line.split(",");
                        elementosOrden.add(new Elemento(st[4], st[2], Integer.parseInt(st[0]), Double.parseDouble(st[1]), "", st[3], Integer.parseInt(st[5])));
                        System.out.println("Piezas "+ st[0]+"\n "+ "Kilos "+st[1]+"\n"+"Servicio"+st[2]+"\n"+ "Descripcion"+st[3]+"\n" +"Acero"+ st[4]+"\n"+"Piezas En."+st[5] +"\n\n");
                    }
                    break;
                }
                
                // System.out.println(st[0]+" " + st[1]+" " + Double.parseDouble(st[2])+" " + st[3]+" " + st[4] + " " + st[5]);
            }          
            
            in.close();
        }
        catch (IOException e) { 
            System.out.println(e.toString());
        }
        ticketsito = new Ticket(nOrden,nameCliente,0.0,0.0,0.0,0.0,costoTotalTicket,opDate,ivab,mPagado, elementosOrden);    

        return ticketsito;
    }

    private String getFilePath(){
        //Creamos un objeto FileChooser 
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("src/Instrucciones"));
        //creamos un filtro para que el usuario seleccione solo archivos jpg
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "RUT files", "rut");
        //agregamos el filtro al chooser
        chooser.setFileFilter(filter);
        //se abre la ventana para seleccionar el archivo
        int returnVal = chooser.showOpenDialog(null);
        //si la operacion no fue aprobada se retorna null
        if(returnVal != JFileChooser.APPROVE_OPTION) {
            return null;
        }
        else{
            //si fue aprobada retornamos el Path del archivo seleccionado
            return chooser.getSelectedFile().getPath();
        }    
    }

    /*public Foto cargarFile() throws FileNotFoundException, IOException{
        String instin;
        //File loadF= new File("src/instrucciones/instruccionF.rut");
        File loadF= new File(getFilePath());
        
        Reader ref = new FileReader(loadF);
        //Se crea un reader y un Stream Tokenizer
        StreamTokenizer tok=new StreamTokenizer(ref);
        //Se obitne la imagen a modificar
        BufferedImage aux;
        aux = fot.getImagen();
        //Variable para el parametro
        double param;
        double col;
        int canal;
        //Variable para la instruccion
        String ins;
        //Mientras los token no llegan al final del archivo
        while(tok.nextToken() != StreamTokenizer.TT_EOF){
            ins=tok.sval; //Se obtiene el valor de token
            instin=ins;
            //Se compara el valor con los nombres de los filtros y se aplica el correspondiente        
            switch(ins){
                case "Negativo":
                    aux=Filtro.getNegativo(aux);
                    break;
                case "Grises":
                    aux=Filtro.getGrayScale(aux);
                    break;
                case "FuncionLogaritmica":
                    aux=Filtro.getLogaritmo(aux);
                    break;
                case "FuncionSenoidal":
                    aux=Filtro.getSinoidal(aux);
                    break;
                case "FuncionCosenoidal":
                    aux=Filtro.getCosinoidal(aux);
                    break;
                case "UmbralizacionOtsu":
                    tok.nextToken();
                    canal=(int)tok.nval;
                    instin=instin+" "+canal;
                    aux=fot.binarizar(canal);
                    break;
                // A partir de aqui los filtro piden un parametro 
                // por ello se lee el siguiente token como un numero
                case "UmbralizacionNormal":
                    tok.nextToken();
                    param=tok.nval;
                    tok.nextToken();
                    canal=(int)tok.nval;
                    instin=instin+" "+param+" "+canal;
                    aux=fot.binarizar(canal,(int)param);
                    break;
                case "Brillo":
                    tok.nextToken();
                    param=tok.nval;
                    tok.nextToken();
                    canal=(int)tok.nval;
                    instin=instin+" "+param+" "+canal;
                    aux=Filtro.getBrillo(aux, (int)param,canal);
                    break;                                
                default:       //Si el token no coincide con uno de parametor se omite   
                    break;
                
            }
            saveInstruction(instin);
            //Se crea un nuevo objeto con la imagen modificada para trabajar sobre esta
            fot = new Foto(aux, fot.getOrigImage(), fot.getMaskImage(), fot.getFileName());  

        }//Se retorna la ia imagen modicada
        return new Foto(aux, fot.getOrigImage(), fot.getMaskImage(), fot.getFileName());
    }*/

    public ArrayList<EntradaRegistro> leerArchivo(){
        File f = new File("src/Registros/pruebas.csv");
        String[] st;
        ArrayList<EntradaRegistro> registros = new ArrayList<EntradaRegistro>();
        //BufferedReader in = new BufferedReader(new FileReader(f));    
        try{            
            DataInputStream in = new DataInputStream(new FileInputStream(f));   

            String line;
            while ((line = in.readLine()) != null){
                //System.out.println(line);
                st=line.split(",");
                registros.add(new EntradaRegistro(st[0], st[1], Double.parseDouble(st[2]), st[3], st[4], st[5]));
                // System.out.println(st[0]+" " + st[1]+" " + Double.parseDouble(st[2])+" " + st[3]+" " + st[4] + " " + st[5]);
            }          
                 
            in.close();
        }
        catch (IOException e) { 
            System.out.println(e.toString());
        }

        return registros;
    }

    public int getNumOrden() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/Registros/numOrden.csv"));
        int num = scanner.nextInt();
        scanner.close();
        return num;
    }
    
    public void incremetNumOrden() throws IOException{
        int num = getNumOrden();
        num++;
        String newNum = String.format("%04d", num);
        PrintWriter actualiza = new PrintWriter("src/Registros/numOrden.csv");
        actualiza.print(newNum);
        actualiza.close();
    }
}