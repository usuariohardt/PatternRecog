
package patternregoc.logica.simulacao.grid;

import java.util.ArrayList;
import patternregoc.logica.Logica;

/**
 *
 * @author Daniel
 */
public class PanelGrid extends javax.swing.JPanel {
    
    private Grid grid;
    
    public PanelGrid() {
        initComponents();
        initGrid();
    }
    //--------------------------------------------------------------------------
    private void initGrid(){
        this.grid = new Grid(220, 300);
        this.grid.setTamanhoBloco(20);
        this.grid.setLinhas(Logica.ALTURA);
        this.grid.setColunas(Logica.LARGURA);
        this.add(grid);
        this.grid.addComponentListener(this.grid.getComponentListener());
        this.setSize(220, 300);
    }
    //--------------------------------------------------------------------------
    public void setGrid(ArrayList<String> padrao){
        this.grid.setGrid(padrao);
    }
    //--------------------------------------------------------------------------
    public Grid getGrid() {
        return this.grid;
    }
    //--------------------------------------------------------------------------
    public void limparGrid(){
        this.grid.limpar();
    }
    //--------------------------------------------------------------------------
    public void isEditable(){
        this.grid.isEditable();
    }
    //--------------------------------------------------------------------------
    public void setEditable(boolean status){
        this.grid.setEditable(status);
    }
    //--------------------------------------------------------------------------
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 179, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 199, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
}
