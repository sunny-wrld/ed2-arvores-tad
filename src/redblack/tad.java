package redblack;

public class tad {

    public static class RedBlack {
        private static final boolean RED = true;
        private static final boolean BLACK = false;

        private final Node NIL;
        private Node root;

        private class Node {
            int key;
            boolean color;
            Node left, right, parent;

            Node(int key, boolean color) {
                this.key = key;
                this.color = color;
                this.left = null;
                this.right = null;
                this.parent = null;
            }
        }

        public RedBlack() {
            NIL = new Node(0, BLACK);
            NIL.left = NIL.right = NIL.parent = NIL;
            root = NIL;
        }

        public void inserir(int key) {
            Node z = new Node(key, RED);
            Node y = NIL;
            Node x = root;

            while (x != NIL) {
                y = x;
                if (z.key < x.key) x = x.left;
                else x = x.right;
            }
            z.parent = y;
            if (y == NIL) root = z;
            else if (z.key < y.key) y.left = z;
            else y.right = z;

            z.left = NIL;
            z.right = NIL;
            z.color = RED;

            insertFix(z);
        }

        private void insertFix(Node z) {
            while (z.parent.color == RED) {
                if (z.parent == z.parent.parent.left) {
                    Node y = z.parent.parent.right;
                    if (y.color == RED) {
                        z.parent.color = BLACK;
                        y.color = BLACK;
                        z.parent.parent.color = RED;
                        z = z.parent.parent;
                    } else {
                        if (z == z.parent.right) {
                            z = z.parent;
                            leftRotate(z);
                        }
                        z.parent.color = BLACK;
                        z.parent.parent.color = RED;
                        rightRotate(z.parent.parent);
                    }
                } else {
                    Node y = z.parent.parent.left;
                    if (y.color == RED) {
                        z.parent.color = BLACK;
                        y.color = BLACK;
                        z.parent.parent.color = RED;
                        z = z.parent.parent;
                    } else {
                        if (z == z.parent.left) {
                            z = z.parent;
                            rightRotate(z);
                        }
                        z.parent.color = BLACK;
                        z.parent.parent.color = RED;
                        leftRotate(z.parent.parent);
                    }
                }
            }
            root.color = BLACK;
        }

        public void remover(int key) {
            Node z = searchNode(root, key);
            if (z == NIL) return;
            Node y = z;
            Node x;
            boolean yOriginalColor = y.color;

            if (z.left == NIL) {
                x = z.right;
                transplant(z, z.right);
            } else if (z.right == NIL) {
                x = z.left;
                transplant(z, z.left);
            } else {
                y = minimum(z.right);
                yOriginalColor = y.color;
                x = y.right;
                if (y.parent == z) {
                    x.parent = y;
                } else {
                    transplant(y, y.right);
                    y.right = z.right;
                    y.right.parent = y;
                }
                transplant(z, y);
                y.left = z.left;
                y.left.parent = y;
                y.color = z.color;
            }

            if (yOriginalColor == BLACK) {
                deleteFix(x);
            }
        }

        private void deleteFix(Node x) {
            while (x != root && x.color == BLACK) {
                if (x == x.parent.left) {
                    Node w = x.parent.right;
                    if (w.color == RED) {
                        w.color = BLACK;
                        x.parent.color = RED;
                        leftRotate(x.parent);
                        w = x.parent.right;
                    }
                    if (w.left.color == BLACK && w.right.color == BLACK) {
                        w.color = RED;
                        x = x.parent;
                    } else {
                        if (w.right.color == BLACK) {
                            w.left.color = BLACK;
                            w.color = RED;
                            rightRotate(w);
                            w = x.parent.right;
                        }
                        w.color = x.parent.color;
                        x.parent.color = BLACK;
                        w.right.color = BLACK;
                        leftRotate(x.parent);
                        x = root;
                    }
                } else {
                    Node w = x.parent.left;
                    if (w.color == RED) {
                        w.color = BLACK;
                        x.parent.color = RED;
                        rightRotate(x.parent);
                        w = x.parent.left;
                    }
                    if (w.right.color == BLACK && w.left.color == BLACK) {
                        w.color = RED;
                        x = x.parent;
                    } else {
                        if (w.left.color == BLACK) {
                            w.right.color = BLACK;
                            w.color = RED;
                            leftRotate(w);
                            w = x.parent.left;
                        }
                        w.color = x.parent.color;
                        x.parent.color = BLACK;
                        w.left.color = BLACK;
                        rightRotate(x.parent);
                        x = root;
                    }
                }
            }
            x.color = BLACK;
        }

        private void transplant(Node u, Node v) {
            if (u.parent == NIL) root = v;
            else if (u == u.parent.left) u.parent.left = v;
            else u.parent.right = v;
            v.parent = u.parent;
        }

        private Node minimum(Node x) {
            while (x.left != NIL) x = x.left;
            return x;
        }

        private void leftRotate(Node x) {
            Node y = x.right;
            x.right = y.left;
            if (y.left != NIL) y.left.parent = x;
            y.parent = x.parent;
            if (x.parent == NIL) root = y;
            else if (x == x.parent.left) x.parent.left = y;
            else x.parent.right = y;
            y.left = x;
            x.parent = y;
        }

        private void rightRotate(Node x) {
            Node y = x.left;
            x.left = y.right;
            if (y.right != NIL) y.right.parent = x;
            y.parent = x.parent;
            if (x.parent == NIL) root = y;
            else if (x == x.parent.right) x.parent.right = y;
            else x.parent.left = y;
            y.right = x;
            x.parent = y;
        }

        private Node searchNode(Node node, int key) {
            while (node != NIL && key != node.key) {
                if (key < node.key) node = node.left;
                else node = node.right;
            }
            return node;
        }

        public boolean contem(int key) {
            return searchNode(root, key) != NIL;
        }

        public void imprimir() {
            inorderPrint(root);
            System.out.println();
        }

        private void inorderPrint(Node node) {
            if (node == NIL) return;
            inorderPrint(node.left);
            System.out.print(node.key + (node.color == RED ? "R " : "B "));
            inorderPrint(node.right);
        }
    }
}
