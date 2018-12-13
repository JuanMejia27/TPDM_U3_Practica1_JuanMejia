package mx.edu.ittepic.tpdm_u3_practica1_juanmejia;

import java.util.ArrayList;

public class Comanda {
    ArrayList<String > platillos;
    ArrayList<String>bebidas;
    int total;

    public Comanda(){
    }

    public Comanda(ArrayList<String>platillos,ArrayList<String>bebidas,int total){
        this.platillos=platillos;
        this.bebidas=bebidas;
        this.total=total;
    }

    public ArrayList<String> getPlatillos() {
        return platillos;
    }

    public ArrayList<String> getBebidas() {
        return bebidas;
    }

    public int getTotal() {
        return total;
    }

    public void setPlatillos(ArrayList<String> platillos) {
        this.platillos = platillos;
    }

    public void setBebidas(ArrayList<String> bebidas) {
        this.bebidas = bebidas;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}