package com.vinodborole.openstack4j.app.commands;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
/**
 * Abstract Shell Commands 
 *  
 * @author vinod borole
 */
public abstract class Osp4jShellCommands {

    protected CommandLineParser subCommandParser = new DefaultParser();
}
