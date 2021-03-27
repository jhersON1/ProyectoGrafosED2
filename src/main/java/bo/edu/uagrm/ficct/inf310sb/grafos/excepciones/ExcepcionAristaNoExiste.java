package bo.edu.uagrm.ficct.inf310sb.grafos.excepciones;

public class ExcepcionAristaNoExiste extends Exception{

    public ExcepcionAristaNoExiste() {
        super("Arista no existe");
    }

    public ExcepcionAristaNoExiste(String message) {
        super(message);
    }
}
