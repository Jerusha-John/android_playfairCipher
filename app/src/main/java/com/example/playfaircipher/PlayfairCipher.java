package com.example.playfaircipher;

import java.util.HashSet;

public class PlayfairCipher {

    private static final char FILLER_CHAR = 'X';

    private char[][] matrix;

    public PlayfairCipher(String keyword) {
        matrix = fillMatrix(keyword);
    }

    private char[][] fillMatrix(String keyword) {
        keyword = keyword.replaceAll("[^a-zA-Z]", "").toUpperCase();
        keyword = keyword.replace("J", "I");

        char[][] matrix = new char[5][5];

        HashSet<Character> uniqueLetters = new HashSet<>();
        for (char c : keyword.toCharArray()) {
            uniqueLetters.add(c);
        }

        int row = 0;
        int col = 0;
        for (char c : uniqueLetters) {
            matrix[row][col] = c;
            col++;
            if (col == 5) {
                col = 0;
                row++;
            }
        }

        char letter = 'A';
        while (row < 5) {
            if (!uniqueLetters.contains(letter) && letter != 'J') {
                matrix[row][col] = letter;
                col++;
                if (col == 5) {
                    col = 0;
                    row++;
                }
            }
            letter++;
        }

        return matrix;
    }

    public String encrypt(String plaintext) {
        StringBuilder ciphertext = new StringBuilder();

        plaintext = preprocessText(plaintext);

        for (int i = 0; i < plaintext.length(); i += 2) {
            char c1 = plaintext.charAt(i);
            char c2 = plaintext.charAt(i + 1);

            if (c1 == 'J') {
                c1 = 'I';
            }
            if (c2 == 'J') {
                c2 = 'I';
            }

            int row1 = -1, col1 = -1, row2 = -1, col2 = -1;

            for (int row = 0; row < 5; row++) {
                for (int col = 0; col < 5; col++) {
                    if (matrix[row][col] == c1) {
                        row1 = row;
                        col1 = col;
                    } else if (matrix[row][col] == c2) {
                        row2 = row;
                        col2 = col;
                    }
                }
            }

            if (row1 == row2) {
                col1 = (col1 + 1) % 5;
                col2 = (col2 + 1) % 5;
            } else if (col1 == col2) {
                row1 = (row1 + 1) % 5;
                row2 = (row2 + 1) % 5;
            } else {
                int temp = col1;
                col1 = col2;
                col2 = temp;
            }

            ciphertext.append(matrix[row1][col1]);
            ciphertext.append(matrix[row2][col2]);
        }

        return ciphertext.toString();
    }

    public String decrypt(String ciphertext) {
        StringBuilder plaintext = new StringBuilder();

        for (int i = 0; i < ciphertext.length(); i += 2) {
            char c1 = ciphertext.charAt(i);
            char c2 = ciphertext.charAt(i + 1);

            int row1 = -1, col1 = -1, row2 = -1, col2 = -1;

            for (int row = 0; row < 5; row++) {
                for (int col = 0; col < 5; col++) {
                    if (matrix[row][col] == c1) {
                        row1 = row;
                        col1 = col;
                    } else if (matrix[row][col] == c2) {
                        row2 = row;
                        col2 = col;
                    }
                }
            }

            if (row1 == row2) {
                col1 = (col1 - 1 + 5) % 5;
                col2 = (col2 - 1 + 5) % 5;
            } else if (col1 == col2) {
                row1 = (row1 - 1 + 5) % 5;
                row2 = (row2 - 1 + 5) % 5;
            } else {
                int temp = col1;
                col1 = col2;
                col2 = temp;
            }

            plaintext.append(matrix[row1][col1]);
            plaintext.append(matrix[row2][col2]);
        }

        // Remove filler characters
        int i = 0;
        while (i < plaintext.length()) {
            if (plaintext.charAt(i) == FILLER_CHAR) {
                plaintext.deleteCharAt(i);
            } else {
                i++;
            }
        }

        return plaintext.toString();
    }
    private String preprocessText(String text) {
        text = text.replaceAll("[^a-zA-Z]", "").toUpperCase();

        StringBuilder processedText = new StringBuilder();
        for (int i = 0; i < text.length(); i += 2) {
            char c1 = text.charAt(i);
            char c2 = (i + 1 < text.length()) ? text.charAt(i + 1) : FILLER_CHAR;

            processedText.append(c1);
            if (c1 == c2) {
                processedText.append(FILLER_CHAR);
            }
            processedText.append(c2);
        }

        return processedText.toString();
    }
}