package proje.view;

import proje.Helper.Config;
import proje.Helper.Helper;
import proje.model.Operator;
import proje.model.User;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OperatorGUI extends JFrame {

    private JPanel wrapper;
    private JTabbedPane tab_operator;
    private JLabel lbl_welcome;
    private JButton btn_logout;
    private JPanel pnl_user_list;
    private JScrollPane scrl_user_list;
    private JTable tbl_user_list;
    private JPanel pnl_user_form;
    private JTextField fld_user_name;
    private JTextField fld_user_uname;
    private JTextField fld_user_pass;
    private JComboBox cmb_user_type;
    private JButton btn_user_add;
    private JTextField fld_user_id;
    private JButton btn_user_delete;
    private JTextField fld_sh_user_name;
    private JTextField fld_sh_user_uname;
    private JComboBox cmb_sh_user_type;
    private JButton btn_user_sh;

    private DefaultTableModel mdl_user_list;
    private Object[] row_user_list;

    private final Operator operator;

    public OperatorGUI(Operator operator) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        this.operator = operator;


        Helper.setLayout();

        add(wrapper);

        setSize(1000,500);// arayüzün ekranda kaplayacağı alan  belirtildi


        // arayüzün ekranda ortalanması için kod bloğu
        int x = Helper.screenCenterPoint("x",getSize());
        int y = Helper.screenCenterPoint("y",getSize());
        setLocation(x,y);


        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE); // projenin başlığı çağırıldı

        setVisible(true);


        lbl_welcome.setText("Hoşgeldin " + operator.getName());// hangi kulanıcj giriş yaptı ise ona hoşgeldin demek için yazıldı


        //modelusserlist
        mdl_user_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0){
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_user_list = {"ID","Ad Soyad","Kullanıcı Adı","Şifre","Üyelik tipi" };
       mdl_user_list.setColumnIdentifiers(col_user_list);

       row_user_list = new Object[col_user_list.length];

       loadUserModel();

       /*
       Object[] firstRow = {"1","Emre Dikici","Emre","123","operator"};//manuel olarak veri ekleme alanı
       mdl_user_list.addRow(firstRow);
       */



       tbl_user_list.setModel(mdl_user_list);
       tbl_user_list.getTableHeader().setReorderingAllowed(false);// kolonların hareket etmemesini sağlıyor

       tbl_user_list.getSelectionModel().addListSelectionListener(e -> {
           try{
               String select_user_id = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 0).toString();
               fld_user_id.setText(select_user_id);
           }catch (Exception exception){
               System.out.println(exception.getMessage());
           }
       });


        // tabloda olan değerleri üstüne imleç ile tıklayarak manuel değiştirme
       tbl_user_list.getModel().addTableModelListener(e -> {
           if(e.getType() == TableModelEvent.UPDATE){
               int user_id = Integer.parseInt(tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),0).toString());
               String user_name = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 1).toString();
               String user_uname = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 2).toString();
               String user_pass = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 3).toString();
               String user_type = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 4).toString();


               if (User.update(user_id,user_name,user_uname,user_pass,user_type)){
                   Helper.sgowMsg("done");

               }else {
                   Helper.sgowMsg("error");
               }
               loadUserModel();
           }
       });


        btn_user_add.addActionListener(e ->  {
           if (Helper.isFileEmpty(fld_user_name) || Helper.isFileEmpty(fld_user_uname) || Helper.isFileEmpty(fld_user_pass)){
               Helper.sgowMsg("fill");

           }else {
               String name = fld_user_name.getText();
               String uname = fld_user_uname.getText();
               String pass = fld_user_pass.getText();
               String type = cmb_user_type.getSelectedItem().toString();

               if(User.add(name,uname,pass,type)){
               Helper.sgowMsg("done");
               loadUserModel();


               // kullanıcı sağ taraftan değer girdikten sonra textareayı boşaltır
               fld_user_name.setText(null);
               fld_user_uname.setText(null);
               fld_user_pass.setText(null);
               }
           }
        });




        // tabloda olan değerleri silme
        btn_user_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Helper.isFileEmpty(fld_user_id)){
                    Helper.sgowMsg("fill");
                }else {
                    int user_id = Integer.parseInt(fld_user_id.getText());
                    if (User.delete(user_id)){
                        Helper.sgowMsg("done");
                        loadUserModel();
                    }else{
                        Helper.sgowMsg("error");
                    }
                }
            }
        });




        //arama butonu
        btn_user_sh.addActionListener(e -> {
            String name = fld_sh_user_name.getText();
            String uname = fld_sh_user_uname.getText();
            String type = cmb_sh_user_type.getSelectedItem().toString();


            String  query = User.searchQuery(name,uname,type);
            ArrayList<User> searchingUser = User.searchUserList(query);

            loadUserModel(searchingUser);

        });
    }




    public void loadUserModel(){

        DefaultTableModel clearModel = (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);

        for (User obj : User.getList()){

            int i = 0;

            row_user_list[i++] = obj.getId();
            row_user_list[i++] = obj.getName();
            row_user_list[i++] = obj.getUname();
            row_user_list[i++] = obj.getPass();
            row_user_list[i++] = obj.getType();
            mdl_user_list.addRow(row_user_list);
        }

    }




    public void loadUserModel(ArrayList<User> list){

        DefaultTableModel clearModel = (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);

        for (User obj : list){

            int i = 0;

            row_user_list[i++] = obj.getId();
            row_user_list[i++] = obj.getName();
            row_user_list[i++] = obj.getUname();
            row_user_list[i++] = obj.getPass();
            row_user_list[i++] = obj.getType();
            mdl_user_list.addRow(row_user_list);
        }

    }


    // çalıştırma alanı
    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Helper.setLayout();
        Operator op = new Operator();
        op.setId(1);
        op.setName("Emre Dikici");
        op.setPass("1234");
        op.setUname("emre");
        op.setType("operator");



        OperatorGUI opGUI = new OperatorGUI(op);
    }

}
