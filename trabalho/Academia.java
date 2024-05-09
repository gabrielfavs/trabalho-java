import java.io.*;
import java.util.*;

class Aluno {
    String nome;
    int idade;
    Nivel nivel;

    public Aluno(String nome, int idade, Nivel nivel) {
        this.nome = nome;
        this.idade = idade;
        this.nivel = nivel;
    }
}

enum Nivel {
    INICIANTE,
    INTERMEDIARIO,
    AVANCADO
}

class Instrutor {
    String nome;
    String especialidade;

    public Instrutor(String nome, String especialidade) {
        this.nome = nome;
        this.especialidade = especialidade;
    }
}

class Treino {
    int numero;
    String nome;
    List<Aluno> alunos;
    Instrutor instrutor;

    public Treino(int numero, String nome, Instrutor instrutor) {
        this.numero = numero;
        this.nome = nome;
        this.alunos = new ArrayList<>();
        this.instrutor = instrutor;
    }

    public void adicionarAluno(Aluno aluno) {
        alunos.add(aluno);
    }

    public void removerAluno(Aluno aluno) {
        alunos.remove(aluno);
    }
}

class FileManager {
    static final String FILENAME = "academia.txt";

    public static void salvar(String data) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILENAME, true))) {
            writer.println(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class Academia {
    static Scanner scanner = new Scanner(System.in);
    static List<Treino> treinos = new ArrayList<>();
    static List<Instrutor> instrutores = new ArrayList<>();
    static List<Aluno> alunos = new ArrayList<>();
    static int contadorTreinos = 1; // Inicializa o contador de treinos

    public static void main(String[] args) {
        while (true) {
            exibirMenu();
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            switch (opcao) {
                case 0:
                    System.out.println("Encerrando o programa.");
                    return; // Encerra o programa
                case 1:
                    adicionarTreino();
                    break;
                case 2:
                    removerTreino();
                    break;
                case 3:
                    pesquisarTreino();
                    break;
                case 4:
                    listarTreinosNumerados();
                    break;
                case 5:
                    listarTreinosOrdenados();
                    break;
                case 6:
                    System.out.println("\nDigite o nome do instrutor que deseja pesquisar:");
                    String nomeInstrutor = scanner.nextLine();
                    PesquisaInstrutor.pesquisarNome(instrutores, nomeInstrutor);
                    break;
                case 7:
                    System.out.println("\nDigite o nome do instrutor que deseja remover:");
                    String nomeInstrutorRemover = scanner.nextLine();
                    RemocaoInstrutor.removerInstrutor(instrutores, nomeInstrutorRemover);
                    break;
                case 8:
                    adicionarAluno();
                    break;
                case 9:
                    removerAluno();
                    break;
                case 10:
                    pesquisarAluno();
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
    }

    static void exibirMenu() {
        System.out.println("\n===== MENU =====");
        System.out.println("0. Finalizar o Programa");
        System.out.println("1. Adicionar um Novo Treino");
        System.out.println("2. Remover um Treino Existente");
        System.out.println("3. Pesquisar um Treino");
        System.out.println("4. Listar Todos os Treinos");
        System.out.println("5. Listar os Treinos Ordenados");
        System.out.println("6. Pesquisar Instrutor por Nome");
        System.out.println("7. Remover Instrutor");
        System.out.println("8. Adicionar um Novo Aluno");
        System.out.println("9. Remover um Aluno Existente");
        System.out.println("10. Pesquisar um Aluno");
        System.out.println("Digite o número correspondente à operação desejada:");
    }

    static void adicionarTreino() {
        System.out.println("\n===== ADICIONAR TREINO =====");
        System.out.println("Digite o nome do novo treino:");
        String nome = scanner.nextLine();

        System.out.println("Digite o nome do instrutor responsável:");
        String nomeInstrutor = scanner.nextLine();

        System.out.println("Digite a especialidade do instrutor:");
        String especialidade = scanner.nextLine();

        Instrutor instrutor = new Instrutor(nomeInstrutor, especialidade);
        instrutores.add(instrutor);
        Treino treino = new Treino(contadorTreinos++, nome, instrutor); // Usa o contador de treinos
        treinos.add(treino);

        FileManager.salvar("Treino adicionado: " + treino.nome);
        System.out.println("Treino adicionado com sucesso!");
    }

    static void removerTreino() {
        System.out.println("\n===== REMOVER TREINO =====");
        System.out.println("Digite o número do treino que deseja remover:");
        int numero = scanner.nextInt();

        boolean encontrado = false;
        for (int i = 0; i < treinos.size(); i++) {
            if (treinos.get(i).numero == numero) {
                treinos.remove(i);
                System.out.println("Treino removido com sucesso!");
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            System.out.println("Nenhum treino encontrado com esse número.");
        }
    }

    static void pesquisarTreino() {
        System.out.println("\n===== PESQUISAR TREINO =====");
        System.out.println("Digite o nome do treino que deseja pesquisar:");
        String nome = scanner.nextLine();

        boolean encontrado = false;
        for (Treino treino : treinos) {
            if (treino.nome.contains(nome)) {
                System.out.println("Treino encontrado: " + treino.nome);
                System.out.println("Instrutor responsável: " + treino.instrutor.nome);
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("Nenhum treino encontrado com esse nome.");
        }
    }

    static void listarTreinosNumerados() {
        System.out.println("\n===== LISTAR TODOS OS TREINOS =====");
        if (treinos.isEmpty()) {
            System.out.println("Nenhum treino cadastrado ainda.");
        } else {
            for (int i = 0; i < treinos.size(); i++) {
                Treino treino = treinos.get(i);
                System.out.println("[" + treino.numero + "] " + treino.nome);
            }
        }
    }

    static void listarTreinosOrdenados() {
        System.out.println("\n===== LISTAR TREINOS ORDENADOS =====");
        List<String> nomesTreinos = new ArrayList<>();
        for (Treino treino : treinos) {
            nomesTreinos.add(treino.nome);
        }
        Collections.sort(nomesTreinos);

        if (nomesTreinos.isEmpty()) {
            System.out.println("Nenhum treino cadastrado ainda.");
        } else {
            for (String nomeTreino : nomesTreinos) {
                System.out.println(nomeTreino);
            }
        }
    }

    static void adicionarAluno() {
        System.out.println("\n===== ADICIONAR ALUNO =====");
        System.out.println("Digite o nome do novo aluno:");
        String nome = scanner.nextLine();

        System.out.println("Digite a idade do aluno:");
        int idade = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        System.out.println("Digite o nível do aluno (INICIANTE, INTERMEDIARIO, AVANÇADO):");
        String nivelString = scanner.nextLine().toUpperCase();
        Nivel nivel;
        try {
            nivel = Nivel.valueOf(nivelString);
        } catch (IllegalArgumentException e) {
            System.out.println("Nível inválido. O aluno será cadastrado como INICIANTE.");
            nivel = Nivel.INICIANTE;
        }

        Aluno aluno = new Aluno(nome, idade, nivel);
        alunos.add(aluno);
        System.out.println("Aluno adicionado com sucesso!");
    }

    static void removerAluno() {
        System.out.println("\n===== REMOVER ALUNO =====");
        System.out.println("Digite o nome do aluno que deseja remover:");
        String nome = scanner.nextLine();

        boolean removido = false;
        Iterator<Aluno> iterator = alunos.iterator();
        while (iterator.hasNext()) {
            Aluno aluno = iterator.next();
            if (aluno.nome.equalsIgnoreCase(nome)) {
                iterator.remove();
                System.out.println("Aluno removido com sucesso!");
                removido = true;
            }
        }

        if (!removido) {
            System.out.println("Nenhum aluno encontrado com esse nome.");
        }
    }

    static void pesquisarAluno() {
        System.out.println("\n===== PESQUISAR ALUNO =====");
        System.out.println("Digite o nome do aluno que deseja pesquisar:");
        String nome = scanner.nextLine();

        boolean encontrado = false;
        for (Aluno aluno : alunos) {
            if (aluno.nome.equalsIgnoreCase(nome)) {
                System.out.println("Aluno encontrado: " + aluno.nome + ", Idade: " + aluno.idade + ", Nível: " + aluno.nivel);
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("Nenhum aluno encontrado com esse nome.");
        }
    }
}

class PesquisaInstrutor {
    public static void pesquisarNome(List<Instrutor> instrutores, String nome) {
        boolean encontrado = false;
        System.out.println("\n===== PESQUISAR INSTRUTOR POR NOME =====");
        for (Instrutor instrutor : instrutores) {
            if (instrutor.nome.equals(nome)) {
                System.out.println("Nome: " + instrutor.nome + ", Especialidade: " + instrutor.especialidade);
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("Nenhum instrutor encontrado com esse nome.");
        }
    }
}

class RemocaoInstrutor {
    public static void removerInstrutor(List<Instrutor> instrutores, String nome) {
        boolean removido = false;
        System.out.println("\n===== REMOVER INSTRUTOR =====");
        for (int i = 0; i < instrutores.size(); i++) {
            if (instrutores.get(i).nome.equals(nome)) {
                instrutores.remove(i);
                System.out.println("Instrutor removido com sucesso!");
                removido = true;
                break;
            }
        }
        if (!removido) {
            System.out.println("Nenhum instrutor encontrado com esse nome.");
        }
    }
}
