package bo.edu.uagrm.ficct.inf310sb.grafos.pesados;

public class AdyacenteConPesoIndiceOrigen implements Comparable<AdyacenteConPesoIndiceOrigen> {
    private double peso;
    private int indiceVerticeOrigen;
    private int indiceVerticeDestino;


    public AdyacenteConPesoIndiceOrigen(int verticeOrigen, int verticeDestino) {
        this.indiceVerticeOrigen = verticeOrigen;
        this.indiceVerticeDestino = verticeDestino;
    }

    public AdyacenteConPesoIndiceOrigen(int verticeOrigen, int verticeDestino, double peso) {
        this.indiceVerticeOrigen = verticeOrigen;
        this.indiceVerticeDestino = verticeDestino;
        this.peso = peso;
    }

    public void setAdyacenteConPesoIndiceOrigen(int verticeOrigen, int verticeDestino, double peso) {
        this.indiceVerticeOrigen = verticeOrigen;
        this.indiceVerticeDestino = verticeDestino;
        this.peso = peso;
    }

    public int getIndiceVerticeOrigen() {
        return indiceVerticeOrigen;
    }

    public int getIndiceVerticeDestino() {
        return indiceVerticeDestino;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + this.indiceVerticeOrigen + this.indiceVerticeDestino;
        return hash;
    }

    @Override
    public boolean equals(Object otro) {
        if (otro == null) {
            return false;
        }
        if (getClass() != otro.getClass()) {
            return false;
        }
        AdyacenteConPesoIndiceOrigen other = (AdyacenteConPesoIndiceOrigen) otro;
        return (this.indiceVerticeOrigen == other.indiceVerticeOrigen) &&
                (this.indiceVerticeDestino == other.indiceVerticeDestino);
    }

    @Override
    public int compareTo(AdyacenteConPesoIndiceOrigen vert) {
        Double peso = this.peso;
        Double otroPeso = vert.peso;
        return peso.compareTo(otroPeso);
    }

}
