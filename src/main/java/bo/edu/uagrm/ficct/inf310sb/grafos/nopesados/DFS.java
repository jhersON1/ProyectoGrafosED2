package bo.edu.uagrm.ficct.inf310sb.grafos.nopesados;

import bo.edu.uagrm.ficct.inf310sb.grafos.excepciones.ExcepcionAristaNoExiste;

import java.util.ArrayList;
import java.util.List;

public class DFS {

    public List<Integer> recorrido;
    private Grafo grafo;
    public UtilsRecorridos controlMarcados;
    public int contador = 0;

    public DFS(Grafo unGrafo, int posVerticePartida) throws ExcepcionAristaNoExiste {
        this.grafo = unGrafo;
        grafo.validarVertice(posVerticePartida);
        recorrido = new ArrayList<>();
        controlMarcados = new UtilsRecorridos(grafo.cantidadDeVertices());
        controlMarcados.desmarcarTodo();
        continuarDFS(posVerticePartida);
    }

    public void continuarDFS(int posVertice) {
        controlMarcados.marcarVertice(posVertice);
        recorrido.add(posVertice);
        Iterable<Integer> adyacentesEnTurno = grafo.adyacentesDeVertice(posVertice);
        for (Integer posVerticeAdyacente : adyacentesEnTurno) {
            if (!controlMarcados.estaMarcado(posVerticeAdyacente)) {
                continuarDFS(posVerticeAdyacente);
            }
        }
    }


    public boolean hayCaminoA(int posVertice) {
        grafo.validarVertice(posVertice);
        return controlMarcados.estaMarcado(posVertice);
    }

    public Iterable<Integer> elRecorrido() {
        return recorrido;
    }

    public boolean hayCaminoATodos(){
        return controlMarcados.estaTodoMarcado();
    }
}
