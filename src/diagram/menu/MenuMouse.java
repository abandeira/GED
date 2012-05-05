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
 * MenuMouse é uma classe abstrata que utiliza as classes do pacote awt para 
 * permitir a criação de menus Popup acionados sempre que houver um clique com o 
 * botão direito do mouse.
 * 
 * As novas opções de menus podem ser inseridos somente ao final da lista de menus, 
 * ou seja, acrescentados somente abaixo do menu popup. Um exemplo simples de como 
 * criar um menu popup é mostrado abaixo.
 *
 * Quando criar um novo modelo de menu, deve-se sobrescrever o método <B>menu</B> e
 * codificar as informações necessárias para exibir o novo menu no editor. Este método
 * será sempre chamado pela classe AreaApplet quando um evento do mouse com o 
 * clique do botão direito ocorrer sobre o editor.<br>
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
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;//Inserir o código do novo menu<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>
 *<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;public void menu(Object objeto, int x, int y)<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;//código para exibir o menu popup<br>
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
	 * Como a classe MenuMouse é abstrata, os novos menus não podem chamar
	 * está classe diretamente.
	 *
	 * @param origem a áreado painel na qual o menu popup será exibido.
	 */
	public MenuMouse(AreaApplet origem) 
	{
		super();
		
		this.origem = origem;
		listaMenu = new LinkedList();
	}
	
	//Metodos para a adição de novos menus
	
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
			//Tenta fazer uma coersão para um JMenu
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
	 * Acrescenta ao final do popup uma linha de separação de menus.
	 *
	 * @see MenuMouse#addNovoMenu
	 */
	public void addSeparadorMenu()
	{
		listaMenu.add(null);
	}
	
	//Metodos get
	
	/**
	 * Retorna a área aplicativo do pacote awt na qual o menu está sendo exibido.
	 *
	 * @return a área aplicativo do editor
	 */
	public AreaApplet getAreaApplet()
	{
		return origem;
	}
	
	/**
	 * Retorna o número de menus existente no MenuMouse incluido os separadores
	 * de menus.
	 *
	 * @return o número de menus
	 */
	public int getNumeroMenus()
	{
		return listaMenu.size();
	}
	
	/**
	 * Retorna o menu localizado na posição passada pelo parâmentro.
	 *
	 * @param localizacao a localização do menu no popup
	 * @return o menu na posição especificada no parâmetro
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
	 * Método invocado pela classe AreaApplet sempre que o usuário final
	 * clicar com o botão direto do mouse sobre a área do editor.
	 *
	 * Esse método deve ser utilizado pelas subclasses para apresentar o popup 
	 * menu no editor e executar suas tarefas após o clique sobre o
	 * popup menu.
	 */
	public abstract void menu(Object objeto, int x, int y);	
}