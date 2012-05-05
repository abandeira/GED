package diagram.menu;

import java.awt.PopupMenu;
import java.awt.MenuItem;
import java.awt.Menu;
import java.awt.Font;

import java.awt.event.ActionListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;

import java.util.LinkedList;

import diagram.editor.AreaApplet;

/**
 * MenuMouse � uma classe abstrata que utiliza as classes do pacote awt para 
 * permitir a cria��o de menus Popup acionados sempre que houver um clique com o 
 * bot�o direito do mouse.
 * 
 * As novas op��es de menus podem ser inseridos somente ao final da lista de menus, 
 * ou seja, acrescentados somente abaixo do menu popup. Um exemplo simples de como 
 * criar um menu popup � mostrado abaixo.
 *
 * Quando criar um novo modelo de menu, deve-se sobrescrever o m�todo <B>menu</B> e
 * codificar as informa��es necess�rias para exibir o novo menu no editor. Este m�todo
 * ser� sempre chamado pela classe AreaApplet quando um evento do mouse com o 
 * clique do bot�o direito ocorrer sobre o editor.<br>
 *<br>
 *<br>
 *&nbsp;import diagram.menu.*;<br>
 *<br>
 *&nbsp;public class ExemploMenu extends MenuMouse<br>
 *&nbsp;{<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;public ExemploMenu(AreaApplet origem)<br>
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
 * @see MenuMouseArea
 * @see MenuMouseAresta
 * @see MenuMouseRotulo
 * @see MenuMouseVertice
 */
public abstract class MenuMouse extends PopupMenu implements ActionListener, MouseListener, MouseMotionListener
{
	private final int		TAMANHO_FONTE = 10;
	
	private LinkedList		listaMenu;
	
	private AreaApplet		origem;
	
	/**
	 * Cria um novo menu.
	 *
	 * Como a classe MenuMouse � abstrata, os novos menus n�o podem chamar
	 * est� classe diretamente.
	 *
	 * @param origem a �reado painel na qual o menu popup ser� exibido.
	 */
	public MenuMouse(AreaApplet origem) 
	{
		super();
		
		this.origem = origem;
		listaMenu = new LinkedList();
	}
	
	//Metodos para a adi��o de novos menus
	
	/**
	 * Acrescenta ao final do popup um novo menu.
	 *
	 * @param menu o novo menu a ser inserido
	 * @see MenuMouse#addSeparadorMenu
	 */
	public void addNovoMenu(MenuItem menu)
	{
		int			i;
		Menu		menuAux;
		MenuItem	menuItem;
		
		if (menu != null)
		{
			//Tenta fazer uma coers�o para um JMenu
			try{
				menuAux = (Menu) menu;
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
	 * @see MenuMouse#addNovoMenu
	 */
	public void addSeparadorMenu()
	{
		listaMenu.add(null);
	}
	
	//Metodos get
	
	/**
	 * Retorna a �rea aplicativo do pacote awt na qual o menu est� sendo exibido.
	 *
	 * @return a �rea aplicativo do editor
	 */
	public AreaApplet getAreaApplet()
	{
		return origem;
	}
	
	/**
	 * Retorna o n�mero de menus existente no MenuMouse incluido os separadores
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
	public MenuItem getMenu(int localizacao)
	{
		return (MenuItem) listaMenu.get(localizacao);
	}
	
	/**
	 * Remove todos os menus armazenados na classe MenuMouse.
	 */
	public void limparMenu()
	{
		listaMenu.clear();
	}
	
	//Metodos chamados pela classe para instanciar os menus

	/**
	 * M�todo invocado pela classe AreaApplet sempre que o usu�rio final
	 * clicar com o bot�o direto do mouse sobre a �rea do editor.
	 *
	 * Esse m�todo deve ser utilizado pelas subclasses para apresentar o popup 
	 * menu no editor e executar suas tarefas ap�s o clique sobre o
	 * popup menu.
	 */
	public abstract void menu(Object objeto, int x, int y);	
}