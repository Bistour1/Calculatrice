import java.text.DecimalFormat;
import java.util.Arrays;

public class Value implements CustomObject{
    protected Double value;
    protected String var;
    public Value(double d){
        this.value = d;
        this.var = "";
    }
    public Value(double d,String variable){
        this.var = variable;
        sortVar();
        this.value = d;
    }
    public double getValue(){
        return value;
    }
    public String getStringValue(){
        if(value == 0) return "";
        String str = String.valueOf(value);
        DecimalFormat df = new DecimalFormat("#");
        if(str.charAt(str.length()-1) == '0'){
            str = df.format(value);
        }
        return str+var;
    }
    @Override
    public int getLength() {
        return getStringValue().length();
    }
    public boolean canMultiply(CustomObject c){
        if(c instanceof Value){
            return true;
        }
        return false;
    }
    public boolean canAdditionOrSub(CustomObject c){
        if(c instanceof Value){
            ((Value) c).sortVar();
            this.sortVar();
            if(((Value) c).getVar() == this.getVar()){
                return true;
            }else {
                return true;
            }
        }else {
            return false;
        }
    }
    public Value multiply(CustomObject c){
        if(c instanceof Value){
            return new Value(this.getValue()*((Value) c).getValue(),this.getVar()+((Value) c).getVar());
        }else {
            return this;
        }
    }
    public Value addition(CustomObject c){
        if(canAdditionOrSub(c)){
            return new Value(this.getValue()+((Value) c).getValue(),getVar());
        }else{
            return this;
        }
    }
    public Value substraction(CustomObject c){
        if(canAdditionOrSub(c)){
            return new Value(this.getValue()-((Value) c).getValue(),getVar());
        }else{
            return this;
        }
    }
    public String getVar(){
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public void setValue(double value1){
        this.value = value1;
    }
    public void sortVar() {
        if (var != ""){
            char[] chars = this.var.toCharArray();
            Arrays.sort(chars);
            var = String.valueOf(chars);
        }
    }

    @Override
    public String toString() {
        return "Value{" +
                "value=" + value +
                ", var='" + var + '\'' +
                '}';
    }
}
