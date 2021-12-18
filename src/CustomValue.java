import java.util.ArrayList;
import java.util.List;

public class CustomValue implements CustomObject{
    ArrayList<CustomObject> list = new ArrayList();
    public CustomValue(){
        list = new ArrayList<>();
    }
    public CustomValue(ArrayList<CustomObject> customObjects){
        list = customObjects;
    }
    public void add(CustomObject customObject){
        list.add(customObject);
    }
    public void add(ArrayList<CustomObject> customObjects){
        list.addAll(customObjects);
    }
    public void simplify(){
        for(int i = list.size()-1; i>= 0;i--){
            if(list.get(i) instanceof CustomValue){
                if(((CustomValue) list.get(i)).getList().size() == 1){
                    if(((CustomValue) list.get(i)).getList().get(0) instanceof Value){
                        list.set(i,((CustomValue) list.get(i)).getList().get(0));
                        System.out.println("wshwsh");
                        simplify();
                        return;
                    }
                }
            }
            if(list.get(i) instanceof Operator){
                switch((Operator)list.get(i)){
                    case Multiply:
                        if(i != list.size() && i!= 0){
                            if(list.get(i+1) instanceof Value) {
                                if(((Value) list.get(i+1)).canMultiply(list.get(i-1))) {
                                    Value result = ((Value) list.get(i+1)).multiply(list.get(i-1));
                                    list.remove(list.get(i+1));
                                    list.remove(list.get(i));
                                    list.set(i-1,result);
                                    simplify();
                                    return;
                                }
                            }
                        }
                        break;
                    case Divide:
                        if(i != list.size() && i!= 0){
                            if(list.get(i+1) instanceof Value && list.get(i-1) instanceof Value) {
                                Fraction fraction = new Fraction(list.get(i-1),list.get(i+1));
                                list.remove(list.get(i+1));
                                list.remove(list.get(i));
                                System.out.println("denominator:"+fraction.getDenominator().toString());
                                if(fraction.getDenominator() instanceof Value && ((Value) fraction.getDenominator()).getValue() == 1&& ((Value) fraction.getDenominator()).getVar() == ""){
                                    list.set(i-1,fraction.getNumerator());
                                }else {
                                    list.set(i - 1, fraction);
                                }
                                simplify();
                                return;
                            }
                        }
                        break;
                }
            }
        }
        for(int i = list.size()-1; i>= 0;i--){
            if(list.get(i) instanceof Operator){
                switch((Operator)list.get(i)){
                    case Addition:
                        if(i != list.size() && i!= 0){
                            if(list.get(i+1) instanceof Value) {
                                if(((Value) list.get(i+1)).canAddition(list.get(i-1))) {
                                    Value result = ((Value) list.get(i+1)).addition(list.get(i-1));
                                    list.remove(list.get(i+1));
                                    list.remove(list.get(i));
                                    list.set(i-1,result);
                                    simplify();
                                    return;
                                }
                            }
                        }
                        break;
                }
            }
        }
    }
    @Override
    public int getLength(){
        int length = list.stream().filter(x -> x instanceof CustomValue).mapToInt(x -> ((CustomValue) x).getLength()).sum();
        length += list.stream().filter(x -> x instanceof Value).mapToInt(x -> ((Value) x).getLength()).sum();
        return length;
    }

    public ArrayList<CustomObject> getList() {
        return list;
    }

    @Override
    public String toString() {
        String s = "CustomValue{" +
                "list=";
        for (CustomObject c:list) {
            s += "["+c.toString()+"]";
        }
        return "CustomValue{" +
                "list=" + list.toString() +
                '}';
    }
}
