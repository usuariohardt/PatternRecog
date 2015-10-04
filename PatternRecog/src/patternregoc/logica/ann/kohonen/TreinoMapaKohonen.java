
package patternregoc.logica.ann.kohonen;

import patternregoc.logica.ann.kohonen.matrix.Matriz;
import patternregoc.logica.ann.kohonen.matrix.MatrizCalculo;

/**
 *
 * @author Daniel
 */
public final class TreinoMapaKohonen {
    
    public enum MetodoAprendizado { ADICAO, SUBTRACAO }
    
    private final MapaKohonen mapaKohonen;
    
    private final MetodoAprendizado metodoAprendizado;
    
    private double taxaAprendizado;
    private final double reducao = .99;
    private double erroTotal;
    private double erroGlobal;
    
    int venc[];
    double treino[][];
    
    private final int contNeuroniosSaida;
    private final int contNeuroniosEntrada;
    
    private final MapaKohonen melhorMapa;
    
    private double melhorErro;
    
    private final Matriz matrizTrabalho;
    private final Matriz matrizCorrecao;
    
    public TreinoMapaKohonen(MapaKohonen mapaKohonen, double treino[][], 
            MetodoAprendizado metodoAprendizado, double taxaAprendizado) {
        this.mapaKohonen = mapaKohonen;
        this.treino = treino;
        this.erroTotal = 1.0;
        this.metodoAprendizado = metodoAprendizado;
        this.taxaAprendizado = taxaAprendizado;
        
        this.contNeuroniosSaida = mapaKohonen.getContadorNeuroniosSaida();
        this.contNeuroniosEntrada = mapaKohonen.getContadorNeuroniosEntrada();
        
        this.erroTotal = 1.0;
        
        for (double[] tr : treino) {
            Matriz aux = Matriz.criarMatrizColuna(tr);
            if (MatrizCalculo.tamanhoVetor(aux) < MapaKohonen.VALOR_MINIMO)
                throw (new RuntimeException( "Normalização por multiplicação tem caso de treinamento nulo."));
        }
        
        this.melhorMapa = new MapaKohonen(this.contNeuroniosEntrada, this.contNeuroniosSaida, this.mapaKohonen.getTipoNormalizacao());
        
        this.venc = new int[this.contNeuroniosSaida];
        this.matrizCorrecao = new Matriz(this.contNeuroniosSaida, this.contNeuroniosEntrada + 1);
        if (this.metodoAprendizado == MetodoAprendizado.ADICAO)
            this.matrizTrabalho = new Matriz(1, this.contNeuroniosEntrada + 1);
        else
            this.matrizTrabalho = null;
        
        inicializar();
        this.melhorErro = Double.MAX_VALUE;
    }
    //--------------------------------------------------------------------------
    protected void ajustarPesos() {
        for (int i = 0; i < this.contNeuroniosSaida; i++) {
            if (this.venc[i] == 0)
                continue;
            
            double f = 1.0 / this.venc[i];
            if (this.metodoAprendizado == MetodoAprendizado.SUBTRACAO)
                f *= this.taxaAprendizado;
            
            for (int j = 0; j <= this.contNeuroniosEntrada; j++) {
                double corr = f * this.matrizCorrecao.get(i, j);
                this.mapaKohonen.getPesosSaida().adicionar(i, j, corr);
            }
        }
    }
    //--------------------------------------------------------------------------
    private void copiarPesos(MapaKohonen origem, MapaKohonen destino) {
        MatrizCalculo.copiar(origem.getPesosSaida(), destino.getPesosSaida());
    }
    //--------------------------------------------------------------------------
    public void avaliarErros() {
        this.matrizCorrecao.limpar();
        
        for (int i = 0; i < this.venc.length; i++)
            this.venc[i] = 0;
        
        this.erroGlobal = 0.0;
        
        for (double[] tr : this.treino) {
            NormalizarEntrada input = new NormalizarEntrada(tr, this.mapaKohonen.getTipoNormalizacao());
            int melhor = this.mapaKohonen.vencedor(input);
            this.venc[melhor]++;
            Matriz aux = this.mapaKohonen.getPesosSaida().getLinha(melhor);
            double tamanho = 0.0;
            double diferenca;
            for (int i = 0; i < this.contNeuroniosEntrada; i++) {
                diferenca = tr[i] * input.getFatorNormalizacao() - aux.get(0, i);
                tamanho += diferenca * diferenca;
                if (this.metodoAprendizado == MetodoAprendizado.SUBTRACAO) {
                    this.matrizCorrecao.adicionar(melhor, i, diferenca);
                } else {
                    this.matrizTrabalho.set(0, i, this.taxaAprendizado * tr[i] * input.getFatorNormalizacao() + aux.get(0, i));
                }
            }
            diferenca = input.getSintetico() - aux.get(0, this.contNeuroniosEntrada);
            tamanho += diferenca * diferenca;
            if (this.metodoAprendizado ==MetodoAprendizado.SUBTRACAO)
                this.matrizCorrecao.adicionar(melhor, this.contNeuroniosEntrada, diferenca);
            else
                this.matrizTrabalho.set(0, this.contNeuroniosEntrada, this.taxaAprendizado * input.getSintetico() + aux.get(0, this.contNeuroniosEntrada));
            if (tamanho > this.erroGlobal)
                this.erroGlobal = tamanho;
            if (this.metodoAprendizado == MetodoAprendizado.ADICAO) {
                normalizarPesos(this.matrizTrabalho, 0);
                for (int i = 0; i <= this.contNeuroniosEntrada; i++)
                    this.matrizCorrecao.adicionar(melhor, i, this.matrizTrabalho.get(0, i) - aux.get(0, i));
            }
        }
        this.erroGlobal = Math.sqrt(this.erroGlobal);
    }
    //--------------------------------------------------------------------------
    @SuppressWarnings("UnusedAssignment")
    protected void forcarVencedor() {
        int melhor, qual = 0;
        
        Matriz pesosSaida = this.mapaKohonen.getPesosSaida();
        
        double dist = Double.MAX_VALUE;
        for (int t = 0; t < this.treino.length; t++) {
            melhor = this.mapaKohonen.vencedor(this.treino[t]);
            final double output[] = this.mapaKohonen.getSaida();
            if (output[melhor] < dist) {
                dist = output[melhor];
                qual = t;
            }
        }
        
        NormalizarEntrada entrada = new NormalizarEntrada(this.treino[qual], this.mapaKohonen.getTipoNormalizacao());
        melhor = this.mapaKohonen.vencedor(entrada);
        double saida[] = this.mapaKohonen.getSaida();
        
        dist = Double.MIN_VALUE;
        int i = this.contNeuroniosSaida;
        
        while ((i--) > 0) {
            if (this.venc[i] != 0)
                continue;
            if (saida[i] > dist) {
                dist = saida[i];
                qual = i;
            }
        }
        
        for (int j = 0; j < entrada.getMatrizEntrada().getColunas(); j++)
            pesosSaida.set(qual, j, entrada.getMatrizEntrada().get(0,j));
        
        normalizarPesos(pesosSaida, qual);
    }
    //--------------------------------------------------------------------------
    public double getMelhorErro() {
        return this.melhorErro;
    }
    //--------------------------------------------------------------------------
    public double getErroTotal() {
        return this.erroTotal;
    }
    //--------------------------------------------------------------------------
    public void inicializar() {
        this.mapaKohonen.getPesosSaida().aleatorio(-1, 1);
        
        for (int i = 0; i < this.contNeuroniosSaida; i++)
            normalizarPesos(this.mapaKohonen.getPesosSaida(), i);
    }
    //--------------------------------------------------------------------------
    public void iteracao() {
        avaliarErros();
        
        this.erroTotal = this.erroGlobal;

        if (this.erroTotal < this.melhorErro) {
            this.melhorErro = this.erroTotal;
            copiarPesos(this.mapaKohonen, this.melhorMapa);
        }
        
        int ganhadores = 0;
        for (int i = 0; i < this.venc.length; i++) {
            if (this.venc[i] != 0)
                ganhadores++;
        }
        
        if ((ganhadores < this.contNeuroniosSaida) && (ganhadores < this.treino.length)) {
            forcarVencedor();
            return;
        }
        
        ajustarPesos();
        
        if (this.taxaAprendizado > 0.01)
            this.taxaAprendizado *= this.reducao;
    }
    //--------------------------------------------------------------------------
    protected void normalizarPesos(Matriz matriz, int linha) {
        double tamanho = MatrizCalculo.tamanhoVetor(matriz.getLinha(linha));
        tamanho = Math.max(tamanho, MapaKohonen.VALOR_MINIMO);
        tamanho = 1.0 / tamanho;
        
        for (int i = 0; i < this.contNeuroniosEntrada; i++)
            matriz.set(linha, i, matriz.get(linha, i) * tamanho);
        matriz.set(linha, this.contNeuroniosEntrada, 0);
    }
    //--------------------------------------------------------------------------
}
