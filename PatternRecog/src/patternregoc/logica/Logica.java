
package patternregoc.logica;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import patternregoc.logica.ann.kohonen.MapaKohonen;
import patternregoc.logica.ann.kohonen.NormalizarEntrada;
import patternregoc.logica.ann.kohonen.TreinoMapaKohonen;
import patternregoc.logica.ann.kohonen.TreinoMapaKohonen.MetodoAprendizado;
import patternregoc.logica.simulacao.FrSimulacao;

/**
 *
 * @author Daniel
 */
public class Logica {
    
    private MapaKohonen rede;
    public static final int ALTURA = 20;
    public static final int LARGURA = 13;
    private static final double ERRO_MAXIMO = 0.01;
    private String tipoEscolhido;
    private ArrayList<ObjetoPadrao> listaTreinada;
    
    public static void main(String[] args) {
        chamaSimulacao();
    }
    //--------------------------------------------------------------------------
    private static void chamaSimulacao(){
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FrSimulacao().setVisible(true);
            }
        });
    }
    //--------------------------------------------------------------------------
    public MapaKohonen getRede() {
        return rede;
    }
    //--------------------------------------------------------------------------
    public void setRede(MapaKohonen rede) {
        this.rede = rede;
    }
    //--------------------------------------------------------------------------
    public String getTipoEscolhido() {
        return tipoEscolhido;
    }
    //--------------------------------------------------------------------------
    public void setTipoEscolhido(String tipoEscolhido) {
        this.tipoEscolhido = tipoEscolhido;
    }
    //--------------------------------------------------------------------------
    public void treinar(ArrayList<ObjetoPadrao> lista, 
            NormalizarEntrada.TipoNormalizacao tipoNormalizacao, 
            MetodoAprendizado metodoAprendizado){
        try {
            int numNeuroniosEntrada = Logica.ALTURA * Logica.LARGURA;
            int numNeuroniosSaida = lista.size();
            
            double conjunto[][] = new double[numNeuroniosSaida][numNeuroniosEntrada];
            
            for (int t = 0; t < numNeuroniosSaida; t++) {
                int idx = 0;
                ObjetoPadrao obj = lista.get(t);
                
                for (int y = 0; y < obj.getAltura(); y++) {
                    for (int x = 0; x < obj.getLargura(); x++)
                        conjunto[t][idx++] = obj.getDados(x, y) ? .5 : -.5;
                }
            }
            this.rede = new MapaKohonen(numNeuroniosEntrada, numNeuroniosSaida, tipoNormalizacao);
            TreinoMapaKohonen treino = new TreinoMapaKohonen(this.rede, conjunto, metodoAprendizado, 0.5);
            
            this.listaTreinada = lista;
            
            do {
                treino.iteracao();
            } while (treino.getErroTotal() > Logica.ERRO_MAXIMO);
        } catch (Exception ex) {
            Logger.getLogger(Logica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //--------------------------------------------------------------------------
    public char reconhecer(ObjetoPadrao obj){
        if (this.rede != null) {
            double entrada[] = new double[Logica.ALTURA * Logica.LARGURA];
            int idx = 0;
            
            for (int y = 0; y < obj.getAltura(); y++) {
                for (int x = 0; x < obj.getLargura(); x++) {
                    entrada[idx++] = obj.getDados(x, y) ? .5 : -.5;
                }
            }
            
            int melhor = this.rede.vencedor(entrada);
            char mapa[] = mapaNeuronios();
            return mapa[melhor];
        } else
            return '?';
    }
    //--------------------------------------------------------------------------
    private char[] mapaNeuronios() {
        char mapa[] = new char[listaTreinada.size()];
        
        for (int i = 0; i < mapa.length; i++)
            mapa[i] = '?';
        
        for (ObjetoPadrao obj : listaTreinada) {
            double entrada[] = new double[Logica.ALTURA * Logica.LARGURA];
            int idx = 0;
            for (int y = 0; y < obj.getAltura(); y++) {
                for (int x = 0; x < obj.getLargura(); x++)
                    entrada[idx++] = obj.getDados(x, y) ? .5 : -.5;
            }
            int melhor = this.rede.vencedor(entrada);
            mapa[melhor] = obj.getSimbolo().toCharArray()[0];
        }
        return mapa;
    }
    //--------------------------------------------------------------------------
}
