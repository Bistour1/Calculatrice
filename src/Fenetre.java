import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;

public class Fenetre extends JFrame {
    private JPanel container = new JPanel();
    //Tableau stockant les éléments à afficher dans la calculatrice
    String[] tab_string = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", ".", "=", "C", "+", "-", "*", "/"};
    //Un bouton par élément à afficher
    JButton[] tab_button = new JButton[tab_string.length];
    private static JLabel ecran = new JLabel();
    private Dimension dim = new Dimension(200, 160);
    private Dimension dim2 = new Dimension(200, 124);
    private double chiffre1;
    private boolean clicOperateur = false, update = false;
    private String operateur = "";
    private JPanel ecranFinal = new EcranFinal();
    private String currentNumber;

    public Fenetre(){
        this.setSize(960,1080);
        this.setTitle("Calculette");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        //On initialise le conteneur avec tous les composants
        initComposant();
        //On ajoute le conteneur
        this.setContentPane(container);
        this.setVisible(true);
    }
    public void initComposant(){
        Font police = new Font("Arial", Font.BOLD, 80);
        ecran = new JLabel("0");
        ecran.setFont(police);
        ecran.setHorizontalAlignment(JLabel.RIGHT);
        ecran.setPreferredSize(new Dimension(880,80));
        JPanel operateur = new JPanel();
        operateur.setPreferredSize(new Dimension(220,880));
        JPanel chiffres = new JPanel();
        chiffres.setPreferredSize(new Dimension(660,880));
        JPanel panEcran = new JPanel();
        panEcran.setPreferredSize(new Dimension(880,200));
        ecranFinal.setPreferredSize(new Dimension(880,120));
        panEcran.setLayout(new BorderLayout());
        panEcran.add(ecran,BorderLayout.SOUTH);
        panEcran.add(ecranFinal,BorderLayout.NORTH);
        for (int i = 0; i < 17;i++) {
            tab_button[i] = new JButton(tab_string[i]);
            tab_button[i].setPreferredSize(dim);
            switch(i){
                case 11:
                    chiffres.add(tab_button[i]);
                    tab_button[i].addActionListener(new EqualListener());
                    break;
                case 12:
                    tab_button[i].setForeground(Color.RED);
                    operateur.add(tab_button[i]);
                    tab_button[i].addActionListener(new ClearListener());
                    break;
                case 13:
                    tab_button[i].setPreferredSize(dim2);
                    operateur.add(tab_button[i]);
                    tab_button[i].addActionListener(new AdditionListener());
                    break;
                case 14:
                    tab_button[i].setPreferredSize(dim2);
                    operateur.add(tab_button[i]);
                    tab_button[i].addActionListener(new SustractListener());
                    break;
                case 15:
                    tab_button[i].setPreferredSize(dim2);
                    operateur.add(tab_button[i]);
                    tab_button[i].addActionListener(new MultiplyListener());
                    break;
                case 16:
                    tab_button[i].setPreferredSize(dim2);
                    operateur.add(tab_button[i]);
                    tab_button[i].addActionListener(new DivideListener());
                    break;
                default:
                    chiffres.add(tab_button[i]);
                    tab_button[i].addActionListener(new ChiffresListener());
                    break;
            }
        }
        container.setLayout(new BorderLayout());
        container.add(panEcran,BorderLayout.NORTH);
        container.add(chiffres,BorderLayout.CENTER);
        container.add(operateur,BorderLayout.EAST);
        this.setContentPane(container);
    }


    class EqualListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            if(ecran.getText() != "0" && ecran.getText().charAt(ecran.getText().length()-1) != '*'){
                if(ecranFinal instanceof EcranFinal){
                    ecranFinal.repaint();
                }
            }
        }
    }
    class ClearListener implements ActionListener{
        public void  actionPerformed(ActionEvent e){
            ecran.setText("0");
            currentNumber ="";
            ecranFinal.repaint();
        }
    }
    class AdditionListener implements ActionListener{
        public void  actionPerformed(ActionEvent e){
            if(ecran.getText() == "0") return;
            if(ecran.getText().charAt(ecran.getText().length()-1) == '*' ||
                    ecran.getText().charAt(ecran.getText().length()-1) == '+'||
                    ecran.getText().charAt(ecran.getText().length()-1) == '/') return;
                ecran.setText(ecran.getText() + "+");
                currentNumber = "";
            ecranFinal.repaint();
        }
    }
    class SustractListener implements ActionListener{
        public void  actionPerformed(ActionEvent e){
            if(ecran.getText().charAt(ecran.getText().length()-1) =='-'||
            ecran.getText().charAt(ecran.getText().length()-1) == '*'||
                    ecran.getText().charAt(ecran.getText().length()-1) == '+'||
                    ecran.getText().charAt(ecran.getText().length()-1) == '/') return;
            if(ecran.getText() == "0"){
                currentNumber = "-0";
                ecran.setText("-");
            }else {
                ecran.setText(ecran.getText() + "-");
                currentNumber = "";
            }
            ecranFinal.repaint();
        }
    }class MultiplyListener implements ActionListener{
        public void  actionPerformed(ActionEvent e){
            if(ecran.getText() == "0") return;
            if(ecran.getText().charAt(ecran.getText().length()-1) == '*' ||
                    ecran.getText().charAt(ecran.getText().length()-1) == '+'||
                    ecran.getText().charAt(ecran.getText().length()-1) == '/') return;
            ecran.setText(ecran.getText() + "*");
            currentNumber = "";
            ecranFinal.repaint();
        }
    }
    class DivideListener implements ActionListener{
        public void  actionPerformed(ActionEvent e){
            if(ecran.getText() == "0") return;
            if(ecran.getText().charAt(ecran.getText().length()-1) == '*' ||
                    ecran.getText().charAt(ecran.getText().length()-1) == '+'||
                    ecran.getText().charAt(ecran.getText().length()-1) == '/') return;
            ecran.setText(ecran.getText() + "/");
            currentNumber = "";
            ecranFinal.repaint();
        }
    }
    class ChiffresListener implements ActionListener{
        public void  actionPerformed(ActionEvent e){
            String chiffre = ((JButton)e.getSource()).getText();
            if(ecran.getText() == "0") ecran.setText("");
            if(chiffre == ".") {
                if (currentNumber.contains(".")) return;
                if(currentNumber == ""){
                    currentNumber = "0.";
                    ecran.setText(ecran.getText()+"0.");
                }else if(currentNumber =="-0") {
                    currentNumber = "-"+chiffre;
                    ecran.setText(ecran.getText()+chiffre);
                }else{
                    currentNumber += ".";
                    ecran.setText(ecran.getText()+".");
                }
                ecranFinal.repaint();
            }else {
                ecran.setText(ecran.getText() + chiffre);
                currentNumber += chiffre;
                ecranFinal.repaint();
            }
        }
    }
    public static String getEcranValue(){
        return ecran.getText();
    }
}