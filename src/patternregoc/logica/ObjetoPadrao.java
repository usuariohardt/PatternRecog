
package patternregoc.logica;

import java.util.ArrayList;

/**
 *
 * @author Daniel
 */
public class ObjetoPadrao implements Comparable <ObjetoPadrao> {
    
    private int id;
    private String simbolo;
    private int tipo;
    private boolean grid[][];
    private ArrayList<String> padrao;

    public ObjetoPadrao(){
        this.grid = new boolean[Logica.LARGURA][Logica.ALTURA];
    }
    //--------------------------------------------------------------------------
    public int getId() {
        return id;
    }
    //--------------------------------------------------------------------------
    public void setId(int id) {
        this.id = id;
    }
    //--------------------------------------------------------------------------
    public String getSimbolo() {
        return simbolo;
    }
    //--------------------------------------------------------------------------
    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }
    //--------------------------------------------------------------------------
    public int getTipo() {
        return tipo;
    }
    //--------------------------------------------------------------------------
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    //--------------------------------------------------------------------------
    public ArrayList<String> getPadrao() {
        return padrao;
    }
    //--------------------------------------------------------------------------
    public void setPadrao(ArrayList<String> padrao) {
        this.padrao = padrao;
        
        for(int i = 0; i < padrao.size(); i++){
            char bits[] = padrao.get(i).toCharArray();
            for(int b = 0; b < bits.length; b++){
                this.grid[b][i] = bits[b] == '1';
            }
        }
    }
    //--------------------------------------------------------------------------
    public boolean[][] getGrid() {
        return grid;
    }
    //--------------------------------------------------------------------------
    public void setGrid(boolean[][] grid) {
        this.grid = grid;
    }
    //--------------------------------------------------------------------------
    public boolean getDados(int x, int y) {
        return this.grid[x][y];
    }
    //--------------------------------------------------------------------------
    public void setDados(int x, int y, boolean v) {
        this.grid[x][y] = v;
    }
    //--------------------------------------------------------------------------
    public int getAltura() {
        return this.grid[0].length;
    }
    //--------------------------------------------------------------------------
    public int getLargura() {
        return this.grid.length;
    }
    //--------------------------------------------------------------------------
    @Override
    public String toString() {
        return "" + this.simbolo;
    }
    //--------------------------------------------------------------------------
    @Override
    public int compareTo(ObjetoPadrao obj) {
        if (this.getSimbolo().equals(obj.getSimbolo()))
            return 1;
        else
            return -1;
    }
    //--------------------------------------------------------------------------
}
