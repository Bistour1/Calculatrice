public class Fraction implements CustomObject{
    protected CustomObject numerator;
    protected CustomObject denominator;
    public Fraction(CustomObject num, CustomObject denominator){
        this.numerator = num;
        this.denominator = denominator;
        simplify();
    }
    public void simplify(){
        if(this.numerator instanceof Value && this.denominator instanceof Value){
            if(((Value) this.denominator).getVar() == ""){
                ((Value) this.numerator).setValue(((Value) this.numerator).getValue()/((Value) this.denominator).getValue());
                this.denominator = new Value(1);
            }else{
                for (char ch :((Value) denominator).getVar().toCharArray()) {
                    if(((Value) this.numerator).getVar().contains(ch+"")){
                        String numVar = ((Value) this.numerator).getVar();
                        numVar.replaceFirst(ch+"","");
                        ((Value) this.numerator).setVar(numVar);

                        ((Value) this.denominator).setVar(((Value) denominator).getVar().replaceFirst(ch+"",""));
                    }
                }
                if(((Value) this.denominator).getVar() ==""){
                    ((Value) this.numerator).setValue(((Value) this.numerator).getValue()/((Value) this.denominator).getValue());
                    this.denominator = new Value(1);
                }else{
                    ((Value) this.numerator).setValue(((Value) this.numerator).getValue()/((Value) this.denominator).getValue());
                    ((Value) this.denominator).setValue(1);
                }
            }
        }
    }

    public CustomObject getNumerator() {
        return numerator;
    }

    public CustomObject getDenominator() {
        return denominator;
    }

    @Override
    public int getLength() {
        if(numerator.getLength() > denominator.getLength()){
            return numerator.getLength();
        }else {
            return denominator.getLength();
        }
    }
}
