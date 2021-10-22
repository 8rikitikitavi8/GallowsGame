import java.util.Arrays;
import java.util.Scanner;

public class Gallows {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Игра Виселица");
        startTwoUserGame(scanner);
    }

    static void startTwoUserGame(Scanner scanner) {
        int currentPlayer = 1;
        while (shouldContinue(currentPlayer, scanner)) {
            String wordForGuessing = getWordFromPlayer(currentPlayer, scanner);
            guessWord(wordForGuessing, scanner);
            currentPlayer = getNextPlayer(currentPlayer);
        }
    }

    static String getWordFromPlayer(int currentPlayer, Scanner scanner) {
        System.out.println("Игрок " + currentPlayer + " введите загаданное слово");
        String wordForGuessing = scanner.nextLine();
        return wordForGuessing;
    }

    static int getNextPlayer(int currentPlayer) {
        return currentPlayer == 1 ? 2 : 1;
    }

    static void guessWord(String word, Scanner scanner) {
        char[] wordForGuessing = word.toCharArray();
        System.out.println("Отгадайте слово из " + wordForGuessing.length + " букв");

        char[] guessedWord = getEmptyGuessedWord(wordForGuessing);

        // обнуляем счетчик попыток
        int tryCount = 0;

        // крутимся в цикле, пока массивы не совпадают, помним, что знак ! – это частичка "НЕ",
        // которая означает отрицание
        while (!isGameOver(guessedWord, wordForGuessing)) {
            tryCount = incrementTryCountAndShow(tryCount);

            char userLetter = showStatusAndGetSymbolFromUser(guessedWord, scanner);

            // выходим из игры, если введен 0
            if (userLetter == '0') {
                break;
            }

            guessedWord = changeGuessedChars(guessedWord, wordForGuessing, userLetter);
        }
        showResult(guessedWord, wordForGuessing, tryCount);
    }

    static int incrementTryCountAndShow(int tryCount) {
        tryCount++;
        System.out.println("Попытка " + tryCount);
        return tryCount;
    }

    static char showStatusAndGetSymbolFromUser(char[] guessedWord, Scanner scanner) {
        System.out.println(guessedWord);
        System.out.println("Введите букву (или 0 для выхода)");

        String userInput = scanner.nextLine();
        char userLetter;
        userLetter = userInput.charAt(0);

        return userLetter;
    }

    static boolean isGameOver(char[] guessedWord, char[] wordForGuessing) {
        return Arrays.equals(guessedWord, wordForGuessing);
    }

    static char[] getEmptyGuessedWord(char[] wordForGuessing) {
        // для разгадывания слова делаем такого же размера массив символов
        // и заполняем его подчеркиваниями, раз пока ни одной буквы не угадано
        char[] guessedWord = new char[wordForGuessing.length];
        Arrays.fill(guessedWord, '_');
        return guessedWord;
    }

    static char[] changeGuessedChars(char[] guessedWord, char[] wordForGuessing, char userLetter) {
        // перебираем все буквы в слове
        for (int i = 0; i < wordForGuessing.length; i++) {
            // проверяем, есть ли такая буква в загаданном слове
            if (wordForGuessing[i] == userLetter) {
                // меняем знак _ на угаданную букву
                guessedWord[i] = userLetter;
            }
        }
        return guessedWord;
    }

    static void showResult(char[] guessedWord, char[] wordForGuessing, int tryCount) {
        if (Arrays.equals(guessedWord, wordForGuessing)) {
            System.out.println("Слово " + String.valueOf(wordForGuessing) + " отгадано за " + tryCount + " попыток");
        } else {
            System.out.println("Попробуйте еще раз");
        }
    }

    static boolean shouldContinue(int currentPlayer, Scanner scanner) {
        System.out.println("Если хотите передать ход игроку " + currentPlayer + " нажмите любую цифру (или 0 для выхода)");
        boolean b = true;
        if (scanner.nextInt() == 0) {
            b = false;
        }
        scanner.nextLine();
        return b;
    } 
}
