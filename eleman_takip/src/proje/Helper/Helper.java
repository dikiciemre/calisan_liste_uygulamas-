package proje.Helper;

import javax.swing.*;
import java.awt.*;

public class Helper {


    // arayüz değişikliği yapıldı
    public static void setLayout() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
            if ("Nimbus".equals(info.getName())){
                UIManager.setLookAndFeel(info.getClassName());
            }
        }
    }


    // açılan arayüz ekranını ekranın ortasından açılmasını sağladık
    public static int screenCenterPoint(String eksen , Dimension size){

        int point;
        switch (eksen){
            case "x":
                point = (Toolkit.getDefaultToolkit().getScreenSize().width-size.width) /2;
                break;
            case "y":
                point = (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
                break;
            default:
                point = 0;
        }
        return point;
    }




    public static boolean isFileEmpty(JTextField field){
        return field.getText().trim().isEmpty();
    }


    // ekle tuşuna bastığında kutulara eksik karakter girildiğinde kontrol kodu yazdım.
    public static void sgowMsg(String str){
        optionPageTR();
        String msg;
        String title;

        switch (str){

            case "fill":
                msg = "Lütfen tüm alanları doldurunuz !";
                title = "Hata";
                break;
            case "done":
                msg = "İşlem Başarılı !";
                title = "Sonuç ";
                break;
            case "error":
                msg = "Bir Hata Oluştu";
                title = "HATA !";
            default:
                msg = str;
                title = "Mesaj";
        }
        JOptionPane.showMessageDialog(null,msg,title,JOptionPane.INFORMATION_MESSAGE);
    }


    // ekle tuşuna bastığında çıkan hatadaki ok tuşunu tamam olarak değiştirdim.
    public static void optionPageTR(){
        UIManager.put("OptionPane.okButtonText","Tamam");
    }



}
