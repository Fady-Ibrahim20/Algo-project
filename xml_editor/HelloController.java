package com.example.guil;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import java.util.Scanner ;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import javax.imageio.ImageIO;
import javax.swing.*;


public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
     static String mystring ;
    ArrayList<String> graph = new ArrayList<String>();
    static String st ,formateed  ;
    StringBuffer str = new  StringBuffer() ;
    ArrayList<String> fixError2 = new ArrayList<String>();
    ArrayList<Integer> lineOfError2= new ArrayList<Integer>();

    @FXML
    private TextArea txt1 ;
    @FXML
    private TextArea txt2 ;

    @FXML
    protected void correctbutton()
    {
        Boolean flag = false ;
        txt2.clear();
        for (int i = 0; i < lineOfError2.size(); i ++)
        {
            if (lineOfError2.get(i) == 1)
            {
                flag = true ;
                break ;
            }
        }
        if(flag)
        {
            txt2.appendText(correction.print(fixError2));
            txt2.appendText("\n"+"press format  then ");

        }
        else
        {
            txt2.appendText("No need to be corrected" );
        }




    }

    @FXML
    protected void checkbutton()
    {
        txt2.clear();
        File file = new File(Reader.filepath);
        Scanner scan = null;
        try {
            scan = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<String> line = new ArrayList<String>();
        ArrayList<Integer> lineOfError = new ArrayList<Integer>();
        ArrayList<String> fixError = new ArrayList<String>();

        while(scan.hasNextLine())
        {
            line.add(scan.nextLine()) ;
        }
        try
        {
            String a ;
            a=  correction.check_error(line, lineOfError, fixError);
            fixError2 =  fixError;
            lineOfError2= lineOfError;
            txt2.appendText(a);
        }
        catch(Exception EmptyStackException)
        {
            txt2.appendText("Wrong XML file , You have a Closing Tag without Opening it" );
            txt2.appendText("Please Fix it, then Try again");

        }


    }




    @FXML
    protected void brwosebutton()
    {
        String f ;
        f=Reader.Read();
        txt1.appendText(f);
        Node.parsingtoarraylist(f, graph);

    }
    @FXML
    protected void Compressbutton()
    {
        txt2.clear();
        String a ;
       a= Compress.compresscaller(formateed);
        txt2.appendText(a);


    }
    @FXML
    protected void Decompressbutton()
    {
        txt2.clear();
        String a ;
        a= Decompress.decompresscaller();
        txt2.appendText(a);

    }
    @FXML
    protected void Minfiybutton()
    {
        txt2.clear();
         String b ;
         b= Minify.minifycaller(formateed);
        txt2.appendText(b);
    }
    @FXML
    protected void Fomratbutton()
    {
        txt2.clear();
        st  =correction.listtostring(fixError2);

        int a[]=new int[st.length()+1] ;
        ArrayList<String> output = new ArrayList<String>();
        Format myformat=new Format();
        myformat.parsing_xml_with_array(st,output,a);
        myformat.Check_ParsingArray(a,output);
        StringBuilder output2= new StringBuilder();
        myformat.pretiffy(a,output,output2);
        String output3=output2.toString();
        formateed = output3 ;
        txt2.appendText(String.valueOf(output3));
        new File("Formatted.xml");
        FileWriter myWriter = null;
        try{
            myWriter = new FileWriter("Formatted.xml");
            myWriter.write((String.valueOf(output3)));}
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        try{
            myWriter.close();}
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    @FXML
    protected void convertbutton()
    {
        txt2.clear();
        ArrayList<String> xmllist = new ArrayList<>();
        Node root = new Node();
        String temp ;
        temp = Minify.minifycaller(st);
        Node.parsingtoarraylist(temp, xmllist);
        root.setNameOfTag(xmllist.get(0));
        count i=new count(1);
        tree.filltree(root, xmllist, i);
        xml2json.convert(root,str);
        txt2.appendText(String.valueOf(str));
        new File("ToJSON.json");
        FileWriter myWriter = null;
        try{
        myWriter = new FileWriter("ToJSON.json");
        myWriter.write((String.valueOf(str)));}
       catch (IOException e) {
           System.out.println("An error occurred.");
           e.printStackTrace();
       }
        try{
    myWriter.close();}
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    @FXML
    public void visualise () {
        int[][] arr = new int[100][100];
        int[] ids = new int[101];
        int users = Graph.graph(graph, arr, ids);
        DefaultDirectedGraph<String, DefaultEdge> g = Graph.createGraph(arr, ids, users);
        try {
            Graph.visualize(g);
        } catch (IOException e) {
        }

        File file = new File("graph.png");
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(file);
       }
       catch (IOException e){};
        ImageIcon imageIcon = new ImageIcon(bufferedImage);
        JFrame jFrame = new JFrame();

        jFrame.setLayout(new FlowLayout());

        jFrame.setSize(500, 500);
        JLabel jLabel = new JLabel();

        jLabel.setIcon(imageIcon);
        jFrame.add(jLabel);
        jFrame.setVisible(true);

        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

