package redblack;

public class Main {
    public static void main(String[] args) {
        tad.RubroNegra tree = new tad.RubroNegra();

        int[] values = {10, 20, 30, 15, 25, 5, 1, 50, 40};
        System.out.println("Inserindo:");
        for (int v : values) {
            System.out.print(" " + v);
            tree.inserir(v);
        }
        System.out.println("\n\nÁrvore (in-order com cores):");
        tree.imprimir();

        System.out.println("\nContém 15? " + tree.contem(15));
        System.out.println("Contém 100? " + tree.contem(100));

        System.out.println("\nRemovendo 20 e 10...");
        tree.remover(20);
        tree.remover(10);

        System.out.println("\nÁrvore após remoções:");
        tree.imprimir();
    }
}

