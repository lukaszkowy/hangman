import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class LoadGame {

    public static void loadGame() {
        String wordToGuess = getRandomWord(loadDictionary());
        String wordToDisplay = drawUnderScore(wordToGuess);
        String wordToGuessWithoutSpaces = wordToGuess.replaceAll(" ", "");
        Set<Character> uniqueLetters = getUniqueLetters(wordToGuessWithoutSpaces);

        Scanner scanner = new Scanner(System.in);

        int i = 0;
        while (i < Utils.NUMBER_OF_CHANCES) {
            System.out.println(UserMessages.ENTER.getMessage());
            char letter = getLetter(scanner);
            boolean isLetterInWord = StringUtils.containsIgnoreCase(wordToGuessWithoutSpaces, String.valueOf(letter));


            if (isLetterInWord) {
                uniqueLetters.remove(letter);
                System.out.println(UserMessages.GOOD.getMessage());
                wordToDisplay = drawUnderScoreWithLetter(wordToDisplay, wordToGuess, letter);
            } else {
                System.out.println(UserMessages.WRONG.getMessage() + "\n" + HangManPic.HANGMAN_PIC.get(i));
                i++;
                checkIfAnyChanceLeft(i);
            }
            i = checkIfUserGuessTheWord(uniqueLetters, i);
        }
        System.out.printf((UserMessages.WORD_TO_GUESS.getMessage()) + "%n", wordToGuess);
    }

    private static int checkIfUserGuessTheWord(Set<Character> uniqueLetters, int userChance) {
        if (isEndOfGame(uniqueLetters.size())) {
            System.out.println(UserMessages.WIN.getMessage());
            return Utils.NUMBER_OF_CHANCES;
        } else {
            return userChance;
        }
    }

    private static void checkIfAnyChanceLeft(int userChance) {
        if (userChance != Utils.NUMBER_OF_CHANCES) {
            int chancesLeft = Utils.NUMBER_OF_CHANCES - userChance;
            System.out.printf((UserMessages.CHANCES_LEFT.getMessage()) + "%n", chancesLeft);
        }
    }

    private static Set<Character> getUniqueLetters(String wordToGuessWithoutSpaces) {
        return wordToGuessWithoutSpaces
                .toLowerCase()
                .chars()
                .mapToObj(letter -> (char) letter)
                .collect(Collectors.toSet());
    }

    private static char getLetter(Scanner scanner) {
        return scanner.next()
                .toLowerCase()
                .trim()
                .charAt(0);
    }

    private static String drawUnderScoreWithLetter(String wordToDisplay, String wordToGuess, char letter) {
        StringBuilder word = new StringBuilder(wordToDisplay);
        for (int i = 0; i < wordToDisplay.toCharArray().length; i++) {
            if (Character.toLowerCase(wordToGuess.toCharArray()[i]) == letter){
                word.setCharAt(i,wordToGuess.toCharArray()[i]);
            }
        }
        System.out.println(word);
        return word.toString();

    }
    private static String drawUnderScore(String wordToGuess) {
        StringBuilder word = new StringBuilder();
        for (int i = 0; i < wordToGuess.toCharArray().length; i++) {
            if (wordToGuess.toCharArray()[i] == ' ') {
                word.append(wordToGuess.toCharArray()[i]);
            } else {
                word.append("_");
            }
        }
        System.out.println(word);
        return word.toString();
    }

    private static boolean isEndOfGame(int lettersLeft) {
        return lettersLeft == 0;
    }

    private static String getRandomWord(List<String> words) {
        return words.get(new Random().nextInt(words.size()));
    }

    private static List<String> loadDictionary() {
        List<String> dictionary = new ArrayList<>();

        try (Scanner s = new Scanner(new File(Utils.PATHNAME))) {
            s.useDelimiter(String.valueOf(Utils.DELIMITER));
            while (s.hasNext()){
                dictionary.add(s.next());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Can't find dictionary file: " + Utils.PATHNAME);
        }

        return dictionary;
    }
}
