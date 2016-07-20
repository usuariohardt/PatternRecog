
package patternregoc.logica.simulacao.grid;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Daniel
 */
public class Celula {
    
    Rectangle2D.Double rect;
    Color color = Color.BLACK;
    Color backColor = Color.WHITE;
    Color selColor = Color.BLUE;
    private boolean selected = false;
    //--------------------------------------------------------------------------
    public Celula(Rectangle2D.Double rect) {
        this.rect = rect;
    }
    //--------------------------------------------------------------------------
    public void draw(Graphics2D g2) {
        g2.setPaint(selected ? selColor : backColor);
        g2.fill(rect);
        g2.setPaint(color);
        g2.draw(rect);
    }
    //--------------------------------------------------------------------------
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    //--------------------------------------------------------------------------
    public boolean isSelected() { 
        return selected; 
    }
    //--------------------------------------------------------------------------
}