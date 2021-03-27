package bo.edu.uagrm.ficct.inf310sb.grafos.nopesados;

import bo.edu.uagrm.ficct.inf310sb.grafos.excepciones.ExcepcionAristaNoExiste;
import bo.edu.uagrm.ficct.inf310sb.grafos.excepciones.ExcepcionAristaYaExiste;
import bo.edu.uagrm.ficct.inf310sb.grafos.excepciones.ExcepcionNroVerticesInvalido;

import java.util.ArrayList;
import java.util.List;

public class Grafo{

    protected List<List<Integer>> listasDeAdyacencias;

    public Grafo(){
        this.listasDeAdyacencias = new ArrayList<>();
    }

    public Grafo(int nroDeVerticesIncial) throws ExcepcionNroVerticesInvalido {
        if (nroDeVerticesIncial < 0){
            throw new ExcepcionNroVerticesInvalido();
        }
        this.listasDeAdyacencias = new ArrayList<>();
        for (int i = 0; i < nroDeVerticesIncial; i++){
            this.listasDeAdyacencias.add(new ArrayList<Integer>());
        }
    }

    public void insertarVertice(){
        this.listasDeAdyacencias.add(new ArrayList<Integer>());
    }


    public int cantidadDeAristas(){
        int cantAristas = 0;
        int cantLazos = 0;
        for (int i = 0; i < this.listasDeAdyacencias.size(); i++) {
            List<Integer> adyacentesDeUnVertice = this.listasDeAdyacencias.get(i);
            for (Integer posAdyacente : adyacentesDeUnVertice) {
                if (i == posAdyacente){
                    cantLazos++;
                }else {
                    cantAristas++;
                }
            }
        }
        cantAristas = (cantAristas / 2) + cantLazos;
        return cantAristas;
    }

    public int cantidadDeVertices() {
        return this.listasDeAdyacencias.size();
    }

    protected void validarVertice(int posicionDeVertice) {
        if (posicionDeVertice < 0 || posicionDeVertice >= cantidadDeVertices()) {
            throw new IllegalArgumentException("El vertice "+ posicionDeVertice + "no pertenece al grafo");
        }
    }

    public void insertarArista (int posVerticeOrigen, int posVerticeDestino) throws ExcepcionAristaYaExiste {
        validarVertice(posVerticeOrigen);
        validarVertice(posVerticeDestino);
        if (this.existeAdyacencia(posVerticeOrigen,posVerticeDestino)) {
            throw new ExcepcionAristaYaExiste();
        }
        List<Integer> adyacenciasDelOrigen = this.listasDeAdyacencias.get(posVerticeOrigen);
        adyacenciasDelOrigen.add(posVerticeDestino);
        if (posVerticeOrigen != posVerticeDestino) {
            List<Integer> adyacenciasDelDestino = this.listasDeAdyacencias.get(posVerticeDestino);
            adyacenciasDelDestino.add(posVerticeOrigen);
        }
    }

    public boolean existeAdyacencia(int posVerticeOrigen, int posVerticeDestino){
        validarVertice(posVerticeOrigen);
        validarVertice(posVerticeDestino);
        List<Integer> adyacenciasDelOrigen = this.listasDeAdyacencias.get(posVerticeOrigen);
        return adyacenciasDelOrigen.contains(posVerticeDestino);
    }

    public void eliminarVertice(int posVerticeAEliminar){
        validarVertice(posVerticeAEliminar);
        this.listasDeAdyacencias.remove(posVerticeAEliminar);
        for (List<Integer> adyacentesDeUnVertice: this.listasDeAdyacencias) {
            int posicionDeVerticeEnAdy = adyacentesDeUnVertice.indexOf(posVerticeAEliminar);
            if (posicionDeVerticeEnAdy >= 0) {
                adyacentesDeUnVertice.remove(posicionDeVerticeEnAdy);
            }
            for (int i = 0; i < adyacentesDeUnVertice.size(); i++){
                int posicionAdyacente = adyacentesDeUnVertice.get(i);
                if (posicionAdyacente > posVerticeAEliminar){
                    adyacentesDeUnVertice.set(i,posicionAdyacente - 1);
                }
            }
        }
    }

    public int gradoDeVertice(int posDeVertice){
        validarVertice(posDeVertice);
        List<Integer> adyacenciasDelVertice = this.listasDeAdyacencias.get(posDeVertice);
        return adyacenciasDelVertice.size();
    }

    public Iterable<Integer> adyacentesDeVertice(int posDeVertice) {
        validarVertice(posDeVertice);
        List<Integer> adyacenciasDelVertice = this.listasDeAdyacencias.get(posDeVertice);
        Iterable<Integer> it = adyacenciasDelVertice;
        return it;
    }
    public int primerAdyacenteDeVertice(int posDeVertice){
        validarVertice(posDeVertice);
        Iterable<Integer> adyacentesEnTurno = adyacentesDeVertice(posDeVertice);
        for (Integer posVerticeAdyacente : adyacentesEnTurno) {
            return  posVerticeAdyacente;
        }
        return -1;
    }
    // hay que implementar

    public void eliminarArista(int posVerticeOrigen, int posVerticeDestino) throws ExcepcionAristaNoExiste {
        validarVertice(posVerticeOrigen);
        validarVertice(posVerticeDestino);
        if (!this.existeAdyacencia(posVerticeOrigen,posVerticeDestino)) {
            throw new ExcepcionAristaNoExiste();
        }
        for (int i = 0; i < listasDeAdyacencias.get(posVerticeOrigen).size(); i++){
            if ( listasDeAdyacencias.get(posVerticeOrigen).get(i) == posVerticeDestino){
                listasDeAdyacencias.get(posVerticeOrigen).remove(i);
            }
        }
        for (int i = 0; i <  listasDeAdyacencias.get(posVerticeDestino).size(); i++){
            if ( listasDeAdyacencias.get(posVerticeDestino).get(i) == posVerticeOrigen){
                listasDeAdyacencias.get(posVerticeDestino).remove(i);
            }
        }
    }

    public List<List<Integer>> copiarListaDeAdyacencia(){
        List<List<Integer>> copiaDeListaDeAdyacencia = new ArrayList<>();
        for (int i = 0; i < listasDeAdyacencias.size(); i++){
            copiaDeListaDeAdyacencia.add(new ArrayList<Integer>());
        }
        for (int i = 0; i < listasDeAdyacencias.size(); i++){
            List<Integer> adyacenciasDelOrigen = copiaDeListaDeAdyacencia.get(i);
            for (int j = 0; j < listasDeAdyacencias.get(i).size(); j++){
                adyacenciasDelOrigen.add(listasDeAdyacencias.get(i).get(j));
            }
        }
        return copiaDeListaDeAdyacencia;
    }

    @Override
    public String toString (){
        return listasDeAdyacencias.toString();
    }

}
