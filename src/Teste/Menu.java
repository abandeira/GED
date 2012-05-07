package Teste;




import diagram.Grafo;
import diagram.componente.*;
import diagram.editor.EditorAplicativoGrafo;
import diagram.organizarAlfabeticamente;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.event.MenuEvent;

public class Menu extends JMenuBar {

        private GUI frame;
                
	private JMenu arquivo, editar, ferramentas, ajuda;
	
	//o menu novo sera um submenu do menu arquivo
	private JMenu novo;
	
        //menu selecionar, lista de atores, lista de casos de uso, navegacao
        private JMenu selecionar, AtorLista, CasoLista, Navegacao;
        
        //itens de menu navegacao por vertice, navegacao por aresta,
        //navegacao por ator, navegacao por caso de uso, navegacao por associacoes de um caso de uso
        private JMenuItem NavegacaoPadrao, NavegacaoVertice, NavegacaoAresta, NavegacaoAtor,
                NavegacaoCaso;
        private JMenuItem NavegacaoAssociacoesVertice = null;
        
	//itens do menu arquivo
	private JMenuItem abrir, salvar, salvarComo, imprimir, sair;
	
	//itens do menu editar
	private JMenuItem copiar, colar, recortar;
	
	//itens do menu ajuda
	private JMenuItem sobre, teclasDeAtalho, comoUsar;
	
	//itens do menu novo e localizar por string
	private JMenuItem casoDeUso, classe, sequencia, localizarString;
	
        //menu para adicionar os vertices de um diagrama de caso de uso
	private JMenu Inserir,CasodeUsoMenu;
        
        //itens de menu para diagrama de caso de uso
        private JMenuItem Ator, CasodeUso, Associacao,Include,Extend;
        
	//construindo o menu
	public Menu(JMenuBar menu, GUI frame) 
        {
            this.frame = frame;
            
            //instanciando os menus
            arquivo = new JMenu("Arquivo");
            Inserir = new JMenu("Inserir");
            CasodeUsoMenu = new JMenu("Caso de Uso");
            editar = new JMenu("Editar");
            ferramentas = new JMenu("Ferramentas");
            ajuda = new JMenu("Ajuda");
            novo = new JMenu("Novo");

            //atribuindo teclas de atalho que serao alt + a letra entre parentes 
            arquivo.setMnemonic('a');
            editar.setMnemonic('e');
            ferramentas.setMnemonic('f');
            ajuda.setMnemonic('j');
            novo.setMnemonic('n');
            CasodeUsoMenu.setMnemonic('c');


            //Criando o menu adicionar
            Inserir.add(CasodeUsoMenu);

            //instanciando os itens do menu novo e os atribuindo ao menu
            casoDeUso = new JMenuItem("Diagrama de Caso de Uso");
            classe = new JMenuItem("Diagrama de Classe");
            sequencia = new JMenuItem("Diagrama de sequencia");
            novo.add(casoDeUso	);
            novo.add(classe);
            novo.add(sequencia);

            //atribuindo teclas de atalho aos itens do menu novo
            casoDeUso.setMnemonic('c');
            classe.setMnemonic('l');
            sequencia.setMnemonic('s');


            //instanciando os itens do menu arquivo e os atribuindo ao menu
            abrir = new JMenuItem("Abrir");
            salvar = new JMenuItem("Salvar");
            salvarComo = new JMenuItem("Salvar Como");
            imprimir = new JMenuItem("Imprimir");
            sair = new JMenuItem("Sair");
            arquivo.add(novo);
            arquivo.add(abrir);
            arquivo.add(salvar);
            arquivo.add(salvarComo);
            arquivo.add(imprimir);
            arquivo.add(sair);

            //atribuindo teclas de atalho aos itens do menu arquivo
            sair.setMnemonic('x');
            abrir.setMnemonic('a');
            salvar.setMnemonic('s');
            imprimir.setMnemonic('i');

            //instanciando os itens do menu editar e os atribuindo ao menu
            copiar = new JMenuItem("Copiar");
            colar = new JMenuItem("Colar");
            recortar = new JMenuItem("Recortar");
            selecionar = new JMenu("Selecionar");
            editar.add(copiar);
            editar.add(colar);
            editar.add(recortar);
            editar.add(selecionar);

            //atribuindo teclas de atalho aos itens do menu editar
            copiar.setMnemonic('c');
            colar.setMnemonic('v');
            recortar.setMnemonic('r');
            selecionar.setMnemonic('l');

            //instanciando os itens do menu ferramentas
            Navegacao = new JMenu("Navegacao");
            ferramentas.add(Navegacao);

            //instanciando os itens do menu navegacao
            NavegacaoPadrao = new JMenuItem ("Navegação Padrão");
            NavegacaoVertice = new JMenuItem("Navegação por Vértices");
            NavegacaoAresta = new JMenuItem("Navegação por Arestas");
            NavegacaoAtor = new JMenuItem("Navegação por Atores");
            NavegacaoCaso = new JMenuItem("Navegação por Casos de Uso");
            NavegacaoAssociacoesVertice = new JMenuItem("");   
            Navegacao.add(NavegacaoPadrao);
            Navegacao.add(NavegacaoVertice);
            Navegacao.add(NavegacaoAresta);
            Navegacao.add(NavegacaoAtor);
            Navegacao.add(NavegacaoCaso);
            Navegacao.add(NavegacaoAssociacoesVertice);           


            //instanciando os itens do menu ajuda e os atribuindo ao menu
            sobre = new JMenuItem("Sobre");
            teclasDeAtalho = new  JMenuItem("Teclas de Atalho");
            comoUsar = new JMenuItem("Como Usar");
            ajuda.add(sobre);
            ajuda.add(teclasDeAtalho);
            ajuda.add(comoUsar);

            //atribuindo teclas de atalho aos itens do menu ajuda
            sobre.setMnemonic('s');
            teclasDeAtalho.setMnemonic('t');
            comoUsar.setMnemonic('c');

            //instanciando os itens do menu caso de uso e os atribuindo ao menu
            Ator = new JMenuItem("Ator");
            CasodeUso = new  JMenuItem("Caso de Uso");
            Associacao = new JMenuItem("Associação");
            Include = new JMenuItem("Include");
            Extend = new JMenuItem("Extend");
            CasodeUsoMenu.add(Ator);
            CasodeUsoMenu.add(CasodeUso);
            CasodeUsoMenu.add(Associacao);
            CasodeUsoMenu.add(Include);
            CasodeUsoMenu.add(Extend);

            //atribuindo teclas de atalho aos itens do menu ajuda
            Ator.setMnemonic('a');
            CasodeUso.setMnemonic('c');
            Associacao.setMnemonic('s');
            Include.setMnemonic('i');
            Extend.setMnemonic('e');
        
            //adicionando os menus a barra de menu	
            menu.add(arquivo);
            menu.add(editar);
            menu.add(Inserir);
            menu.add(ferramentas);
            menu.add(ajuda);

            //instanciando o menu Atores e o menu Casos de uso em localizar
            AtorLista = new JMenu("Atores");
            CasoLista = new JMenu("Casos de Uso");
            localizarString = new JMenuItem("Localizar");        
            selecionar.add(AtorLista);
            selecionar.add(CasoLista);
            selecionar.add(localizarString);

            eventos();

            //Desabilita os menus do diagrama de caso de uso
            CasodeUsoMenu.setVisible(false);
            localizarString.setVisible(false);
            AtorLista.setVisible(false);
            CasoLista.setVisible(false);
            Navegacao.setVisible(false);
            NavegacaoPadrao.setVisible(false);
            NavegacaoVertice.setVisible(false);
            NavegacaoAresta.setVisible(false);
            NavegacaoAtor.setVisible(false);
            NavegacaoCaso.setVisible(false);
            NavegacaoAssociacoesVertice.setVisible(false);

            getAccessibleContext().setAccessibleName("Menu");

	}

	private void eventos() { 
		
            sair.addActionListener(new ActionListener() {   
            @Override
                public void actionPerformed(ActionEvent e) {   
                    System.exit(0);   
		}   
            });
		
            //Adicionar um diagrama de caso de uso
            casoDeUso.addActionListener(new ActionListener() {   
            @Override
               public void actionPerformed(ActionEvent e) {
                    int AdicionarAbas = frame.AdicionarAbas();
                    if(AdicionarAbas==1) //A ABA FOI ADICIONADA COM SUCESSO
                    {
                        CasodeUsoMenu.setVisible(true);
                        Navegacao.setVisible(true);
                        frame.getEditorSelecionado().requestFocus();
                    }
               }   
            });
 
            //Adiciona um ator ao diagrama
            Ator.addActionListener(new ActionListener() {   
            @Override
                public void actionPerformed(ActionEvent e) {
                    EditorAplicativoGrafo editor = frame.getEditorSelecionado();
                    Grafo grafo = editor.getGrafo();
                    String nome=""; 
                    while ("".equals(nome)) {
                        //Recebe o nome digitado na variável nome
                        nome = JOptionPane.showInputDialog(editor,"Digite o rótulo do ator?","Digite o rótulo do ator?",1);

                        //Se não foi digitado nenhum nome, pede para digitar um nome válido
                        if ("".equals(nome))  {
                            JOptionPane.showMessageDialog(editor, "Rótulo Inválido");
                        }
                    }
                    
                    //Se não foi clicado em cancelar, cria um ator com o nome digitado
                    if (nome!=null)
                    {
                        //Parametros: codigo, x, y, largura, altura
                        VerticeAtor v1 = new VerticeAtor(1,editor.x,editor.y,50,50,nome);
                        editor.x+=100;
                        editor.y+=100;
                        grafo.setVertice(v1,editor);
                    
                        //Adiciona o novo ator em uma lista de atores
                        grafo.addAtor(v1);
                        
                        if(grafo.getListaAtor().size()==1)
                        {
                            AtorLista.setVisible(true);                   
                            localizarString.setVisible(true);
                            NavegacaoPadrao.setVisible(true);
                            NavegacaoVertice.setVisible(true);
                            NavegacaoAtor.setVisible(true);
                        }
                    }
                    editor.repaint();
                    frame.repaint();
               }   
            });
            
            //Adiciona um ator ao diagrama
            CasodeUso.addActionListener(new ActionListener() {   
            @Override
               public void actionPerformed(ActionEvent e) {
                   EditorAplicativoGrafo editor = frame.getEditorSelecionado();
                   Grafo grafo = editor.getGrafo();
                   String nome="";
                   while ("".equals(nome)) {
                       //Recebe o nome digitado na variável nome
                       nome = JOptionPane.showInputDialog(editor,"Digite o rótulo do Caso de Uso?","Digite o rótulo do Caso de Uso?",1);
                      
                       //Se não foi digitado nenhum nome, pede para digitar um nome válido
                       if ("".equals(nome)) {
                           JOptionPane.showMessageDialog(editor, "Rótulo Inválido");
                       }
                   }
                   
                   //Se não foi clicado em cancelar, cria um caso de uso com o nome digitado
                   if(nome!=null)
                   {
                        // Parametros: codigo, x, y, largura, altura
                        VerticeCasoUso c1 = new VerticeCasoUso (2, editor.x, editor.y, 200, 60, 1,nome);
                        editor.x+=100;
                        editor.y+=100;
                        grafo.setVertice(c1,editor);
                   
                        //Adiciona o novo caso de uso em uma lista de casos de uso
                        grafo.addCasoUso(c1);
                        
                        if(grafo.getListaCasoUso().size()==1)
                        {
                            localizarString.setVisible(true);
                            CasoLista.setVisible(true);
                            NavegacaoPadrao.setVisible(true);
                            NavegacaoVertice.setVisible(true);
                            NavegacaoCaso.setVisible(true);
                        }
                   }
               }   
            });

            //Adiciona uma associação ao diagrama
            Associacao.addActionListener(new ActionListener() {   
            @Override
                public void actionPerformed(ActionEvent e) {
                    EditorAplicativoGrafo editor = frame.getEditorSelecionado();
                    Grafo grafo = editor.getGrafo();
                    //Recebe uma lista de todos os vértices do diagrama
                    Vertice[] opcoes = grafo.getTodosVertices();
                    Vertice resposta;
                    Vertice resposta2;
                    
                    //Recebe o vertice origem da associação
                    resposta = (Vertice) JOptionPane.showInputDialog(editor,"Escolha o vertice origem?","Escolha o vertice origem?",JOptionPane.PLAIN_MESSAGE,null,opcoes,""); 
                          
                    //Verifica se o botão cancelar foi apertado (reposta = null)
                    if(resposta!=null)
                    {
                        //Chama o metodo que retorna os vertices possiveis para uma associação
                        Vertice[] opcoes2 = PossiveisAssociacoes(resposta,opcoes);
                        
                        //Recebe o vertice destino da associação
                        resposta2 = (Vertice) JOptionPane.showInputDialog(editor,"Escolha o vertice destino?","Escolha o vertice destino?",JOptionPane.PLAIN_MESSAGE,null,opcoes2,""); 

                        //Se não foi clicado em cancelar, cria a associação
                        if(resposta2!=null)
                        {
                            // Parametros: codigo, x, y, largura, altura
                            ArestaSimples a1 = new ArestaSimples (resposta,resposta2);
                        
                            //Cria a aresta
                            grafo.setAresta(a1,editor);     
                            
                            if(grafo.getArestasOrdenadas().size()==1)
                            {
                                NavegacaoAresta.setVisible(true);
                            }
                        }
                    }
               }   
            });
            
            //Adiciona um Include ao diagrama
            Include.addActionListener(new ActionListener() {   
            @Override
                public void actionPerformed(ActionEvent e) {
                    EditorAplicativoGrafo editor = frame.getEditorSelecionado();
                    Grafo grafo = editor.getGrafo();
                    //Recebe uma lista de todos os vértices do diagrama
                    Vertice[] opcoes = grafo.getTodosVertices();
                    Vertice resposta;
                    Vertice resposta2;

                    //Recebe o vertice origem do include
                    resposta = (Vertice) JOptionPane.showInputDialog(editor,"Escolha o vertice origem?","Escolha o vertice origem?",JOptionPane.PLAIN_MESSAGE,null,opcoes,""); 

                    //Verifica se o botão cancelar foi apertado (reposta = null)
                    if(resposta!=null)
                    {
                        //Chama o metodo que retorna os vertices possiveis para uma associação
                        Vertice[] opcoes2 = PossiveisAssociacoes(resposta,opcoes);
                    
                        //Recebe o vertice destino do include
                        resposta2 = (Vertice) JOptionPane.showInputDialog(editor,"Escolha o vertice destino?","Escolha o vertice destino?",JOptionPane.PLAIN_MESSAGE,null,opcoes2,""); 
                    
                        //Se não foi clicado em cancelar, cria o include
                        if(resposta2!=null)
                        {
                            // Parametros: codigo, x, y, largura, altura
                            ArestaIncludeExtend a1 = new ArestaIncludeExtend("Include",resposta,resposta2);
                            
                            //Cria o include
                            grafo.setAresta(a1,editor);   
                            
                            if(grafo.getArestasOrdenadas().size()==1)
                            {
                                NavegacaoAresta.setVisible(true);
                            }
                        }
                    }
               }   
            });
           
            //Adiciona um Extend ao diagrama
            Extend.addActionListener(new ActionListener() {   
            @Override
                public void actionPerformed(ActionEvent e) {
                    EditorAplicativoGrafo editor = frame.getEditorSelecionado();
                    Grafo grafo = editor.getGrafo();
                    //Recebe uma lista de todos os vértices do diagrama
                    Vertice[] opcoes = grafo.getTodosVertices();
                    Vertice resposta;
                    Vertice resposta2;

                    //Recebe o vertice origem do extend
                    resposta = (Vertice) JOptionPane.showInputDialog(editor,"Escolha o vertice origem?","Escolha o vertice origem?",JOptionPane.PLAIN_MESSAGE,null,opcoes,""); 

                    //Verifica se o botão cancelar foi apertado (reposta = null)
                    if(resposta!=null)
                    {
                        //Chama o metodo que retorna os vertices possiveis para uma associação
                        Vertice[] opcoes2 = PossiveisAssociacoes(resposta,opcoes);
                    
                        //Recebe o vertice destino do extend
                        resposta2 = (Vertice) JOptionPane.showInputDialog(editor,"Escolha o vertice destino?","Escolha o vertice destino?",JOptionPane.PLAIN_MESSAGE,null,opcoes2,""); 
         
                        //Se não foi clicado em cancelar, cria o extend
                        if(resposta2!=null)
                        {
                            // Parametros: codigo, x, y, largura, altura
                            ArestaIncludeExtend a1 = new ArestaIncludeExtend("Extend",resposta2,resposta);
                   
                            //cria o extend
                            grafo.setAresta(a1,editor); 
                            
                            if(grafo.getArestasOrdenadas().size()==1)
                            {
                                NavegacaoAresta.setVisible(true);
                            }
                        }
                    }
                   
               }   
            });

            //Cria uma lista de atores no menu localizar
            AtorLista.addMenuListener(new javax.swing.event.MenuListener() {
                
                //Quando o meu atores for selecionado, chama o método listarAtores()
                @Override
                public void menuSelected(javax.swing.event.MenuEvent evt) {
                    ListarAtores();
                }

                @Override
                public void menuDeselected(MenuEvent e) {
                
                }

                @Override
                public void menuCanceled(MenuEvent e) {
                
                }
            });

            //Cria uma lista de casos de uso no menu localizar
            CasoLista.addMenuListener(new javax.swing.event.MenuListener() {

                //Quando o menu Casos de uso for selecionado, chama o método listarCasos()
                @Override
                public void menuSelected(javax.swing.event.MenuEvent evt) {
                    ListarCasos();
                }

                @Override
                public void menuDeselected(MenuEvent e) {
                
                }

                @Override
                public void menuCanceled(MenuEvent e) {
                
                }

            });      
            
            //Cria um item de menu navegacao por associações de determinado vertice
            //Quando o menu navegação for selecionado
            Navegacao.addMenuListener(new javax.swing.event.MenuListener() {

                //Quando o menu navegação for selecionado, chama o método NavegacaoPorAssociacoesVertice()
                @Override
                public void menuSelected(javax.swing.event.MenuEvent evt) {
                    EditorAplicativoGrafo editor = frame.getEditorSelecionado();
                    Grafo grafo = editor.getGrafo();
                    String verticeSelecionado = editor.getVerticeSelecionado();
                    System.out.println("AQUI");
                    if(editor.getAresta()==0&&grafo.getArestasOrdenadas().size()>0&&
                            (verticeSelecionado.startsWith("Caso")||verticeSelecionado.startsWith("Ator")))
                    {
                        System.out.println("ENTROU");
                        NavegacaoPorAssociacoesVertice();
                    }
                    else
                    {
                        NavegacaoAssociacoesVertice.setVisible(false);
                    }
                }

                @Override
                public void menuDeselected(MenuEvent e) {
                
                }

                @Override
                public void menuCanceled(MenuEvent e) {
                
                }

            });    

            //Localiza uma ator por uma string passada pelo usuário
            localizarString.addActionListener(new ActionListener() {   
            @Override
                public void actionPerformed(ActionEvent e) {
                    localizarPorString();
                }
            }); 

            //Ativar navegacao padrão
            NavegacaoPadrao.addActionListener(new ActionListener() {   
            @Override
                public void actionPerformed(ActionEvent e) {
                    EditorAplicativoGrafo editor = frame.getEditorSelecionado();
                    editor.NavegacaoPadrao();
                }
            }); 
            
            //Ativar navegacao por vertice
            NavegacaoVertice.addActionListener(new ActionListener() {   
            @Override
                public void actionPerformed(ActionEvent e) {
                    EditorAplicativoGrafo editor = frame.getEditorSelecionado();
                    editor.NavegacaoVertice();
                }
            }); 
            
            //Ativar navegacao por aresta
            NavegacaoAresta.addActionListener(new ActionListener() {   
            @Override
                public void actionPerformed(ActionEvent e) {
                    EditorAplicativoGrafo editor = frame.getEditorSelecionado();
                    editor.NavegacaoAresta();
                }
            }); 

            //Ativar navegacao por ator
            NavegacaoAtor.addActionListener(new ActionListener() {   
            @Override
                public void actionPerformed(ActionEvent e) {
                    EditorAplicativoGrafo editor = frame.getEditorSelecionado();
                    editor.NavegacaoAtor();
                }
            }); 
            
            //Ativar navegacao por caso
            NavegacaoCaso.addActionListener(new ActionListener() {   
            @Override
                public void actionPerformed(ActionEvent e) {
                    EditorAplicativoGrafo editor = frame.getEditorSelecionado();                
                    editor.NavegacaoCaso();
                }
            }); 
              
            //Ativar navegacao por associações do vertice
            NavegacaoAssociacoesVertice.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    EditorAplicativoGrafo editor = frame.getEditorSelecionado();                    
                    editor.NavegacaAssociacaoVertice();
                }
        
            });
        }

        //Método usado para pesquisar um componente na lista de componentes
        //Retorna uma lista de possibilidades
        private ArrayList<Componente> Pesquisar(String nome)
        {
            EditorAplicativoGrafo editor = frame.getEditorSelecionado();
            Grafo grafo = editor.getGrafo();
            //Recebe uma lista todos os componentes do diagrama
            ArrayList<Componente> componentes = grafo.getComponentesOrdenados();
            
            //Cria uma lista onde será amazenado os componetes de acordo com o termo pesquisado
            ArrayList<Componente> PesquisarComponentes = new ArrayList<Componente>();
            
            //Pesquisa os componentes e adiciona a lista aqueles que estejam de acordo com termo pequisado
            for (int i = 0; i < componentes.size(); i++) {
                if (nome.equalsIgnoreCase(componentes.get(i).getRotulo().getTexto()))
                {        
                    PesquisarComponentes.add(componentes.get(i));
                }
            }
            
            //Retorna uma lista de componentes
            return PesquisarComponentes;
        }
        

        private void ListarCasos() {
            //Remove todos os elementos da lista de casos de uso do menu localizar
            CasoLista.removeAll();

            EditorAplicativoGrafo editor = frame.getEditorSelecionado();
            Grafo grafo = editor.getGrafo();
            
            //Recebe todos os casos de uso do diagrama
            ArrayList<Vertice> Verticecasos = grafo.getListaCasoUso();
            
            //Converter o caso de uso para o tipo componente
            ArrayList<Componente> casos = (ArrayList<Componente>) Verticecasos.clone();
            
            //Organiza por ordem alfabetica todos os casos de uso
            Collections.sort(casos, new organizarAlfabeticamente());
            
            for (int i = 0; i < casos.size(); i++) {
                //Cria um item de menu para cada caso de uso e adiciona ao menu casos de uso
                JMenuItem ItemCaso = new JMenuItem(casos.get(i).getRotulo().getTexto());
                CasoLista.add(ItemCaso);  
                Itens(ItemCaso,casos);
            } 
        }
                   
        private void ListarAtores() {
            //Remove todos os elementos da lista de atores do menu localizar
            AtorLista.removeAll();
            
            EditorAplicativoGrafo editor = frame.getEditorSelecionado();
            Grafo grafo = editor.getGrafo();
            
            //Recebe todos os atores do diagrama
            ArrayList<Vertice> Verticeatores = grafo.getListaAtor();
            
            //Converter o caso de uso para o tipo componente
            ArrayList<Componente> atores = (ArrayList<Componente>) Verticeatores.clone();
            
            //Organiza por ordem alfabetica todos os atores
            Collections.sort(atores, new organizarAlfabeticamente());
            
            for (int i = 0; i < atores.size(); i++) {
                //Cria um item de menu para cada ator e adiciona ao menu atores
                JMenuItem ItemAtor = new JMenuItem(atores.get(i).getRotulo().getTexto());
                AtorLista.add(ItemAtor);  
                Itens(ItemAtor,atores);
            } 
        }

    private void Itens(final JMenuItem Item, final ArrayList<Componente> componentes) {
        //Metodo invocado quando um vertice é selecionado no menu localizar
        Item.addActionListener(new ActionListener() {   
        @Override
            public void actionPerformed(ActionEvent e) {
                //recebe o nome do vertice
                String name = Item.getText();
                EditorAplicativoGrafo editor = frame.getEditorSelecionado();
                for (int i = 0; i < componentes.size(); i++) {
                    //Pesquisa o vertice na lista de vertices
                    if (name.contentEquals(componentes.get(i).getRotulo().getTexto()))
                    {
                        //Seleciona o componente
                        Componente c = componentes.get(i);
                        editor.selecionarComponente(c);
                        i=componentes.size();
                    }
                }
            }   
        });
    }
//    
//    public void ReceberEditoreGrafo(EditorAplicativoGrafo editor, Grafo grafo)
//    {
//        System.out.println("aqui");
//        this.editor = editor;
//        this.grafo = grafo;
//    }
    
    public void localizarPorString()
    {
                         String nome="";
                   while ("".equals(nome)) {
                       //Recebe nome a pesquisar
                       nome = JOptionPane.showInputDialog(null,"Digite o rótulo?","Digite o rótulo?",1);
                      
                       //Se não digitado nenhum nome, pede para o usuário digitar
                       if ("".equals(nome)) {
                           JOptionPane.showMessageDialog(null, "Rótulo Inválido");
                       }
                   }
                
                   //Chama o método Pesquisar() que retorna uma lista de possibilidades
                   ArrayList<Componente> Pesquisar = Pesquisar(nome);
                   
                   //Cria um vetor com as possibilidade
                   Componente[] opcoes =  new Componente[Pesquisar.size()];
                   for (int i = 0; i < Pesquisar.size(); i++) {
                      opcoes[i] = Pesquisar.get(i);
                   }
                   
                   //Recebe o componente escolhido
                   Componente resposta = (Vertice) JOptionPane.showInputDialog(null,"Escolha o componente?","Escolha o componente?",JOptionPane.PLAIN_MESSAGE,null,opcoes,""); 
                    
                   EditorAplicativoGrafo editor = frame.getEditorSelecionado();

                   
                   //Seleciona o componente
                   editor.selecionarComponente(resposta);
    }
    
    private Vertice[] PossiveisAssociacoes(Vertice resposta, Vertice[] opcoes)
    {
        ArrayList<Vertice> vertices = resposta.getVertices();

        //Cria um vetor de opções com o qual o vertice origem pode se relacionar
        Vertice[] opcoes2 = new Vertice[opcoes.length-1];
                        
        int j=0;
                        
        //variavel auxiliar para auxilio na operação de exclusão dos vertices já associados ao vertice resposta
        int aux=0;
                        
        //Exclui os vertices que possuem associação com o vertice resposta
        for (int i = 0; i< opcoes.length ;i++)
        {
            for (int k = 0; k < vertices.size() ; k++) 
            {
                if (opcoes[i]==vertices.get(k))
                {
                    aux=1;
                }
            }
            if(aux!=1)
            {                                    
                opcoes2[j]=opcoes[i];
                j++;
            }
            aux=0;
        }
        return opcoes2;          
    }

    public void NavegacaoPorAssociacoesVertice()
    {
        EditorAplicativoGrafo editor = frame.getEditorSelecionado();

        String verticeSelecionado = editor.getVerticeSelecionado();

        NavegacaoAssociacoesVertice.setLabel("Navegação por Associações do "+verticeSelecionado);
        NavegacaoAssociacoesVertice.setVisible(true);     
        
    }
}
