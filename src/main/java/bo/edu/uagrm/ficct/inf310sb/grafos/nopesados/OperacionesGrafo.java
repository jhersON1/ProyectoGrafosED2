package bo.edu.uagrm.ficct.inf310sb.grafos.nopesados;


import bo.edu.uagrm.ficct.inf310sb.grafos.excepciones.ExcepcionAristaNoExiste;
import bo.edu.uagrm.ficct.inf310sb.grafos.excepciones.ExcepcionAristaYaExiste;

import java.util.ArrayList;
import java.util.List;

public class OperacionesGrafo {

    private Grafo grafo;
    private DFS dfs;
    private Conexo conexo;

    public OperacionesGrafo(Grafo unGrafo) throws ExcepcionAristaNoExiste {
        this.grafo = unGrafo;
        this.conexo = new Conexo(grafo);
        //this.dfs = new DFS(grafo,0);
    }

    public int cantidadDeIslas() throws ExcepcionAristaNoExiste {
        int cantIslas = 1;
        if (!conexo.esConexoGrafo(grafo,0)) {
            while (!dfs.controlMarcados.estaTodoMarcado()) {
                int noMarcado = dfs.controlMarcados.encontrarNoMarcado();
                if (noMarcado > 0){
                    dfs.continuarDFS(noMarcado);
                    cantIslas++;
                }
            }
        }
        return cantIslas;
    }

    public boolean existeCicloEnUnGrafo() throws ExcepcionAristaNoExiste {
            if (grafo.cantidadDeAristas() >= (grafo.cantidadDeVertices()-(cantidadDeIslas() - 1))){
                return true;
            }
            return false;
    }

    public boolean existeCicloEnElVertice(int posVertice) throws ExcepcionAristaNoExiste, ExcepcionAristaYaExiste {
        if (!existeCicloEnUnGrafo()){
            return false;
        }
        for (int i = 0; i < grafo.listasDeAdyacencias.get(posVertice).size(); i++) {
            int adyacenteDelVertice = grafo.listasDeAdyacencias.get(posVertice).get(i);
            grafo.eliminarArista(posVertice, adyacenteDelVertice);
            DFS dfsCiclo = new DFS(grafo, adyacenteDelVertice);
            Iterable<Integer> recorrido = dfsCiclo.elRecorrido();
            for (Integer posVerticeAdyacente : recorrido) {
                if (posVerticeAdyacente == posVertice) {
                    grafo.insertarArista(posVertice,adyacenteDelVertice);
                    return true;
                }
            }
            grafo.insertarArista(posVertice,adyacenteDelVertice);
        }
        return false;
        // lo que hago es empezar en un vertice adyacente del vertice puesto  como parametro (posVertice) y se elimina la arista entre ellas.
        // entonces desde ese vertice adyacente empieza a hacer un recorrido DFS
        // como se elimina una arista luego añadimos la misma arista para que no haya cambios en la listaDeAdyacencia.
        // y si en la lista de recorridos se encuentra el vertice puesto como parametro (posVertice) entoces significa que ese vertice si tiene ciclo
        // se hace lo mismo con todos los vertices adyacentes al vertice puesto como parametro (posVertice).
    }

    // 4. Para un grafo no dirigido implementar un algoritmo para encontrar que en que vértices del grafo hay ciclos

    public List<Integer> listaDeVerticesQueTienenCiclo() throws ExcepcionAristaNoExiste, ExcepcionAristaYaExiste {
        List<Integer> listaDeVerticesConCiclo = new ArrayList<>();
        for (int i = 0; i < grafo.listasDeAdyacencias.size(); i++){
            if (existeCicloEnElVertice(i)){
                listaDeVerticesConCiclo.add(i);
            }
        }
        return listaDeVerticesConCiclo;
    }

}
