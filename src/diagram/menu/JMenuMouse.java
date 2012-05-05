package diagram.menu;

import Teste.GUI;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

import java.util.LinkedList;

import java.awt.Component;
import java.awt.Font;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

import diagram.editor.AreaAplicativo;
import diagram.editor.EditorAplicativoGrafo;

/**
 * JMenuMouse � uma classe abstrata que utiliza as classes do pacote swing para 
 * permitir a cria��o de menus Popup acionados sempre que houver um clique com o 
 * bot�o direito do mouse.
 * 
 * As novas op��es de menus podem ser inseridos somente ao final da lista de menus, 
 * ou seja, acrescentados somente abaixo do menu popup. Um exemplo simples de como 
 * criar um menu popup � mostrado abaixo.
 *
 * Quando criar um novo modelo de menu, deve-se sobrescrever o m�todo <B>menu</B> e
 * codificar as informa��es necess�rias para exibir o novo menu no editor. Este m�todo
 * ser� sempre chamado pela classe AreaAplicativo quando um evento do mouse com o 
 * clique do bot�o direito ocorrer sobre o editor.
 *<br>
 *<br>
 *&nbsp;import diagram.menu.*;<br>
 *&nbsp;public class ExemploMenu extends MenuMouse<br>
 *&nbsp;{<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;public ExemploMenu(AreaAplicativo origem)<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;super(origem);<br>
 *<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;//Inserir o c�digo do novo menu<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>
 *<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;public void menu(Object objeto, int x, int y)<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;//c�digo para exibir o menu popup<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>
 *&nbsp;}<br>
 *
 * @author Luis Henrique Castilho da Silva
 * @see JMenuMouseArea
 * @see JMenuMouseAresta
 * @see JMenuMouseRotulo
 * @see JMenuMouseVertice
 */
public abstract class JMenuMouse extends JPopupMenu implements ActionListener, MouseListener, MouseMotionListener
{
	private final int		TAMANHO_FONTE = 10;
	
	private LinkedList		listaMenu;
	
	private AreaAplicativo	origem;
	
        private GUI frame;
	/**
	 * Cria um novo menu.
	 *
	 * Como a classe JMenuMouse � abstrata, os novos menus n�o podem chamar
	 * est� classe diretamente.
	 *
	 * @param origem a �rea so painel na qual o menu popup ser� exibido.
	 */
	public JMenuMouse(AreaAplicativo origem, GUI frame) 
	{
		super();
                
		this.origem = origem;
		
                this.frame = frame;
                                
                listaMenu = new LinkedList();
	}
	
	//Metodos para a adi��o de novos menus
	
	/**
	 * Acrescenta ao final do popup um novo menu.
	 *
	 * @param menu o novo menu a ser inserido
	 * @see JMenuMouse#addSeparadorMenu
	 */
	public void addNovoMenu(JMenuItem menu)
	{
		int			i;
		JMenu		menuAux;
		JMenuItem	menuItem;
		
		if (menu != null)
		{
			//Tenta fazer uma coers�o para um JMenu
			try{
				menuAux = (JMenu) menu;
			}catch(Exception e){
				menuAux = null;	
			}
			
			if (menuAux != null)
			{
				//Objeto JMenu		
				menuAux.setFont(new Font("Times", Font.PLAIN, TAMANHO_FONTE));
				listaMenu.add(menuAux);
				
				for (i = 0; i < menuAux.getItemCount(); i++)
				{
					menuItem = menuAux.getItem(i);
					menuItem.setFont(new Font("Times", Font.PLAIN, TAMANHO_FONTE));
					menuItem.addActionListener(this);
				}
			}
			else
			{
				//ObjetoJMenuItem
				menu.setFont(new Font("Times", Font.PLAIN, TAMANHO_FONTE));
				listaMenu.add(menu);
				menu.addActionListener(this);	
			}
		}
	}
	
	/**
	 * Acrescenta ao final do popup uma linha de separa��o de menus.
	 *
	 * @see JMenuMouse#addNovoMenu
	 */
	public void addSeparadorMenu()
	{
		listaMenu.add(null);
	}
	
	//Metodos get
	
	/**
	 * Retorna a �rea aplicativo do pacote swing na qual o menu est� sendo exibido.
	 *
	 * @return a �rea aplicativo do editor
	 */
	public AreaAplicativo getAreaAplicativo()
	{
                if (frame.getQuantidadeAbas()>1)
                {
                    EditorAplicativoGrafo editorGrafo = frame.getEditorSelecionado();
                    origem = editorGrafo.getAreaGrafo();
                }
		return origem;
	}
	
	/**
	 * Retorna o n�mero de menus existente no JMenuMouse incluido os separadores
	 * de menus.
	 *
	 * @return o n�mero de menus
	 */
	public int getNumeroMenus()
	{
		return listaMenu.size();
	}
	
	/**
	 * Retorna o menu localizado na posi��o passada pelo par�mentro.
	 *
	 * @param localizacao a localiza��o do menu no popup
	 * @return o menu na posi��o especificada no par�metro
	 */
	public JMenuItem getMenu(int localizacao)
	{
		return (JMenuItem) listaMenu.get(localizacao);
	}
	
	/**
	 * Remove todos os menus armazenados na classe JMenuMouse.
	 */
	public void limparMenu()
	{
		listaMenu.clear();
	}
	
	//Metodos chamados pela classe para instanciar os menus
	
	/**
	 * M�todo invocado pela classe AreaAplicativo sempre que o usu�rio final
	 * clicar com o bot�o direto do mouse sobre a �rea do editor.
	 *
	 * Esse m�todo deve ser utilizado pelas subclasses para apresentar o popup 
	 * menu no editor e executar suas tarefas ap�s o clique sobre o
	 * popup menu.
	 */
	public abstract void menu(Object objeto, int x, int y);
}