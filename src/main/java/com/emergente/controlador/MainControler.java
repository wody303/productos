package com.emergente.controlador;

import com.emergente.modelo.Producto;
import com.emergentes.utiles.ConexionDB;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "MainControler", urlPatterns = {"/MainControler"})
public class MainControler extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String op;
            op = (request.getParameter("op") != null) ? request.getParameter("op") : "list";

            ArrayList<Producto> lista = new ArrayList<Producto>();

            ConexionDB canal = new ConexionDB();

            Connection conn = canal.conectar();

            PreparedStatement ps;
            ResultSet rs;

            if (op.equals("list")) {

                String sql = "select * from productos";
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();

                while (rs.next()) {
                    Producto prod = new Producto();

                    prod.setId(rs.getInt("id"));
                    prod.setProducto(rs.getString("producto"));
                    prod.setPrecio(rs.getDouble("precio"));
                    prod.setCantidad(rs.getInt("cantidad"));

                    lista.add(prod);
                }
                request.setAttribute("lista", lista);;
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }

            if (op.equals("nuevo")) {
                //insercion de nuevo registro
                Producto pro = new Producto();

                request.setAttribute("prod", pro);;
                request.getRequestDispatcher("editar.jsp").forward(request, response);

            }
            if (op.equals("eliminar")) {
                //eliminar
                int id = Integer.parseInt(request.getParameter("id"));

                String sql = "delete from productos where id=?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, id);

                ps.executeUpdate();
                response.sendRedirect("MainControler");
            }
            if (op.equals("editar")) {
                //eliminar
                int id = Integer.parseInt(request.getParameter("id"));

                String sql = "select * from productos where id = ?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                rs = ps.executeQuery();
                Producto p = new Producto();
                while (rs.next()) {
                    p.setId(rs.getInt("id"));
                    p.setProducto(rs.getString("producto"));
                    p.setPrecio(rs.getDouble("precio"));
                    p.setCantidad(rs.getInt("cantidad"));
                }
                request.setAttribute("prod", p);;
                request.getRequestDispatcher("editar.jsp").forward(request, response);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MainControler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            int id = Integer.parseInt(request.getParameter("id"));
            String producto = request.getParameter("producto");
            Float precio = Float.parseFloat(request.getParameter("precio"));
            int cantidad = Integer.parseInt(request.getParameter("cantidad"));

            Producto prod = new Producto();

            prod.setId(id);
            prod.setProducto(producto);
            prod.setPrecio(precio);
            prod.setCantidad(cantidad);

            ConexionDB canal = new ConexionDB();
            Connection conn = canal.conectar();

            PreparedStatement ps;

            if (id == 0) {
                //nuevo registro 
                String sql = "insert into productos(id, producto, precio, cantidad) values (?,?,?,?)";

                ps = conn.prepareStatement(sql);

                ps.setString(1, Integer.toString(prod.getId()));
                ps.setString(2, prod.getProducto());
                ps.setString(3, Double.toString(prod.getPrecio()));
                ps.setString(4, Integer.toString(prod.getCantidad()));

                ps.executeUpdate();
                response.sendRedirect("MainControler");
            } else {
                String sql = "update productos set producto=?, precio=?, cantidad=? where id=?";

                ps = conn.prepareStatement(sql);

                ps.setString(1, prod.getProducto());
                ps.setString(2, Float.toString((float) prod.getPrecio()));
                ps.setString(3, Integer.toString(prod.getCantidad()));
                ps.setString(4, Integer.toString(prod.getId()));

                ps.executeUpdate();
                response.sendRedirect("MainControler");
            }
        } catch (SQLException e) {
            System.out.println("Error en SQL " + e.getMessage());
        }
    }
}
