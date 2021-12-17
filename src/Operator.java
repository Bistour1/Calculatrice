public enum Operator implements CustomObject{
    Multiply('*'),
    Divide('/'),
    Addition('+');

    protected char symbol;
    Operator(char symbol){
        this.symbol = symbol;
    }
    @Override
    public int getLength() {

        return 1;
    }

    public char getSymbol() {
        return symbol;
    }
}
