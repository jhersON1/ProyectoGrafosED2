package bo.edu.uagrm.ficct.inf310sb.grafos.excepciones;

public class ExcepcionNroVerticesInvalido extends Exception{

    public ExcepcionNroVerticesInvalido() {
        super("Nro de vertices invalido");
    }

    public ExcepcionNroVerticesInvalido(String message) {
        super(message);
    }
}
