
package patternregoc.logica.ann.kohonen;

import patternregoc.logica.ann.kohonen.matrix.Matriz;
import patternregoc.logica.ann.kohonen.matrix.MatrizCalculo;

/**
 *
 * @author Daniel
 */
public class NormalizarEntrada {
    
    public enum TipoNormalizacao { MULTIPLICACAO, EIXO_Z }
    
    private final TipoNormalizacao tipo;
    
    private double fatorNormalizacao;
    private double sintetico;
    
    protected Matriz matrizEntrada;
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public NormalizarEntrada(double entrada[], TipoNormalizacao tipo) {
        this.tipo = tipo;
        calcularFatores(entrada);
        this.matrizEntrada = this.criarMatrizEntrada(entrada, this.sintetico);
    }
    //--------------------------------------------------------------------------
    private Matriz criarMatrizEntrada(double padrao[], double extra) {
        Matriz resultado = new Matriz(1, padrao.length + 1);
        
        for (int i = 0; i < padrao.length; i++)
            resultado.set(0, i, padrao[i]);
        
        resultado.set(0, padrao.length, extra);
        return resultado;
    }
    //--------------------------------------------------------------------------
    public Matriz getMatrizEntrada() {
        return this.matrizEntrada;
    }
    //--------------------------------------------------------------------------
    public double getFatorNormalizacao() {
        return this.fatorNormalizacao;
    }
    //--------------------------------------------------------------------------
    public double getSintetico() {
        return this.sintetico;
    }
    //--------------------------------------------------------------------------
    @SuppressWarnings("LocalVariableHidesMemberVariable")
    protected void calcularFatores(double entrada[]) {
        Matriz matrizEntrada = Matriz.criarMatrizColuna(entrada);
        double tamanho = MatrizCalculo.tamanhoVetor(matrizEntrada);
        tamanho = Math.max(tamanho, MapaKohonen.VALOR_MINIMO);
        
        int numEntradas = entrada.length;
        
        if (this.tipo == TipoNormalizacao.MULTIPLICACAO) {
            this.fatorNormalizacao = 1.0 / tamanho;
            this.sintetico = 0.0;
        } else {
            this.fatorNormalizacao = 1.0 / Math.sqrt(numEntradas);
            final double d = numEntradas - Math.pow(tamanho, 2);
            if (d > 0.0)
                this.sintetico = Math.sqrt(d) * this.fatorNormalizacao;
            else
                this.sintetico = 0;
        }
    }
    //--------------------------------------------------------------------------
}
