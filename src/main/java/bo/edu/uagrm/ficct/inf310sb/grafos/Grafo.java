package bo.edu.uagrm.ficct.inf310sb.grafos;

public class Grafo {

    private static final int MAXVERTEX = 49;
    public Lista V[];
    public String Nombre[];
    private int n;

    //matrices para recorrido mas corto
    private double M1[][];
    private double M2[][];
    private double M3[][];
    private double M4[][];
    
    //vectores para guardar la poscion al momento de graficar
    public int puntosX[];
    public int puntosY[];

    public Grafo() {
        V = new Lista[MAXVERTEX + 1];
        n = -1;
        Nombre = new String[MAXVERTEX + 1];     // inicializa el vector de nombres 
        marca = new boolean[MAXVERTEX + 1];    //Iniciar la ED para el marcado de los vértices.
        
        M1 = new double[MAXVERTEX + 1][MAXVERTEX + 1];       //Iniciar las matrces de adyacencia
        M2 = new double[MAXVERTEX + 1][MAXVERTEX + 1];
        M3 = new double[MAXVERTEX + 1][MAXVERTEX + 1];
        M4 = new double[MAXVERTEX + 1][MAXVERTEX + 1];
        
        puntosX = new int[MAXVERTEX+1];              // iniciar los vectores 
        puntosY = new int[MAXVERTEX+1];
    }

    public void addVertice(String nombre,int nuevaPosX,int nuevaPosY) {
        if (n == MAXVERTEX) {
            System.err.println("Grafo.addVertice: Demasiados vértices (solo se permiten " + (MAXVERTEX + 1) + ")");
            return;
        }
        n++;
        V[n] = new Lista();     //Crear un nuevo vértice sin adyacentes (o sea con su Lista de adyacencia vacía)
        Nombre[n] = nombre;
        
        puntosX[n] = nuevaPosX;
        puntosY[n] = nuevaPosY;
    }

    public int cantVertices() {
        return n + 1;
    }

    public boolean isVerticeValido(int v) {
        return (0 <= v && v <= n);
    }

    public void addArista(int u, double precio, double distancia, int v) {  //Crea la arista u-->v
        if (precio <= 0 || distancia <= 0) {
            System.err.println("Grafo.addArista:  El precio o distancia debe ser mayor que cero");
            return;
        }

        String metodo = "addArista";
        if (!isVerticeValido(u, metodo) || !isVerticeValido(v, metodo)) {
            return;     //No existe el vertice u o el vertice v.
        }
        V[u].add(v, precio, distancia);      //Adicionar (data, precio,distancia) a la lista V[u]    
    }
    public boolean existearista(int u, int v){
        for (int i=0;i<V[u].length();i++){
            if(V[u].get(i)==v)
                return true;
        }
        return false;
    }
    private boolean isVerticeValido(int v, String metodo) {
        boolean b = isVerticeValido(v);
        if (!b) {
            System.err.println("Grafo." + metodo + ": " + Nombre[v] + " no es un vértice del Grafo ");
        }

        return b;
    }
    //*****************************************************************//

    @Override
    public String toString() {
        if (cantVertices() == 0) {
            return "(Grafo Vacío)";
        }
        final String SEPARADOR = ", ";

        //MOSTRAR ARSTAS u-->v del grafo como (u, precio, distancia, v)
        desmarcarTodos();
        String S = "[";
        String coma = "";
        for (int i = 0; i <= n; i++) {
            for (int k = 0; k <= n; k++) {
                double peso = costo(i, k);
                double peso2 = costo2(i, k);
                if (peso > 0) {
                    String arista = "(" + Nombre[i] + "," + peso + "," + peso2 + "," + Nombre[k] + ")";
                    S += coma + arista;
                    coma = SEPARADOR;
                    marcar(i);
                }
            }

            if (!isMarcado(i)) {     //El vertice i no tiene aristas
                S += coma + Nombre[i];
                coma = SEPARADOR;
            }
        }

        return S + "]";
    }

    public double costo(int u, int v) {  //Devuelve el precio de la arista u-->v.  Si esa arista no existe, devuelve 0
        if (!isVerticeValido(u) || !isVerticeValido(v)) {
            return 0;
        }

        return V[u].getPrecio(v);
    }

    public double costo2(int u, int v) {  //Devuelve la distancia de la arista u-->v.  Si esa arista no existe, devuelve 0
        if (!isVerticeValido(u) || !isVerticeValido(v)) {
            return 0;
        }

        return V[u].getDistancia(v);
    }

////********* PARA MARCAR VERTICES*******************//////
    private boolean marca[];

    private void desmarcarTodos() {
        for (int i = 0; i <= n; i++) {
            marca[i] = false;
        }
    }

    private void marcar(int u) {
        if (isVerticeValido(u)) {
            marca[u] = true;
        }
    }

    private void desmarcar(int u) {
        if (isVerticeValido(u)) {
            marca[u] = false;
        }
    }

    private boolean isMarcado(int u) {   //Devuelve true sii el vertice u está marcado.
        return marca[u];
    }

    ///------------MATRICES------------------------
    ////------------OBCION  1 PRECIOS--------
    public void caragarM1() {

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == j) {
                    M1[i][j] = 0;
                } else {
                    boolean b = true;
                    int l = 0;
                    while (b == true && l < V[i].length()) {
                        if (V[i].get(l) == j) {
                            b = false;
                            M1[i][j] = V[i].getPrecio(j);
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
        for (int k = 0; k <= n; k++) {
            for (int i = 0; i <= n; i++) {
                for (int j = 0; j <= n; j++) {
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

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == j) {
                    M3[i][j] = 0;
                } else {
                    boolean b = true;
                    int l = 0;
                    while (b == true && l < V[i].length()) {
                        if (V[i].get(l) == j) {
                            b = false;
                            M3[i][j] = V[i].getDistancia(j);
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
        for (int k = 0; k <= n; k++) {
            for (int i = 0; i <= n; i++) {
                for (int j = 0; j <= n; j++) {
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
    
    ///---------MOSTRAR MATRICES-------
    public void mostrar() { //String mostrar(){
        String s;
        for (int i = 0; i <= n; i++) {
            s = "";
            for (int j = 0; j <= n; j++) {
                double m;
                m = M1[i][j];
                s = s + (doubleToStr(m)) + ",";

            }
            System.out.println(s);
        }
        //return s;
    }

    public void mostrar2() { //String mostrar(){
        String s;
        for (int i = 0; i <= n; i++) {
            s = "";
            for (int j = 0; j <= n; j++) {
                double m;
                m = M2[i][j];
                s = s + (doubleToStr(m)) + ",";

            }
            System.out.println(s);
        }
        //return s;
    }

    //---------------MOSTRAR PRECIOS------------------
    public String mostrarP(int x, int y) {
        String s = "";
        if (M1[x][y] == 9999) {
            s = "no se puede llegar de: " + Nombre[x] + " a " + Nombre[y];
            return s;
        }
        s += "El precio mas barato es: " + M1[x][y]+"bs";
        return s;
    }

    public String mostrarPR(int x, int y) {
        String s = "";
        if (M1[x][y] == 9999) {
            s = "no se puede llegar de: " + Nombre[x] + " a " + Nombre[y];
            return s;
        }
        s += "El recorido sera: " + Nombre[x];
        double m = -1;
        int c = x;
        int n;
        while (m != y) {
            m = M2[c][y];
            n = (int) m;
            c = n;
            s += "-->" + Nombre[n];
        }
        return s;
    }
    
     public int[] mostrarPRV(int x, int y) {
        System.err.println("Entrando A PRV");
        String s = "";
        int[]d=new int[MAXVERTEX+1];
        if (M1[x][y] == 9999) {
            s = "no se puede llegar de: " + Nombre[x] + " a " + Nombre[y];
            return null;
        }
        s += "El recorido sera: " + Nombre[x];
        double m = -1;
        int c = x;
        int n;
        d[0]=x;
        int posicion = 1;
        while (m != y) {
            m = M2[c][y];
            n = (int) m;
            c = n;
            s += "-->" + Nombre[n];
            if (posicion < d.length) {
                d[posicion]=n;
                
            }posicion ++;
        }
        int[]d2=new int[posicion];
        for(int i=0;i<posicion;i++){
            d2[i]=d[i];
        }
        return d2;
    }
     

    //---------------MOSTRAR DISTANCIA------------------
    public String mostrarD(int x, int y) {
        String s = "";
        if (M3[x][y] == 9999) {
            s = "no se puede llegar de: " + Nombre[x] + " a " + Nombre[y];
            return s;
        }
        s += "El recorrido mas corto es: " + M3[x][y]+"km";
        return s;
    }

    public String mostrarDR(int x, int y) {
        String s = "";
        if (M3[x][y] == 9999) {
            s = "no se puede llegar de: " + Nombre[x] + " a " + Nombre[y];
            return s;
        }
        s += "El recorido sera: " + Nombre[x];
        double m = -1;
        int c = x;
        int n;
        while (m != y) {
            m = M4[c][y];
            n = (int) m;
            c = n;
            s += "-->" + Nombre[n];
        }
        return s;
    }
    
    public int[] mostrarDRV(int x, int y) {
        String s = "";
        int[]d=new int[MAXVERTEX+1];
        if (M3[x][y] == 9999) {
            s = "no se puede llegar de: " + Nombre[x] + " a " + Nombre[y];
            return null;
        }
        s += "El recorido sera: " + Nombre[x];
        double m = -1;
        int c = x;
        int n;
        d[0]=x;
        int posicion=1;
        while (m != y) {
            m = M4[c][y];
            n = (int) m;
            c = n;
            s += "-->" + Nombre[n];
            d[posicion]=n;
            posicion ++;
        }
        int[]d2=new int[posicion];
        for(int i=0;i<posicion;i++){
            d2[i]=d[i];
        }
        return d2;
    }
    
    ///------------MOSTRAR LISTAS-----

    public void printListas() {  //Muestra las listas del Grafo.  Util para el programador de esta class
        if (cantVertices() == 0) {
            System.out.println("(Grafo Vacío)");
        } else {
            for (int i = 0; i <= n; i++) {
                System.out.println("V[" + i + "]-->" + V[i]);
            }
        }
    }

    private String doubleToStr(double d) { //Devuelve d sin el pto decimal innecesario.
        String s = "" + d;
        int posPto = s.indexOf('.');
        for (int i = posPto + 1; i < s.length(); i++) {  //Ver si después del '.' todos son ceros.
            if (s.charAt(i) != '0') {
                return s;
            }
        }

        return s.substring(0, posPto);
    }
    //--------FUNCION PARA MOSTAR------- 
    public int getNumero(String ciudadSeleccionada) {
        for (int i = 0; i <= n; i++) {
            if (Nombre[i].equals(ciudadSeleccionada)) {
                return i;
            }
        }
        return -1;
    }
}
