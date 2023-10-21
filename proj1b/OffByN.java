public class OffByN implements CharacterComparator {
    private int N;
    public OffByN(int n){
        N = n;
    }
    public boolean equalChars(char x, char y){

        return x == y + N || x == y - N;
    }
    public static void main(String[] args){
        OffByN cc = new OffByN(5);

    }
}
