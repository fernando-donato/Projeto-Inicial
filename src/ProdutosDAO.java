/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    public void cadastrarProduto(ProdutosDTO produto) {

        conn = new conectaDAO().connectDB();

        try {
            prep = conn.prepareStatement("INSERT INTO produtos (nome, valor, status) VALUES (?,?,?)");
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            prep.executeUpdate();
            System.out.println("PRODUTO CADASTRADO COM SUCESSO!");

        } catch (SQLException e) {
            System.out.println("ERRO AO CADASTRAR PRODUTO: " + e.getMessage());
        }
    }
    
        public ArrayList<ProdutosDTO> listarProdutos() {

        conn = new conectaDAO().connectDB();

        String sql = "SELECT * FROM produtos";

        try {
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();

            while (resultset.next()) {

                ProdutosDTO produto = new ProdutosDTO();

                produto.setId(resultset.getInt("ID"));
                produto.setNome(resultset.getString("Nome"));
                produto.setValor(resultset.getInt("Valor"));
                produto.setStatus(resultset.getString("Status"));

                listagem.add(produto);
            }
            return listagem;

        } catch (SQLException e) {
            return null;
        }
    }
        
        public void venderProduto(int id) {

        conn = new conectaDAO().connectDB();

        String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";

        try {
            prep = conn.prepareStatement(sql);

            prep.setInt(1, id); //DEFININDO ID DO PRODUTO PARA ATUALIZACAO.

            int linha = prep.executeUpdate();

            if (linha > 0) {
                JOptionPane.showMessageDialog(null, "PRODUTO VENDIDO COM SUCESSO.");
            } else {
                JOptionPane.showMessageDialog(null, "PRODUTO NÃO ENCONTRADO OU JA VENDIDO.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERRO AO VENDER O PRODUTO."+e.getMessage());
        }
    }
   
        public ArrayList<ProdutosDTO> listarProdutosVendidos() {
        
        ArrayList<ProdutosDTO> listaVendidos = new ArrayList<>();
        
        conn = new conectaDAO().connectDB();

        String sql = "SELECT * FROM produtos WHERE status = 'Vendido'";
        
        try {
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();

            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));
                listaVendidos.add(produto); 
            }
        } catch (SQLException e) {
            
            JOptionPane.showMessageDialog(null, "ERRO AO LISTAR PRODUTOS VENDIDOS: " + e.getMessage());
        }

        return listaVendidos;
    }

}


