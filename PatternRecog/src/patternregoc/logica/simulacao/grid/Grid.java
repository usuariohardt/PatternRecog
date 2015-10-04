
package patternregoc.logica.simulacao.grid;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Daniel
 */
public class Grid extends JPanel {
    
    private Celula[][] celulas;
    // Valores padrão
    private int BLOCO = 20;
    private int LINHAS = 30;
    private int COLS = 30;
    private boolean STATUS_CELULA = false;
    private boolean EDITABLE = false;
    
    //--------------------------------------------------------------------------
    public Grid() {
        this.addMouseListener(ml);
        this.addMouseMotionListener(mm);
        this.setSize(400, 500);
    }
    //--------------------------------------------------------------------------
    public Grid(int w, int h) {
        this.addMouseListener(ml);
        this.addMouseMotionListener(mm);
        this.setSize(w, h);
    }
    //--------------------------------------------------------------------------
    private ComponentListener cl = new ComponentAdapter() {
        @Override
        public void componentResized(ComponentEvent e) {
            celulas = null;
            repaint();
        }
    };
    //--------------------------------------------------------------------------
    public void setTamanhoBloco(int tamanho){
        this.BLOCO = tamanho;
    }
    //--------------------------------------------------------------------------
    public void setLinhas(int linhas){
        this.LINHAS = linhas;
    }
    //--------------------------------------------------------------------------
    public void setColunas(int colunas){
        this.COLS = colunas;
    }
    //--------------------------------------------------------------------------
    public boolean isEditable(){
        return EDITABLE;
    }
    //--------------------------------------------------------------------------
    public void setEditable(boolean status){
        this.EDITABLE = status;
    }
    //--------------------------------------------------------------------------
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if(celulas == null) {
            instanciarCelulas();
        }
        // Desenha células
        g2.setPaint(Color.blue);
        for(int i = 0; i < LINHAS; i++) {
            for(int j = 0; j < COLS; j++) {
                celulas[i][j].draw(g2);
            }
        }
    }
    //--------------------------------------------------------------------------
    private void instanciarCelulas() {
        celulas = new Celula[LINHAS][COLS];
        int w = getWidth();
        int h = getHeight();
        double xInc = (double)(w - 2*BLOCO)/COLS;
        double yInc = (double)(h - 2*BLOCO)/LINHAS;
        for(int i = 0; i < LINHAS; i++) {
            double y = BLOCO + i*yInc;
            for(int j = 0; j < COLS; j++) {
                double x = BLOCO + j*xInc;
                Rectangle2D.Double r = new Rectangle2D.Double(x, y, xInc, yInc);
                celulas[i][j] = new Celula(r);
            }
        }
    }
    //--------------------------------------------------------------------------
    private MouseListener ml = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if(EDITABLE){
                Point p = e.getPoint();
                if(!isInGrid(p))
                    return;
                double xInc = (double)(getWidth() - 2*BLOCO)/COLS;
                double yInc = (double)(getHeight() - 2*BLOCO)/LINHAS;
                int row = (int)((p.y - BLOCO)/yInc);
                int col = (int)((p.x - BLOCO)/xInc);
                boolean isSelected = celulas[row][col].isSelected();
                celulas[row][col].setSelected(!isSelected);
                repaint();
            }
        }
        @Override
        public void mousePressed(MouseEvent e) {
            if(EDITABLE){
                Point p = e.getPoint();
                if(!isInGrid(p))
                    return;
                double xInc = (double)(getWidth() - 2*BLOCO)/COLS;
                double yInc = (double)(getHeight() - 2*BLOCO)/LINHAS;
                int row = (int)((p.y - BLOCO)/yInc);
                int col = (int)((p.x - BLOCO)/xInc);
                STATUS_CELULA = !celulas[row][col].isSelected();
            }
        }
    };
    //--------------------------------------------------------------------------
    @SuppressWarnings("FieldMayBeFinal")
    private MouseMotionAdapter mm = new MouseMotionAdapter() {
        @Override
        public void mouseDragged(MouseEvent e) {
            if(EDITABLE){
                Point p = e.getPoint();
                if(!isInGrid(p))
                    return;
                double xInc = (double)(getWidth() - 2*BLOCO)/COLS;
                double yInc = (double)(getHeight() - 2*BLOCO)/LINHAS;
                int row = (int)((p.y - BLOCO)/yInc);
                int col = (int)((p.x - BLOCO)/xInc);
                celulas[row][col].setSelected(STATUS_CELULA);
                repaint();
            }
        }
    };
    //--------------------------------------------------------------------------
    public void limpar(){
        for(int l = 0; l < LINHAS; l++)
            for(int c = 0; c < COLS; c++)
                celulas[l][c].setSelected(false);
        repaint();
    }
    //--------------------------------------------------------------------------
    private boolean isInGrid(Point p) {
        Rectangle r = getBounds();
        r.grow(-BLOCO, -BLOCO);
        return r.contains(p);
    }
    //--------------------------------------------------------------------------
    public ComponentListener getComponentListener(){
        return cl;
    }
    //--------------------------------------------------------------------------
    public int[][] getMatrizCelulas(){
        int matriz[][] = new int[LINHAS][COLS];
        for(int l = 0; l < LINHAS; l++){
            for(int c = 0; c < COLS; c++){
                if(celulas[l][c].isSelected())
                    matriz[l][c] = 1;
                else
                    matriz[l][c] = 0;
            }
        }
        return matriz;
    }
    //--------------------------------------------------------------------------
    public ArrayList<String> getMatrizLinhas(){
        ArrayList<String> linhas = new ArrayList<>();
        for(int l = 0; l < LINHAS; l++){
            String linha = "";
            for(int c = 0; c < COLS; c++){
                if(celulas[l][c].isSelected())
                    linha += "1";
                else
                    linha += "0";
            }
            linhas.add(linha);
        }
        return linhas;
    }
    //--------------------------------------------------------------------------
    public void setGrid(ArrayList<String> padrao){
        for(int l = 0; l < padrao.size(); l++){
            @SuppressWarnings("MismatchedReadAndWriteOfArray")
            char[] cols = padrao.get(l).toCharArray();
            for(int c = 0; c < cols.length; c++){
                celulas[l][c].setSelected(cols[c] == '1');
            }
        }
        repaint();
    }
    //--------------------------------------------------------------------------
}
