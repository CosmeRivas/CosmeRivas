/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codigo_de_barras;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
/**
 *
 * @author RIVAS
 */
public class Dese_mysql {
    public static void main(String[] args) {
        Image img;
        Barcode128 barra = new Barcode128();
        try {
            Document doc = new Document();
            PdfWriter pdf = PdfWriter.getInstance(doc, new FileOutputStream("codigos desde MYSQL.pdf"));
            doc.open();
            Paragraph pa = new Paragraph();
            pa.setAlignment(Paragraph.ALIGN_LEFT);
            pa.setFont(FontFactory.getFont("Tahoma", 17));
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/practica", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select codigo, producto from codigo_de_barras");
            while(rs.next()){
                barra.setCode(rs.getString("codigo"));
                img = barra.createImageWithBarcode(pdf.getDirectContent(), BaseColor.BLACK, BaseColor.BLACK);
                pdf.add(img);
                pa.add(rs.getString("producto"));         
                doc.add(pa);
                doc.add(img);
                doc.add(new Paragraph("\n"));
                        
            }
            doc.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Dese_mysql.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(Dese_mysql.class.getName()).log(Level.SEVERE, null, ex);
        } catch(ClassNotFoundException ex){
            ex.printStackTrace();
        } catch(SQLException e){
            System.err.print(e);
        }
    }
}
