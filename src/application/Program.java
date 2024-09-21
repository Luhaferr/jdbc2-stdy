package application;

import db.DB;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Program {
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Connection conn = null;
        PreparedStatement st = null;
        try {
            conn = DB.getConnection();

            //objeto para executar consultas SQL com parâmetros.
            st = conn.prepareStatement(
                    "INSERT INTO seller "
                    + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
                    + "VALUES "
                    + "(?, ?, ?, ?, ?)", //esse interroção é o Placeholder, depois iremos preencher essas informações
                    Statement.RETURN_GENERATED_KEYS); //volta o id gerado

            st.setString(1, "Carl Purple");
            st.setString(2, "carl@gmail.com");
            //aqui para trabalharmos com data temos que usar o java.sql.Date pq estamos lidando com o SQL
            st.setDate(3, new java.sql.Date(sdf.parse("22/04/1985").getTime()));
            st.setDouble(4, 3000.0);
            st.setInt(5, 4);

            //variavel para sabermos quantas linhas foram alteradas recebendo operação que altera os dados no comando acima.
            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                //st.getgeneratedkey é um objeto tipo resultSet, por isso declaramos um ResultSet pra receber esse dado.
                ResultSet rs = st.getGeneratedKeys();
                /*
                como podemos inserir mais de uma pessoa no BD, essa função pode gerar mais de um id, por isso
                o while. Enquanto houver resultado ele vai guardar pra gente o id numa coluna partindo da posição 1.
                */
                while (rs.next()) {
                    int id = rs.getInt(1);
                    System.out.println("Done! Id = " + id);
                }
            } else {
                System.out.println("No rows affected!");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        finally {
            DB.closeStatement(st);
            DB.closeConnection();
        }
    }
}