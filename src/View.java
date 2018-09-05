/**
 * Created by cjurthe on 06.02.2017.
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.lang.Integer;		//int from Model is passed as an Integer
import java.util.*;

class View implements java.util.Observer {

    //attributes as must be visible within class
    private JFrame frame;
    private JPanel panel_10;
    private JTextField textField_source;
    private JTextField textField_target;
    private JTextField textField_trennzeichen;

    private JButton btn_source_durchsuchen;
    private JButton btn_target_durchsuchen;
    private JButton btnUmbenennungStarten;
    private JButton btn_source_confirm;
    private JButton btn_target_confirm;
    private JButton btn_ean;
    private JButton btnStatischesElement;
    private JButton btnFortlaufendeKennung;
    private JButton btnReset;
    private JButton btnPreview;
    private JButton btn_paula;
    private JButton btn_galeria;
    private JButton btn_sols;
    private JButton btnConfirmTrennzeichen;
    private JButton btnConfirmKennung;

    private JTextArea textArea_log;
    private JTextArea textArea_1;
    private JTextArea textArea_2;

    private JPopupMenu popup_galeria;
    public JLabel source_count;
    public JProgressBar progressBar;

    public myTable table;
    //private Model model;		//Joe: Model is hardwired in,
    //needed only if view initialises model (which we aren't doing)

    View() {
        System.out.println("View()");

        frame = new JFrame();
        frame.setBounds(80, 40, 1100, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(new ImageIcon(getClass().getResource("/images/icon.png")).getImage());
        frame.setTitle("AZURE-LE - Awesome Zuumeo Renametool LookletEdition");

        frame.getContentPane().setLayout(new GridLayout(1, 0, 0, 5));
        // 1 Column

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel);
        panel.setLayout(new BorderLayout(0, 0));

        JPanel panel_1 = new JPanel();
        panel.add(panel_1, BorderLayout.SOUTH);

        progressBar = new JProgressBar();
        progressBar.setForeground(new Color(124, 252, 0));
        progressBar.setBackground(new Color(255, 250, 250));
        panel_1.add(progressBar);

        JLabel lblBereit = new JLabel("Bereit");
        panel_1.add(lblBereit);

        JPanel panel_main = new JPanel();
        panel_main.setBackground(new Color(245, 245, 245));
        // Main-Panel - Covers everything
        panel.add(panel_main, BorderLayout.CENTER);
        panel_main.setLayout(new GridLayout(2, 1));

        JPanel row_first = new JPanel();
        panel_main.add(row_first);
        row_first.setLayout(new GridLayout(1,2 ));

        JPanel row_second = new JPanel();
        row_second.setBackground(new Color(255, 255, 255));
        //panel_8.setBounds(0, 0, 550, 109);
        panel_main.add(row_second);
        row_second.setLayout(null);


        // first row

        JPanel panel_row_first_left = new JPanel();
        row_first.add(panel_row_first_left);
        panel_row_first_left.setLayout(new GridLayout(1, 1));

        JPanel panel_row_first_right = new JPanel();
        row_first.add(panel_row_first_right);
        panel_row_first_right.setLayout(new GridLayout(1, 1));

        JPanel panel_source_target = new JPanel();
        panel_row_first_left.add(panel_source_target);
        panel_source_target.setLayout(null);

        JPanel panel_source_first = new JPanel();
        panel_source_first.setBounds(0,0,200,30);
        panel_source_target.add(panel_source_first);
        panel_source_first.setLayout(null);

        JPanel panel_source_second = new JPanel();
        panel_source_second.setBackground(new Color(255,255,255));
        panel_source_second.setBounds(0,30,500,100);
        panel_source_target.add(panel_source_second);
        panel_source_second.setLayout(null);

        JPanel panel_target_first = new JPanel();
        panel_target_first.setBounds(0,130,200,30);
        panel_source_target.add(panel_target_first);
        panel_target_first.setLayout(null);

        JPanel panel_target_second = new JPanel();
        panel_target_second.setBackground(new Color(255,255,255));
        panel_target_second.setBounds(0,160,500,100);
        panel_source_target.add(panel_target_second);
        panel_target_second.setLayout(null);

        JPanel panel_file_first = new JPanel();
        panel_file_first.setBackground(new Color(255,255,255));
        panel_file_first.setBounds(0,230,200,30);
        panel_source_target.add(panel_file_first);
        panel_file_first.setLayout(null);

        JPanel panel_options = new JPanel();
        //panel_3.setBounds(6, 6, 348, 109);
        panel_row_first_right.add(panel_options);
        panel_options.setLayout(null);



        JLabel lblQuellordner = new JLabel("Quellordner");
        lblQuellordner.setBounds(10,4,100,20);
        panel_source_first.add(lblQuellordner);
        JButton btnI = infoButton(""
                + "Alle Dateien in diesem Ordner, inklusive der Unterordner werden für die Umbenennung berücksichtigt.<br><br>"
                + "'durchsuchen' öffnet einen Dialog, der dich deine Ordnerstruktur durchklicken lässt.<br> "
                + "Du kannst aber auch den Ordnerpfad eingeben und auf 'übernehmen' klicken."

        );
        btnI.setBounds(120,4,20,20);
        panel_source_first.add(btnI);

        btn_source_durchsuchen = new JButton("durchsuchen");
        btn_source_durchsuchen.setBackground(new Color(204, 255, 204));
        btn_source_durchsuchen.setBounds(204, 20, 117, 29);
        panel_source_second.add(btn_source_durchsuchen);

        source_count = new JLabel("");
        source_count.setForeground(new Color(144, 0, 255));
        source_count.setBounds(204, 50, 117, 29);
        panel_source_second.add(source_count);

        JSeparator separator_2 = new JSeparator();
        separator_2.setBounds(185, 10, 7, 61);
        panel_source_second.add(separator_2);
        separator_2.setOrientation(SwingConstants.VERTICAL);

        textField_source = new JTextField();
        textField_source.setBounds(16, 10, 157, 26);
        panel_source_second.add(textField_source);
        textField_source.setColumns(10);

        btn_source_confirm = new JButton("übernehmen");
        btn_source_confirm.setBackground(new Color(204, 255, 204));
        btn_source_confirm.setBounds(16, 50, 117, 29);
        panel_source_second.add(btn_source_confirm);


        JLabel lblZielordner = new JLabel("Zielordner");
        lblZielordner.setBounds(10,4,100,20);
        panel_target_first.add(lblZielordner);
        JButton btnII = infoButton(""
                + "Alle umbenannten Dateien werden in den Zielordner kopiert. Die ehemalige Ordnerstruktur wird nicht übernommen.<br>"
                + "Der Zielordner darf nicht identisch mit dem Quellordner sein!<br><br>"
                + "'durchsuchen' öffnet einen Dialog, der dich deine Ordnerstruktur durchklicken lässt.<br> "
                + "Du kannst aber auch den Ordnerpfad eingeben und auf 'übernehmen' klicken."
        );
        btnII.setBounds(120,4,20,20);
        panel_target_first.add(btnII);

        btn_target_durchsuchen = new JButton("durchsuchen");
        btn_target_durchsuchen.setBackground(new Color(204, 255, 204));
        btn_target_durchsuchen.setBounds(204, 20, 117, 29);
        panel_target_second.add(btn_target_durchsuchen);

        JSeparator separator_target = new JSeparator();
        separator_target.setBounds(185, 10, 7, 61);
        panel_target_second.add(separator_target);
        separator_target.setOrientation(SwingConstants.VERTICAL);

        textField_target = new JTextField();
        textField_target.setBounds(16, 10, 157, 26);
        panel_target_second.add(textField_target);
        textField_target.setColumns(10);

        btn_target_confirm = new JButton("übernehmen");
        btn_target_confirm.setBackground(new Color(204, 255, 204));
        btn_target_confirm.setBounds(16, 50, 117, 29);
        panel_target_second.add(btn_target_confirm);

        JLabel lblFile = new JLabel("Namensliste");
        lblFile.setBounds(10,4,100,20);
        panel_file_first.add(lblFile);

        JPanel panel_filename = new JPanel();
        panel_filename.setBounds(0, 0, 1100, 150);
        row_second.add(panel_filename);
        panel_filename.setLayout(null);

        JPanel panel_result = new JPanel();
        panel_result.setBounds(0, 150, 1100, 200);
        row_second.add(panel_result);
        panel_result.setLayout(null);

        JLabel lblNeuerDateiname = new JLabel("neuer Dateiname");
        lblNeuerDateiname.setBounds(6, 6, 106, 16);
        panel_filename.add(lblNeuerDateiname);

        btn_ean = new JButton("EAN");
        btn_ean.setBounds(6, 25, 94, 29);
        panel_filename.add(btn_ean);
        btn_ean.setBackground(new Color(102, 153, 204));

        btnStatischesElement = new JButton("statischer Text");
        btnStatischesElement.setBackground(new Color(102, 153, 255));
        btnStatischesElement.setBounds(112, 25, 143, 29);
        panel_filename.add(btnStatischesElement);

        btnFortlaufendeKennung = new JButton("fortl. Kennung");
        btnFortlaufendeKennung.setBackground(new Color(51, 153, 255));
        btnFortlaufendeKennung.setBounds(266, 25, 143, 29);
        panel_filename.add(btnFortlaufendeKennung);

        btnReset = new JButton("reset");
        btnReset.setBackground(new Color(204, 102, 102));
        btnReset.setBounds(438, 25, 106, 29);
        panel_filename.add(btnReset);

        btnPreview = new JButton("Vorschau");
        btnPreview.setBackground(new Color(238,232,170));
        btnPreview.setBounds(550, 25, 106, 29);
        panel_filename.add(btnPreview);

        panel_10 = new JPanel();
        panel_10.setBounds(5, 70, 500, 70);
        panel_filename.add(panel_10);
        panel_10.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));


        JSeparator separator_filename = new JSeparator();
        separator_filename.setBounds(0, 0, 1500, 10);
        separator_filename.setForeground(Color.DARK_GRAY);
        panel_filename.add(separator_filename);



        JPanel panel_log = new JPanel();
        panel_log.setBounds(0, 0, 200, 200);
        panel_result.add(panel_log);
        panel_log.setLayout(null);

        btnUmbenennungStarten = new JButton("Umbenennung starten");
        btnUmbenennungStarten.setBackground(new Color(0, 204, 102));
        btnUmbenennungStarten.setBounds(10, 5, 180, 30);
        panel_log.add(btnUmbenennungStarten);

        JScrollPane scrollPane_log = new JScrollPane();
        scrollPane_log.setBounds(5, 40, 190, 135);
        panel_log.add(scrollPane_log);

        JLabel lblLog = new JLabel("Log");
        lblLog.setBackground(Color.LIGHT_GRAY);
        scrollPane_log.setColumnHeaderView(lblLog);

        textArea_log = new JTextArea();
        textArea_log.setEditable(false);
        textArea_log.setBackground(new Color(255,255,255));
        scrollPane_log.setViewportView(textArea_log);

        JPanel panel_table = new JPanel();
        panel_table.setBounds(200, 0, 1000, 200);
        panel_result.add(panel_table);
        panel_table.setLayout(null);

        JScrollPane scrollPane_table = new JScrollPane();
        scrollPane_table.setBounds(0, 0, 890, 175);
        panel_table.add(scrollPane_table);

        DefaultTableModel model_1 = new DefaultTableModel();
        model_1.setColumnIdentifiers(new Object[]{"Dateiname","EAN","Ansicht_Quelle","Ansicht_Ziel","Zieldateiname"});
        table = new myTable(model_1);
        table.setBackground(new Color(255,255,255));
        scrollPane_table.setViewportView(table);

        // right side

        // presets
        JPanel panel_presets = new JPanel();
        panel_presets.setBounds(0, 0, 800, 40);
        panel_options.add(panel_presets);
        panel_presets.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

        btn_paula = new JButton("Hey Paula");
        btn_paula.setBackground(new Color(250, 159, 175));
        panel_presets.add(btn_paula);

        popup_galeria = new JPopupMenu();

        btn_galeria = new JButton("Galeria Kaufhof");
        btn_galeria.setBackground(new Color(161, 250, 213));
        btn_galeria.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                popup_galeria.show(e.getComponent(), e.getX(), e.getY());
            }
        });
        panel_presets.add(btn_galeria);

        btn_sols = new JButton("SOL");
        btn_sols.setBackground(new Color(180, 232, 185));
        panel_presets.add(btn_sols);

        // Trennzeichen

        JPanel panel_trennzeichen = new JPanel();
        panel_trennzeichen.setBounds(0, 40, 800, 110);
        panel_options.add(panel_trennzeichen);
        panel_trennzeichen.setLayout(null);

        JLabel lblOptionaleOptionen = new JLabel("Optionen");
        lblOptionaleOptionen.setBounds(5, 5, 120, 20);
        panel_trennzeichen.add(lblOptionaleOptionen);

        JButton button_2 = this.infoButton(""
                + "Das Trennzeichen gibt an, an welcher Stelle des Dateinamen die EAN endet. Dabei ist das erste Vorkommen des Zeichen relevant.<br>"
                + "Haben die Dateien unterschiedliche Trennzeichen, können mehrere Trennzeichen, kommagetrennt, eingegeben werden.<br><br>"
                + "Das Trennzeichen kann auch aus mehreren Zeichen bestehen.<br>Beispiel:<br><br>"
                + "12345_ABC_-23 || Trennzeichen: '_'  &nbsp;&nbsp;|| EAN: 12345<br>"
                + "12345_ABC_-23 || Trennzeichen: '_-' || EAN: 12345_ABC"
        );
        button_2.setBounds(400, 25, 20, 27);
        panel_trennzeichen.add(button_2);

        JLabel lblTrennzeichen_ean = new JLabel("EAN: links vom 1. Vorkommen. Ansicht: rechts vom letzten Vorkommen");
        lblTrennzeichen_ean.setBounds(5, 25, 380, 25);
        panel_trennzeichen.add(lblTrennzeichen_ean);

        JLabel lblTrennzeichen = new JLabel("Trennzeichen");
        lblTrennzeichen.setBounds(5, 60, 80, 25);
        panel_trennzeichen.add(lblTrennzeichen);

        textField_trennzeichen = new JTextField();
        textField_trennzeichen.setBounds(100, 60, 50, 25);
        textField_trennzeichen.setText("_");
        panel_trennzeichen.add(textField_trennzeichen);

        btnConfirmTrennzeichen = new JButton("übernehmen");
        btnConfirmTrennzeichen.setBackground(new Color(204, 255, 204));
        btnConfirmTrennzeichen.setBounds(160, 60, 120, 25);
        panel_trennzeichen.add(btnConfirmTrennzeichen);



        // Kennung

        JPanel panel_kennung = new JPanel();
        panel_kennung.setBounds(0, 150, 800, 180);
        panel_options.add(panel_kennung);
        panel_kennung.setLayout(null);

        JScrollPane scrollPane_quellkennung = new JScrollPane();
        scrollPane_quellkennung.setBounds(5, 0, 100, 150);
        panel_kennung.add(scrollPane_quellkennung);

        textArea_1 = new JTextArea();
        scrollPane_quellkennung.setViewportView(textArea_1);

        JLabel lblQuellkennung = new JLabel("Quellkennung");
        lblQuellkennung.setForeground(new Color(25, 25, 112));
        scrollPane_quellkennung.setColumnHeaderView(lblQuellkennung);

        JScrollPane scrollPane_zielkennung = new JScrollPane();
        scrollPane_zielkennung.setBounds(180, 0, 100, 150);
        panel_kennung.add(scrollPane_zielkennung);

        JLabel lblZielkennung = new JLabel("Zielkennung");
        lblZielkennung.setForeground(new Color(25, 25, 112));
        scrollPane_zielkennung.setColumnHeaderView(lblZielkennung);

        textArea_2 = new JTextArea();
        scrollPane_zielkennung.setViewportView(textArea_2);

        JLabel label_2 = new JLabel(">>");
        label_2.setBounds(130, 60, 29, 16);
        panel_kennung.add(label_2);

        btnConfirmKennung = new JButton("übernehmen");
        btnConfirmKennung.setBackground(new Color(204, 255, 204));
        btnConfirmKennung.setBounds(332, 0, 117, 29);
        panel_kennung.add(btnConfirmKennung);

        frame.setVisible(true);
    } //View()

    // Called from the Model
    public void update(Observable obs, Object obj) {

        //who called us and what did they send?
        //System.out.println ("View      : Observable is " + obs.getClass() + ", object passed is " + obj.getClass());

        //model Pull
        //ignore obj and ask model for value,
        //to do this, the view has to know about the model (which I decided I didn't want to do)
        //uncomment next line to do Model Pull
        //myTextField.setText("" + model.getValue());

        //model Push
        //parse obj
        //myTextField.setText("" + ((Integer)obj).intValue());	//obj is an Object, need to cast to an Integer

    } //update()



    private JButton infoButton(String text){
        JButton aButton = new JButton();
        ImageIcon icon = new ImageIcon(this.getClass().getResource("/images/info.png"));
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        aButton.setIcon(new ImageIcon(newImg));
        aButton.setBorderPainted(false);
        aButton.setContentAreaFilled(false);
        aButton.setFocusPainted(false);
        aButton.setOpaque(false);

        aButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String html = "<html><body><p style='width: 600px;'>"+text+"</p></body></html>";
                JOptionPane.showMessageDialog(null,html);

            }
        });
        return aButton;
    }

    public void addController(Controller controller){
        System.out.println("View      : adding controller");
        btnUmbenennungStarten.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.rename();
            }
        });
        btn_source_durchsuchen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.browse("source");
            }
        });
        btn_target_durchsuchen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.browse("target");
            }
        });
        btn_source_confirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.confirm("source");
            }
        });
        btn_target_confirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.confirm("target");
            }
        });
        btn_ean.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.addPattern("ean",null);
            }
        });
        btnStatischesElement.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.addPattern("static",null);
            }
        });
        btnFortlaufendeKennung.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.addPattern("kennung",null);
            }
        });
        btnReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.addPattern("reset",null);
            }
        });
        btnPreview.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.preview();
            }
        });
        btn_paula.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.setPreset("paula");
            }
        });
        popup_galeria.add(new JMenuItem(new AbstractAction("Front~Back~FrontRight") {
            public void actionPerformed(ActionEvent e) {
                controller.setPreset("galeria_fbfr");
            }
        }));
        popup_galeria.add(new JMenuItem(new AbstractAction("FrontRight~BackLeft~Front") {
            public void actionPerformed(ActionEvent e) {
                controller.setPreset("galeria_frbfl");
            }
        }));
        popup_galeria.add(new JMenuItem(new AbstractAction("1->001") {
            public void actionPerformed(ActionEvent e) {
                controller.setPreset("galeria_add0");
            }
        }));
        btn_sols.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.setPreset("sols");
            }
        });
        btnConfirmTrennzeichen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.confirm("trennzeichen");
            }
        });
        btnConfirmKennung.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.confirm("kennung");
            }
        });

    } //addController()


    public void log(String text){
        this.textArea_log.append(text + " \r\n");
    }
    public void setField(String field, String text){
        switch(field){
            case "source":
                this.textField_source.setText(text);
                break;
            case "target":
                this.textField_target.setText(text);
                break;
            case "trennzeichen":
                this.textField_trennzeichen.setText(text);
                break;


        }
    }
    public void setTextArea(String area, java.util.List<String> values){
        switch(area){
            case "kennung_source":
                this.textArea_1.setText("");
                for(String xyz : values){
                    textArea_1.append(xyz.toString() + "\r\n");
                }
                break;
            case "kennung_target":
                this.textArea_2.setText("");
                for(String xyz : values){
                    textArea_2.append(xyz.toString() + "\r\n");
                }
                break;
        }
    }
    String getSource(){
        return this.textField_source.getText();
    }
    String getTarget(){
        return this.textField_target.getText();
    }
    String getKennung_source(){
        return this.textArea_1.getText();
    }
    String getKennung_target(){
        return this.textArea_2.getText();
    }
    String getTrennzeichen(){
        return this.textField_trennzeichen.getText();
    }

    public void pattern_show(String type,String defaultVal){
        JLabel lblPreview = null;
        JTextField txtPreview = null;
        switch(type){
            case "ean":
                lblPreview = new JLabel("EAN");
                break;
            case "kennung":
                lblPreview = new JLabel("Anischt");
                break;
            case "static":

                txtPreview = new JTextField(defaultVal);
                txtPreview.setColumns(1);
                JTextField finalTxtPreview = txtPreview;
                txtPreview.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent e) {
                        finalTxtPreview.setColumns((finalTxtPreview.getText().length()));
                        panel_10.revalidate();
                        panel_10.repaint();
                    }
                });
                txtPreview.requestFocus();
                break;
            case "reset":
                this.panel_10.removeAll();
                break;
        }
        if(lblPreview != null){
            this.panel_10.add(lblPreview);
        }
        if(txtPreview != null){
            this.panel_10.add(txtPreview);
        }
        panel_10.revalidate();
        panel_10.repaint();
    }

    public static class CloseListener extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            e.getWindow().setVisible(false);
            System.exit(0);
        } //windowClosing()
    } //CloseListener

} //View