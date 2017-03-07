package com.thefitnation.cucumber.stepdefs;

import com.thefitnation.TheFitNationApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = TheFitNationApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
