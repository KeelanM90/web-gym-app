package controllers;

import models.Member;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

/**
 * Responsible for loading Models from the data.yml file.
 *
 * @author Keelan Murphy
 * @version 18/05/2017
 */

@OnApplicationStart
public class Bootstrap extends Job {
    public void doJob() {
        if (Member.count() == 0) {
            Fixtures.delete();
            Fixtures.loadModels("data.yml");
        }
    }
}