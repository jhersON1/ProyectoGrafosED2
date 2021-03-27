package bo.edu.uagrm.ficct.inf310sb.grafos.nopesados;

import bo.edu.uagrm.ficct.inf310sb.grafos.excepciones.ExcepcionAristaNoExiste;

public class Conexo {
    private DFS dfsGrafo;


    public Conexo(Grafo unGrafo) throws ExcepcionAristaNoExiste {
        dfsGrafo = new DFS(unGrafo,0);
    }
    public Conexo(Grafo unGrafo,int posVerticeDePartida) throws ExcepcionAristaNoExiste {
        dfsGrafo = new DFS(unGrafo,posVerticeDePartida);
    }

    public boolean esConexo(){
       // dfsGrafo.controlMarcados.desmarcarTodo();
        return dfsGrafo.hayCaminoATodos();
    }
    public boolean esConexo(Digrafo unGrafo, int posVerticeInicial) throws ExcepcionAristaNoExiste {
        dfsGrafo.controlMarcados.desmarcarTodo();
        dfsGrafo = new DFS(unGrafo,posVerticeInicial);
        return dfsGrafo.hayCaminoATodos();
    }
    public boolean esConexoGrafo(Grafo unGrafo, int posVerticeInicial) throws ExcepcionAristaNoExiste {
        //dfsGrafo.controlMarcados.desmarcarTodo();
        dfsGrafo = new DFS(unGrafo,posVerticeInicial);
        return dfsGrafo.hayCaminoATodos();
    }

    public Iterable<Integer> elRecorrido() {
        return dfsGrafo.elRecorrido();
    }
}
