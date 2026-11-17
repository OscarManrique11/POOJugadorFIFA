package models;

public class Persona {
    public String name;
    private String country ;
    private String id ;
    public boolean is_alive = true;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("El ID no puede ser negativo.");
        }
        this.id = id;
    }

    public String getCountry(){
        return country;
    }

    public void setCountry(String newCountry){
        this.country=newCountry;
    }

    @Override
    public String toString() {
        return "models.Persona{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", is_alive=" + is_alive +
                '}';
    }




}
