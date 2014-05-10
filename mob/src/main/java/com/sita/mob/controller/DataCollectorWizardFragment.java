package com.sita.mob.controller;

import com.sita.mob.controller.facility.FacilityFragment;
import org.codepond.wizardroid.WizardFlow;
import org.codepond.wizardroid.layouts.BasicWizardLayout;

/**
 * Created by smurali on 5/9/14.
 */
public class DataCollectorWizardFragment extends BasicWizardLayout{
    public DataCollectorWizardFragment() {
        super();
    }

    public static DataCollectorWizardFragment newInstance() {
        DataCollectorWizardFragment fragment = new DataCollectorWizardFragment();
        return fragment;
    }

    @Override
    public WizardFlow onSetup() {
        /* Optional -  set different labels for the control buttons
        setNextButtonLabel("Advance");
        setBackButtonLabel("Return");
        setFinishButtonLabel("Finalize"); */

        return new WizardFlow.Builder()
                .addStep(FacilityFragment.class)           //Add your steps in the order you want them
                .addStep(FacilityFragment.class)           //to appear and eventually call create()
                .create();                              //to create the wizard flow.
    }
}
