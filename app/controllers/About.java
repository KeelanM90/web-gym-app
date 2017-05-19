package controllers;

import play.Logger;
import play.mvc.Controller;

/**
 * Renders the about view
 *
 * @author Keelan Murphy
 * @version 18/05/2017
 */
public class About extends Controller
{
    public static void index() {
        Logger.info("Rendering About");
        render ("about.html");
    }
}