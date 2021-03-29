package bo.edu.uagrm.ficct.inf310sb.grafos.pesados;

import bo.edu.uagrm.ficct.inf310sb.grafos.excepciones.ExcepcionAristaNoExiste;
import bo.edu.uagrm.ficct.inf310sb.grafos.excepciones.ExcepcionAristaYaExiste;
import bo.edu.uagrm.ficct.inf310sb.grafos.excepciones.ExcepcionNroVerticesInvalido;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GrafoPesado {

    protected List<List<AdyacenteConPeso>> listasDeAdyacencias;
    //private DFS dfs;
    public List<String> nombres;
    public List<Integer> puntosX;
    public List<Integer> puntosY;

    private static final int MAXVERTEX = 49;
    //matrices para recorrido mas corto
    private double M1[][];
    private double M2[][];
    private double M3[][];
    private double M4[][];

    public GrafoPesado() {
        this.listasDeAdyacencias = new ArrayList<List<AdyacenteConPeso>>();
        this.nombres = new ArrayList<>();
        this.puntosX = new ArrayList<>();
        this.puntosY = new ArrayList<>();

        M1 = new double[MAXVERTEX + 1][MAXVERTEX + 1];       //Iniciar las matrces de adyacencia
        M2 = new double[MAXVERTEX + 1][MAXVERTEX + 1];
        M3 = new double[MAXVERTEX + 1][MAXVERTEX + 1];
        M4 = new double[MAXVERTEX + 1][MAXVERTEX + 1];
    }

    public GrafoPesado(int nroDeVerticesIncial) throws ExcepcionNroVerticesInvalido {
        if (nroDeVerticesIncial < 0){
            throw new ExcepcionNroVerticesInvalido();
        }
        this.listasDeAdyacencias = new ArrayList<>();
        for (int i = 0; i < nroDeVerticesIncial; i++){
            this.listasDeAdyacencias.add(new ArrayList<AdyacenteConPeso>());
        }
    }
    public void insertarVertice() {
        this.listasDeAdyacencias.add(new ArrayList<AdyacenteConPeso>());
    }

    public void addVertice(String nombre, int nuevaPosX, int nuevaPosY) {
        this.listasDeAdyacencias.add(new ArrayList<AdyacenteConPeso>());
        nombres.add(nombre);
        puntosX.add(nuevaPosX);
        puntosY.add(nuevaPosY);
    }

    public int cantidadDeAristas() {
        int cantAristas = 0;
        int cantLazos = 0;
        for (int i = 0; i < this.listasDeAdyacencias.size(); i++) {
            List<AdyacenteConPeso> adyacentesDeUnVertice = this.listasDeAdyacencias.get(i);
            for (AdyacenteConPeso posAdyacente : adyacentesDeUnVertice) {
                if (i == posAdyacente.getIndiceVertice()) {
                    cantLazos++;
                } else {
                    cantAristas++;
                }
            }
        }
        cantAristas = (cantAristas / 2) + cantLazos;
        return cantAristas;
    }

    public int cantidadDeVertices() {
        return listasDeAdyacencias.size();
    }

    public void validarVertice(int posicionDeVertice) {
        if (posicionDeVertice < 0 || posicionDeVertice >= cantidadDeVertices()) {
            throw new IllegalArgumentException("El vértice " + posicionDeVertice + " no pertece al grafo");
        }
    }

    public void insertarArista(int posVerticeOrigen, int posVerticeDestino, double costo) throws ExcepcionAristaYaExiste {
        validarVertice(posVerticeOrigen);
        validarVertice(posVerticeDestino);
        if (this.existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            throw new ExcepcionAristaYaExiste();
        }
        List<AdyacenteConPeso> adyacenciasDelOrigen = this.listasDeAdyacencias.get(posVerticeOrigen);
        adyacenciasDelOrigen.add(new AdyacenteConPeso(posVerticeDestino, costo));
        if (posVerticeOrigen != posVerticeDestino) {
            List<AdyacenteConPeso> adyacenciasDelDestino = this.listasDeAdyacencias.get(posVerticeDestino);
            adyacenciasDelDestino.add(new AdyacenteConPeso(posVerticeOrigen, costo));
        }
    }

    public boolean existeAdyacencia(int posVerticeOrigen, int posVerticeDestino) {
        validarVertice(posVerticeOrigen);
        validarVertice(posVerticeDestino);
        List<AdyacenteConPeso> adyacenciasDelOrigen = this.listasDeAdyacencias.get(posVerticeOrigen);
        AdyacenteConPeso destino = new AdyacenteConPeso(posVerticeDestino);
        /*return adyacenciasDelOrigen.contains(posVerticeDestino);*/
        return adyacenciasDelOrigen.contains(destino);
    }

    public void eliminarVertice(int posVerticeAEliminar) {
        validarVertice(posVerticeAEliminar);
        this.listasDeAdyacencias.remove(posVerticeAEliminar);
        for (List<AdyacenteConPeso> adyacentesDeUnVertice : this.listasDeAdyacencias) {
            AdyacenteConPeso adyacenteConPeso = new AdyacenteConPeso(posVerticeAEliminar);
            int posicionDeVerticeEnAdy = adyacentesDeUnVertice.indexOf(posVerticeAEliminar);
            if (posicionDeVerticeEnAdy >= 0) {
                adyacentesDeUnVertice.remove(posicionDeVerticeEnAdy);
            }
            for (int i = 0; i < adyacentesDeUnVertice.size(); i++) {
                AdyacenteConPeso posicionAdyacente = adyacentesDeUnVertice.get(i);
                if (posicionAdyacente.getIndiceVertice() > posVerticeAEliminar) {
                    posicionAdyacente.setIndiceVertice(posicionAdyacente.getIndiceVertice() - 1);
                    //adyacentesDeUnVertice.set(i,posicionAdyacente - 1);
                }
            }
        }
    }

    public int gradoDeVertice(int posDeVertice) {
        validarVertice(posDeVertice);
        List<AdyacenteConPeso> adyacenciasDelVertice = this.listasDeAdyacencias.get(posDeVertice);
        return adyacenciasDelVertice.size();
    }

    public Iterable<Integer> adyacentesDeVertice(int posDeVertice) {
        validarVertice(posDeVertice);
        List<AdyacenteConPeso> adyacenciasDelVertice = this.listasDeAdyacencias.get(posDeVertice);
        List<Integer> adyacentesDelVertice = new ArrayList<>();
        for (AdyacenteConPeso adyacente : adyacenciasDelVertice) {
            adyacentesDelVertice.add(adyacente.getIndiceVertice());
        }
        Iterable<Integer> it = adyacentesDelVertice;
        return it;
    }

    public void eliminarArista(int posVerticeOrigen, int posVerticeDestino) throws ExcepcionAristaNoExiste {
        validarVertice(posVerticeOrigen);
        validarVertice(posVerticeDestino);
        if (!this.existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            throw new ExcepcionAristaNoExiste();
        }
        for (int i = 0; i < listasDeAdyacencias.get(posVerticeOrigen).size(); i++) {
            if (listasDeAdyacencias.get(posVerticeOrigen).get(i).getIndiceVertice() == posVerticeDestino) {
                listasDeAdyacencias.get(posVerticeOrigen).remove(i);
            }
        }
        for (int i = 0; i < listasDeAdyacencias.get(posVerticeDestino).size(); i++) {
            if (listasDeAdyacencias.get(posVerticeDestino).get(i).getIndiceVertice() == posVerticeOrigen) {
                listasDeAdyacencias.get(posVerticeDestino).remove(i);
            }
        }
    }

    public void eliminarArista2(int posVerticeOrigen, int posVerticeDestino) throws ExcepcionAristaNoExiste {
        validarVertice(posVerticeOrigen);
        validarVertice(posVerticeDestino);
        if (!this.existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            throw new ExcepcionAristaNoExiste();
        }
        for (int i = 0; i < listasDeAdyacencias.get(posVerticeOrigen).size(); i++) {
            if (listasDeAdyacencias.get(posVerticeOrigen).get(i).getIndiceVertice() == posVerticeDestino) {
                listasDeAdyacencias.get(posVerticeOrigen).remove(i);
            }
        }

    }

    public List<List<AdyacenteConPeso>> listaDeAyacencia() {
        return this.listasDeAdyacencias;
    }

    @Override
    public String toString() {
        return listasDeAdyacencias.toString();
    }

    public List<List<AdyacenteConPeso>> copiaAristas() {
        List<List<AdyacenteConPeso>> listaAux = new ArrayList<>();
        for (int i = 0; i < listasDeAdyacencias.size(); i++) {
            listaAux.add(listasDeAdyacencias.get(i));
        }
        return listaAux;
    }

    public List<AdyacenteConPeso> listaDesordenada() {
        List<AdyacenteConPeso> listaAux = new ArrayList<>();
        for (int i = 0; i < listasDeAdyacencias.size(); i++) {
            for (int j = 0; j < listasDeAdyacencias.get(i).size(); j++) {
                listaAux.add(listasDeAdyacencias.get(i).get(j));
            }
        }
        return listaAux;
    }

    public List<AdyacenteConPeso> ordenarListasDeAdyacencia() {
        List<AdyacenteConPeso> listaResultado = new ArrayList<AdyacenteConPeso>();
        for (List<AdyacenteConPeso> listaDeAdyacencia : listasDeAdyacencias) {
            for (AdyacenteConPeso adyacenteConPeso : listaDeAdyacencia) {
                listaResultado.add(adyacenteConPeso);
            }
        }
        ordenarLista(listaResultado);
        return listaResultado;
    }

    private void ordenarLista(List<AdyacenteConPeso> lista) {
        for (int i = 0; i < lista.size() - 1; i++) {
            for (int j = i + 1; j < lista.size(); j++) {
                AdyacenteConPeso adyacenteConPesoA = lista.get(i);
                AdyacenteConPeso adyacenteConPesoB = lista.get(j);
                if (adyacenteConPesoB.getPeso() < adyacenteConPesoA.getPeso()) {
                    lista.set(i, adyacenteConPesoB);
                    lista.set(j, adyacenteConPesoA);
                }
            }
        }
    }

    public int cantidadDeIslas(GrafoPesado grafo) throws ExcepcionAristaNoExiste {
        int cantIslas = 1;
        bo.edu.uagrm.ficct.inf310sb.grafos.pesados.DFS dfs = new bo.edu.uagrm.ficct.inf310sb.grafos.pesados.DFS(grafo, 0);
        while (!dfs.controlMarcados.estaTodoMarcado()) {
            int noMarcado = dfs.controlMarcados.encontrarNoMarcado();
            if (noMarcado > 0) {
                dfs.continuarDFS(noMarcado);
                cantIslas++;
            }
        }

        return cantIslas;
    }

    public boolean existeCicloEnUnGrafo(GrafoPesado grafoPesado) throws ExcepcionAristaNoExiste {
        if (cantidadDeAristas() >= (cantidadDeVertices() - (cantidadDeIslas(grafoPesado) - 1))) {
            return true;
        }
        return false;
    }

    // algoritmo de kruskal

    /*    public GrafoPesado kruskal (GrafoPesado grafoPesado) throws ExcepcionNroVerticesInvalido, ExcepcionAristaYaExiste {
        GrafoPesado grafoAux = new GrafoPesado(grafoPesado.cantidadDeVertices());
        List<AdyacenteConPeso> listaOrdenada = new ArrayList<AdyacenteConPeso>();
        listaOrdenada = grafoPesado.ordenarListasDeAdyacencia();

        for (int i = 0; i < listaOrdenada.size(); i++) {
            int posDestino = listaOrdenada.get(i).getIndiceVertice();
            double peso = listaOrdenada.get(i).getPeso();
            if (!grafoAux.existeAdyacencia(i,posDestino)){
                grafoAux.insertarArista(i, posDestino, peso);
            }
        }
        return grafoAux;
    }*/
    private void cargarListaDeAristas(List<AdyacenteConPesoIndiceOrigen> lista) {
        int i = 0;
        for (List<AdyacenteConPeso> unaListaDeAdyacencia : this.listasDeAdyacencias) {
            for (AdyacenteConPeso unAdyacenteConPeso : unaListaDeAdyacencia) {
                AdyacenteConPesoIndiceOrigen adyacenteEnTurno = new AdyacenteConPesoIndiceOrigen(i,
                        unAdyacenteConPeso.getIndiceVertice(), unAdyacenteConPeso.getPeso());
                AdyacenteConPesoIndiceOrigen adyacenteParalelo = new AdyacenteConPesoIndiceOrigen(unAdyacenteConPeso.getIndiceVertice(),
                        i, unAdyacenteConPeso.getPeso());
                if (!lista.contains(adyacenteParalelo)) {
                    lista.add(adyacenteEnTurno);
                }
            }
            i++;
        }
        Collections.sort(lista);
    }

    private void cargarListaDeVertices(List<Integer> listaDeVertices) {
        for (Integer unVertice : listaDeVertices) {
            this.insertarVertice();
        }
    }

    public void caragarM1() {
        for (int i = 0; i < listasDeAdyacencias.size(); i++) {
            for (int j = 0; j < listasDeAdyacencias.get(i).size(); j++) {
                if (i == j) {
                    M1[i][j] = 0;
                } else {
                    boolean b = true;
                    int l = 0;
                    while (b == true && l < listasDeAdyacencias.get(i).size()) {
                        if (listasDeAdyacencias.get(i).get(l).getIndiceVertice() == j) {
                            b = false;
                            M1[i][j] = listasDeAdyacencias.get(i).get(j).getPeso();
                        }

                        l++;
                    }
                    if (b == true) {
                        M1[i][j] = 99999999;
                    }
                }
                M2[i][j] = j;
            }
        }
    }

    public void floyd1() {
        for (int k = 0; k < listasDeAdyacencias.size(); k++) {
            for (int i = 0; i < listasDeAdyacencias.get(i).size(); i++) {
                for (int j = 0; j < listasDeAdyacencias.size(); j++) {
                    double aux1 = M1[i][j];
                    double aux2 = M1[i][k];
                    double aux3 = M1[k][j];
                    double s = aux2 + aux3;
                    double res = Math.min(aux1, s);
                    if (aux1 != s) {
                        if (res == s) {
                            M1[i][j] = s;
                            M2[i][j] = k;
                        }
                    }
                }
            }
        }
    }

    ////------------OBCION  2 DISTANCIA--------
    public void caragarM3() {
        for (int i = 0; i < listasDeAdyacencias.size(); i++) {
            for (int j = 0; j < listasDeAdyacencias.get(i).size(); j++) {
                if (i == j) {
                    M3[i][j] = 0;
                } else {
                    boolean b = true;
                    int l = 0;
                    while (b == true && l < listasDeAdyacencias.get(i).size()) {
                        if (listasDeAdyacencias.get(i).get(l).getIndiceVertice() == j) {
                            b = false;
                            M3[i][j] = listasDeAdyacencias.get(i).get(j).getPeso();
                        }

                        l++;
                    }
                    if (b == true) {
                        M3[i][j] = 99999999;
                    }
                }
                M4[i][j] = j;
            }
        }
    }

    public void floyd3() {
        for (int k = 0; k < listasDeAdyacencias.size(); k++) {
            for (int i = 0; i < listasDeAdyacencias.get(i).size(); i++) {
                for (int j = 0; j < listasDeAdyacencias.size(); j++) {
                    double aux1 = M3[i][j];
                    double aux2 = M3[i][k];
                    double aux3 = M3[k][j];
                    double s = aux2 + aux3;
                    double res = Math.min(aux1, s);
                    if (aux1 != s) {
                        if (res == s) {
                            M3[i][j] = s;
                            M4[i][j] = k;
                        }
                    }
                }
            }
        }
    }

    //
    public int[] mostrarDRV(int x, int y) {
                System.err.println("Entrando A DRV");
        String s = "";
        int[] d = new int[MAXVERTEX + 1];
        if (M3[x][y] == 9999) {
            s = "no se puede llegar de: " + nombres.get(x) + " a " + nombres.get(y);
            return null;
        }
        s += "El recorido sera: " + nombres.get(x);
        double m = -1;
        int c = x;
        int n;
        d[0] = x;
        int posicion = 0;
        while (m != y) {
            m = M4[c][y];
            n = (int) m;
            c = n;
            s += "-->" + nombres.get(n);
            if (posicion < d.length) {
                d[posicion]=n;
                
            }posicion ++;
        }
        int[] d2 = new int[posicion];
        for (int i = 0; i < posicion; i++) {
            d2[i] = d[i];
        }
        return d2;
    }

    public int[] mostrarPRV(int x, int y) {
                System.err.println("Entrando A PRV");
        String s = "";
        int[] d = new int[MAXVERTEX + 1];
        if (M1[x][y] == 9999) {
            s = "no se puede llegar de: " + nombres.get(x) + " a " + nombres.get(y);
            return null;
        }
        s += "El recorido sera: " + nombres.get(x);
        double m = -1;
        int c = x;
        int n;
        d[0] = x;
        int posicion = 1;
        while (m != y) {
            m = M2[c][y];
            n = (int) m;
            c = n;
            s += "-->" + nombres.get(n);
            if (posicion < d.length) {
                d[posicion]=n;
                
            }posicion ++;
        }
        int[] d2 = new int[posicion];
        for (int i = 0; i < posicion; i++) {
            d2[i] = d[i];
        }
        return d2;
    }
    // algoritmo de FloydWarshall
    public List<Integer> caminoMasCortoFloydWarshall(int verticeOrigen,int  verticeDestino) {
        List<Integer> recorrido = new ArrayList<>();
        int dim = cantidadDeVertices();
        Double[][] matrizCostos = new Double[dim][dim];
        Integer[][] matrizPredecesores = new Integer[dim][dim];
        algoritmoFloydWarshall(matrizCostos, matrizPredecesores);
        cargarRecorridoConCamino(recorrido, verticeOrigen, verticeDestino, matrizPredecesores);
        return recorrido;
    }
    private void algoritmoFloydWarshall(Double[][] matrizCostos, Integer[][] matrizPredecesores) {
        cargarMatrizPredecesoraFloydWarshall(matrizPredecesores);
        cargarMatrizCostosFloydWarshall(matrizCostos);
        int dim = cantidadDeVertices();
        for (int t = 0; t < listasDeAdyacencias.size(); t++) {
            int indicePivote = t;
            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {
                    Double valor1 = matrizCostos[i][j];
                    Double valor2 = matrizCostos[i][indicePivote];
                    Double valor3 = matrizCostos[indicePivote][j];
                    Double valorSumado = valor2 + valor3;
                    if (valor1 > valorSumado) {
                        matrizCostos[i][j] = matrizCostos[i][indicePivote] + matrizCostos[indicePivote][j];
                        matrizPredecesores[i][j] = indicePivote;
                    }
                }
            }
        }
    }
    private void cargarMatrizPredecesoraFloydWarshall(Integer[][] matriz) {
        int dim = cantidadDeVertices();
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                matriz[i][j] = -1;
            }
        }
    }
    private void cargarMatrizCostosFloydWarshall(Double[][] matriz) {
        preCargarMatrizCostosFloydWarshall(matriz);
        for (int i = 0; i < listasDeAdyacencias.size(); i++) {
            int indiceVertice = i;
            for (AdyacenteConPeso unAdyacente : listasDeAdyacencias.get(indiceVertice)) {
                matriz[indiceVertice][unAdyacente.getIndiceVertice()] = unAdyacente.getPeso();
            }
        }

    }
    private void preCargarMatrizCostosFloydWarshall(Double[][] matriz) {
        int dim = cantidadDeVertices();
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (i == j) {
                    matriz[i][j] = 0.0;
                } else {
                    matriz[i][j] = Double.POSITIVE_INFINITY;
                }
            }

        }
    }
    private void cargarRecorridoConCamino(List<Integer> recorrido, int verticeOrigen, int verticeDestino, Integer[][] matrizPredecesores) {
        recorrido.add(verticeOrigen);
        cargarRecorridoConCaminoRecursivo(recorrido, verticeOrigen, verticeDestino, matrizPredecesores);
        recorrido.add(verticeDestino);
    }
    private void cargarRecorridoConCaminoRecursivo(List<Integer> recorrido, int verticeOrigen, int verticeDestino, Integer[][] matrizPredecesores) {
        int indiceVerticeOrigen = verticeOrigen;
        int indiceVerticeDestino = verticeDestino;
        if (matrizPredecesores[indiceVerticeOrigen][indiceVerticeDestino] != -1) {
            int verticeIntermedio = matrizPredecesores[indiceVerticeOrigen][indiceVerticeDestino];
            cargarRecorridoConCaminoRecursivo(recorrido, verticeOrigen, verticeIntermedio, matrizPredecesores);
            recorrido.add(verticeIntermedio);
            cargarRecorridoConCaminoRecursivo(recorrido, verticeIntermedio, verticeDestino, matrizPredecesores);
        }
    }
    
    public double getPeso(int posicionVerticeU, int verticeV) {
        int verticeAdyacente;
        for (int i = 0; i < listasDeAdyacencias.get(posicionVerticeU).size(); i++) {
            verticeAdyacente = listasDeAdyacencias.get(posicionVerticeU).get(i).getIndiceVertice();
            if (verticeAdyacente == verticeV){
                return listasDeAdyacencias.get(posicionVerticeU).get(i).getPeso();
            }
        }
        return -1;
    }
    // verifica si existe un camino de un vertice origen a un vertice destino
    public boolean existeCamino(int verticeOrigen, int verticeDestino) throws ExcepcionAristaNoExiste {
        DFS dfs = new DFS(this, verticeOrigen);
        List<Boolean> listaDeMarcados = new ArrayList<>();
        listaDeMarcados = dfs.controlMarcados.listaMarcados();
        
        if (listaDeMarcados.get(verticeDestino) == Boolean.TRUE) {
            return true;
        }
        return false;
    }
}
