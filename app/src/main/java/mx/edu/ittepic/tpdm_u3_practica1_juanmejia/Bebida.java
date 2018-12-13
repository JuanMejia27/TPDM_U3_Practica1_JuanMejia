package mx.edu.ittepic.tpdm_u3_practica1_juanmejia;

public class Bebida {
    String nombre;
    int precio;

    public Bebida(){
    }

    public Bebida(String nombre,int precio ){
        this.nombre=nombre;
        this.precio=precio;
    }

    public String getNombre(){
        return nombre;
    }

    public int getPrecio(){
        return precio;
    }

    public void setNombre(String nombre){
        this.nombre=nombre;
    }

    public void setPrecio(int precio){
        this.precio=precio;
    }
}
