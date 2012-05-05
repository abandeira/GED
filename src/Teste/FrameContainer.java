/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Teste;

import diagram.editor.EditorAplicativoGrafo;
import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

/**
 *
 * @author ANDRE
 */
public class FrameContainer extends JInternalFrame{
    private EditorAplicativoGrafo EditorGrafo;
    private FrameContainer container;

    FrameContainer(EditorAplicativoGrafo editor) {
        EditorGrafo = editor;
        container = this;
        this.setClosable(true);
        this.setFocusable(true);
        this.setTitle("Editor de Grafo");
        eventos();
    }

    public EditorAplicativoGrafo getEditor() {
        return EditorGrafo;
    }

    private void eventos() {
       
        this.addInternalFrameListener( new InternalFrameListener() {  
                
           @Override
           public void internalFrameClosed(InternalFrameEvent e) {               
               EditorGrafo.getFrame().RemoverAbas(container);
            }  

            @Override
            public void internalFrameClosing(InternalFrameEvent e) {}

            @Override
            public void internalFrameOpened(InternalFrameEvent e) {}

            @Override
            public void internalFrameIconified(InternalFrameEvent e) {}

            @Override
            public void internalFrameDeiconified(InternalFrameEvent e) {}

            @Override
            public void internalFrameActivated(InternalFrameEvent e) {}

            @Override
            public void internalFrameDeactivated(InternalFrameEvent e) {}

       });
    }
}
