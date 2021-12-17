import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EcranFinal extends JPanel {

    Font police = new Font("Arial", Font.BOLD, 80);
    List<Character> numbers = Arrays.asList('1', '2', '3', '4', '5','6', '7', '8', '9', '0', '.');
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0,0,this.getWidth(),this.getHeight());
        g.setColor(Color.BLACK);
        if(Fenetre.getEcranValue().length() != 0 && Fenetre.getEcranValue() != "0") {
            int size = 80;
            int x = 920;
            int y = 80;
            int multiplier = 1;
            CustomValue result = calcul(Fenetre.getEcranValue().length()-1,0);
            result.simplify();
            int l = result.getLength();
            if(l >18){
                while (l>18){
                    l-=18;
                    multiplier = multiplier*2;
                }
            }
            size = size/multiplier;
            police = new Font("Arial", Font.BOLD, size);
            g.setFont(police);
            write(result, x,80, g,multiplier);
        }
    }
    public CustomValue calcul(int start, int stop){
        CustomValue values = new CustomValue();
        for(int i = start;i >= stop;i--) {
            if (Fenetre.getEcranValue().charAt(i) == '+') {
                if(start != i) {
                    values.add(calcul(start, i + 1));
                    values.add(Operator.Addition);
                    values.add(calcul(i - 1, stop));
                    values.simplify();
                    return values;
                }else{
                    return calcul(i - 1, stop);
                }
            }
        }
        for(int i = start;i >= stop;i--) {
            if (Fenetre.getEcranValue().charAt(i) == '*') {
                if(start != i) {
                    values.add(calcul(start, i + 1));
                    values.add(Operator.Multiply);
                    values.add(calcul(i - 1, stop));
                    values.simplify();
                    return values;
                }else{
                    return calcul(i - 1, stop);
                }
            }else if(Fenetre.getEcranValue().charAt(i) == '/'){
                if(start != i){
                    values.add(calcul(i-1,stop));
                    values.add(Operator.Divide);
                    values.add(calcul(start, i + 1));
                    return values;
                }else{
                    return calcul(i - 1, stop);
                }
            }
        }
        String str = Fenetre.getEcranValue().substring(stop,start+1);
        String number ="";
        String var ="";
        for (char c : str.toCharArray()){
            if(numbers.contains(c)){
                number += c;
            }else{
                var += c;
            }
        }
        double coef = 0;
        if(var != ""){
            coef = 1;
        }
        if(number != "") {
            coef = Double.valueOf(number);
        }
        values.add(new Value(coef,var));
        return values;
    }

    public void write(CustomObject o,int x,int y,Graphics g,int multiplier){
        police = new Font("Arial", Font.BOLD, 80/multiplier);
        if(o instanceof Value){
            g.setFont(police);
            g.drawString(((Value) o).getStringValue(),x-(((Value) o).getLength()*44/multiplier),y);
        }else if(o instanceof CustomValue){
            int x2 = x;
            for (CustomObject c: ((CustomValue) o).getList()) {
                write(c,x2,y,g,multiplier);
                x2 -= c.getLength();
            }
        }else if(o instanceof Operator){
            switch ((Operator)o){
                case Multiply:
                    g.drawString("*",x-(44/multiplier),y);
                    break;
                case Addition:
                    g.drawString("+",x-(44/multiplier),y);
                    break;
            }
        }else if(o instanceof Fraction){
            if(((Fraction) o).getDenominator() instanceof Value
                    && ((Value) ((Fraction) o).getDenominator()).getVar() == ""
                    && ((Value) ((Fraction) o).getDenominator()).getValue() == 1){
                write(((Fraction) o).getNumerator(),x,y,g,multiplier);
            }else {
                write(((Fraction) o).getNumerator(), x, y - 15, g, multiplier*2);
                g.drawLine(x, y + 5, x - (o.getLength() * 22) / multiplier, y + 5);
                write(((Fraction) o).getDenominator(), x, y + 15, g, multiplier*2);
            }
        }
    }

    private int getNextOpe(String s, int i){
        for(int d = i; d >= 0; d--){
            if(s.charAt(d) == '*' && s.charAt(d) == '+' && s.charAt(d) == '-' && s.charAt(d) == '/'){
                return  d;
            }
        }
        return 0;
    }
}
