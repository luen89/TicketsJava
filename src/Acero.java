public class Acero {
    int id;
    String name;
    String nameAbrv;


    public Acero(int id, String name){
        this.id=id;
        this.name=name;
        this.nameAbrv=name;
    }

    public Acero(int id, String name, String nameAbrv){
        this.id=id;
        this.name=name;
        this.nameAbrv=nameAbrv;
    }

    public Object[] getData(int Identificador){

        return new Object[] {
            Identificador,
            id,
            name,
            nameAbrv};

    }
}
