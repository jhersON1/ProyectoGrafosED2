package bo.edu.uagrm.ficct.inf310sb.grafos.nopesados;

import java.util.ArrayList;
import java.util.List;

public class UtilsRecorridos {
    private List<Boolean> marcados;
    private int nroVertices;
    public UtilsRecorridos(int nroVertices){
        this.nroVertices = nroVertices;
    }
    public void desmarcarTodo(){
        marcados = new ArrayList<>();

        for (int i = 0; i < this.nroVertices; i++){
            marcados.add(Boolean.FALSE);
        }
    }

    public void marcarVertice(int posVertice) {
        marcados.set(posVertice, Boolean.TRUE);
    }
    public void desmarcarVertice(int posVertice) {
        marcados.set(posVertice, Boolean.FALSE);
    }

    public boolean estaMarcado(int posVertice) {
        return marcados.get(posVertice);
    }

    public boolean estaTodoMarcado(){
        for (int i = 0; i < marcados.size(); i++){
            if (marcados.get(i)==Boolean.FALSE){
                return false;
            }
        }
        return true;
    }

    public int encontrarNoMarcado(){
        for (int i = 0; i < marcados.size(); i++ ){
            if (marcados.get(i)==false){
                return i;
            }
        }
        return -1;
    }
    public List<Boolean> listaMarcados(){
        List<Boolean> listaDeMarcados = new ArrayList<>();
        for (int i = 0; i < this.marcados.size(); i++){
           listaDeMarcados.add(marcados.get(i));
        }
        return listaDeMarcados;
    }
}
