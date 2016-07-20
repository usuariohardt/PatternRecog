
package patternregoc.logica.ann.kohonen;

import patternregoc.logica.ann.kohonen.matrix.Matriz;
import patternregoc.logica.ann.kohonen.matrix.MatrizCalculo;

/**
 *
 * @author Daniel
 */
public class MapaKohonen {
    
    public static final double VALOR_MINIMO = 1.E-30;
    
    Matriz pesosSaida;
    protected double saida[];
    protected int contadorNeuroniosEntrada;
    protected int contadorNeuroniosSaida;
    protected NormalizarEntrada.TipoNormalizacao tipoNormalizacao;
    
    public MapaKohonen(int contagemEntrada, int contagemSaida, NormalizarEntrada.TipoNormalizacao tipoNormalizacao) {
        this.contadorNeuroniosEntrada = contagemEntrada;
        this.contadorNeuroniosSaida = contagemSaida;
        this.pesosSaida = new Matriz(this.contadorNeuroniosSaida, this.contadorNeuroniosEntrada + 1);
        this.saida = new double[this.contadorNeuroniosSaida];
        this.tipoNormalizacao = tipoNormalizacao;
    }
    //--------------------------------------------------------------------------
    public int getContadorNeuroniosEntrada() {
        return this.contadorNeuroniosEntrada;
    }
    //--------------------------------------------------------------------------
    public NormalizarEntrada.TipoNormalizacao getTipoNormalizacao() {
        return this.tipoNormalizacao;
    }
    //--------------------------------------------------------------------------
    public double[] getSaida() {
        return this.saida;
    }
    //--------------------------------------------------------------------------
    public int getContadorNeuroniosSaida() {
        return this.contadorNeuroniosSaida;
    }
    //--------------------------------------------------------------------------}
    public Matriz getPesosSaida() {
        return this.pesosSaida;
    }
    //--------------------------------------------------------------------------
    public void setPesosSaida(Matriz pesosSaida) {
        this.pesosSaida = pesosSaida;
    }
    //--------------------------------------------------------------------------
    public int vencedor(double entrada[]) {
        final NormalizarEntrada normalizedInput = new NormalizarEntrada(entrada, this.tipoNormalizacao);
        return vencedor(normalizedInput);
    }
    //--------------------------------------------------------------------------
    public int vencedor(NormalizarEntrada normEntrada) {
        int venc = 0;
        double maior = Double.MIN_VALUE;
        for (int i = 0; i < this.contadorNeuroniosSaida; i++) {
            Matriz matrizLinha = this.pesosSaida.getLinha(i);
            this.saida[i] = MatrizCalculo.produtoEscalar(normEntrada.getMatrizEntrada(), matrizLinha) * normEntrada.getFatorNormalizacao();
            this.saida[i] = (this.saida[i]+1.0)/2.0;
            
            if (this.saida[i] > maior) {
                maior = this.saida[i];
                venc = i;
            }
            
            if( this.saida[i] < 0 )
                this.saida[i] = 0;
            
            if( this.saida[i] > 1 )
                this.saida[i] = 1;
        }
        
        return venc;
    }
    //--------------------------------------------------------------------------
}
