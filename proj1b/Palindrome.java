public class Palindrome {
    /**
     * This method return a Deque where i entry is the i position of String word
     */
    public Deque<Character> wordToDeque(String word) {
        LinkedListDeque<Character> returnDeque = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            returnDeque.addLast(word.charAt(i));
        }
        return returnDeque;
    }
    /**
     * This method test if the String passed is a palindrome
     */
    public boolean isPalindrome(String word) {
        LinkedListDeque<Character> lDeque = (LinkedListDeque<Character>) wordToDeque(word);
        for (int i = 0; i < word.length() / 2; i++) {
            if (lDeque.removeFirst() != lDeque.removeLast()) {
                return false;
            }
        }
        return true;
    }

    /**
     * test if the word is by Palindrome by character rank defined in cc
     *
     */
    public boolean isPalindrome(String s, CharacterComparator cc) {
        LinkedListDeque<Character> ld = (LinkedListDeque<Character>) wordToDeque(s);
        for (int i = 0; i < s.length() / 2; i++) {
            if (!cc.equalChars(ld.removeFirst(), ld.removeLast())) {
                return false;
            }
        }
        return true;
    }
}
