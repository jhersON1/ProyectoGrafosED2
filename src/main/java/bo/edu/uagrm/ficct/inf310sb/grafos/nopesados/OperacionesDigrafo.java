package bo.edu.uagrm.ficct.inf310sb.grafos.nopesados;

import bo.edu.uagrm.ficct.inf310sb.grafos.excepciones.ExcepcionAristaNoExiste;
import bo.edu.uagrm.ficct.inf310sb.grafos.excepciones.ExcepcionAristaYaExiste;

import java.util.ArrayList;
import java.util.List;

public class OperacionesDigrafo {
    public Digrafo digrafo;
    public DFS dfs;
    public int [][] p;
    public OperacionesDigrafo(Digrafo unDigrafo, int posVerticePartida) throws ExcepcionAristaNoExiste {
        this.digrafo = unDigrafo;
        this.dfs = new DFS(unDigrafo,posVerticePartida);
        this.p = new int[digrafo.cantidadDeVertices()][digrafo.cantidadDeVertices()];
        for (int i = 0;  i < digrafo.listasDeAdyacencias.size(); i++){
            for (int j = 0; j < digrafo.listasDeAdyacencias.get(i).size(); j++){
                int n = digrafo.listasDeAdyacencias.get(i).get(j);
                p[i][n] = 1;
            }
        }
    }
//modificado
    public boolean verificarSiVerticeEsAdyacenteDeUnMarcado(int posvertice){
        Iterable<Integer> listaDeAdyacentes = digrafo.listasDeAdyacencias.get(posvertice);
        for(Integer adyacenteEnturno: listaDeAdyacentes){
            if (dfs.controlMarcados.listaMarcados().get(adyacenteEnturno) == Boolean.TRUE){
                return true;
            }
        }
        return false;
    }
//modificado
    public int cantidadDeIslasDigrafo(){
        int cantidadIslas = 1;
        while (!dfs.controlMarcados.estaTodoMarcado()) {
            for (int i = 0; i < dfs.controlMarcados.listaMarcados().size(); i++) {
                if (dfs.controlMarcados.listaMarcados().get(i) == false) {
                    if (verificarSiVerticeEsAdyacenteDeUnMarcado(i)) {
                        dfs.controlMarcados.marcarVertice(i);
                        i = -1;
                    }
                }
            }
            if (!dfs.controlMarcados.estaTodoMarcado()){
                cantidadIslas++;
                int noMarcado = dfs.controlMarcados.encontrarNoMarcado();
                dfs.continuarDFS(noMarcado);
            }
        }
        return cantidadIslas;
    }

    public boolean existeCicloEnElVertice(int posVertice) throws ExcepcionAristaNoExiste, ExcepcionAristaYaExiste {
        dfs.controlMarcados.desmarcarTodo();
        dfs.recorrido.clear();
        for (int i = 0; i < digrafo.listasDeAdyacencias.get(posVertice).size(); i++) {
            int adyacenteDelVertice = digrafo.listasDeAdyacencias.get(posVertice).get(i);
            digrafo.eliminarArista(posVertice, adyacenteDelVertice);
            dfs.continuarDFS(adyacenteDelVertice);
            Iterable<Integer> recorrido = dfs.elRecorrido();
            for (Integer posVerticeAdyacente : recorrido) {
                if (posVerticeAdyacente == posVertice) {
                    digrafo.insertarArista(posVertice,adyacenteDelVertice);
                    return true;
                }
            }
            digrafo.insertarArista(posVertice,adyacenteDelVertice);
        }
        return false;
        // lo que hago es empezar en un vertice adyacente del vertice puesto  como parametro (posVertice) y se elimina la arista entre ellas.
        // entonces desde ese vertice adyacente empieza a hacer un recorrido DFS
        // como se elimina una arista luego añadimos la misma arista para que no haya cambios en la listaDeAdyacencia.
        // y si en la lista de recorridos se encuentra el vertice puesto como parametro (posVertice) entoces significa que ese vertice si tiene ciclo
        // se hace lo mismo con todos los vertices adyacentes al vertice puesto como parametro (posVertice).
    }

    public boolean existeCiclo() throws ExcepcionAristaNoExiste, ExcepcionAristaYaExiste {
        for (int i = 0; i < digrafo.cantidadDeVertices(); i++){
            if (existeCicloEnElVertice(i) == true){
                return true;
            }
        }
        return false;
    }
    // matriz de caminos (algoritmo de Wharsall)

    public void algoritmoWharsall (){
        for (int k = 0; k < digrafo.cantidadDeVertices(); k++){
            for (int i = 0; i < digrafo.cantidadDeVertices(); i++){
                for (int j = 0; j < digrafo.cantidadDeVertices(); j++){
                    p[i][j] = p[i][j] | (p[i][k] & p[k][j]);
                }
            }
        }
    }

    public List<Integer> verticesConCaminoA(int verticeDestino){
        dfs.controlMarcados.desmarcarTodo();
        dfs.recorrido.clear();
        List<Integer> verticesConCaminoA = new ArrayList<>();
        for (int i = 0; i < digrafo.listasDeAdyacencias.size(); i++){
            dfs.continuarDFS(i);
            Iterable<Integer> recorrido = dfs.elRecorrido();
            for (Integer verticeMarcado: recorrido) {
                if (verticeMarcado == verticeDestino && i != verticeDestino){
                    verticesConCaminoA.add(i);
                }
            }
            dfs.controlMarcados.desmarcarTodo();
            dfs.recorrido.clear();
        }
        return verticesConCaminoA;
    }

    public List<Integer> componentesDeLasIslas(){
        List<Integer> listAux = new ArrayList<>();
        for (int i = 0; i < digrafo.listasDeAdyacencias.size(); i++){
            listAux.add(i);
        }
        return listAux;
    }
}
