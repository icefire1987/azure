import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by cjurthe on 06.02.2017.
 */
public class Controller {

    Model model;
    View view;

    Controller() {
        System.out.println ("Controller()");
    } //Controller()


    //invoked when a button is pressed
    public void actionPerformed(java.awt.event.ActionEvent e){
        //uncomment to see what action happened at view

		System.out.println ("Controller: The " + e.getActionCommand()
			+ " button is clicked at " + new java.util.Date(e.getWhen())
			+ " with e.paramString " + e.paramString() );

        System.out.println("Controller: acting on Model");
        //model.incrementValue();
    } //actionPerformed()

    public void rename(){
        this.view.log("Start Progress");
        Integer renamed = 0;
        Integer not_renamed = 0;
        System.out.println("Controller: Rename");
        view.progressBar.setIndeterminate(true);
        view.table.clear();
        try{
            List<File> filesInFolder = this.model.getFilesFromDir(this.model.source);
            view.source_count.setText(filesInFolder.size() + " Dateien");
            Integer c = 0;
            for(File aFile:filesInFolder){
                c++;
                String file_string = aFile.getName();
                HashMap<String, String> fileobj;
                fileobj = this.model.filename_obj(file_string);

                if(fileobj.get("ext").equals("JPEG") || fileobj.get("ext").equals("jpeg") || fileobj.get("ext").equals("JPG") || fileobj.get("ext").equals("jpg")){
                    String newFilename = this.model.getNewFilename(fileobj);
                    if (this.model.copy_file(aFile.toPath(), this.model.target, newFilename) != false) {
                        renamed++;
                    }else{
                        not_renamed++;
                        this.view.log("Nicht umbenannt: " + file_string);
                    }
                    view.table.model.addRow(
                            new Object[]{
                                    file_string,
                                    fileobj.get("sourcename"),
                                    fileobj.get("kennung_source"),
                                    fileobj.get("kennung_target"),
                                    newFilename
                            }
                    );
                }

            }
            this.view.log("umbenannt: " + renamed);
            this.view.log("Nicht umbenannt: " + not_renamed);
            view.progressBar.setIndeterminate(false);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            view.log(e.toString());
        }

    }
    public void addPattern(String name, String defaultVal){
        switch (name){
            case "ean":
            case "static":
            case "kennung":
                System.out.println("Controller: add "+name);
                this.model.pattern_add(name);
                this.view.pattern_show(name,defaultVal);
                break;
            case "kennung_func":
                System.out.println("Controller: add "+name);
                this.model.pattern_add(name);
                this.view.pattern_show(name,defaultVal);
                break;
            case "reset":
                System.out.println("Controller: reset");
                this.model.pattern_reset();
                this.view.pattern_show(name,null);
                break;

        }
    }
    public void browse(String name){
        Path temp;
        switch (name){
            case "source":
                System.out.println("Controller: browse source");
                this.setSource(this.model.browse(this.model.favorite_path));
                break;
            case "target":
                System.out.println("Controller: browse target");
                if(this.model.source != null){
                    temp =  this.model.browse(this.model.source.getParent());
                }else{
                    temp =  this.model.browse(this.model.favorite_path);
                }
                this.setTarget(temp);
                break;

        }
    }
    public void setSource(Path directory){
        if(directory != null && this.model.check_dir_exist(directory)==true){
            this.model.source = directory;
            view.setField("source",this.model.source.toString());
            view.log("Quellordner gesetzt: \r\n"+ this.model.source);
        }else{
            view.log("kein gültiger Ordner");
        }
    }
    public void setTarget(Path directory){
        if(directory != null && this.model.check_dir_writeable(directory)==true){
            this.model.target = directory;
            view.setField("target",this.model.target.toString());
            view.log("Zielordner gesetzt: \r\n"+ this.model.target);
        }else{
            view.log("kein gültiger, beschreibbarer Ordner");
        }
    }
    void setTrennzeichen(String trennzeichen){
        this.model.trennzeichen = trennzeichen;
        view.setField("trennzeichen",this.model.trennzeichen.toString());
        view.log("Trennzeichen gesetzt: \r\n"+ this.model.trennzeichen);
    }
    void setKennung(String kennung_source,String source_split, String kennung_target, String target_split){

        this.model.setKennung(kennung_source.split(source_split), "source");
        this.model.setKennung(kennung_target.split(target_split), "target");

        view.setTextArea("kennung_source",this.model.kennung_source);
        view.setTextArea("kennung_target",this.model.kennung_target);
        view.log("Kennung gesetzt");
    }

    void setKennung_func(){

        this.model.setKennung(null, "func");

        view.setTextArea("Kennung",null);

        view.log("Kennung_Func gesetzt");
    }
    public void confirm(String name){
        switch (name){
            case "source":
                System.out.println("Controller: confirm source");
                this.setSource(Paths.get(view.getSource()));
                break;
            case "target":
                System.out.println("Controller: confirm target");
                this.setTarget(Paths.get(view.getTarget()));
                break;
            case "kennung":
                System.out.println("Controller: confirm kennung");
                this.setKennung(view.getKennung_source(),"\\r?\\n", view.getKennung_target(),"\\r?\\n");
                System.out.println(this.model.kennung_source);
                System.out.println(this.model.kennung_target);
                System.out.println(this.model.kennung_source.size());
                break;
            case "trennzeichen":
                System.out.println("Controller: confirm trennzeichen");
                this.setTrennzeichen(view.getTrennzeichen());
                break;


        }
    }
    public void preview(){
        System.out.println("Controller: Preview");
        view.progressBar.setIndeterminate(true);
        view.table.clear();
        try{
            List<File> filesInFolder = this.model.getFilesFromDir(this.model.source);
            view.source_count.setText(filesInFolder.size() + " Dateien");
            Integer c = 0;
            for(File aFile:filesInFolder){
                c++;
                String file_string = aFile.getName();
                HashMap<String, String> fileobj;
                fileobj = this.model.filename_obj(file_string);

                if(fileobj.get("ext").equals("JPEG") || fileobj.get("ext").equals("jpeg") || fileobj.get("ext").equals("JPG") || fileobj.get("ext").equals("jpg")){
                    String newFilename = this.model.getNewFilename(fileobj);
                    System.out.println("fileobj");
                    System.out.println(fileobj);
                    view.table.model.addRow(
                            new Object[]{
                                    file_string,
                                    fileobj.get("sourcename"),
                                    fileobj.get("kennung_source"),
                                    fileobj.get("kennung_target"),
                                    newFilename
                            }
                    );
                }

            }
            view.progressBar.setIndeterminate(false);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("Controller: preview error");
            e.printStackTrace();
            view.log(e.toString());
        }
    }
    public void setPreset(String name){
        switch (name){
            case "galeria_fbfr":
                System.out.println("Controller: preset paula");
                this.setTrennzeichen("_");
                this.setKennung(
                        "Front,Back,Front-Right" ,
                        ",",
                        "001,002,003",
                        ","
                );
                this.model.favorite_path = Paths.get("//10.1.10.10/produktion/03_Operations/Galeria");
                this.addPattern("reset",null);
                this.addPattern("ean",null);
                this.addPattern("static","_");
                this.addPattern("kennung",null);
                break;
            case "galeria_frbfl":
                System.out.println("Controller: preset paula");
                this.setTrennzeichen("_");
                this.setKennung(
                        "Front-Right,Back-Left,Front" ,
                        ",",
                        "001,002,003",
                        ","
                );
                this.model.favorite_path = Paths.get("//10.1.10.10/produktion/03_Operations/Galeria");
                this.addPattern("reset",null);
                this.addPattern("ean",null);
                this.addPattern("static","_");
                this.addPattern("kennung",null);
                break;
            case "galeria_add0":
                System.out.println("Controller: preset GK 00");
                this.setTrennzeichen("_");
                this.setKennung(
                        "1,2,3,4,5,6,7,8,9,10,11" ,
                        ",",
                        "001,002,003,004,005,006,007,008,009,010,011",
                        ","
                );
                this.model.favorite_path = Paths.get("//10.1.10.10/produktion/03_Operations/Galeria");
                this.addPattern("reset",null);
                this.addPattern("ean",null);
                this.addPattern("static","_");
                this.addPattern("kennung",null);
                break;
            case "paula":
                System.out.println("Controller: preset galeria");
                this.setTrennzeichen("_");
                this.setKennung(
                        "Front,Front-Right,Back,FABRIC_DETAIL,Back-Left,GHOST" ,
                        ",",
                        "01,02,03,04,02,06",
                        ","
                );
                this.model.favorite_path = Paths.get("//10.1.10.10/produktion/03_Operations/Hey Paula");
                this.addPattern("reset",null);
                this.addPattern("ean",null);
                this.addPattern("static","_");
                this.addPattern("kennung",null);
                break;
            case "sols":
                System.out.println("Controller: preset Sols");
                this.model.favorite_path = Paths.get("//10.1.10.10/produktion/03_Operations/SOL'S");
                this.setTrennzeichen("_");
                this.setKennung_func();
                this.addPattern("reset",null);
                this.addPattern("ean",null);
                this.addPattern("kennung","A");
                break;

        }
    }

    public void addModel(Model m){
        System.out.println("Controller: adding model");
        this.model = m;
    } //addModel()

    public void addView(View v){
        System.out.println("Controller: adding view");
        this.view = v;
    } //addView()

} //Controller