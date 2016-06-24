package com.vinodborole.openstack4j.app.commands;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.openstack4j.model.network.Network;

import com.vinodborole.openstack4j.app.api.NeutronAPI;
import com.vinodborole.openstack4j.app.commands.factory.IOsp4jShellCommands;
import com.vinodborole.openstack4j.app.utils.Osp4jShellCommmandHelpInfo;
import com.vinodborole.openstack4j.app.utils.OspPrintWriter;
/**
 * Responsible for executing cinder neutron on Openstack cloud
 *  
 * @author vinod borole
 */
public class Osp4jShellNeutronCommands extends Osp4jShellCommands implements IOsp4jShellCommands{

    private static final Osp4jShellNeutronCommands INSTANCE = new Osp4jShellNeutronCommands();
    private Osp4jShellNeutronCommands(){}
    public static Osp4jShellNeutronCommands getInstance(){
        return INSTANCE;
    }   
    public void executeCommand(Commands command, List<String> params) throws Exception{

        switch(command!=null?command:Commands.NULL){
        case NEUTRON:
        {
            Commands subcommand=Commands.fromString(params.get(1));
            switch(subcommand!=null?subcommand:Commands.NULL){
                case NET_LIST:
                {
                    NeutronAPI.printNetsDetails(NeutronAPI.netList());
                }
                break; 
                case NET_CREATE_DEFAULT:
                {
                    Network network = NeutronAPI.createNetDefault(params.get(2));
                    NeutronAPI.printNetDetails(network);
                }
                break;
                case NET_SHOW:
                {
                    Network network=NeutronAPI.getNetDetails(params.get(2));
                    NeutronAPI.printNetDetails(network);
                }
                break;
                case NET_CREATE:
                {
                    Network network=NeutronAPI.createNetwork(params.get(2));
                    NeutronAPI.printNetDetails(network);
                }
                break;
                case NET_DELETE:
                {
                    NeutronAPI.deleteNetwork(params.get(2));
                }
                break;
                case ROUTER_CREATE:
                {
                    NeutronAPI.printRouterDetails(NeutronAPI.createRouter(params.get(2)));
                }
                break;
                case ROUTER_DELETE:
                {
                    NeutronAPI.deleteRouter(params.get(2));
                }
                break;
                case ROUTER_LIST:
                {
                    NeutronAPI.printRouterDetails(NeutronAPI.getAllRouters());
                }
                break;
                case ROUTER_SHOW:
                {
                    NeutronAPI.printRouterDetails(NeutronAPI.getRouterDetails(params.get(2)));
                }
                break;
                case ROUTER_INTERFACE_ADD :
                {
                    NeutronAPI.addSubnetInterfaceToRouter(params.get(2), params.get(3));
                }
                break;
                case ROUTER_INTERFACE_DELETE:
                {
                    NeutronAPI.deleteSubnetInterfaces(params.get(2), params.get(3));
                }
                break;
                case HELP:
                {
                    Osp4jShellCommmandHelpInfo.neutronHelp();
                }
                break;
                case NULL:
                    System.err.println("Invaid command");
                default:
                    break; 
            }
        }
            default:
                break;
     }
        
    }
    public void executeCommand(String[] args) throws Exception {
        Commands subcommand=Commands.fromString(args.length > 1 ? args[1]:null);
        switch(subcommand!=null?subcommand:Commands.NULL){
            case NET_LIST:
            {
                NeutronAPI.printNetsDetails(NeutronAPI.netList());
            }
            break; 
            case NET_CREATE_DEFAULT:
            {
                Options netCreateDefaultOptions=Osp4jShellCommonCommandOptions.getNeutronNetCreateDefaultHelpOptions();
                try{
                    CommandLine line = subCommandParser.parse(netCreateDefaultOptions, Arrays.copyOfRange(args, 2, args.length));
                    Network network = NeutronAPI.createNetDefault(line.getOptionValue("name"));
                    NeutronAPI.printNetDetails(network);
                }catch(ParseException e){
                    System.out.println(e.getMessage());
                    OspPrintWriter.printHelp(args[0]+" "+args[1], netCreateDefaultOptions);
                }
            }
            break;
            case NET_SHOW:
            {
                Options netShowOptions=Osp4jShellCommonCommandOptions.getNeutronShowHelpOptions();
                try{
                    CommandLine line = subCommandParser.parse(netShowOptions, Arrays.copyOfRange(args, 2, args.length));
                    System.out.println(line.getOptionValue("id"));
                    Network network=NeutronAPI.getNetDetails(line.getOptionValue("id"));
                    NeutronAPI.printNetDetails(network);
                }catch(ParseException e){
                    System.out.println(e.getMessage());
                    OspPrintWriter.printHelp(args[0]+" "+args[1], netShowOptions);
                }
            }
            break;
            case NET_CREATE:
            {
                Options netCreateOptions=Osp4jShellCommonCommandOptions.getNeutronNetCreateHelpOptions();
                try{
                    CommandLine line = subCommandParser.parse(netCreateOptions, Arrays.copyOfRange(args, 2, args.length));
                    Network network=NeutronAPI.createNetwork(line.getOptionValue("name"));
                    NeutronAPI.printNetDetails(network);
                }catch(ParseException e){
                    System.out.println(e.getMessage());
                    OspPrintWriter.printHelp(args[0]+" "+args[1], netCreateOptions);
                }
            }
            break;
            case NET_DELETE:
            {
                Options netDeleteOptions=Osp4jShellCommonCommandOptions.getNeutronNetDeleteHelpOptions();
                try{
                    CommandLine line = subCommandParser.parse(netDeleteOptions, Arrays.copyOfRange(args, 2, args.length));
                    NeutronAPI.deleteNetwork(line.getOptionValue("id"));
                }catch(ParseException e){
                    System.out.println(e.getMessage());
                    OspPrintWriter.printHelp(args[0]+" "+args[1], netDeleteOptions);
                }
            }
            break;
            case ROUTER_CREATE:
            {
                Options routerCreateOptions=Osp4jShellCommonCommandOptions.getNeutronRouterCreateHelpOptions();
                try{
                    CommandLine line = subCommandParser.parse(routerCreateOptions, Arrays.copyOfRange(args, 2, args.length));
                    NeutronAPI.printRouterDetails(NeutronAPI.createRouter(line.getOptionValue("name")));
                }catch(ParseException e){
                    System.out.println(e.getMessage());
                    OspPrintWriter.printHelp(args[0]+" "+args[1], routerCreateOptions);
                }
            }
            break;
            case ROUTER_DELETE:
            {
                Options routerDeleteOptions=Osp4jShellCommonCommandOptions.getNeutronRouterDeleteHelpOptions();
                try{
                    CommandLine line = subCommandParser.parse(routerDeleteOptions, Arrays.copyOfRange(args, 2, args.length));
                    NeutronAPI.deleteRouter(line.getOptionValue("id"));
                }catch(ParseException e){
                    System.out.println(e.getMessage());
                    OspPrintWriter.printHelp(args[0]+" "+args[1], routerDeleteOptions);
                }
            }
            break;
            case ROUTER_LIST:
            {
                NeutronAPI.printRouterDetails(NeutronAPI.getAllRouters());
            }
            break;
            case ROUTER_SHOW:
            {
                Options routerShowOptions=Osp4jShellCommonCommandOptions.getNeutronRouterShowHelpOptions();
                try{
                    CommandLine line = subCommandParser.parse(routerShowOptions, Arrays.copyOfRange(args, 2, args.length));
                    NeutronAPI.printRouterDetails(NeutronAPI.getRouterDetails(line.getOptionValue("id")));
                }catch(ParseException e){
                    System.out.println(e.getMessage());
                    OspPrintWriter.printHelp(args[0]+" "+args[1], routerShowOptions);
                }
            }
            break;
            case ROUTER_INTERFACE_ADD :
            {
                Options routerInterfaceAddOptions=Osp4jShellCommonCommandOptions.getNeutronRouterInterfaceAddHelpOptions();
                try{
                    CommandLine line = subCommandParser.parse(routerInterfaceAddOptions, Arrays.copyOfRange(args, 2, args.length));
                    NeutronAPI.addSubnetInterfaceToRouter(line.getOptionValue("routerid"), line.getOptionValue("subnetid"));
                }catch(ParseException e){
                    System.out.println(e.getMessage());
                    OspPrintWriter.printHelp(args[0]+" "+args[1], routerInterfaceAddOptions);
                }
            }
            break;
            case ROUTER_INTERFACE_DELETE:
            {
                Options routerInterfaceDeleteOptions=Osp4jShellCommonCommandOptions.getNeutronRouterDeleteHelpOptions();
                try{
                    CommandLine line = subCommandParser.parse(routerInterfaceDeleteOptions, Arrays.copyOfRange(args, 2, args.length));
                    NeutronAPI.deleteSubnetInterfaces(line.getOptionValue("routerid"), line.getOptionValue("subnetid"));
                }catch(ParseException e){
                    System.out.println(e.getMessage());
                    OspPrintWriter.printHelp(args[0]+" "+args[1], routerInterfaceDeleteOptions);
                }
            }
            break;
            case NULL:;
            case HELP:
            {
                OspPrintWriter.printHelp(args[0], Osp4jShellCommonCommandOptions.getNeutronHelpOptions());
            }
            break;
            default:
                throw new Exception("Invalid Command!"); 
        }
    }
}