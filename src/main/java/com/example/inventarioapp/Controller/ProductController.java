package com.example.inventarioapp.Controller;

import com.example.inventarioapp.Model.Product;
import com.example.inventarioapp.Model.ProductRepository;
import com.example.inventarioapp.View.ProductView;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.springframework.stereotype.Controller;



public class ProductController implements ActionListener, MouseListener {

    private final ProductRepository productRepository;
    private final ProductView view;

    DefaultTableModel model = new DefaultTableModel();
    private String msg;

    public ProductController(ProductRepository productRepository,ProductView view) {

        this.productRepository = productRepository;
        this.view = view;
        mapEvents();
        printInTable();
    }

    private void mapEvents(){
        view.getBtnAdd().addActionListener(this);
        view.getBtnUpd().addActionListener(this);
        view.getBtnDel().addActionListener(this);
        view.getBtnInf().addActionListener(this);
        view.getTblProd().addMouseListener(this);
        view.getBtnUpdateF().addActionListener(this);
    }
    
    private List<Product> getProducts(){
        
        return (List<Product>)productRepository.findAll();
    }

    private List<Integer> printInTable(){
        List<Product> listP = this.getProducts();
        JTable tblProd = view.getTblProd();

        Integer row = 0;
        List<Integer> listCodInf = new ArrayList();
        for ( Product p : listP ){           
            listCodInf.add(p.getCodigo());
            view.getTblProd().setValueAt(p.getNombre(), row,0);
            view.getTblProd().setValueAt(p.getPrecio(), row,1);
            view.getTblProd().setValueAt(p.getInventario(), row,2);
            row++;           
        }
        
        for(int i = 0; i < tblProd.getRowCount(); i++){
            view.getTblProd().setValueAt("",  row,0);
            view.getTblProd().setValueAt("",  row,1);
            view.getTblProd().setValueAt("",  row,2);
        }
        
        return listCodInf;
    }
    
    public boolean fieldValidation(JTextField name, JTextField price, JTextField inventory){
        boolean allIsOK = false;
        if (name.getText().isEmpty() || 
            price.getText().isEmpty() ||
            inventory.getText().isEmpty()){
            msg = "Debes llenar Todos los Campos!";
            JOptionPane.showMessageDialog(view, msg, "Campos Incompletos", 
                        JOptionPane.WARNING_MESSAGE);
        } else{
            
            allIsOK = true;
        } 
        return allIsOK;
    }
    
    
    public String addProduct(){
        String name = null;
        Double price = null;
        Integer inventory = null;
        
        if(fieldValidation(view.getTxtName(), 
                    view.getTxtPrice(), 
                    view.getTxtInvent()) == true){
            
            name = view.getTxtName().getText();
            price = Double.parseDouble(view.getTxtPrice().getText());
            inventory = Integer.parseInt(view.getTxtInvent().getText());
        }
            
        try {
            Product p = new Product (
                   name,
                   price,
                   inventory
            );           
            productRepository.save(p);
            msg = "¡El producto se agregó correctamente!";
            JOptionPane.showMessageDialog(view, msg, "Actualización Exitosa", JOptionPane.INFORMATION_MESSAGE);
            printInTable();
            
        } catch (NullPointerException e) {
            msg = "Error! Vuelve a intentarlo";
            JOptionPane.showMessageDialog(view, msg, "Error", JOptionPane.ERROR_MESSAGE);
        }
        
     
        

        return msg;
    }

    public void updateProduct(){
        String name = null;
        Double price = null;
        Integer inventory = null;
        
        if(fieldValidation(view.getTxtUpName(), 
                    view.getTxtUpPrice(), 
                    view.getTxtUpInvent()) == true){
            
            name = view.getTxtName().getText();
            price = Double.parseDouble(view.getTxtPrice().getText());
            inventory = Integer.parseInt(view.getTxtInvent().getText());
        }
        
        try {

            Product p = new Product (
                   Integer.parseInt(view.getTxtUpCode().getText()),
                   name,
                   price,
                   inventory
            );
            productRepository.save(p);
            msg = "¡Se actualizó el producto!";
            JOptionPane.showMessageDialog(view, msg, "Actualización Exitosa", JOptionPane.INFORMATION_MESSAGE); 
            printInTable();
        } catch (Exception e){
            msg = "Error! Vuelve a intentarlo";
            JOptionPane.showMessageDialog(view, msg, "Error", JOptionPane.ERROR_MESSAGE);
        }

        
    }

    public void deleteProduct(){
        try {
            int idCod = Integer.parseInt(view.getTxtCode().getText());
            productRepository.deleteById(idCod);
            msg = "¡Se Eliminó el producto!";
            
            JOptionPane.showMessageDialog(view, msg, "Actualización Exitosa", JOptionPane.INFORMATION_MESSAGE); 
            printInTable();
        } catch (Exception e){
            msg = "Error! Vuelve a intentarlo";
            JOptionPane.showMessageDialog(view, msg, "Error", JOptionPane.ERROR_MESSAGE);
        }
  
    }

    public void productReport(){
        List<Product> listP = this.getProducts();
        String nameMaxPrice = productRepository.maxPrice();
        String nameMinPrice = productRepository.minPrice();
        double avgPrice = productRepository.avgPrice();
        double totalInventory = this.totalValue(listP);
        
        msg = "Producto Precio Mayor: "+nameMaxPrice+"\n"
             +"Producto Precio Menor: "+nameMinPrice+"\n"
             +"Promedio Precios: "+avgPrice+"\n"
             +"Valor del Inventario: "+totalInventory;
        JOptionPane.showMessageDialog(view, msg);

    }
    
    public double totalValue(List<Product> listP){
        double sumCont = 0;
        for(Product p: listP){
            double total = p.getPrecio() * p.getInventario();
            sumCont += total;
        }
        return sumCont;
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if( e.getSource() == view.getBtnAdd()  ){           
            addProduct(); 
        } else if(e.getSource() == view.getBtnUpd()){
            int row = view.getTblProd().getSelectedRow();
            if(row != -1){
                view.getUpWindow().setVisible(true);
                view.getUpWindow().setLocationRelativeTo(view); 
            } else{
                msg = "Debes seleccionar un producto para actualizarlo.";
                JOptionPane.showMessageDialog(view, msg, "", JOptionPane.WARNING_MESSAGE);
            }                 
        } else if(e.getSource() == view.getBtnUpdateF()){
            updateProduct();    
        } else if (e.getSource() == view.getBtnDel()){
            int row = view.getTblProd().getSelectedRow();
            if(row != -1){
                deleteProduct();
            } else{
                msg = "Debes seleccionar un producto para eliminarlo.";
                JOptionPane.showMessageDialog(view, msg, "", JOptionPane.WARNING_MESSAGE);
            }   
        } else if(e.getSource() == view.getBtnInf()){
            productReport();
        }

    }


    @Override
    public void mouseClicked(MouseEvent e) {
        

        int row = view.getTblProd().getSelectedRow();
        List<Integer> listCode = printInTable();
        
        
            view.getTxtUpCode().setText(listCode.get(row).toString());
            view.getTxtCode().setText(listCode.get(row).toString());
            view.getTxtUpName().setText(view.getTblProd().getValueAt(row, 0).toString());
            view.getTxtUpPrice().setText(view.getTblProd().getValueAt(row, 1).toString());
            view.getTxtUpInvent().setText(view.getTblProd().getValueAt(row, 2).toString());
        
        
        
        
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
