package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.Scanner;


public class Controller implements Initializable {

    public TextField processInput;


    public VBox chartVBox;


    public BorderPane root;
    public VBox legendVBox;
    public Label numberError;

    public MenuItem play;


    public TableColumn TokenColumn;
    public TableColumn Typecolumn;
    public TableView table;
    public Button upload;

   public File file=new File("");
    public Label fileError;
    public Menu simulate;
    public Label fileName;
    public TextArea textarae;
    public ImageView pimage;
    private Parser parser;
    private String filePath;
    //called as soon as the layout loads
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        root.getStylesheets().add(getClass().getResource("Viper.css").toExternalForm());
        TokenColumn.setCellValueFactory(new PropertyValueFactory<>("input"));
        Typecolumn.setCellValueFactory(new PropertyValueFactory<>("value"));


    }
    boolean validateFile(File file)
    {


            if(file.length()==0)
            {
                fileError.setText("*File is Empty");
                return false;
            }
            fileError.setText("");
            return true;

    }

boolean Is_fileOpen(File file){
    
    try {
        org.apache.commons.io.FileUtils.touch(file);

        return   true;
    } catch (IOException e) {
        return  false;
    }
}
boolean is_file_txt(File file) throws IOException {

try {
    String mimeType = Files.probeContentType(Paths.get(file.getPath()));

    if (mimeType.compareTo("text/plain") == 0) {
        return true;
    }else{
        return  false;
    }
}catch (Exception e){
    return false;

}
}
    int flag_comment_start=0;
    Token token = new Token();
void send(String a){

        if(a.length()==0){
            return;
        }
    token = new Token();

    String substring = new String();
    int l = a.length() - 1;
    if (a.compareTo("{") == 0) {
        flag_comment_start = 1;
        return;
    } else if (a.compareTo("}") == 0 && flag_comment_start == 1) {
      //  substring = a;
      //  token.findType(a);
      ///  token.setInput(substring);
      //  table.getItems().add(token);
        flag_comment_start = 0;
      return;
    } else if (flag_comment_start == 1) {
       // substring = a;
      //  token.setValue(Tokens.type.comment);
      //  token.setInput(substring);
      //  table.getItems().add(token);
      return;
    }

    token.findType(a);
    token.setInput(a);
    table.getItems().add(token);
}
int flag=0;
    public void startOnAction(ActionEvent actionEvent) throws IOException, SyntaxErrorException {
        table.getItems().clear();
        flag_comment_start=0;
        pimage.setVisible(false);
if(!Is_fileOpen(file)||flag==1){
    fileError.setText("*ERROR, Please Enter text File");
    return ;
}
if(!validateFile(file)){
    return;
}
        try {
            parser = new Parser(filePath);
            parser.start("png");
            byte[] imgBytes = parser.getTree().getImg("png");
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(imgBytes));
            Image image = SwingFXUtils.toFXImage(img, null );


            pimage.setImage(image);
            pimage.setVisible(true);



        }
        catch (SyntaxErrorException e){
            fileError.setText("Syntax Error");


        }

        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String line;
        while((line = br.readLine()) != null){
            String word=new String();
            for (int i = 0; i < line.length(); i++) {

                if(token.getValue()== Token.TokenType.error) {
                    return;
                }
                if(line.charAt(i)==' '||line.charAt(i)=='\t'){
                    if(flag_comment_start==1){
                        continue;
                    }
                    send(word);
                    if(token.getValue()== Token.TokenType.error){
                        return;
                    }
                    word="";
                }else if(line.charAt(i)==']'){
                    if(flag_comment_start==1){
                        continue;
                    }
                    send(word);
                    if(token.getValue()== Token.TokenType.error){
                        return;
                    }
                    send(line.charAt(i)+"");
                    word="";

                }else if(line.charAt(i)=='['){
                    if(flag_comment_start==1){
                        continue;
                    }
                    send(word);
                    if(token.getValue()== Token.TokenType.error){
                        return;
                    }
                    send(line.charAt(i)+"");
                    word="";

                }
                else if(line.charAt(i)=='<'){
                    if(flag_comment_start==1){
                        continue;
                    }
                    send(word);
                    if(token.getValue()== Token.TokenType.error){
                        return;
                    }
                    send(line.charAt(i)+"");
                    word="";

                }else if(line.charAt(i)=='>'){
                    if(flag_comment_start==1){
                        continue;
                    }
                    send(word);
                    if(token.getValue()== Token.TokenType.error){
                        return;
                    }
                    send(line.charAt(i)+"");
                    word="";

                }
                else if(line.charAt(i)=='+'){
                    if(flag_comment_start==1){
                        continue;
                    }
                    send(word);
                    if(token.getValue()== Token.TokenType.error){
                        return;
                    }
                    send(line.charAt(i)+"");
                    word="";

                }else if(line.charAt(i)=='-'){
                    if(flag_comment_start==1){
                        continue;
                    }
                    send(word);
                    if(token.getValue()== Token.TokenType.error){
                        return;
                    }
                    send(line.charAt(i)+"");
                    word="";

                }else if(line.charAt(i)=='/'){
                    if(flag_comment_start==1){
                        continue;
                    }
                    send(word);
                    if(token.getValue()== Token.TokenType.error){
                        return;
                    }
                    send(line.charAt(i)+"");
                    word="";

                }else if(line.charAt(i)=='*'){
                    if(flag_comment_start==1){
                        continue;
                    }
                    send(word);
                    if(token.getValue()== Token.TokenType.error){
                        return;
                    }
                    send(line.charAt(i)+"");
                    word="";

                }else if(line.charAt(i)=='='){
                    if(flag_comment_start==1){
                        continue;
                    }
                    if(word.length()!=0&&word.charAt(word.length()-1)==':'){
                        send(word.substring(0,word.length()-1));
                        if(token.getValue()== Token.TokenType.error){
                            return;
                        }
                        send(":=");
                        word="";}
                    else  if(word.length()!=0&&word.charAt(word.length()-1)=='!'){
                        send(word.substring(0,word.length()-1));
                        if(token.getValue()== Token.TokenType.error){
                            return;
                        }
                        send("!=");
                        word="";}
                    else  if(line.length()!=i+1&&line.charAt(i+1)=='='){
                        send(word);
                        if(token.getValue()== Token.TokenType.error){
                            return;
                        }
                        send("==");

                        word="";
                        i++;
                    }

                    else {
                        send(word);
                        if(token.getValue()== Token.TokenType.error){
                            return;
                        }
                        send(line.charAt(i)+"");
                        word="";}

                }
                else if(line.charAt(i)==')'){
                    if(flag_comment_start==1){
                        continue;
                    }
                    send(word);
                    if(token.getValue()== Token.TokenType.error){
                        return;
                    }
                    send(line.charAt(i)+"");
                    word="";

                }
                else if(line.charAt(i)=='('){
                    if(flag_comment_start==1){
                        continue;
                    }
                    send(word);
                    if(token.getValue()== Token.TokenType.error){
                        return;
                    }
                    send(line.charAt(i)+"");
                    word="";

                }
                else if(line.charAt(i)==';'){
                    if(flag_comment_start==1){
                        continue;
                    }
                    send(word);
                    if(token.getValue()== Token.TokenType.error){
                        return;
                    }
                    send(line.charAt(i)+"");
                    word="";

                }
                else if(line.charAt(i)=='{') {
                    if(flag_comment_start==1){
                        continue;
                    }
                    if(token.getValue()== Token.TokenType.error){
                        return;
                    }
                    send(word);
                    send(line.charAt(i) + "");
                    word="";
                }else if(line.charAt(i)=='}') {
                    // send(word);
                    send(line.charAt(i) + "");
                    //  word = "";
                }
                else if(i==line.length()-1){
                    if(flag_comment_start==1){
                        continue;
                    }
                    send(word+line.charAt(i));
                    if(token.getValue()== Token.TokenType.error){
                        return;
                    }
                    word="";}
                else  {
                    if(flag_comment_start==1){
                        continue;
                    }
                    if(line.charAt(i)!='\t'){
                        word+=line.charAt(i);
                    }


                }


            }

        }







        if(flag_comment_start==1){
            token.setInput("un complete comment");
            token.setValue(Token.TokenType.error);
            table.getItems().add(token);
        }

    }

    public void uploadOnAction(ActionEvent actionEvent) throws IOException {
        Stage primaryStage=new Stage();
        primaryStage.setTitle("JavaFX App");
        fileError.setText("");

        FileChooser fileChooser = new FileChooser();

            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            file=selectedFile;
        fileName.setText( file.getName());

        textarae.clear();
       if(!is_file_txt(file)){
           fileError.setText("*ERROR, Please Enter text File");
           pimage.setVisible(false);
           flag=1;

           return;
       }
         Scanner scan=new Scanner(file);
while(scan.hasNext()){
    String s=scan.nextLine();
    if(textarae.getText().length()!=0){
    textarae.setText(textarae.getText()+"\n"+s);}else
    textarae.setText(s);
}
         filePath = selectedFile.getAbsolutePath();
flag=0;

    }

    public void closeOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) table.getScene().getWindow();

        stage.close();
    }

    public void SaveOnAction(ActionEvent actionEvent) throws IOException {
        if(!Is_fileOpen(file)){
            fileError.setText("*Please Enter File");
            return ;
        }
        OutputStream os = null;
       String data= textarae.getText();
        os = new FileOutputStream(file);

        os.write(data.getBytes(), 0, data.length());


    }

    public void clearOnAction(ActionEvent actionEvent) {
        textarae.clear();

    }
}