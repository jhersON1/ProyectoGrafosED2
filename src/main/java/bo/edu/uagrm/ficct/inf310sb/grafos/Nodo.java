
package bo.edu.uagrm.ficct.inf310sb.grafos;


public class Nodo {
    public int Data;
    public double Precio;
    public double Distancia;
    public Nodo Link;

    public Nodo(){
        this(0, 0, 0);
    }
    
    public Nodo(int Data, double Precio,double Distancia) {
        this.Data = Data;
        this.Precio = Precio;
        this.Distancia=Distancia;
        this.Link = null;
    }

    public int getData() {
        return Data;
    }

    public void setData(int Data) {
        this.Data = Data;
    }

    public double getPrecio() {
        return Precio;
    }

    public void setPrecio(double Precio) {
        this.Precio = Precio;
    }
    
    public double getDistancia() {
        return Distancia;
    }

    public void setDistancia(double Distancia) {
        this.Distancia = Distancia;
    }
    
    public Nodo getLink() {
        return Link;
    }

    public void setLink(Nodo Link) {
        this.Link = Link;
    }
    
}
