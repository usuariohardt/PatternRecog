
package patternregoc.logica.ann.kohonen.matrix;

/**
 *
 * @author Daniel
 */
public class MatrizCalculo {
    
    public static Matriz somar(Matriz a, Matriz b) {
        if (a.getLinhas() != b.getLinhas())
            throw new MatrizException("Para somar as matrizes elas têm de possuir o mesmo número de linhas e colunas."
                    + " Matriz a possui " + a.getLinhas() + " linhas e matriz b possui " + b.getLinhas() + " linhas.");
        if (a.getColunas() != b.getColunas())
            throw new MatrizException("Para somar as matrizes elas têm de possuir o mesmo número de linhas e colunas."
                    + " Matriz a possui " + a.getColunas() + " colunas e matriz b possui " + b.getColunas() + " colunas.");
        
        double resultado[][] = new double[a.getLinhas()][a.getColunas()];
        for (int resLinha = 0; resLinha < a.getLinhas(); resLinha++) {
            for (int resColuna = 0; resColuna < a.getColunas(); resColuna++)
                resultado[resLinha][resColuna] = a.get(resLinha, resColuna) + b.get(resLinha, resColuna);
        }
        return new Matriz(resultado);
    }
    //--------------------------------------------------------------------------
    public static Matriz subtrair(Matriz a, Matriz b) {
        if (a.getLinhas() != b.getLinhas())
            throw new MatrizException("Para a subtração de matrizes ambas devem possuir o mesmo número de linhas e colunas. "
                    + "Matriz a possui " + a.getLinhas() + " linhas e matriz b possui " + b.getLinhas() + " linhas.");
        
        if (a.getColunas() != b.getColunas())
            throw new MatrizException("Para a subtração de matrizes ambas devem possuir o mesmo número de linhas e colunas. "
                    + "Matriz a possui " + a.getColunas() + " colunas e matriz b possui " + b.getColunas() + " colunas.");
        
        double resultado[][] = new double[a.getLinhas()][a.getColunas()];
        
        for (int linha = 0; linha < a.getLinhas(); linha++) {
            for (int coluna = 0; coluna < a.getColunas(); coluna++)
                resultado[linha][coluna] = a.get(linha, coluna) - b.get(linha, coluna);
        }
        return new Matriz(resultado);
    }
    //--------------------------------------------------------------------------
    public static Matriz multiplicar(Matriz matriz, double valor) {
        double resultado[][] = new double[matriz.getLinhas()][matriz.getColunas()];
        for (int linha = 0; linha < matriz.getLinhas(); linha++) {
            for (int coluna = 0; coluna < matriz.getColunas(); coluna++)
                resultado[linha][coluna] = matriz.get(linha, coluna) * valor;
        }
        return new Matriz(resultado);
    }
    //--------------------------------------------------------------------------
    public static Matriz multiplicar(Matriz a, Matriz b) {
        if (a.getColunas() != b.getLinhas())
            throw new MatrizException("Para usar a multiplicação de matrizes, o número de colunas "
                    + "da primeira matriz deve ser igual ao número de linhas da segunda.");
        
        double resultado[][] = new double[a.getLinhas()][b.getColunas()];
        
        for (int linha = 0; linha < a.getLinhas(); linha++) {
            for (int coluna = 0; coluna < b.getColunas(); coluna++) {
                double valor = 0;
                for (int i = 0; i < a.getColunas(); i++)
                    valor += a.get(linha, i) * b.get(i, coluna);
                resultado[linha][coluna] = valor;
            }
        }
        return new Matriz(resultado);
    }
    //--------------------------------------------------------------------------
    public static Matriz dividir(Matriz matriz, double valor) {
        double resultado[][] = new double[matriz.getLinhas()][matriz.getColunas()];
        for (int linha = 0; linha < matriz.getLinhas(); linha++) {
            for (int coluna = 0; coluna < matriz.getColunas(); coluna++)
                resultado[linha][coluna] = matriz.get(linha, coluna) / valor;
        }
        return new Matriz(resultado);
    }
    //--------------------------------------------------------------------------
    public static void copiar(Matriz origem, Matriz destino) {
        for (int linha = 0; linha < origem.getLinhas(); linha++) {
            for (int coluna = 0; coluna < origem.getColunas(); coluna++)
                destino.set(linha, coluna, origem.get(linha, coluna));
        }
    }
    //--------------------------------------------------------------------------
    public static Matriz deletarColuna(Matriz matriz, int coluna) {
        if (coluna >= matriz.getColunas())
            throw new MatrizException("Não é possível deletar a coluna " + coluna + " da matriz, "
                    + "ela possui apenas " + matriz.getColunas() + " colunas.");
        
        double novaMatriz[][] = new double[matriz.getLinhas()][matriz.getColunas() - 1];
        
        for (int linha = 0; linha < matriz.getLinhas(); linha++) {
            int colunaAlvo = 0;
            for (int c = 0; c < matriz.getColunas(); c++) {
                if (c != coluna) {
                    novaMatriz[linha][colunaAlvo] = matriz.get(linha, c);
                    colunaAlvo++;
                }
            }
        }
        return new Matriz(novaMatriz);
    }
    //--------------------------------------------------------------------------
    public static Matriz deletarLinha(Matriz matriz, int linha) {
        if (linha >= matriz.getLinhas())
            throw new MatrizException("Não é possível deletar a linha " + linha + " da matriz, " 
                    + "ela possui apenas " + matriz.getLinhas() + " linhas.");
        
        double novaMatriz[][] = new double[matriz.getLinhas() - 1][matriz.getColunas()];
        
        int linhaAlvo = 0;
        for (int l = 0; l < matriz.getLinhas(); l++) {
            if (l != linha) {
                for (int coluna = 0; coluna < matriz.getColunas(); coluna++)
                    novaMatriz[linhaAlvo][coluna] = matriz.get(l, coluna);
                linhaAlvo++;
            }
        }
        return new Matriz(novaMatriz);
    }
    //--------------------------------------------------------------------------
    public static double produtoEscalar(Matriz a, Matriz b) {
        if (!a.ehVetor() || !b.ehVetor())
            throw new MatrizException("Para o cálculo do produto escalar, ambas as matrizes devem ser vetores.");
        
        Double aArray[] = a.matrizParaArray();
        Double bArray[] = b.matrizParaArray();
        
        if (aArray.length != bArray.length)
            throw new MatrizException("Para o cálculo do produto escalar, ambas as matrizes devem ter o mesmo tamanho.");
        
        double resultado = 0;
        for (int i = 0; i < aArray.length; i++)
            resultado += aArray[i] * bArray[i];
        
        return resultado;
    }
    //--------------------------------------------------------------------------
    public static Matriz identidade(int tamanho) {
        if (tamanho < 1)
            throw new MatrizException("Matriz identidade deve ter o tamanho de no mínimo 1.");
        
        Matriz matriz = new Matriz(tamanho, tamanho);
        for (int i = 0; i < tamanho; i++)
            matriz.set(i, i, 1);
        
        return matriz;
    }
    //--------------------------------------------------------------------------
    public static Matriz transposta(Matriz matriz) {
        double matrizInversa[][] = new double[matriz.getColunas()][matriz.getLinhas()];
        
        for (int l = 0; l < matriz.getLinhas(); l++) {
            for (int c = 0; c < matriz.getColunas(); c++)
                matrizInversa[c][l] = matriz.get(l, c);
        }
        
        return new Matriz(matrizInversa);
    }
    //--------------------------------------------------------------------------
    public static double tamanhoVetor(Matriz matriz) {
        if (!matriz.ehVetor())
            throw new MatrizException("A matriz de entrada para o cálculo do tamanho do vetor não é um vetor.");
        
        Double v[] = matriz.matrizParaArray();
        double r = 0.0;
        for (Double valor : v) {
            r += Math.pow(valor, 2);
        }
        return Math.sqrt(r);
    }
    //--------------------------------------------------------------------------
}
