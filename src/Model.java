import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by cjurthe on 06.02.2017.
 */
public class Model extends java.util.Observable {

    private int counter;	//primitive, automatically initialised to 0

    public Path favorite_path;

    public Path source;
    public Path target;
    public String trennzeichen;
    public Boolean kennung_func= false;
    public String kennung_func_name;

    public List<String> kennung_source;
    public List<String> kennung_target;
    public List<String> pattern;
    public List<String> rules;
    public HashMap<String, String> fileobj;

    public Model(){

        System.out.println("Model()");

        this.favorite_path = Paths.get("//10.1.10.10/produktion/03_Operations/");

    } //Model()

    public Boolean check_dir_exist(Path path_to_check){
        if(Files.exists(path_to_check)){
            return true;
        }
        return false;
    }
    public Boolean check_dir_writeable(Path path_to_check){
        if(Files.isWritable(path_to_check)){
            return true;
        }
        return false;
    }
    public Path browse (Path directory){
        JFileChooser fileChooser = new JFileChooser();

        if(directory != null){
            fileChooser.setCurrentDirectory(new File(directory.toString()));
        }

        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile.toPath();
        }
        return null;
    }

    public void setValue(int value) {

        this.counter = value;
        System.out.println("Model init: counter = " + counter);
        setChanged();
        //model Push - send counter as part of the message
        notifyObservers(counter);
        //if using Model Pull, then can use notifyObservers()
        //notifyObservers()

    } //setValue()

    public void setKennung(String[] input, String type){
        switch(type){
            case "source":
                this.kennung_source = Arrays.asList(input);
                break;
            case "target":
                this.kennung_target = Arrays.asList(input);
                break;
            case "func":

                break;
        }
    }

    void pattern_add(String key){
        if(this.pattern == null){
            this.pattern = new ArrayList();
        }
        this.pattern.add(key);
    }

    void pattern_reset(){
        this.pattern = null;
    }

    public HashMap<String, String> filename_obj(String filename){
        this.fileobj = new HashMap<String, String>();

        String[] temp_ext = null;
        String[] temp_pattern = null;
        temp_ext = filename.split("\\.(?=[^\\.]*$)");

        this.fileobj.put("ext",temp_ext[1]);
        this.fileobj.put("filename",temp_ext[0]);


        temp_pattern = this.fileobj.get("filename").split("(" + Pattern.quote(this.trennzeichen) + ")",2);

        if(this.rules!=null && this.rules.size()>0){
            if(this.rules.contains("sols_cropped")){
                String first = temp_pattern[0].substring(0,1);
                String rest = temp_pattern[0].substring(1);
                temp_pattern[0] = first + "c" + rest;
            }
        }
        this.fileobj.put("sourcename",temp_pattern[0]);
        if(temp_pattern.length>1) {
            String[] x = temp_pattern[1].split("(" + Pattern.quote(this.trennzeichen) + ")", 2);

            if (x.length > 1) {
                //Element nach letztem Trennzeichen zur Umbenennung nutzen
                this.fileobj.put("kennung_source", x[(x.length-1)]);
                this.fileobj.put("kennung_target", null);
            } else if (x.length == 1) {
                this.fileobj.put("kennung_source", x[0]);
                this.fileobj.put("kennung_target", null);
            }

            if (this.kennung_func) {
                String result = "";
               switch(this.kennung_func_name){
                   case "sols":
                       result = rename_sols(x[0]);
                       break;
               }
                this.fileobj.put("kennung_target", result);
            } else {
                if (this.kennung_source != null && this.kennung_source.size() > 0) {
                    for (int i = 0; i < this.kennung_source.size(); i++) {
                        if (this.fileobj.get("kennung_source").equals(this.kennung_source.get(i))) {
                            if (this.kennung_target != null && this.kennung_target.size() > 0) {
                                this.fileobj.put("kennung_target", this.kennung_target.get(i));
                            }
                        }
                    }
                }
            }

        }


        return this.fileobj;
    }

    public List<File> getFilesFromDir(Path dirpath) throws IOException {
        List<File> filesInFolder = null;
        try{
            filesInFolder = Files.walk(dirpath)
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());
        }catch(Exception e){
            System.out.println(e);
        }
        return filesInFolder;
    }

    public String getNewFilename(HashMap fileobj){
        String newFilename = "";
        System.out.println(this.pattern);
        for(int i=0; i< this.pattern.size(); i++){
            switch(this.pattern.get(i)){
                case "ean":
                    newFilename += fileobj.get("sourcename");
                    break;
                case "kennung":
                    newFilename += fileobj.get("kennung_target");
                    break;
                case "static":
                    newFilename +=  this.trennzeichen;
                    break;
            }
        }
        newFilename += "." + fileobj.get("ext");
                //newFilename = fileobj.get("sourcename") + this.trennzeichen + fileobj.get("kennung_target") + "." + fileobj.get("ext");
        return newFilename;
    }

    public Boolean copy_file(Path source,Path target,String filename){
        try{
            if(Files.copy(source, target.resolve(filename), StandardCopyOption.REPLACE_EXISTING) != null){
                return true;
            }
        }catch(Exception copyEx){

        }
        return false;
    }
    // x = split nach dem ersten trennzeichen, gesplittet nach weiteren trennzeichen
    public String rename_sols(String x){

        return  x.substring(x.length() - 1);

    }



} //Model
