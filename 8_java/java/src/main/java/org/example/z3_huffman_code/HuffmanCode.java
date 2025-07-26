package org.example.z3_huffman_code;

import java.util.*;

public class HuffmanCode {
    private Node root;
    // Мапа кодов и символов
    private final Map<Character, String> huffmanCode = new HashMap<>();

    public void buildTree(String text) {
        // Подсчёт частот появления каждого символа
        // и сохранение ее в map
        Map<Character, Integer> freq = new HashMap<>();
        for (char ch : text.toCharArray()) {
            freq.put(ch, freq.getOrDefault(ch, 0) + 1);
        }

        // Создание приоритетной очереди для хранения активных узлов дерева Хаффмана
        // Элемент с наивысшим приоритетом имеет наименьшую частоту
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.frequency));

        // Создание конечного узла для каждого символа и добавление его
        // в приоритетную очередь.
        for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
            pq.add(new Node(entry.getKey(), entry.getValue()));
        }

        while (pq.size() > 1) {
            // Удаление из очереди два узла с наивысшим приоритетом
            // (с самой низкой частотой)
            Node left = pq.poll();
            Node right = pq.poll();

            assert right != null;
            // Создание нового внутреннего узла с этими двумя узлами в качестве дочерних
            // и частотой, равной сумме частот двух узлов
            // Добавление нового узла в очередь приоритетов.
            pq.add(new Node('\0', left.frequency + right.frequency, left, right));
        }

        // корень дерева
        root = pq.peek();

        generateCodes(root, "");
    }

    private void generateCodes(Node node, String code) {
        if (node == null) return;

        // помещаем в мапу код и символ
        if (node.left == null && node.right == null) {
            huffmanCode.put(node.character, !code.isEmpty() ? code : "0");
        }

        generateCodes(node.left, code + "0");
        generateCodes(node.right, code + "1");
    }

    public String encode(String text) {
        StringBuilder sb = new StringBuilder();
        for (char ch : text.toCharArray()) {
            // берем с мапы код символа
            sb.append(huffmanCode.get(ch));
        }
        return sb.toString();
    }

    public String decode(String encodedText) {
        StringBuilder decoded = new StringBuilder();
        Node current = root;
        for (int i = 0; i < encodedText.length(); i++) {
            // спускаемся вниз по дереву (1 - вправо, 0 - влево)
            char bit = encodedText.charAt(i);
            current = (bit == '0') ? current.left : current.right;

            assert current != null;
            // если дошли до листа дерева
            if (current.left == null && current.right == null) {
                // добавляем в ответ и переставляем курсор
                decoded.append(current.character);
                current = root;
            }
        }
        return decoded.toString();
    }

    public void printCodes() {
        System.out.println("Huffman Codes:");
        for (Map.Entry<Character, String> entry : huffmanCode.entrySet()) {
            System.out.println("'" + entry.getKey() + "' : " + entry.getValue());
        }
    }

    public static void main(String[] args) {

        // Пример 1

        String text = "Huffman coding is a data compression algorithm.";

        HuffmanCode huffman = new HuffmanCode();
        huffman.buildTree(text);

        huffman.printCodes();

        String encoded = huffman.encode(text);
        System.out.println("\nEncoded:\n" + encoded);

        String decoded = huffman.decode(encoded);
        System.out.println("\nDecoded:\n" + decoded);

        // Пример 2

        String text1 = "The Java language is named after the island.";
        huffman.buildTree(text1);

        huffman.printCodes();

        String encoded1 = huffman.encode(text1);
        System.out.println("\nEncoded1:\n" + encoded1);

        String decoded1 = huffman.decode(encoded1);
        System.out.println("\nDecoded1:\n" + decoded1);

    }
}

