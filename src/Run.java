/**
 * Created by cjurthe on 06.02.2017.
 */
public class Run {

    //The order of instantiating the objects below will be important for some pairs of commands.
    //I haven't explored this in any detail, beyond that the order below works.

    private int start_value = 10;	//initialise model, which in turn initialises view

    public Run() {

        //create Model and View
        Model myModel 	= new Model();
        View myView 	= new View();

        //tell Model about View.
        myModel.addObserver(myView);
		/*
			init model after view is instantiated and can show the status of the model
			(I later decided that only the controller should talk to the model
			and moved initialisation to the controller (see below).)
		*/
        //uncomment to directly initialise Model
        //myModel.setValue(start_value);

        //create Controller. tell it about Model and View, initialise model
        Controller myController = new Controller();
        myController.addModel(myModel);
        myController.addView(myView);

        //tell View about Controller
        myView.addController(myController);

    } //RunMVC()

    public static void main(String[] args){

        Run mainRun = new Run();

    }

} //RunMVC