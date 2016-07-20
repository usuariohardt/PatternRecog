
package patternregoc.logica.simulacao;

import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import patternregoc.logica.Logica;
import patternregoc.logica.ObjetoPadrao;
import patternregoc.logica.ann.kohonen.NormalizarEntrada.TipoNormalizacao;
import patternregoc.logica.ann.kohonen.TreinoMapaKohonen.MetodoAprendizado;
import patternregoc.logica.simulacao.grid.PanelGrid;

/**
 *
 * @author Daniel
 */
public class FrSimulacao extends javax.swing.JFrame {
    
    private final String VERSAO = "1.0.0";
    private PanelGrid panelGrid;
    private final DefaultListModel modeloListaPadroes;
    private final Logica logica;
    
    public FrSimulacao() {
        initComponents();
        this.setTitle("Pattern Recognition - " + VERSAO);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        initGrid();
        this.modeloListaPadroes = new DefaultListModel();
        this.listaPadroes.setModel(this.modeloListaPadroes);
        btLimpar.setEnabled(false);
        btReconhecer.setEnabled(false);
        logica = new Logica();
    }
    //--------------------------------------------------------------------------
    private void initGrid(){
        panelGrid = new PanelGrid();
        panelGrid.setLocation(10, 15);
        panelGrid.setEditable(true);
        panelGrid.addComponentListener(panelGrid.getGrid().getComponentListener());
        panelGridEntrada.add(panelGrid);
    }
    //--------------------------------------------------------------------------
    private void adicionarPadrao() {
        String simbolo = JOptionPane.showInputDialog("Digite o símbolo que representa o padrão:");
        if (simbolo == null)
            return;
        
        if (simbolo.length() > 1) {
            JOptionPane.showMessageDialog(this, "Somente um símbolo é válida.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        ObjetoPadrao obj = new ObjetoPadrao();
        obj.setSimbolo(String.valueOf(simbolo.charAt(0)));
        
        ArrayList<String> padrao = new ArrayList<>();
        int matriz[][] = panelGrid.getGrid().getMatrizCelulas();
        for(int a = 0; a < Logica.ALTURA; a++){
            String linha = "";
            for(int l = 0; l < Logica.LARGURA; l++)
                linha += matriz[a][l];
            padrao.add(linha);
        }
        
        obj.setPadrao(padrao);
        
        for (int i = 0; i < this.modeloListaPadroes.size(); i++) {
            Comparable str = (Comparable) this.modeloListaPadroes.getElementAt(i);
            if (str.equals(simbolo)) {
                JOptionPane.showMessageDialog(this, "Esse símbolo já está definido!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (str.compareTo(obj) > 0) {
                this.modeloListaPadroes.add(i, obj);
                return;
            }
        }
        this.modeloListaPadroes.add(this.modeloListaPadroes.size(), obj);
        panelGrid.limparGrid();
    }
    //--------------------------------------------------------------------------
    private void deletarPadrao() {
        int i = this.listaPadroes.getSelectedIndex();
        
        if (i == -1) {
            JOptionPane.showMessageDialog(this, "Selecione o símbolo para deletar.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        this.modeloListaPadroes.remove(i);
    }
    //--------------------------------------------------------------------------
    @SuppressWarnings("null")
    private void treinar(){
        ArrayList<ObjetoPadrao> lista = new ArrayList<>();
        for (int i = 0; i < this.modeloListaPadroes.size(); i++) {
            ObjetoPadrao obj = (ObjetoPadrao) this.modeloListaPadroes.getElementAt(i);
            lista.add(obj);
        }
        
        if(lista != null){
            if(lista.size() > 1)
                logica.treinar(lista, TipoNormalizacao.MULTIPLICACAO, MetodoAprendizado.SUBTRACAO);
            else
                erro("A base deve possuir no mínimo 2 padrões.");
        }else
            erro("Não há registros para o tipo especificado.");
        
        panelGrid.limparGrid();
        btReconhecer.setEnabled(true);
        btLimpar.setEnabled(true);
    }
    //--------------------------------------------------------------------------
    private void erro(String erro){
        JOptionPane.showMessageDialog(this, erro, "Erro", JOptionPane.ERROR_MESSAGE);
    }
    //--------------------------------------------------------------------------
    private void reconhecer(){
        ObjetoPadrao obj = new ObjetoPadrao();
        ArrayList<String> padrao = new ArrayList<>();
        int matriz[][] = panelGrid.getGrid().getMatrizCelulas();
        for(int a = 0; a < Logica.ALTURA; a++){
            String linha = "";
            for(int l = 0; l < Logica.LARGURA; l++)
                linha += matriz[a][l];
            padrao.add(linha);
        }
        obj.setPadrao(padrao);
        
        char simbolo = logica.reconhecer(obj);
        
        imprimirAmostra(obj);
        
        if(simbolo != '?')
            mostraResposta(simbolo);
        else
            erro("Falha no reconhecimento de padrão.");
        panelGrid.limparGrid();
    }
    //--------------------------------------------------------------------------
    private void imprimirAmostra(ObjetoPadrao obj){
        for(int l = 0; l < Logica.ALTURA; l++){
            for(int c = 0; c < Logica.LARGURA; c++){
                if(obj.getDados(c, l))
                    System.out.print("1");
                else
                    System.out.print("0");
            }
            System.out.println();
        }
        System.out.println();
    }
    //--------------------------------------------------------------------------
    private void mostraResposta(char simbolo){
        String msg = "Símbolo reconhecido [ " + simbolo + " ] \n";
        JOptionPane.showMessageDialog(this, msg, "Resposta", JOptionPane.WARNING_MESSAGE);
    }
    //--------------------------------------------------------------------------
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPrincipal = new javax.swing.JPanel();
        panelGridEntrada = new javax.swing.JPanel();
        btLimpar = new javax.swing.JButton();
        btReconhecer = new javax.swing.JButton();
        panelPadrao = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listaPadroes = new javax.swing.JList();
        btTreinar = new javax.swing.JButton();
        btDeletar = new javax.swing.JButton();
        btAdicionar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelGridEntrada.setBorder(javax.swing.BorderFactory.createTitledBorder("Grid"));

        btLimpar.setText("Limpar");
        btLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLimparActionPerformed(evt);
            }
        });

        btReconhecer.setText("Reconhecer");
        btReconhecer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btReconhecerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelGridEntradaLayout = new javax.swing.GroupLayout(panelGridEntrada);
        panelGridEntrada.setLayout(panelGridEntradaLayout);
        panelGridEntradaLayout.setHorizontalGroup(
            panelGridEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGridEntradaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelGridEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btLimpar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btReconhecer, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelGridEntradaLayout.setVerticalGroup(
            panelGridEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelGridEntradaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btLimpar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btReconhecer)
                .addGap(12, 12, 12))
        );

        panelPadrao.setBorder(javax.swing.BorderFactory.createTitledBorder("Padrão"));

        listaPadroes.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listaPadroesValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(listaPadroes);

        javax.swing.GroupLayout panelPadraoLayout = new javax.swing.GroupLayout(panelPadrao);
        panelPadrao.setLayout(panelPadraoLayout);
        panelPadraoLayout.setHorizontalGroup(
            panelPadraoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPadraoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelPadraoLayout.setVerticalGroup(
            panelPadraoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPadraoLayout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        btTreinar.setText("Treinar");
        btTreinar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btTreinarActionPerformed(evt);
            }
        });

        btDeletar.setText("Deletar");
        btDeletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDeletarActionPerformed(evt);
            }
        });

        btAdicionar.setText("Adicionar");
        btAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAdicionarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelGridEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btDeletar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btAdicionar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(panelPadrao, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btTreinar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 10, Short.MAX_VALUE))
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelGridEntrada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addComponent(panelPadrao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btAdicionar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btDeletar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btTreinar)
                        .addGap(0, 127, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLimparActionPerformed
        panelGrid.limparGrid();
    }//GEN-LAST:event_btLimparActionPerformed

    private void btAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAdicionarActionPerformed
        adicionarPadrao();
    }//GEN-LAST:event_btAdicionarActionPerformed

    private void btDeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDeletarActionPerformed
        deletarPadrao();
    }//GEN-LAST:event_btDeletarActionPerformed

    private void btTreinarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btTreinarActionPerformed
        treinar();
    }//GEN-LAST:event_btTreinarActionPerformed

    private void btReconhecerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btReconhecerActionPerformed
        reconhecer();
    }//GEN-LAST:event_btReconhecerActionPerformed

    private void listaPadroesValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listaPadroesValueChanged
        if(listaPadroes.getSelectedIndex() != -1){
            ObjetoPadrao sel = (ObjetoPadrao) this.modeloListaPadroes.getElementAt(listaPadroes.getSelectedIndex());
            panelGrid.setGrid(sel.getPadrao());
        }
    }//GEN-LAST:event_listaPadroesValueChanged
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAdicionar;
    private javax.swing.JButton btDeletar;
    private javax.swing.JButton btLimpar;
    private javax.swing.JButton btReconhecer;
    private javax.swing.JButton btTreinar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList listaPadroes;
    private javax.swing.JPanel panelGridEntrada;
    private javax.swing.JPanel panelPadrao;
    private javax.swing.JPanel panelPrincipal;
    // End of variables declaration//GEN-END:variables

}
