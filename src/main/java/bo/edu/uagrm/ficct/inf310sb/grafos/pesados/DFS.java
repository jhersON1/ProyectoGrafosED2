package bo.edu.uagrm.ficct.inf310sb.grafos.pesados;

import bo.edu.uagrm.ficct.inf310sb.grafos.excepciones.ExcepcionAristaNoExiste;
import java.util.ArrayList;
import java.util.List;

public class DFS {
    public List<Integer> recorrido;
    private GrafoPesado grafoPesado;
    public UtilsRecorridos controlMarcados;
    public int contador = 0;

    public DFS(GrafoPesado unGrafoPesado, int posVerticePartida) throws ExcepcionAristaNoExiste {
        this.grafoPesado = unGrafoPesado;
        grafoPesado.validarVertice(posVerticePartida);
        recorrido = new ArrayList<>();
        controlMarcados = new UtilsRecorridos(grafoPesado.cantidadDeVertices());
        controlMarcados.desmarcarTodo();
        continuarDFS(posVerticePartida);
    }

    public void continuarDFS(int posVertice) {
        controlMarcados.marcarVertice(posVertice);
        recorrido.add(posVertice);
        Iterable<Integer> adyacentesEnTurno = grafoPesado.adyacentesDeVertice(posVertice);
        for (Integer posVerticeAdyacente : adyacentesEnTurno) {
            if (!controlMarcados.estaMarcado(posVerticeAdyacente)) {
                continuarDFS(posVerticeAdyacente);
            }
        }
    }


    public boolean hayCaminoA(int posVertice) {
        grafoPesado.validarVertice(posVertice);
        return controlMarcados.estaMarcado(posVertice);
    }

    public Iterable<Integer> elRecorrido() {
        return recorrido;
    }

    public boolean hayCaminoATodos(){
        return controlMarcados.estaTodoMarcado();
    }
}
