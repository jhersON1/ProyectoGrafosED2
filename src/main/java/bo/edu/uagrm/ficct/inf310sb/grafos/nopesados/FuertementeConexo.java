package bo.edu.uagrm.ficct.inf310sb.grafos.nopesados;


import bo.edu.uagrm.ficct.inf310sb.grafos.excepciones.ExcepcionAristaNoExiste;

public class FuertementeConexo {
    private Conexo grafoConexo;
    private int posVerticeDePartida = 0;

    public FuertementeConexo(Digrafo unDigrafo) throws ExcepcionAristaNoExiste {

        this.grafoConexo = new Conexo(unDigrafo, posVerticeDePartida);
    }

    public boolean esConexo(){
        return this.grafoConexo.esConexo();
    }
    public boolean esFuertementeConexo(Digrafo unDigrafo) throws ExcepcionAristaNoExiste {
        if (esConexo()) {
            for (Integer verticesPorRecorrer : grafoConexo.elRecorrido()) {
                posVerticeDePartida = verticesPorRecorrer.intValue();
                if (!grafoConexo.esConexo(unDigrafo, posVerticeDePartida)) {
                    return false;
                }

            }
            return true;
        }else {
            return false;
        }
    }

    public boolean esDebilmenteConexo (Digrafo unDigrafo) throws ExcepcionAristaNoExiste {
        if (!esFuertementeConexo(unDigrafo)){
                return true;
        }

        return false;
    }

/*    public List<Integer> componentesFuertementeConexas(Digrafo digrafo) throws ExcepcionAristaNoExiste {
        int componetes = 1;
        List<Integer> listaAux = new ArrayList<>();
        List<Integer> listaAux2 = new ArrayList<>();
        int n
        if (!esFuertementeConexo(digrafo)) {
            while (listaAux2.size() <= digrafo.listasDeAdyacencias.size()) {
                //DFS dfs = new DFS(digrafo, 0);
                for (int i = 0; i < digrafo.listasDeAdyacencias.size(); i++) {
                    if (hayCamino(i, 0, digrafo)) {
                        listaAux.add(i);
                        listaAux2.add(i);
                    }
                }
                if (listaAux.size() > 2) {
                    componetes++;
                }
            }
        }
        return listaAux;
    }*/

    public boolean hayCamino(int verticeA, int verticeB, Digrafo digrafo) throws ExcepcionAristaNoExiste {
        DFS dfs = new DFS(digrafo,verticeA);
        if (dfs.hayCaminoA(verticeB)){
            return true;
        }
        return false;
    }
}
