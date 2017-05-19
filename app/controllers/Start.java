package controllers;

import play.Logger;
import play.mvc.Controller;

/**
 * Renders the start (index) view.
 *
 * @author Keelan Murphy
 * @version 18/05/2017
 */

public class Start extends Controller {
    public static void index() {
        Logger.info("Rendering Start");
        render("start.html");
    }
}