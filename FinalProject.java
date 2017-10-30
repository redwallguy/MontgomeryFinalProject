/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.util.LinkedList;
import java.sql.*;
import java.io.*;

/**
 *
 * @author vibhusomeshwar
 */
public class FinalProject extends Application {
    
    Button connect = new Button("Connect");
    Button createTablesbtn = new Button("Create Tables");
    Button close = new Button("Close");
    Button isbnSearchbtn = new Button("Search By ISBN");
    Button userSearchbtn = new Button("Search By User ID");
    Button checkouts = new Button("Check Out");
    Button returns = new Button("Return");
    Button checkByBook = new Button("Check Rentals By Book ID");
    Button checkByUser = new Button("Check Rentals By User ID");
    Button clear = new Button("Clear");
    Label statuslbl = new Label("Welcome to the Library Database System!");
    TextField isbnSearchIsbn = new TextField();
    TextField isbnSearchTitle = new TextField();
    TextField isbnSearchAuthor = new TextField();
    TextArea isbnSearchIdsIn = new TextArea();
    TextArea isbnSearchIdsOut = new TextArea();
    TextField userSearchUser = new TextField();
    TextField userSearchName = new TextField();
    TextArea userSearchAddress = new TextArea();
    TextField checkoutUser = new TextField();
    TextField checkoutBook = new TextField();
    Label isbnlbl = new Label("ISBN: ");
    Label titlelbl = new Label("Title: ");
    Label authorlbl = new Label("Author: ");
    Label idsinlbl = new Label("Book IDs in: ");
    Label idsoutlbl = new Label("Book IDs out: ");
    Label userlbl = new Label("User ID: ");
    Label namelbl = new Label("User name: ");
    Label addresslbl = new Label("User address: ");
    Label checkoutbooklbl = new Label("Book ID: ");
    Label checkoutuserlbl = new Label("User ID: ");
    Statement stmt;
    Connection connection;
    
    @Override
    public void start(Stage primaryStage) {
        //databaseChoice.setEditable(true);
        Pane pane = new Pane();
        HBox btnbox = new HBox(10);
        connect.setMinWidth(100);
        createTablesbtn.setMinWidth(100);
        close.setMinWidth(100);
        btnbox.layoutXProperty().bind(pane.widthProperty().divide(2).subtract(160));
        btnbox.getChildren().addAll(connect, createTablesbtn, close);
        pane.getChildren().add(btnbox);
        
        Pane databaseMainPane = new Pane();
        Pane statusPane = new Pane();
        VBox isbnbox = new VBox();
        VBox userbox = new VBox();
        VBox checkoutbox = new VBox();
        isbnbox.setMaxWidth(300);
        userbox.setMaxWidth(300);
        checkoutbox.setMaxWidth(300);
        isbnbox.setMaxHeight(500);
        userbox.setMaxHeight(500);
        checkoutbox.setMaxHeight(500);
        statusPane.setMinWidth(900);
        statusPane.setMinHeight(200);
        
        isbnSearchIdsIn.setMaxWidth(200);
        isbnSearchIdsOut.setMaxWidth(200);
        userSearchAddress.setMaxWidth(200);
        
        HBox isbnhbox = new HBox(5);
        HBox titlehbox = new HBox(5);
        HBox authorhbox = new HBox(5);
        HBox idsinhbox = new HBox(5);
        HBox idsouthbox = new HBox(5);
        HBox userhbox = new HBox(5);
        HBox namehbox = new HBox(5);
        HBox addresshbox = new HBox(5);
        HBox checkoutbookhbox = new HBox(5);
        HBox checkoutuserhbox = new HBox(5);
        
        isbnhbox.getChildren().addAll(isbnlbl, isbnSearchIsbn);
        titlehbox.getChildren().addAll(titlelbl, isbnSearchTitle);
        authorhbox.getChildren().addAll(authorlbl, isbnSearchAuthor);
        idsinhbox.getChildren().addAll(idsinlbl, isbnSearchIdsIn);
        idsouthbox.getChildren().addAll(idsoutlbl, isbnSearchIdsOut);
        userhbox.getChildren().addAll(userlbl, userSearchUser);
        namehbox.getChildren().addAll(namelbl, userSearchName);
        addresshbox.getChildren().addAll(addresslbl, userSearchAddress);
        checkoutbookhbox.getChildren().addAll(checkoutbooklbl, checkoutBook);
        checkoutuserhbox.getChildren().addAll(checkoutuserlbl, checkoutUser);
        
        isbnbox.getChildren().addAll(isbnhbox, titlehbox, authorhbox,
                idsinhbox, idsouthbox, isbnSearchbtn);
        userbox.getChildren().addAll(userhbox, namehbox, addresshbox, userSearchbtn);
        checkoutbox.getChildren().addAll(checkoutuserhbox, checkoutbookhbox,
                checkouts, returns, checkByBook, checkByUser, clear);
        statusPane.getChildren().add(statuslbl);
        
        isbnbox.setLayoutX(0);
        userbox.setLayoutX(300);
        checkoutbox.setLayoutX(600);
        statusPane.setLayoutY(500);
        databaseMainPane.getChildren().addAll(isbnbox, userbox, checkoutbox,
                statusPane);

        connect.setOnAction(e -> 
        {
            try
            {
                initializeDatabase();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        });
        
        createTablesbtn.setOnAction(e ->
        {
            try
            {
                createTables();
                populateTables();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        });
        
        close.setOnAction(e ->
        {
            try
            {
                connection.close();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        });
        
        isbnSearchbtn.setOnAction(e -> 
        {
            try
            {
                ISBNSearch();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        });
        
        userSearchbtn.setOnAction(e -> 
        {
            try
            {
                UserSearch();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        });
        
        checkouts.setOnAction(e -> 
        {
            try
            {
                Checkout();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        });
        
        returns.setOnAction(e ->
        {
            try
            {
                Returns();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        });
        
        checkByBook.setOnAction(e -> 
        {
            try
            {
                StatusByBook();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        });
        
        checkByUser.setOnAction(e -> 
        {
            try
            {
                RentalsByUser();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        });
        
        clear.setOnAction(e -> 
        {
            isbnSearchIsbn.setText("");
            isbnSearchTitle.setText("");
            isbnSearchAuthor.setText("");
            isbnSearchIdsIn.setText("");
            isbnSearchIdsOut.setText("");
            userSearchUser.setText("");
            userSearchName.setText("");
            userSearchAddress.setText("");
            checkoutBook.setText("");
            checkoutUser.setText("");
        });
        
        Stage databaseStage = new Stage();
        Scene databaseScene = new Scene(databaseMainPane, 900, 700);
        databaseStage.setScene(databaseScene);
        databaseStage.setTitle("Database GUI");
        databaseStage.show();
        
        Scene initScene = new Scene(pane, 300, 400);
        primaryStage.setScene(initScene);
        primaryStage.setTitle("Database initializer");
        primaryStage.show();
        
    }
    
    private void initializeDatabase() throws IOException, ClassNotFoundException,
            SQLException
    {
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("Driver loaded");
        
        connection = DriverManager.getConnection("jdbc:mysql://"
        + "localhost/bookslibrary", "scott", "tiger");
        System.out.println("Database connected");
    }
    
    private void createTables() throws SQLException
    {
        stmt = connection.createStatement();
        stmt.executeUpdate("create table users (" + " userId varchar(2), " +
                "userName varchar(50), address varchar(25), primary key " +
                "(userId) )");
        stmt.executeUpdate("create table books ( bookId varchar(2), " +
                "ISBN char(13), title varchar(50), author varchar(25), " +
                "checkout varchar(2), primary key (bookId), foreign key " +
                "(checkout) references users(userId) )");
    }
    
    private void populateTables() throws SQLException
    {
        stmt = connection.createStatement();
        stmt.executeUpdate("insert into books values ('1', '1357924680113', "
        + "'Intro to Java Programming', 'Robert Markos', null)");
        stmt.executeUpdate("insert into books values ('2', '1357924680113', "
        + "'Intro to Java Programming', 'Robert Markos', null)");
        stmt.executeUpdate("insert into books values ('3', '1357924680113', "
        + "'Intro to Java Programming', 'Robert Markos', null)");
        stmt.executeUpdate("insert into books values ('4', '1234567890123', "
        + "'Master of None', 'Yarros Haben', null)");
        stmt.executeUpdate("insert into books values ('5', '1234567890123', "
        + "'Master of None', 'Yarros Haben', null)");
        stmt.executeUpdate("insert into books values ('6', '1234567890123', "
        + "'Master of None', 'Yarros Haben', null)");
        stmt.executeUpdate("insert into books values ('7', '1237894560321', "
        + "'Alas Poor Yorick', 'Hamnet Hazard', null)");
        stmt.executeUpdate("insert into books values ('8', '1237894560321', "
        + "'Alas Poor Yorick', 'Hamnet Hazard', null)");
        stmt.executeUpdate("insert into books values ('9', '0987654321098', "
        + "'The Last of Them', 'Vidya Manny', null)");
        stmt.executeUpdate("insert into books values ('10', '0987654321098', "
        + "'The Last of Them', 'Vidya Manny', null)");
        stmt.executeUpdate("insert into users values ('1', 'John Smith', "
        + "'111 Marble Way')");
        stmt.executeUpdate("insert into users values ('2', 'Mark Hamill', "
        + "'123 Skywalker Street')");
        stmt.executeUpdate("insert into users values ('3', 'Nasus Ascendant', "
        + "'1000 Stacks Drive')");
        stmt.executeUpdate("insert into users values ('4', 'Renekton Ascendant', "
        + "'666 Cull Avenue')");
        stmt.executeUpdate("insert into users values ('5', 'Riven of Noxus', "
        + "'101 Exile Street')");
        stmt.executeUpdate("insert into users values ('6', 'Irelia of Ionia', "
        + "'45 Hiten Way')");
        stmt.executeUpdate("insert into users values ('7', 'Taric the Fabulous', "
        + "'105 Targon Avenue')");
        stmt.executeUpdate("insert into users values ('8', 'Annie Hastur', "
        + "'765 Tibbers Court')");
        stmt.executeUpdate("insert into users values ('9', 'Nathaniel Jones', "
        + "'342 Samarkand Street')");
        stmt.executeUpdate("insert into users values ('10', 'Bartimaeus Gate', "
        + "'3 Ptolemy Way')");
    }
    //make random entries to tables to start out, PopulateTables method
    private void ISBNSearch() throws SQLException
    {
        LinkedList<String> IDInHold = new LinkedList<>();
        LinkedList<String> IDOutHold = new LinkedList<>();
        String textIn = "", textOut = "";
        stmt = connection.createStatement();
        ResultSet rset = stmt.executeQuery("select * from books where ISBN = '" + 
                isbnSearchIsbn.getText() + "'");
        if(!rset.next())
        {
            statuslbl.setText("Error: ISBN not in table");
        }
        rset = stmt.executeQuery("select * from books where ISBN = '" + 
                isbnSearchIsbn.getText() + "'");
        while(rset.next())
        {
            isbnSearchTitle.setText(rset.getString("Title"));
            isbnSearchAuthor.setText(rset.getString("Author"));
            if(rset.getString("checkout") != null)
            {
                IDInHold.add(rset.getString("bookId"));
            }
            else
            {
                IDOutHold.add(rset.getString("bookId"));
            }
        }
        for(int i=0; i < IDInHold.size(); i++)
        {
            textIn += IDInHold.get(i) +"\n";
        }
        for(int i=0; i < IDOutHold.size(); i++)
        {
            textOut += IDOutHold.get(i) + "\n";
        }
        isbnSearchIdsIn.setText(textIn);
        isbnSearchIdsOut.setText(textOut);
    }
    
    private void UserSearch() throws SQLException
    {
        stmt = connection.createStatement();
        ResultSet rset = stmt.executeQuery("select * from users where " + 
                "userId = '" + userSearchUser.getText() + "'");
        if(!rset.next())
        {
            statuslbl.setText("Error: User not found");
        }
        rset = stmt.executeQuery("select * from users where " + 
                "userId = '" + userSearchUser.getText() + "'");
        while(rset.next())
        {
            userSearchName.setText(rset.getString("userName"));
            userSearchAddress.setText(rset.getString("address"));
        }
    }
    
    private void Checkout() throws SQLException
    {
        stmt = connection.createStatement();
        ResultSet rset = stmt.executeQuery("select checkout from books " + 
                "where bookId = '" + checkoutBook.getText() + "'");
        ResultSet rset2;
        if(!rset.next())
        {
            statuslbl.setText("Error: Book does not exist");
        }
        else
        {
            rset2 = stmt.executeQuery("select checkout from books " + 
                "where bookId = '" + checkoutBook.getText() + "'");
            if(rset2.next())
            {
            if(rset2.getString("checkout") != null)
            {
                statuslbl.setText("Error: Book already checked out");
            }
            else
            {
                stmt.executeUpdate("update books set checkout = '" + 
                        checkoutUser.getText() + "' where bookId = '" +
                        checkoutBook.getText() + "'");
                statuslbl.setText("Checked Out Sucessfully");
            }
            }
        }
    }
    
    private void Returns() throws SQLException
    {
        stmt = connection.createStatement();
        ResultSet rset = stmt.executeQuery("select checkout from books " + 
                "where bookId = '" + checkoutBook.getText() + "'");
        ResultSet rset2;
        if(!rset.next())
        {
            statuslbl.setText("Error: Book does not exist");
        }
        else
        {
            rset2 = stmt.executeQuery("select checkout from books " + 
                "where bookId = '" + checkoutBook.getText() + "'");
            if(rset2.next())
            {
            if(rset2.getString("checkout") == null)
            {
                statuslbl.setText("Error: Book already returned");
            }
            else
            {
                if(!rset2.getString("checkout").equals(checkoutUser.getText()))
                {
                    statuslbl.setText("Error: Book checked out by another user");
                }
                else
                {
                stmt.executeUpdate("update books set checkout = null where bookId = '" +
                        checkoutBook.getText() + "'");
                statuslbl.setText("Returned successfully");
                }
            }
            }
        }
    }
    
    private void StatusByBook() throws SQLException
    {
        stmt = connection.createStatement();
        ResultSet rset = stmt.executeQuery("select checkout from books " +
                "where bookId = '" + checkoutBook.getText() + "'");
        ResultSet rset2;
        if(!rset.next())
        {
            statuslbl.setText("Error: Book does not exist");
        }
        else
        {
            rset2 = stmt.executeQuery("select checkout from books " +
                "where bookId = '" + checkoutBook.getText() + "'");
            if(rset2.next())
            {
            if(rset2.getString("checkout") == null)
            {
                statuslbl.setText("Book available");
            }
            else
            {
                String UID = rset2.getString("checkout");
                rset2 = stmt.executeQuery("select * from users where userId = '"
                + UID + "'");
                while(rset2.next())
                {
                    statuslbl.setText("Book checked out by user " + UID + 
                            ", name " + rset2.getString("userName") + 
                            ", address " + rset2.getString("address"));
                }
            }
            }
        }
    }
    
    private void RentalsByUser() throws SQLException
    {
        String lbltext = "";
        stmt = connection.createStatement();
        ResultSet rset = stmt.executeQuery("select bookId from books where" +
                " checkout = '" + checkoutUser.getText() + "'");
        ResultSet rset2;
        if(!rset.next())
        {
            statuslbl.setText("User has not checked out any books");
        }
        else
        {
            rset2 = stmt.executeQuery("select bookId from books where" +
                " checkout = '" + checkoutUser.getText() + "'");
            lbltext += "User has checked out: ";
            while(rset2.next())
            {
                lbltext += "Book " + rset2.getString("bookId") + "\n";
            }
            statuslbl.setText(lbltext);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
