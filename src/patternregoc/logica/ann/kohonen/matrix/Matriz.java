
package patternregoc.logica.ann.kohonen.matrix;

/**
 *
 * @author Daniel
 */
public class Matriz implements Cloneable {
    
    private final double matriz[][];
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Matriz(boolean matrizAux[][]) {
        this.matriz = new double[matrizAux.length][matrizAux[0].length];
        for (int l = 0; l < getLinhas(); l++) {
            for (int c = 0; c < getColunas(); c++) {
                if (matrizAux[l][c])
                    this.set(l, c, 1);
                else
                    this.set(l, c, -1);
            }
        }
    }
    //--------------------------------------------------------------------------
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Matriz(double matrizAux[][]) {
        this.matriz = new double[matrizAux.length][matrizAux[0].length];
        for (int l = 0; l < getLinhas(); l++) {
            for (int c = 0; c < getColunas(); c++) {
                this.set(l, c, matrizAux[l][c]);
            }
        }
    }
    //--------------------------------------------------------------------------
    public Matriz(int linhas, int colunas) {
        this.matriz = new double[linhas][colunas];
    }
    //--------------------------------------------------------------------------
    public static Matriz criarMatrizColuna(double dados[]) {
        double d[][] = new double[dados.length][1];
        for (int l = 0; l < d.length; l++)
            d[l][0] = dados[l];
        return new Matriz(d);
    }
    //--------------------------------------------------------------------------
    public static Matriz criarMatrizLinha(double dados[]) {
        double d[][] = new double[1][dados.length];
        System.arraycopy(dados, 0, d[0], 0, dados.length);
        return new Matriz(d);
    }
    //--------------------------------------------------------------------------
    public void adicionar(int linha, int coluna, double valor) {
        validar(linha, coluna);
        double novo = get(linha, coluna) + valor;
        set(linha, coluna, novo);
    }
    //--------------------------------------------------------------------------
    public void limpar() {
        for (int l = 0; l < getLinhas(); l++) {
            for (int c = 0; c < getColunas(); c++)
                set(l, c, 0);
        }
    }
    //--------------------------------------------------------------------------
    @Override
    @SuppressWarnings("CloneDoesntCallSuperClone")
    public Matriz clone() throws CloneNotSupportedException {
        return new Matriz(this.matriz);
    }
    //--------------------------------------------------------------------------
    public boolean equals(Matriz matrix) {
        return equals(matrix, 10);
    }
    //--------------------------------------------------------------------------
    public boolean equals(Matriz matriz, int precisao) {
        if (precisao < 0)
            throw new MatrizException("Precisão não pode ser um número negativo.");
        
        double teste = Math.pow(10.0, precisao);
        if (Double.isInfinite(teste) || (teste > Long.MAX_VALUE))
            throw new MatrizException("Precisão de " + precisao + " casas decimais não é suportado.");
        
        precisao = (int) Math.pow(10, precisao);
        for (int l = 0; l < getLinhas(); l++) {
            for (int c = 0; c < getColunas(); c++) {
                if ((long) (get(l, c) * precisao) != (long) (matriz.get(l, c) * precisao))
                    return false;
            }
        }
        return true;
    }
    //--------------------------------------------------------------------------
    public double get(int linha, int coluna) {
        validar(linha, coluna);
        return this.matriz[linha][coluna];
    }
    //--------------------------------------------------------------------------
    public Matriz getColuna(int col) {
        if (col > getColunas())
            throw new MatrizException("Coluna #" + col + " não existe.");
        
        double novaMatrix[][] = new double[getLinhas()][1];
        for (int linha = 0; linha < getLinhas(); linha++)
            novaMatrix[linha][0] = this.matriz[linha][col];

        return new Matriz(novaMatrix);
    }
    //--------------------------------------------------------------------------
    public int getColunas() {
        return this.matriz[0].length;
    }
    //--------------------------------------------------------------------------
    @SuppressWarnings("ManualArrayToCollectionCopy")
    public Matriz getLinha(int linha) {
        if (linha > getLinhas())
            throw new MatrizException("Linha #" + linha + " não existe.");
        
        double novaMatriz[][] = new double[1][getColunas()];
        for (int c = 0; c < getColunas(); c++)
            novaMatriz[0][c] = this.matriz[linha][c];
        return new Matriz(novaMatriz);
    }
    //--------------------------------------------------------------------------
    public int getLinhas() {
        return this.matriz.length;
    }
    //--------------------------------------------------------------------------
    public boolean ehVetor() {
        if (getLinhas() == 1)
            return true;
        else
            return getColunas() == 1;
    }
    //--------------------------------------------------------------------------
    public boolean ehZero() {
        for (int l = 0; l < getLinhas(); l++) {
            for (int c = 0; c < getColunas(); c++) {
                if (this.matriz[l][c] != 0)
                    return false;
            }
        }
        return true;
    }
    //--------------------------------------------------------------------------
    public void aleatorio(double min, double max) {
        for (int l = 0; l < getLinhas(); l++) {
            for (int c = 0; c < getColunas(); c++)
                this.matriz[l][c] = (Math.random() * (max - min)) + min;
        }
    }
    //--------------------------------------------------------------------------
    public void set(int linha, int coluna, double valor) {
        validar(linha, coluna);
        if (Double.isInfinite(valor) || Double.isNaN(valor))
            throw new MatrizException("Tentando atribuir valor inválido para matriz: " + valor);
        this.matriz[linha][coluna] = valor;
    }
    //--------------------------------------------------------------------------
    public int tamanho() {
        return this.matriz[0].length * this.matriz.length;
    }
    //--------------------------------------------------------------------------
    public double soma() {
        double resultado = 0;
        for (int l = 0; l < getLinhas(); l++) {
            for (int c = 0; c < getColunas(); c++)
                resultado += this.matriz[l][c];
        }
        return resultado;
    }
    //--------------------------------------------------------------------------
    public Double[] matrizParaArray() {
        Double resultado[] = new Double[getLinhas() * getColunas()];
        int index = 0;
        for (int l = 0; l < getLinhas(); l++) {
            for (int c = 0; c < getColunas(); c++)
                resultado[index++] = this.matriz[l][c];
        }
        return resultado;
    }
    //--------------------------------------------------------------------------
    public int arrayParaMatriz(Double[] array, int i) {
        for (int l = 0; l < getLinhas(); l++) {
            for (int c = 0; c < getColunas(); c++)
                this.matriz[l][c] = array[i++];
        }
        return i;
    }
    //--------------------------------------------------------------------------
    private void validar(int linha, int coluna) {
        if ((linha >= getLinhas()) || (linha < 0))
            throw new MatrizException("A linha " + linha + " está fora de alcance. Máximo " + getLinhas() + " linhas.");

        if ((coluna >= getColunas()) || (coluna < 0))
            throw new MatrizException("A coluna " + coluna + " está fora de alcance. Máximo " + getColunas() + " colunas.");
    }
    //--------------------------------------------------------------------------
}
