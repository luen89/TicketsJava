import java.util.prefs.InvalidPreferencesFormatException;

public class MatrizValidacion {
    int maxRows;
    int maxCols;
    private double[][] matriz;

    public MatrizValidacion(int maxRows, int maxCols){
        this.maxRows=maxRows;
        this.maxCols=maxCols;
        matriz=new double[maxRows][maxCols];}

    public void restarAcelda(int indiceServicio, int indiceAcero, double kilos){
        matriz[indiceServicio][indiceAcero]-=kilos;
    }

    public void sumarAcelda(int indiceServicio, int indiceAcero, double kilos){
        matriz[indiceServicio][indiceAcero]+=kilos;
    }

    public double getCelda(int indiceServicio, int indiceAcero){
        return matriz[indiceServicio][indiceAcero];
    }

    public void printMatriz(){
        System.out.println("Matriz de Validacion");
        for(int i=0;i<maxRows;i++){
            for(int j=0;j<maxCols;j++){
                System.out.print(matriz[i][j]+" ");
            }
            System.out.println("");    
        }
    }
        
    }

    
