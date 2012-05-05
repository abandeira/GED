/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GUI.java
 *
 * Created on 30/04/2012, 11:36:46
 */
package Teste;

import diagram.Grafo;
import diagram.componente.Componente;
import diagram.editor.EditorAplicativoGrafo;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author ANDRE
 */
public class GUI extends javax.swing.JFrame {
    private Grafo grafo;
    private EditorAplicativoGrafo editor;
    private JPanel jp;
    private Menu menu;

    /** Creates new form GUI */
    public GUI() {
        initComponents();
        Menu m = new Menu(JMenuBar,this);
        menu = m;
        this.setTitle("EDITOR DE DIAGRAMAS (GED)");
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane1 = new javax.swing.JDesktopPane();
        Abas = new javax.swing.JTabbedPane();
        JMenuBar = new javax.swing.JMenuBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Abas.setAutoscrolls(true);
        setJMenuBar(JMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Abas, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Abas, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new GUI().setVisible(true);
            }
        });
    }
    
    public void AdicionarAbas(){
        grafo = new Grafo();
            
        editor = new EditorAplicativoGrafo(grafo,this);
            
        grafo.setEditor(editor);
            
//        menu.ReceberEditoreGrafo(editor, grafo);
        
        jp = new JPanel(new GridLayout(2,2));
        jp.setLayout(new BorderLayout());
                
        //Adiciona o JScrollPane ao Painel
        jp.add(BorderLayout.CENTER,editor);
            
        //Adicionar o painel a JTabbedPane() na Tabela
        Abas.addTab("teste", jp);


        //Ir para a aba selecionada
        Abas.setSelectedComponent(jp);

        repaint();
                
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane Abas;
    private javax.swing.JMenuBar JMenuBar;
    private javax.swing.JDesktopPane jDesktopPane1;
    // End of variables declaration//GEN-END:variables

    public void RemoverAbas(EditorAplicativoGrafo editor) {
        Abas.remove(editor.getParent());
    }

    public EditorAplicativoGrafo getEditorSelecionado()
    {
        JPanel panel = (JPanel)Abas.getSelectedComponent();
        EditorAplicativoGrafo editorgrafo = (EditorAplicativoGrafo)panel.getComponent(0);
        return editorgrafo;
    }

    public int getQuantidadeAbas()
    {
        return Abas.getTabCount();
    }

}
