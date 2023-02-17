public class MatrizValidacion {
    int maxRows;
    int maxCols;
    public double[][] matriz;

    public MatrizValidacion(int maxRows, int maxCols){
        this.maxRows=maxRows;
        this.maxCols=maxCols;
        matriz=new double[maxRows][maxCols];}

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

    
