import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
////////////////////////////////////////////////
// Model (M)

class ClickModel extends Observable implements ActionListener{
  protected int clicksum; //累計のクリック数
  protected double nowclick; //現在保有しているクイック数(表示するもの)
  protected int perclick; //一回あたりに増えるクリックの数
  protected double persec; //一秒あたりに増える数
  protected int haveobject[]; //現在保有しているアイテムの数(5個)クリックあたりに増える
  protected int haveobject2[]; //現在保有しているアイテムの数(5個)秒数増える
  protected int lis1[] = {1, 5, 10, 100, 1000}; // persecを増加させる配列
  protected int lis2[] = {3, 8, 100, 1000, 10000}; // perclickを増加させる配列
  protected int buy1[] = {20, 100, 500, 2000, 10000}; //persecを買うために必要な量
  protected int buy2[] = {50, 100, 1000, 5000, 30000}; //perclickを買うために必要な量
  private javax.swing.Timer timer; //タイマー
  public ClickModel() {
      haveobject = new int[10];
      haveobject2 = new int[10];
      perclick = 1;
      persec = 0.0;
      timer = new javax.swing.Timer(100, this);
      timer.start();
  }
  public int getint(){
      return (int)nowclick;
  }
  public void setint(){
      clicksum++;
      nowclick += perclick;
      setChanged();
      notifyObservers();
  }
  public double get_persec(){  //persecを返す関数
      return persec;
  }
  public int get_perclick(){  //perclickを返す関数
      return perclick;
  }
  public int get_buy1(int n){
      return buy1[n];
  }
  public int get_buy2(int n){
      return buy2[n];
  }
  public int gainpersec(int n){ //ｎはボタンの番号
      if(nowclick < buy1[n]){
          //System.out.println("not enough nomey");
          return -1;
      }
      haveobject[n]++;
      persec = persec + lis1[n];
      nowclick = nowclick - buy1[n];
      setChanged();
      notifyObservers();
      return haveobject[n];
  }
  public int gainperclick(int n){ //nはボタンの番号
      if(nowclick < buy2[n]){
          //System.out.println("not enough nomey");
          return -1;
      }
      haveobject2[n]++;
      perclick += lis2[n];
      nowclick = nowclick - buy2[n];
      setChanged();
      notifyObservers();
      return haveobject2[n];
  }
  public void actionPerformed(ActionEvent e) {
      nowclick += (double)persec / 10;
      //System.out.println((double)persec / 10);
      setChanged();
      notifyObservers();
  }
}


////////////////////////////////////////////////
// View (V)
//メインパネルで、ここでクリックの動作と値の表示をする。ほかのパネルは別でつくる。
class ViewPanel extends JPanel implements Observer {
    protected ClickModel model;
    public JButton clickbutton;
    private int count, perclick;
    private double persec;
    private JLabel label_nowclick, label_now,label_persec, label_perclick,  num_persec,num_perclick;
    private JPanel p1, p2, p3;
    /*メインのパネル、もうあきらめてラベルもってきちゃった。p1につくってはりつける、
    もしくはもうメインのsetsizeしないでこっちで全部つくってもいいかも*/
    public ViewPanel(ClickModel m) {
        model = m;
	    model.addObserver(this);

        setFocusable(true);
        this.setLayout(new GridLayout(2,1));
        label_nowclick = new JLabel("0");
	label_now = new JLabel("髪の毛は :");
	label_now.setHorizontalAlignment(JLabel.CENTER);
        label_nowclick.setHorizontalAlignment(JLabel.CENTER);
        label_persec = new JLabel("１秒間の植毛数 : ");
        label_persec.setHorizontalAlignment(JLabel.CENTER);
        label_perclick = new JLabel("１回の植毛数 : ");
        label_perclick.setHorizontalAlignment(JLabel.CENTER);
        num_persec = new JLabel("0");
        num_persec.setHorizontalAlignment(JLabel.CENTER);
        num_perclick = new JLabel("0");
        num_perclick.setHorizontalAlignment(JLabel.CENTER);
	    label_persec.setFont(new Font(Font.SANS_SERIF,Font.BOLD,20));
	    label_perclick.setFont(new Font(Font.SANS_SERIF,Font.BOLD,20));
	    num_persec.setFont(new Font(Font.SANS_SERIF,Font.BOLD,20));
	    num_perclick.setFont(new Font(Font.SANS_SERIF,Font.BOLD,20));
	    label_nowclick.setFont(new Font(Font.SANS_SERIF,Font.BOLD,20));
	    label_persec.setForeground(Color.WHITE);
	    label_perclick.setForeground(Color.WHITE);
	    num_persec.setForeground(Color.WHITE);
	    num_perclick.setForeground(Color.WHITE);
	    label_now.setForeground(Color.ORANGE);
	    label_nowclick.setForeground(Color.ORANGE);
	    label_persec.setFont(new Font("MS ゴシック",Font.BOLD,21));
	    label_perclick.setFont(new Font("MS ゴシック",Font.BOLD,23));
	    num_persec.setFont(new Font("MS ゴシック",Font.BOLD,25));
	    num_perclick.setFont(new Font("MS ゴシック",Font.BOLD,25));
	    label_now.setFont(new Font("MS ゴシック",Font.BOLD,25));
	    label_nowclick.setFont(new Font("MS ゴシック",Font.BOLD,25));
	    p1 = new JPanel();
        p1.setLayout(new GridLayout(2,2));
        p1.add(label_persec);
        p1.add(num_persec);
        p1.add(label_perclick);
        p1.add(num_perclick);
	    p1.setBackground(new Color(0,0,0));
        p2 = new JPanel();
        p2.setLayout(new GridLayout(2,1));
        this.add(p1);
	    p3 = new JPanel();
	    p3.setLayout(new GridLayout(1,2));
	    p3.add(label_now);
	    p3.add(label_nowclick);
        p2.add(p3);
	    p3.setBackground(new Color(31,47,84));
        clickbutton = new JButton("<html><span style='font-size:45pt; color:black;'>植毛！！</span></html>");
        clickbutton.setBackground(new Color(234, 182, 143));    
        p2.add(clickbutton);
	    this.add(p2);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
    public void update(Observable o,Object arg){
        count = model.getint();
        persec = model.get_persec();
        perclick = model.get_perclick();
        label_nowclick.setText(Integer.toString(count, 10));
        num_persec.setText(String.valueOf(persec));
        num_perclick.setText(String.valueOf(perclick));
    }
}

////////////////////////////////////////////////
//graphics class

class Figure {
  protected int x,y,size, k;
  protected double sita;
  protected int hairlist[] = {20, 50, 100, 200, 400};
  protected int basiclist2[] = {10, 20, 30, 50, 100};
  Figure(int i) {
    x = (int)(Math.random()*333) - 166;
    y = 500 - (int)Math.sqrt(150 * 150 - (15*15*x*x)/(16*16));
    int f;
    if(i >  10000){
        f = 4;
    }else if(i > 5000){
        f = 3;
    }else if(i > 2000){
        f = 2;
    }else if(i > 500){
        f = 1;
    }else{
        f = 0;
    }
    size=(int)(Math.random()*hairlist[f]+basiclist2[f]);
    x += 160;
    sita = Math.toRadians(Math.random()*180);
  }
  void draw(Graphics g) {
    g.setColor(Color.BLACK);
    g.drawLine(x,y,x+(int)(size*Math.cos(sita)),y-(int)(size*Math.sin(sita)));
  }
}

///////////////////////////////////////////////////////
//view2
class View2Panel extends JPanel implements Observer{
  protected ClickModel model;
  private int pre, next;
  private ArrayList<Figure> fig;
  private JLabel namihei;
  public View2Panel(ClickModel m){
        ClassLoader cl = this.getClass().getClassLoader();
        this.setLayout(new BorderLayout());
	    model = m;
	    model.addObserver(this);
        setFocusable(true);
        fig=new ArrayList<Figure>();
        pre = 0;
        next = 0;
        ImageIcon pic = new ImageIcon(cl.getResource("pic/namihei.png"));
        namihei = new JLabel(pic);
        this.add(namihei, BorderLayout.SOUTH);
    }
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    for(Figure f: fig){
      f.draw(g); 
    }
  }
  public void update(Observable o,Object arg){
        next = model.getint();
        if(pre <= next){
            for(int i = pre; i < next; i++){
                fig.add(new Figure(i));
            }
        }else{
            for(int i = pre; i > next; i--){
                fig.remove(i-1);
            }
        }
        pre = next;
        this.repaint();
    }
}

class Overpanel extends JPanel{
    public JLabel have;
    public JButton B; 
    public Overpanel(){
        this.setVisible(true);
        B = new JButton("");
        have = new JLabel("×0");
        this.setLayout(new BorderLayout());
        have.setHorizontalAlignment(JLabel.CENTER);
        this.add(B,BorderLayout.CENTER);
        this.add(have,BorderLayout.EAST);
        B.setForeground(Color.BLACK);
        this.setBackground(Color.BLACK);
        have.setForeground(Color.WHITE);
        B.setEnabled(false);
    }
    public void setname(String s){
        B.setText(s);
    }
    public void sethave(int n){
        this.have.setText("×" + Integer.toString(n, 10));
    }
    public void settooltip(String a){
        B.setToolTipText(a);
    }
}

class Overpanel2 extends JPanel{
    public JLabel have;
    public JButton B; 
    private ImageIcon pic;
    public Overpanel2(ImageIcon p){
        this.setVisible(true);
        pic = p;
        B = new JButton(pic);
        have = new JLabel("×0");
        this.setLayout(new BorderLayout());
        have.setHorizontalAlignment(JLabel.CENTER);
        this.add(B,BorderLayout.CENTER);
        this.add(have,BorderLayout.EAST);
        B.setForeground(Color.BLACK);
        this.setBackground(Color.BLACK);
        have.setForeground(Color.WHITE);
        B.setEnabled(false);
    }
    public void setname(String s){
        B.setText(s);
    }
    public void sethave(int n){
        this.have.setText("×" + Integer.toString(n, 10));
    }
    public void settooltip(String a){
        B.setToolTipText(a);
    }
}
    	
class View3Panel extends JPanel implements Observer{//OptionのView
    protected ClickModel model;
    private JLabel store;
    private JPanel clickp, secp;
    public JLabel label;
    public Overpanel2 i1, i2, i3, i4, i5;
    public Overpanel  l1, l2, l3, l4, l5;
    private int count;
    public View3Panel(ClickModel m){
    model = m;
    //画像はりつけるところ
    ClassLoader cl = this.getClass().getClassLoader();
    ImageIcon gobo = new ImageIcon(cl.getResource("pic/gobou.jpg"));
    Image smallImg_gobo = gobo.getImage().getScaledInstance((int) (gobo.getIconWidth() * 0.3), -1,
        Image.SCALE_SMOOTH);
    ImageIcon smallIcon_gobo = new ImageIcon(smallImg_gobo);
    ImageIcon wakame = new ImageIcon(cl.getResource("pic/wakame.jpg"));
    Image smallImg_wakame = wakame.getImage().getScaledInstance((int) (wakame.getIconWidth() * 0.3), -1,
        Image.SCALE_SMOOTH);
    ImageIcon smallIcon_wakame = new ImageIcon(smallImg_wakame);
    ImageIcon ikumou = new ImageIcon(cl.getResource("pic/drag.jpg"));
    Image smallImg_ikumou = ikumou.getImage().getScaledInstance((int) (ikumou.getIconWidth() * 0.3), -1,
        Image.SCALE_SMOOTH);
    ImageIcon smallIcon_ikumou = new ImageIcon(smallImg_ikumou);
    ImageIcon doc = new ImageIcon(cl.getResource("pic/doc.jpg"));
    Image smallImg_doc = doc.getImage().getScaledInstance((int) (doc.getIconWidth() * 0.3), -1,
        Image.SCALE_SMOOTH);
    ImageIcon smallIcon_doc = new ImageIcon(smallImg_doc);
    ImageIcon zura = new ImageIcon(cl.getResource("pic/katura.jpg"));
    Image smallImg_zura = zura.getImage().getScaledInstance((int) (zura.getIconWidth() * 0.3), -1,
        Image.SCALE_SMOOTH);
    ImageIcon smallIcon_zura = new ImageIcon(smallImg_zura);

	JPanel p1 = new JPanel(), p2 = new JPanel();

	l1 = new Overpanel(); l2 = new Overpanel(); l3 = new Overpanel(); l4 = new Overpanel();l5 = new Overpanel();
    i1 = new Overpanel2(smallIcon_gobo); i2 = new Overpanel2(smallIcon_wakame); i3 = new Overpanel2(smallIcon_ikumou); 
    i4 = new Overpanel2(smallIcon_doc); i5 = new Overpanel2(smallIcon_zura);

	setFocusable(true);
	this.setLayout(new GridLayout(2,1));
	p1.setLayout(new BorderLayout());
	p2.setLayout(new GridLayout(1,2));
	label = new JLabel("Message");
	label.setHorizontalAlignment(JLabel.CENTER);
	p1.add(label, BorderLayout.CENTER);
	p1.setBackground(new Color(0,0,51));
	label.setForeground(Color.WHITE);
	label.setFont(new Font(Font.SERIF,Font.PLAIN,30));
	this.add(p1);
	clickp = new JPanel();
	secp = new JPanel();
	clickp.setLayout(new GridLayout(5,1));
	secp.setLayout(new GridLayout(5,1));

	i1.setname("ごぼう"); i2.setname("わかめ"); i3.setname("育毛剤"); i4.setname(" 医者 "); i5.setname("カツラ");
    l1.setname("普通の手"); l2.setname("アマチュアの手"); l3.setname("プロの手"); l4.setname("ゴッドハンド"); l5.setname("千手観音");

    l1.settooltip("購入費用 50本： クリック時 3本追加"); l2.settooltip("購入費用 100本： クリック時 8本追加"); l3.settooltip("購入費用 1000本： クリック時 100本追加");
    l4.settooltip("購入費用 5000本： クリック時 1000本追加"); l5.settooltip("購入費用 30000本： クリック時 10000本追加");
    i1.settooltip("購入費用 20本： 毎秒 1本追加"); i2.settooltip("購入費用 100本： 毎秒 8本追加"); i3.settooltip("購入費用 500本： 毎秒 10本追加");
    i4.settooltip("購入費用 2000本： 毎秒 100本追加"); i5.settooltip("購入費用 10000本： 毎秒 1000本追加");

	clickp.add(i1); clickp.add(i2); clickp.add(i3); clickp.add(i4); clickp.add(i5);
    secp.add(l1); secp.add(l2); secp.add(l3); secp.add(l4); secp.add(l5);
	p2.add(clickp);
	p2.add(secp);
	this.add(p2);
    model.addObserver(this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
    //ボタンの使用判定
    public void update(Observable o,Object arg){
        count = model.getint();
        if(count<model.get_buy1(0)){
            i1.B.setEnabled(false);
        } else {
            i1.B.setEnabled(true);
        }
        if(count<model.get_buy1(1)){
            i2.B.setEnabled(false);
        } else {
            i2.B.setEnabled(true);
        }
        if(count<model.get_buy1(2)){
            i3.B.setEnabled(false);
        } else {
            i3.B.setEnabled(true);
        }
        if(count<model.get_buy1(3)){
            i4.B.setEnabled(false);
        } else {
            i4.B.setEnabled(true);
        }
        if(count<model.get_buy1(4)){
            i5.B.setEnabled(false);
        } else {
            i5.B.setEnabled(true);
        }
        if(count<model.get_buy2(0)){
            l1.B.setEnabled(false);
        } else {
            l1.B.setEnabled(true);
        }
        if(count<model.get_buy2(1)){
            l2.B.setEnabled(false);
        } else {
            l2.B.setEnabled(true);
        }
        if(count<model.get_buy2(2)){
            l3.B.setEnabled(false);
        } else {
            l3.B.setEnabled(true);
        }
        if(count<model.get_buy2(3)){
            l4.B.setEnabled(false);
        } else {
            l4.B.setEnabled(true);
        }
        if(count<model.get_buy2(4)){
            l5.B.setEnabled(false);
        } else {
            l5.B.setEnabled(true);
        }
    }
}    
	    	

//////////////////////////////////////////////////
// controller class

class ClickController implements ActionListener {
    protected ClickModel model;
    protected ViewPanel view;
    //protected View2Panel view2;
    protected View3Panel view3;
    private int k;           
    //メインのコントローラ部分
    public ClickController(ClickModel c, ViewPanel v, View3Panel v3) {
        model = c;
        view = v;
        view3 = v3;
        this.initViewActionListners();
    }
    
    //ボタンにアクションリスナーつけるところ
    private void initViewActionListners(){
        view.clickbutton.addActionListener(this);
        view3.i1.B.addActionListener(this);
        view3.i2.B.addActionListener(this);
        view3.i3.B.addActionListener(this);
        view3.i4.B.addActionListener(this);
        view3.i5.B.addActionListener(this);
        view3.l1.B.addActionListener(this);
        view3.l2.B.addActionListener(this);
        view3.l3.B.addActionListener(this);
        view3.l4.B.addActionListener(this);
        view3.l5.B.addActionListener(this);
    }

    //各種ボタンに処理を加えるところ
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == view.clickbutton){
            model.setint();
        } else if(e.getSource() == view3.i1.B) {
            k = model.gainpersec(0);
            if(k==-1) { 
                view3.label.setText("髪の毛が足りません");
            } else {
                view3.label.setText("<html>ごぼうを買って、<br>たくさん食べた！</html>");
                view3.i1.sethave(k);
            }
            //view3.i1.sethave(k);
        } else if(e.getSource() == view3.i2.B){
            k = model.gainpersec(1);
            if(k==-1){
                view3.label.setText("髪の毛が足りません");
            } else {
                view3.label.setText("<html>わかめを買って、<br>たくさん食べた！</html>");
                view3.i2.sethave(k);
            }
        } else if(e.getSource() == view3.i3.B){
            k = model.gainpersec(2);
            if(k==-1){
                view3.label.setText("髪の毛が足りません");
            } else {
                view3.label.setText("<html>育毛剤を買って、<br>頭皮に塗った！</html>");
                view3.i3.sethave(k);
            }
        } else if(e.getSource() == view3.i4.B){
            k = model.gainpersec(3);
            if(k==-1){
                view3.label.setText("髪の毛が足りません");
            } else {
                view3.label.setText("<html>医者に治療してもらった！<html>");
                view3.i4.sethave(k);
            }
        } else if(e.getSource() == view3.i5.B){
            k = model.gainpersec(4);
            if(k==-1){
                view3.label.setText("髪の毛が足りません");
            } else {
                view3.label.setText("<html>カツラを買った！</html>");
                view3.i5.sethave(k);
            }
        } else if(e.getSource() == view3.l1.B){
            k = model.gainperclick(0);
            if(k==-1){
                view3.label.setText("髪の毛が足りません");
            } else {
                view3.label.setText("<html>普通の手を手に入れた！</html>");
                view3.l1.sethave(k);
            }
        } else if(e.getSource() == view3.l2.B){
            k = model.gainperclick(1);
            if(k==-1){
                view3.label.setText("髪の毛が足りません");
            } else {
                view3.label.setText("<html>アマチュアの手を<br>手に入れた！</html>");
                view3.l2.sethave(k);
            }
        } else if(e.getSource() == view3.l3.B){
            k = model.gainperclick(2);
            if(k==-1){
                view3.label.setText("髪の毛が足りません");
            } else {
                view3.label.setText("<html>プロの手を手に入れた！</html>");
                view3.l3.sethave(k);
            }
        } else if(e.getSource() == view3.l4.B){
            k = model.gainperclick(3);
            if(k==-1){
                view3.label.setText("髪の毛が足りません");
            } else {
                view3.label.setText("<html>ゴッドハンドを手に入れた！</html>");
                view3.l4.sethave(k);
            }
        } else if(e.getSource() == view3.l5.B){
            k = model.gainperclick(4);
            if(k==-1){
                view3.label.setText("髪の毛が足りません");
            } else {
                view3.label.setText("<html>千手観音を手に入れた！</html>");
                view3.l5.sethave(k);
            }
        }
    }
}

//////////////////////////////////////////////////
// main class
class ClickFrame extends JFrame {
    ClickModel model;
    ViewPanel view;
     View2Panel view2;
     View3Panel view3;
    ClickController cont;
    
    public ClickFrame(){
        model = new ClickModel();
        view = new ViewPanel(model);
	    view2 = new View2Panel(model);
	    view3 = new View3Panel(model);
	    this.setLayout(new GridLayout(1,3));
        cont = new ClickController(model, view,view3);
	    view2.setBackground(Color.white);
	    view3.setBackground(Color.BLUE);	
        this.setBackground(Color.white);
        this.setTitle("Syokumo Clicker");
        this.setSize(1000,500);
        this.add(view);
	    this.add(view2);
	    this.add(view3);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String argv[]){
        new ClickFrame();
    }
}