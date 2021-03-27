package bo.edu.uagrm.ficct.inf310sb.grafos.nopesados;

import bo.edu.uagrm.ficct.inf310sb.grafos.excepciones.ExcepcionAristaNoExiste;
import bo.edu.uagrm.ficct.inf310sb.grafos.excepciones.ExcepcionAristaYaExiste;
import bo.edu.uagrm.ficct.inf310sb.grafos.excepciones.ExcepcionNroVerticesInvalido;

import java.util.List;

public class Digrafo extends Grafo{
    public Digrafo() {
    }

    public Digrafo(int nroDeVerticesIncial) throws ExcepcionNroVerticesInvalido {
        super(nroDeVerticesIncial);
    }

    @Override
    public void insertarArista(int posVerticeOrigen, int posVerticeDestino) throws ExcepcionAristaYaExiste {
        super.validarVertice(posVerticeOrigen);
        super.validarVertice(posVerticeDestino);
        if (super.existeAdyacencia(posVerticeOrigen,posVerticeDestino)) {
            throw new ExcepcionAristaYaExiste();
        }

        List<Integer> adyacenciasDelOrigen = super.listasDeAdyacencias.get(posVerticeOrigen);
        adyacenciasDelOrigen.add(posVerticeDestino);
    }

    @Override
    public int gradoDeVertice(int posDeVertice) {
        throw new UnsupportedOperationException("No soportados en grafos dirigidos");
    }

    public int gradoDeSalida(int posDeVertice) {
        return super.gradoDeVertice(posDeVertice);
    }

    public int gradoDeEntrada(int posDeVertice) {
        super.validarVertice(posDeVertice);
        int entradaDeVertice = 0;
        for (List<Integer> adyacentesDeUnVertice : super.listasDeAdyacencias){
            for (Integer posAdyacente : adyacentesDeUnVertice) {
                if (posAdyacente == posDeVertice) {
                    entradaDeVertice++;
                }
            }
        }
        return entradaDeVertice;
    }

//tarea
    @Override
    public int cantidadDeAristas() {
        int cantArista = 0;
        for (List<Integer> adyacentesDeUnVertice : super.listasDeAdyacencias){
            for (Integer posAdyacente : adyacentesDeUnVertice) {
                cantArista++;
            }
        }
        return cantArista;
    }

    // tarea
    @Override
    public void eliminarArista(int posVerticeOrigen, int posVerticeDestino) throws ExcepcionAristaNoExiste {
        super.validarVertice(posVerticeOrigen);
        super.validarVertice(posVerticeDestino);
        if (!super.existeAdyacencia(posVerticeOrigen,posVerticeDestino)) {
            throw new ExcepcionAristaNoExiste();
        }
        List<Integer> adyacenciasDelOrigen = super.listasDeAdyacencias.get(posVerticeOrigen);
        for (int i = 0; i<adyacenciasDelOrigen.size(); i++){
            if(adyacenciasDelOrigen.get(i)==posVerticeDestino){
                adyacenciasDelOrigen.remove(i);
            }
        }
    }
    @Override
    public String toString (){
        return listasDeAdyacencias.toString();
    }
}
