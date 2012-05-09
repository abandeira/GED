package diagram.editor;

import Teste.GUI;
import Teste.Menu;
import diagram.Grafo;
import diagram.componente.Aresta;
import diagram.componente.Componente;
import diagram.componente.Vertice;
import diagram.componente.VerticeAtor;
import diagram.menu.JMenuMouse;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;

/**
 * Est� � a classe principal do editor de aplicativo. Uma inst�ncia simples desta 
 * classe cria-se um novo editor de grafo preparado para editar e apresentar os diagramas.
 * Este novo editor utiliza todos os objetos do pacote Swing.
 *
 * A classe chama automaticamente a classe <B>AreaAplicativo</B> criando uma �rea padr�o
 * para editar o diagrama.
 *
 * @author Luis Henrique Castilho da Silva
 * @see EditorAppletGrafo
 */
public class EditorAplicativoGrafo extends JInternalFrame implements AdjustmentListener, ComponentListener, KeyListener
{
    
        ArrayList<Componente> componentes = null;
        
        private GUI frame;
        private EditorAplicativoGrafo editor;
        private javax.swing.JLabel Leitura =  new javax.swing.JLabel();
    
	/**
	 * O valor padr�o do canto superior esquerdo do editor no eixo horizontal.
	 */
	public static final int		LOCALEDITORPADRAOHOR = 100;
	
	/**
	 * O valor padr�o do canto superior esquerdo do editor no eixo vertical.
	 */
	public static final int		LOCALEDITORPADRAOVER = 100;
	
	/**
	 * A largura padr�o da janela do editor.
	 */
	public static final int		TAMANHOEDITORPADRAOHOR = 600;
	
	/**
	 * A altura padr�o da janela do editor.
	 */
	public static final int		TAMANHOEDITORPADRAOVER = 400;
	
	/**
	 * O valor de edi��o do grafo.
	 */
	public static final int		EDITAR = 1;
	
	/**
	 * O valor de n�o edi��o do grafo. Este grafo ser� somente apresentado.
	 */						
	public static final int		NAO_EDITAR = 0;
	
	private static int			DISTANCIA_EIXO_X = 50,
								DISTANCIA_EIXO_Y = 80;
	
	//Tratam o layout do Aplicativo
	private GridBagLayout		layoutJanela;
	private GridBagConstraints	componenteLayout;
	
	//Barras de rolagem Vertical e Horizontal...
	private JScrollBar			barraVertical,
								barraHorizontal;
	
	//Variavel responsavel pela capta��o da tela...
	private Container			areaLayout;

	//Painel onde ser� apresentado o grafo...	
	private AreaAplicativo		areaGrafo;
	
	//Variaveis reponsaveis pelo tamanho e localizacao do editor...
	private int					editar,
								posicaoAtualBarraVertical,
								posicaoAtualBarraHorizontal;
								
	private Grafo				grafo;
	
	private	boolean				teclaShiftPressionada=false,
								teclaCtrlPressionada,
								teclaDeletePressionada,
								teclaCtrlZPressionada,
                                                                NavegacaoVertice,
                                                                NavegacaoAresta,
                                                                NavegacaoAtores,
                                                                NavegacaoCasos,
                                                                teclaCtrlSPressionada;
	
        private int i=-1, IDlista=0, IDlistaAnterior,j,aresta=0;
    
        public int x,y;
        private Vertice verticeFoco;
        private Menu menu;
	
        /**
	 * Cria um editor de aplicativo padr�o com o t�tulo <B>Editor de Grafo</B> sem
	 * nenhum grafo apresentado. Este editor poder� ser editado pelo usu�rio final.
	 */			
	public EditorAplicativoGrafo(GUI frame) 
	{
		//T�tulo do EditorDiagrama
		super("Editor de Grafo");
		
                this.frame = frame;
                
		//Seta o editor para editar o grafo...
		editar = EDITAR;
		               
		//O grafo inicial n�o existe...
		grafo = null;
		
		layoutJanela = new GridBagLayout();
		componenteLayout = new GridBagConstraints();
		
		//Recupera a �rea do EditorGrafo e seta o layout de inser��o dos objetos...
		areaLayout = getContentPane();
		areaLayout.setLayout(layoutJanela);
		
		//Instancia��o da barra de rolagem Vertical
		barraVertical = new JScrollBar();
		barraVertical.setEnabled(false);
		barraVertical.setOrientation(JScrollBar.VERTICAL);
		barraVertical.setUnitIncrement(10);
		barraVertical.addAdjustmentListener(this);
		
		//Instancia��o da barra de rolagem Horizontal
		barraHorizontal = new JScrollBar();
		barraHorizontal.setEnabled(false);
		barraHorizontal.setOrientation(JScrollBar.HORIZONTAL);
		barraHorizontal.setUnitIncrement(10);
		barraHorizontal.addAdjustmentListener(this);
		
		areaGrafo = new AreaAplicativo(this, frame);
		
		//Inser��o dos objetos no EditorGrafo...
		//Configura��o dos parametros da �rea de plotagem...
		componenteLayout.fill = GridBagConstraints.BOTH;
		componenteLayout.gridx = 0;
		componenteLayout.gridy = 0;
		componenteLayout.gridheight = 1;
		componenteLayout.gridwidth = 1;
		componenteLayout.weightx = 2.0;
		componenteLayout.weighty = 2.0;
		layoutJanela.setConstraints(areaGrafo, componenteLayout);
		areaLayout.add(areaGrafo);
		//Configura��o dos parametros para a BarraVertical...
		componenteLayout.fill = GridBagConstraints.VERTICAL;
		componenteLayout.gridx = 1;
		componenteLayout.gridy = 0;
		componenteLayout.gridheight = 1;
		componenteLayout.gridwidth = 1;
		componenteLayout.weightx = 0;
		componenteLayout.weighty = 0;
		layoutJanela.setConstraints(barraVertical, componenteLayout);
		areaLayout.add(barraVertical);
		//Configura��o dos parametros para a BarraHorizontal...
		componenteLayout.fill = GridBagConstraints.HORIZONTAL;
		componenteLayout.gridx = 0;
		componenteLayout.gridy = 1;
		componenteLayout.gridheight = 1;
		componenteLayout.gridwidth = 1;
		componenteLayout.weightx = 0;
		componenteLayout.weighty = 0;
		layoutJanela.setConstraints(barraHorizontal, componenteLayout);
		areaLayout.add(barraHorizontal);
		
		posicaoAtualBarraVertical = 0;
		posicaoAtualBarraHorizontal = 0;
		
		setLocation(LOCALEDITORPADRAOHOR , LOCALEDITORPADRAOVER);
		setSize(TAMANHOEDITORPADRAOHOR , TAMANHOEDITORPADRAOVER);
		show();
		
		teclaShiftPressionada = false;
		teclaCtrlPressionada = false;
		teclaDeletePressionada = false;
		teclaCtrlZPressionada = false;
		
		addComponentListener(this);
		addKeyListener(this);
		setFocusable(true);
                
                //Retirar a tecla tab do padrão
                Set<AWTKeyStroke> empty = Collections.emptySet();  
                this.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, empty);  
                this.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, empty);
                
                //Remover a barra de titulo
                ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        
                //Selecionar o JInternalFrame
                try {
                    this.setSelected(true);
                } catch (PropertyVetoException ex) {
                     Logger.getLogger(EditorAplicativoGrafo.class.getName()).log(Level.SEVERE, null, ex);
                }
              
                eventos();
	}
	
	/**
	 * Cria um editor de aplicativo padr�o com o t�tulo <B>Editor de Grafo</B> sem
	 * nenhum grafo apresentado e com tamanho da janela e sua localiza��o definido
	 * pelos par�metros. Este editor poder� ser editado pelo usu�rio final.
	 *
	 * @param localJanelaH a coordenada da janela no eixo horizontal
	 * @param localJanelaV a coordenada da janela no eixo vertical
	 * @param tamanhoJanelaH a largura da janela
	 * @param tamanhoJanemaV a altura da janela
	 */	
	public EditorAplicativoGrafo(int localJanelaH, int localJanelaV, int tamanhoJanelaH, int tamanhoJanelaV, GUI frame)
	{
		this(frame);
		
		setSize(tamanhoJanelaH, tamanhoJanelaV);
		setLocation(localJanelaH, localJanelaV);
	}
	
	/**
	 * Cria um editor de aplicativo padr�o com o t�tulo <B>Editor de Grafo</B> e com
	 * o grafo inicial passado pelo par�metro. Este editor poder� ser editado 
	 * pelo usu�rio final.
	 *
	 * @param grafo o grafo a ser apresentado
	 */
	public EditorAplicativoGrafo(Grafo grafo, GUI frame)
	{
		this(frame);
                this.grafo=grafo;
		this.add(Leitura);
		setarBarraHorizontal();
		setarBarraVertical();
                                
	}
	
//M�todos Get...

	/**
	 * Retorna o grafo atual do editor.
	 *
	 * @return o grafo apresentado
	 */
	public Grafo getGrafo()
	{
		return grafo;
	}
	
	/**
	 * Retorna o menu utilizado pelo pacote GEDE quando ocorrer um evento sobre 
	 * uma aresta.
	 *
	 * @return o menu que cont�m as op��es para altera��o das informa��es da aresta
	 */
	public JMenuMouse getMenuMouseAresta()
	{
		return getAreaGrafo().getJMenuMouseAresta();
	}
	
	/**
	 * Retorna o menu utilizado pelo pacote GEDE quando ocorrer um evento sobre 
	 * um r�tulo.
	 *
	 * @return o menu que cont�m as op��es para altera��o das informa��es do r�tulo
	 */
	public JMenuMouse getMenuMouseRotulo()
	{
		return getAreaGrafo().getJMenuMouseRotulo();
	}
	
	/**
	 * Retorna o menu utilizado pelo pacote GEDE quando ocorrer um evento sobre 
	 * um v�rtice.
	 *
	 * @return o menu que cont�m as op��es para altera��o das informa��es do v�rtice
	 */
	public JMenuMouse getMenuMouseVertice()
	{
		return getAreaGrafo().getJMenuMouseVertice();
	}
	
	/**
	 * Retorna o menu utilizado pelo pacote GEDE quando ocorrer um evento sobre nenhum dos 
	 * componentes.Este menu possui op��es genericas do grafo.
	 *
	 * @return o menu que cont�m as op��es para altera��o das informa��es gerais
	 */
	public JMenuMouse getMenuMouse()
	{
		return getAreaGrafo().getJMenuMouse();
	}
	
	/**
	 * Retorna o valor que define se o grafo pode ser editado pelo usu�rio final.
	 * 
	 *
	 * @return o valor que definindo se o grafo pode ser editado ou somente apresentado
	 * @see EditorAplicativoGrafo#EDITAR
	 * @see EditorAplicativoGrafo#NAO_EDITAR
	 */
	public int getEditar()
	{
		return editar;
	}
	
	/**
	 * Retorna o boleano indicando se o tecla Ctrl est� pressionada pelo usu�rio.
	 * Caso seja verdadeiro, a tecla est� pressionada e falso caso contr�rio.
	 *
	 * @return o valor que indicando se a tecla est� pressionada ou n�o
	 */
	public boolean getTeclaCtrlPressionada()
	{
		return teclaCtrlPressionada;
	}
	
	/**
	 * Retorna o boleano indicando se o tecla Ctrl + Z est� pressionada pelo usu�rio.
	 * Caso seja verdadeiro, a tecla est� pressionada e falso caso contr�rio.
	 *
	 * @return o valor que indicando se as teclas est�o pressionadas ou n�o
	 */
	public boolean getTeclaCtrlZPressionada()
	{
		return teclaCtrlZPressionada;
	}
	
	/**
	 * Retorna o boleano indicando se o tecla Shift est� pressionada pelo usu�rio.
	 * Caso seja verdadeiro, a tecla est� pressionada e falso caso contr�rio.
	 *
	 * @return o valor que indicando se a tecla est� pressionada ou n�o
	 */
	public boolean getTeclaShiftPressionada()
	{
		return teclaShiftPressionada;
	}
	
	/**
	 * Retorna o boleano indicando se o tecla delete est� pressionada pelo usu�rio.
	 * Caso seja verdadeiro, a tecla est� pressionada e falso caso contr�rio.
	 *
	 * @return o valor que indicando se a tecla est� pressionada ou n�o
	 */
	public boolean getTeclaDeletePressionada()
	{
		return teclaDeletePressionada;
	}
	
//M�todos Set..

	/**
	 * Altera o grafo apresnetado no editor.
	 *
	 * @param grafo o novo grafo a ser apresentado
	 */
	public void setGrafo(Grafo grafo)
	{
		this.grafo = grafo;
		getAreaGrafo().setGrafo(grafo);

		setarBarraHorizontal();
		setarBarraVertical();

		getAreaGrafo().repaint();
	}
	
	/**
	 * Altera o menu utilizado pelo GEDE quando houver um evento sobre uma aresta.
	 *
	 * @param menuMouse o menu de aresta do editor
	 */
	public void setMenuMouseAresta(JMenuMouse menuMouse)
	{
		getAreaGrafo().setJMenuMouseAresta(menuMouse);
	}
	
	/**
	 * Altera o menu utilizado pelo GEDE quando houver um evento sobre um r�tulo.
	 *
	 * @param menuMouse o menu do r�tulo do editor
	 */
	public void setMenuMouseRotulo(JMenuMouse menuMouse)
	{
		getAreaGrafo().setJMenuMouseRotulo(menuMouse);
	}
	
	/**
	 * Altera o menu utilizado pelo GEDE quando houver um evento sobre um v�rtice.
	 *
	 * @param menuMouse o menu de v�rtice do editor
	 */
	public void setMenuMouseVertice(JMenuMouse menuMouse)
	{
		getAreaGrafo().setJMenuMouseVertice(menuMouse);
	}
	
	/**
	 * Altera o menu utilizado pelo GEDE quando houver um evento sobre nenhum dos 
	 * componentes contidos na �rea de apresenta��o.
	 *
	 * @param menuMouse o menu do editor
	 */
	public void setMenuMouse(JMenuMouse menuMouse)
	{
		getAreaGrafo().setJMenuMouse(menuMouse);
	}
	
	/**
	 * Altera a informa��o do editor para permitir ou n�o edi��o do grafo.
	 *
	 * @param editar o valor indicado se o grafo pode ser ou n�o editado
	 * @see EditorAplicativoGrafo#EDITAR
	 * @see EditorAplicativoGrafo#NAO_EDITAR
	 */
	public void setEditar(int editar)
	{
		if ((editar == NAO_EDITAR) || (editar == EDITAR))
			this.editar = editar;
		else
			this.editar = EDITAR;
	}
	
	/**
	 * Chama o m�todo <B>repaint</B> da classe AreaAplicativo para atualizar a apresenta��o
	 * do grafo na tela.
	 */
	public void repintarArea()
	{
		getAreaGrafo().repaint();
	}
	
	/**
	 * Atualiza os valores das barras de rolagem horizontal e vertical. Este m�todo 
	 * � chamado automaticamente pela classe AreaAplicativo quando o usu�rio final 
	 * arrasta um componente dentro do editor.
	 */
	public void setBarrasRolagem()
	{		
		setarBarraHorizontal();
		setarBarraVertical();
	}
	
// M�todos de tratamento de eventos

	/**
	 * M�todo chamado pela classe AreaAplicativo sempre que o usu�rio final pressionar
	 * a tecla CTRL e movimentar o mouse sobre um componente contido dentrio do
	 * editor. Ele � muito util quando deseja-se chamar uma nova URL na Web 
	 * ou executar um determinada tarefa.
	 *
	 * Para o pacote GEDE, nenhuma implementa��o deste m�todo � feita e portanto 
	 * as novas aplica��es dever�o sobrescrever este m�todo para executar suas
	 * tarefas.
	 *
	 * @param e o evento de mouse gerado sobre o componente
	 * @param objetoClicado o componente do editor que sofreu a chamada do evento
	 */
	public void movimentoMouse(MouseEvent e, Object objetoClicado){}
	
	/**
	 * M�todo chamado pela classe AreaAplicativo sempre que usu�rio final executar
	 * um clique duplo sobre algum componente do editor.
	 *
	 * Para o pacote GEDE, nenhuma implementa��o deste m�todo � feita e portanto 
	 * as novas aplica��es dever�o sobrescrever este m�todo para executar suas
	 * tarefas.
	 *
	 * @param e o evento de mouse gerado sobre o componente
	 * @param objetoClicado o componente do editor que sofreu a chamada do evento
	 */
	public void cliqueDuploMouse(MouseEvent e, Object objetoClicado){}
	
	/**
	 * Este m�todo � chamado sempre que o usu�rio final movimetar as barras de 
	 * rolagem do editor. Ele atualiza a AreaAplicativo onde o grafo est� sendo 
	 * apresentado.
	 *
	 * @param e o evento de barra gerado pelo usu�rio
	 */
    @Override
	public void adjustmentValueChanged(AdjustmentEvent e)
	{
		if (e.getSource() == barraHorizontal)
		{
			//Move o painel na Horizontal
			getAreaGrafo().setBounds(barraHorizontal.getValue() * -1,
								getAreaGrafo().getY(),
								getWidth()-25 + barraHorizontal.getValue(),
								getHeight()-44 + barraVertical.getValue());
			
			posicaoAtualBarraHorizontal = barraHorizontal.getValue();
		}
		
		if (e.getSource() == barraVertical)
		{
			//Move o painel na Vertical...
			getAreaGrafo().setBounds(getAreaGrafo().getX(),
								barraVertical.getValue() * -1,
								getWidth()-25 + barraHorizontal.getValue(),
								getHeight()-44 + barraVertical.getValue());

			posicaoAtualBarraVertical = barraVertical.getValue();
		}
	}
	
	/**
	 * Este m�todo � chamado sempre que o usu�rio final redimensionar o tamanho
	 * da janela do editor. Ele atualiza a AreaAplicativo onde o grafo est� sendo 
	 * apresentado.
	 *
	 * @param e o evento de janela gerado pelo usu�rio
	 */
    @Override
	public void componentResized(ComponentEvent e)
	{
		setarBarraHorizontal();
		setarBarraVertical();
	}
	
	/**
	 * Este m�todo � chamado sempre que o usu�rio mover a janela do editor.
	 *
	 * @param e o evento de janela gerado pelo usu�rio
	 */
    @Override
	public void componentMoved(ComponentEvent e){}
	
	/**
	 * Este m�todo � chamado sempre que a janela do editor for mostrada.
	 *
	 * @param e o evento de janela gerado pelo usu�rio
	 */
    @Override
	public void componentShown(ComponentEvent e){}
	
	/**
	 * Este m�todo � chamado sempre que a janela do editor ficar escondida.
	 *
	 * @param e o evento de janela gerado pelo usu�rio
	 */
    @Override
	public void componentHidden(ComponentEvent e){}
	
//Metodos de evento do teclado
	
	/**
	 * M�todo invocado sempre que uma tecla for digitada pelo usu�rio final.
	 * 
	 * @param e o evento do teclado
	 */
    @Override
	public void keyTyped(KeyEvent e){}
	
	/**
	 * M�todo invocado sempre que uma tecla for pressionada pelo usu�rio final.
	 * 
	 * @param e o evento do teclado
	 */
    @Override
	public void keyPressed(KeyEvent e)
	{
            
           int tecla = e.getKeyCode();
            System.out.println("TECLA PRESSIONADA: "+tecla);

		if (e.getKeyCode() == KeyEvent.VK_Z)
		{
			if (teclaCtrlPressionada)
			{
				//Ctrl + Z
				teclaCtrlZPressionada = true;
			}
		}
		else if (e.getKeyCode() == KeyEvent.VK_DELETE)
		{
			//Captura o tecla delete...
			teclaDeletePressionada = true;
			getAreaGrafo().keyPressed(e);
		}
		else if (e.getKeyCode() == KeyEvent.VK_SHIFT)
		{
			//Captura a tecla Shift
			teclaShiftPressionada = true;
		}
		else if (e.getKeyCode() == KeyEvent.VK_CONTROL)
		{
			//Captura a tecla Control
			teclaCtrlPressionada = true;
		}
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			//Tecla seta para a Direita
			if (barraHorizontal.isEnabled())
				barraHorizontal.setValue(barraHorizontal.getValue() + 1);
		}
		else if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			//Tecla seta para a Direita
			if (barraHorizontal.isEnabled())
				barraHorizontal.setValue(barraHorizontal.getValue() - 1);
		}
		else if (e.getKeyCode() == KeyEvent.VK_UP)
		{
			//Tecla seta para Cima
			if (barraVertical.isEnabled())
				barraVertical.setValue(barraVertical.getValue() - 1);
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			//Tecla seta para baixo
			if (barraVertical.isEnabled())
				barraVertical.setValue(barraVertical.getValue() + 1);
		}
		else if (e.getKeyCode() == KeyEvent.VK_TAB)
                {
                    //Desmarcar todos os componentes do grafo
                    getAreaGrafo().desmarcarTodosComponentes();
                                       
                    //Verifica o tipo de navegação ativada
                    if(IDlista == 0)
                    {
                        //Pega a lista de todos os componentes
                        componentes = grafo.getComponentesOrdenados();
                    }
                    else
                    {
                        if (IDlista == 1)
                        {
                            //pega a lista de todos os vertices
                            ArrayList<Vertice> verticesOrdenados = grafo.getVerticesOrdenados();
                                
                            //Converter o vertice para o tipo componente
                            componentes = (ArrayList<Componente>) verticesOrdenados.clone();
                                
                        }
                        else
                        {
                            if(IDlista ==2 )
                            {
                                //pega a lista de todas as arestas
                                ArrayList<Aresta> arestasOrdenadas = grafo.getArestasOrdenadas();
                                
                                //Converter a aresta para o tipo componente
                                componentes = (ArrayList<Componente>) arestasOrdenadas.clone();
                            }
                            else
                            {
                                if(IDlista == 3)
                                {
                                    //pega a lista de todos os atores
                                    ArrayList<Vertice> atoresOrdenados = grafo.getListaAtor();
                                
                                    //Converter o ator para o tipo componente
                                    componentes = (ArrayList<Componente>) atoresOrdenados.clone();                                    
                                }
                                else
                                {
                                    if(IDlista == 4)
                                    {
                                        //pega a lista de todos os casos de uso
                                        ArrayList<Vertice> casoOrdenados = grafo.getListaCasoUso();
                                
                                        //Converter o caso de uso para o tipo componente
                                        componentes = (ArrayList<Componente>) casoOrdenados.clone();     
                                    }
                                    else
                                    {
                                            //pega a lista das associações do componente selecionado
                                            ArrayList<Aresta> arestasVertice = verticeFoco.getArestas();

                                            //Converter a associação para o tipo componente
                                            componentes = (ArrayList<Componente>) arestasVertice.clone();                                            
                                        
                                    }
                                }
                            }
                                
                        }
                    }
                    
                    //verifica se a navegação é para frente ou para trás
                    if(this.teclaShiftPressionada==true)
                    {
                        //Verifica se já esta no primeiro elemento da lista, 
                        //se já estiver ele volta para o último elemento
                        if (i<1)
                        {
                            i=componentes.size();
                        }

                        i--;                                                                                 
                    }
                    else
                    {
                        //Verifica se já esta no último elemento da lista, 
                        //se já estiver ele volta para o primeiro elemento
                        System.out.println("tamanho = "+i);
                        if (i>(componentes.size()-2))
                        {
                            i=-1;
                        }
                        
                        i++;
                                                                                        
                    }
                    
                    if(!componentes.isEmpty())
                    {
                        //Seleciona o componente
                        grafo.SelecionarComponente(componentes.get(i));
                    
 
                    }
                }
                else if (e.getKeyCode() == KeyEvent.VK_CONTEXT_MENU)
		{
                    //Tecla que representa botao direito do mouse
                    getAreaGrafo().getJMenuMouseVertice().menu(grafo.getTodosVertices()[i-1], grafo.getTodosVertices()[i-1].getX()+(grafo.getTodosVertices()[i-1].getLargura()/2), grafo.getTodosVertices()[i-1].getY()+(grafo.getTodosVertices()[i-1].getAltura()/2));               
		}
                
                //Teclas F2 PRESSIONADA - ATIVAR NAVEGAÇÃO PADRÃO
                else if (e.getKeyCode() == KeyEvent.VK_F2)
                {
                    this.NavegacaoPadrao();
                }
             
                //Teclas F3 PRESSIONADA - ATIVAR/DESATIVAR NAVEGAÇÃO POR VERTICES
                else if (e.getKeyCode() == KeyEvent.VK_F3)
                {
                    this.NavegacaoVertice();
                }
                
                //Teclas F4 PRESSIONADA - ATIVAR/DESATIVAR NAVEGAÇÃO POR ARESTAS
                else if (e.getKeyCode() == KeyEvent.VK_F4)
                {
                    this.NavegacaoAresta();
                }
               
                //Teclas F5 PRESSIONADA - ATIVAR/DESATIVAR NAVEGAÇÃO POR ATORES
                else if (e.getKeyCode() == KeyEvent.VK_F5)
                {
                    this.NavegacaoAtor();
                }
        
                //Teclas F6 PRESSIONADA - ATIVAR/DESATIVAR NAVEGAÇÃO POR CASOS DE USO
                else if (e.getKeyCode() == KeyEvent.VK_F6)
                {
                    this.NavegacaoCaso();
                }
                
                //Teclas CTRL+E PRESSIONADA - LER COMPONENTE SELECIONADO
                else if (e.getKeyCode() == KeyEvent.VK_E)
                {
                    if (teclaCtrlPressionada)
                    {
                        if (componentes!=null)
                        {
                            this.leitura(componentes.get(i).getDescricao());
                        }
                        else
                        {
                            this.leitura("Não há nenhum componente selecionado");
                        }
                    }                   
                }
 
                 //Teclas CTRL+L PRESSIONADA - LOCALIZAR COMPONENTE
                else if (e.getKeyCode() == KeyEvent.VK_L)
                {
                    if (teclaCtrlPressionada)
                    {
                        menu.localizarPorString();
                    }                   
                }
                
                else if (e.getKeyCode() == KeyEvent.VK_S)
                {
                    //Teclas CTRL+S PRESSIONADA - LER ASSOCIAÇÕES DE UM DETERMINADO COMPONENTE
                    if (teclaCtrlPressionada)
                    {                       
                        this.NavegacaAssociacaoVertice();
                    }                   
                }
                                
                //Tecla home ativada retorna ao primeiro elemento da lista
                else if (e.getKeyCode() == KeyEvent.VK_HOME)
                {
                    
                    if (componentes==null)
                    {
                        //Pega a lista de todos os componentes
                        componentes = grafo.getComponentesOrdenados();  
                    }
                    
                    //Realiza o backup da lista de componente, pois esta será
                    //será zerada pelo método desmarcar todos os componentes do grafo
                    ArrayList<Componente> componentesBackup;
                    componentesBackup = componentes;
                    
                    //Desmarcar todos os componentes do grafo
                    getAreaGrafo().desmarcarTodosComponentes();
                    
                    //Volta a lista a lista para os componentes
                    componentes = componentesBackup;
                    
                    //Seleciona o primeiro componente da lista
                    i=0;
                    
                    //Seleciona o componente
                    grafo.SelecionarComponente(componentes.get(i));
 

                }
                
                //Tecla end ativada retorna ao ultimo elemento da lista
                else if (e.getKeyCode() == KeyEvent.VK_END)
                {
                    
                    if (componentes==null)
                    {
                        //Pega a lista de todos os componentes
                        componentes = grafo.getComponentesOrdenados();  
                    }
                    
                    //Realiza o backup da lista de componente, pois esta será
                    //será zerada pelo método desmarcar todos os componentes do grafo
                    ArrayList<Componente> componentesBackup;
                    componentesBackup = componentes;
                    
                    //Desmarcar todos os componentes do grafo
                    getAreaGrafo().desmarcarTodosComponentes();
                    
                    //Volta a lista a lista para os componentes
                    componentes = componentesBackup;
                    
                    //Seleciona o primeiro componente da lista
                    i=(componentes.size()-1);
                    
                    //Seleciona o componente
                    grafo.SelecionarComponente(componentes.get(i));
 

                }
        }
	
	/**
	 * M�todo invocado sempre que uma tecla for liberada pelo usu�rio final.
	 *
	 * @param e o evento do teclado
	 */
    @Override
	public void keyReleased(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_Z)
		{
			teclaCtrlZPressionada = false;		
		}
		if (e.getKeyCode() == KeyEvent.VK_DELETE)
		{
			//Captura o tecla delete...
			teclaDeletePressionada = false;
		}
		else if (e.getKeyCode() == KeyEvent.VK_SHIFT)
		{
			//Libera a tecla Shift
			teclaShiftPressionada = false;
		}
		else if (e.getKeyCode() == KeyEvent.VK_CONTROL)
		{
			//Libera a tecla CTRL
			teclaCtrlPressionada = false;
			teclaCtrlZPressionada = false;
		}
        }

//	M�todos privados
	private void setarBarraHorizontal()
	{
		int maiorPosicaoX;
		
		maiorPosicaoX = verificarMaiorPosicaoX() + DISTANCIA_EIXO_X;
		
		if (maiorPosicaoX > getWidth())
		{
			barraHorizontal.setEnabled(true);
			barraHorizontal.setMinimum(0);
			barraHorizontal.setMaximum(maiorPosicaoX - getWidth());
			barraHorizontal.setValue(posicaoAtualBarraHorizontal);
			barraHorizontal.setUnitIncrement(20);
		}
		else
		{
			getAreaGrafo().setBounds(0, 0, getWidth()-25, getHeight()-44);
			barraHorizontal.setEnabled(false);
			posicaoAtualBarraHorizontal = 0;
		}
	}
	
	private void setarBarraVertical()
	{
		int maiorPosicaoY;
		
		maiorPosicaoY = verificarMaiorPosicaoY() + DISTANCIA_EIXO_Y;
		
		if (maiorPosicaoY > getHeight())
		{
			barraVertical.setEnabled(true);
			barraVertical.setMinimum(0);
			barraVertical.setMaximum(maiorPosicaoY - getHeight());
			barraVertical.setValue(posicaoAtualBarraVertical);
			barraVertical.setUnitIncrement(20);
		}
		else
		{
			getAreaGrafo().setBounds(0, 0, getWidth()-25, getHeight()-44);
			barraVertical.setEnabled(false);
			posicaoAtualBarraVertical = 0;
		}
	}
	
	private int verificarMaiorPosicaoX()
	{
		int 	i, j, pontoFinalX, eixoX, quebras[];
		Vertice verticeTemp;
		Aresta	arestas[];
		
		eixoX = 0;
		
		if (grafo == null)
			return eixoX;
			
		for (i = 0; i < grafo.getNumeroTotalVertices(); i++)
		{
			verticeTemp = grafo.getVertice(i);
			
			pontoFinalX = verticeTemp.getX() + verticeTemp.getLargura();
			
			if (pontoFinalX > eixoX)
				eixoX = pontoFinalX;
		}
		
		arestas = grafo.getTodasArestas();
		if (arestas != null)
		{
			for (i = 0; i < arestas.length; i++)
			{
				quebras = arestas[i].getTodasQuebrasX();
				
				if (quebras != null)
				{
					for (j = 0; j < quebras.length; j++)
					{
						pontoFinalX = quebras[j];
						if (pontoFinalX > eixoX)
							eixoX = pontoFinalX;
					}
				}
			}
		}
		
		return eixoX;
	}
	
	private int verificarMaiorPosicaoY()
	{
		int 	i, j, pontoFinalY, eixoY, quebras[];
		Vertice verticeTemp;
		Aresta	arestas[];
		
		eixoY = 0;
		
		if (grafo == null)
			return eixoY;
			
		for (i = 0; i < grafo.getNumeroTotalVertices(); i++)
		{
			verticeTemp = grafo.getVertice(i);
			
			pontoFinalY = verticeTemp.getY() + verticeTemp.getAltura();
			
			if (pontoFinalY > eixoY)
				eixoY = pontoFinalY;
		}
		
		arestas = grafo.getTodasArestas();
		if (arestas != null)
		{
			for (i = 0; i < arestas.length; i++)
			{
				quebras = arestas[i].getTodasQuebrasY();
				
				if (quebras != null)
				{
					for (j = 0; j < quebras.length; j++)
					{
						pontoFinalY = quebras[j];
						if (pontoFinalY > eixoY)
							eixoY = pontoFinalY;
					}
				}
			}
		}
		return eixoY;
	}
        
        //Realiza a leitura através do leitor de tela
        public void leitura(String descricao)
        {
            Leitura.getAccessibleContext().setAccessibleName("");
            Leitura.getAccessibleContext().setAccessibleDescription(descricao);
            
            
            Leitura.requestFocus();

            this.requestFocus();
            
        }

        public void selecionarComponente(Componente c)
        {
 
            //Realiza o backup da lista de componente, pois esta será
            //será zerada pelo método desmarcar todos os componentes do grafo
            ArrayList<Componente> componentesBackup;
            componentesBackup = componentes;
            
            //Desmarcar todos os componentes do grafo
            getAreaGrafo().desmarcarTodosComponentes();
            
            int indexOf=0;
            try{
                //Recupera a lista de componentes
                componentes = componentesBackup;
            
                //Recebe o indice do componente selecionado
                indexOf = componentes.indexOf(c);
            }catch(java.lang.NullPointerException erro)
            {
                i=-1;
            }
            
            if(indexOf==-1||componentes==null)
            {
                
                //Pega a lista de todos os componentes
                componentes = grafo.getComponentesOrdenados();
       
                //Recebe o indice do componente selecionado
                indexOf = componentes.indexOf(c);
                
                //Reseta os modos de navegação
                NavegacaoCasos=false;
                NavegacaoAtores=false;
                NavegacaoAresta=false;
                NavegacaoVertice=false;
                IDlista=0;
            }
            
            //seta o contador para o componente selecionado
            i=indexOf;
                      
            //Seleciona o componente
            grafo.SelecionarComponente(componentes.get(i));
                    

            
        } 
        
        public void zerarLista() {
            componentes = null;
        }


        public void setMenu(Menu menu) {
            this.menu =  menu;
        }

        public void NavegacaoPadrao(){
            IDlista=0;
            i=-1;
            NavegacaoCasos=false;
            NavegacaoAtores=false;
            NavegacaoAresta=false;
            NavegacaoVertice=false;
            this.leitura("Navegação padrão ativada");            
        }
        
        public void NavegacaoVertice(){
            if(NavegacaoVertice==true)
            {
                //NAVEGAÇÃO POR VERTICES DESATIVADA
                NavegacaoVertice=false;
                IDlista=0;
                this.leitura("Navegação padrão ativada");
            }
            else
            {      
                //NAVEGAÇÃO POR VERTICES ATIVADA;
                NavegacaoCasos=false;
                NavegacaoAtores=false;
                NavegacaoAresta=false;
                NavegacaoVertice=true;
                IDlista=1;           
                this.leitura("Navegação por vertices ativada");
            }
            //reseta o contador
            i=-1;            
        }

        public void NavegacaoAresta(){
            if(NavegacaoAresta==true)
            {
            //NAVEGAÇÃO POR ARESTAS DESATIVADA
            NavegacaoAresta=false;
            IDlista=0;   
            this.leitura("Navegação padrão ativada");
            }
            else
            {
            //NAVEGAÇÃO POR ARESTAS ATIVADA
            NavegacaoCasos=false;
            NavegacaoAtores=false;
            NavegacaoAresta=true;
            NavegacaoVertice=false;
            IDlista=2;
            this.leitura("Navegação por arestas ativada");
            }
            i=-1;            
        }

        public void NavegacaoAtor(){
            if(NavegacaoAtores==true)
            {
                //DESATIVA A NAVEGAÇÃO POR ATORES
                NavegacaoAtores=false;
                IDlista=0;   
                this.leitura("Navegação padrão ativada");
            }
            else
            {
                //ATIVA A NAVEGAÇÃO POR ATORES
                NavegacaoCasos=false;
                NavegacaoAtores=true;
                NavegacaoAresta=false;
                NavegacaoVertice=false;
                IDlista=3;
                this.leitura("Navegação por atores ativada");
            }
            //reseta o contador
            i=-1;            
        }

        public void NavegacaoCaso(){
            if(NavegacaoCasos==true)
            {
                //DESATIVA A NAVEGAÇÃO POR CASOS DE USO
                NavegacaoCasos=false;
                IDlista=0;   
                this.leitura("Navegação padrão ativada");
            }
            else
            {
                //ATIVA A NAVEGAÇÃO POR CASOS DE USO
                NavegacaoCasos=true;
                NavegacaoAtores=false;
                NavegacaoAresta=false;
                NavegacaoVertice=false;
                IDlista=4;
                this.leitura("Navegação por casos de uso ativada");
            }
            //reseta o contador
            i=-1;            
        }

        public void NavegacaAssociacaoVertice(){
            if(teclaCtrlSPressionada)
            {
                System.out.println("NAVEGACAO PELAS ASSOCIACOES DESATIVADA");
                teclaCtrlSPressionada=false;
                IDlista = IDlistaAnterior;
                i=j;
                this.leitura("Navegação pelas associações do "+verticeFoco.getDescricao()+" desativada");
                i=-1;
            }
            else
            {
                if(getAresta()==0||verticeFoco!=null)
                {
                    System.out.println("NAVEGACAO PELAS ASSOCIACOES ATIVADA");
                    teclaCtrlSPressionada=true;
                    IDlistaAnterior = IDlista;
                    IDlista=5;
                    j=i;
                    this.leitura("Navegação pelas associações do "+verticeFoco.getDescricao()+" ativada"); 
                    i=-1;
                }
                else
                {
                    this.leitura("Não há vertice selecionado"); 
                }
            }            
        }

    public String getVerticeSelecionado() {
        
        String nome = "";
        
        if(verticeFoco!=null)
        {
            nome = this.verticeFoco.getDescricao();
        }

        return nome;
    }

    /**
     * @return the aresta
     */
    public int getAresta() {
        return aresta;
    }

    public void setAresta(int a) {
        aresta = a;
    }

    /**
     * @param verticeSelecionado the verticeSelecionado to set
     */
    public void setVerticeFocado(Vertice verticeSelecionado) {
        this.verticeFoco = verticeSelecionado;
    }

    public Vertice getVerticeFocado() {
        return this.verticeFoco;
    }
    
    /**
     * @return the areaGrafo
     */
    public AreaAplicativo getAreaGrafo() {
        return areaGrafo;
    }

    /**
     * @param areaGrafo the areaGrafo to set
     */
    public void setAreaGrafo(AreaAplicativo areaGrafo) {
        this.areaGrafo = areaGrafo;
    }

    /**
     * @return the frame
     */
    public GUI getFrame() {
        return frame;
    }

    private void eventos() {

        this.addFocusListener( new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
               
            }

            @Override
            public void focusLost(FocusEvent e) {
                
                if (!grafo.getComponentesOrdenados().isEmpty())
                {
                    //Desmarcar todos os componentes do grafo
                    getAreaGrafo().desmarcarTodosComponentes();
                }
            }
                

       });
    }

 
}