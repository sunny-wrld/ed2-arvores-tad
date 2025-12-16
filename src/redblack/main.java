package redblack;

public class main {
    public static void main(String[] args) {
        TADArvoreRB arvore = new TADArvoreRB();

        System.out.println("Inserindo: 10, 20, 30, 15, 25, 5, 1");
        arvore.inserir(10);
        arvore.inserir(20);
        arvore.inserir(30);
        arvore.inserir(15);
        arvore.inserir(25);
        arvore.inserir(5);
        arvore.inserir(1);

        arvore.emOrdem();
        arvore.exibirArvore();

        System.out.println("\nRemovendo: 20");
        arvore.remover(20);
        arvore.emOrdem();
        arvore.exibirArvore();

        System.out.println("\nRemovendo: 10");
        arvore.remover(10);
        arvore.emOrdem();
        arvore.exibirArvore();

        System.out.println("\nInserindo: 12, 18, 22");
        arvore.inserir(12);
        arvore.inserir(18);
        arvore.inserir(22);
        arvore.emOrdem();
        arvore.exibirArvore();
    }
}
