package com.vinodborole.openstack4j.app.commands;

import java.io.PrintWriter;
import java.util.List;

import org.openstack4j.api.OSClient;
import org.openstack4j.model.compute.ActionResponse;
import org.openstack4j.model.identity.Tenant;
import org.openstack4j.model.image.Image;

import com.vinodborole.openstack4j.app.Osp4jSession;
import com.vinodborole.openstack4j.app.ShellContext;
import com.vinodborole.openstack4j.app.api.CinderAPI;
import com.vinodborole.openstack4j.app.api.CommonAPI;
import com.vinodborole.openstack4j.app.api.GlanceAPI;
import com.vinodborole.openstack4j.app.api.NeutronAPI;
import com.vinodborole.openstack4j.app.api.NovaAPI;
import com.vinodborole.openstack4j.app.api.TenantAPI;
import com.vinodborole.openstack4j.app.commands.factory.IOsp4jShellCommands;
import com.vinodborole.openstack4j.app.utils.Osp4jShellCommmandHelpInfo;
import com.vinodborole.openstack4j.app.utils.TableBuilder;

public class Osp4jShellCommanCommands implements IOsp4jShellCommands {

    private static final Osp4jShellCommanCommands INSTANCE = new Osp4jShellCommanCommands();
    private Osp4jShellCommanCommands(){}
    public static Osp4jShellCommanCommands getInstance(){
        return INSTANCE;
    }   
    
    @Override
    public void executeCommand(Commands command, List<String> params) throws Exception{
    try{  
            switch(command!=null?command:command.NULL){
                case SOURCE:
                {
                    System.out.println("loading properties");
                    Osp4jSession.loadProperties(params.get(1));
                }
                    break;
                case PRINT:
                {
                    Commands subcommand=Commands.fromString(params.get(1));
                    switch(subcommand!=null?subcommand:subcommand.NULL){
                        case CONFIG:
                        {
                            PrintWriter writer = new PrintWriter(System.out);
                            Osp4jSession.getConfigProperties().list(writer);
                            writer.flush();
                        }
                        break;
                        case TENANT_LIST:
                        {
                            OSClient os=Osp4jSession.getOspSession();
                            List<? extends Tenant> lstTenant=os.identity().tenants().list();
                            TableBuilder tb = new TableBuilder();
                            tb.addRow("Tenant ID","Tenant Name");
                            tb.addRow("---------","---------");
                            for(Tenant t : lstTenant){
                                tb.addRow(t.getId(),t.getName());
                            }
                            System.out.println(tb.toString());
                        }
                        break;
                        case HELP:
                        {
                            Osp4jShellCommmandHelpInfo.printHelp();
                         }
                        break;
                        case TENANT_INFO:
                        {
                            TenantAPI.printTenantInfo();
                        }
                        break;
                        case NULL:
                            System.err.println("Invaid command");
                    }
                }
                    break; 
                case DELETE:
                {
                    Commands subcommand=Commands.fromString(params.get(1));
                    switch(subcommand!=null?subcommand:subcommand.NULL){
                        case TENANT_ALL_INSTANCES:
                        {
                            TenantAPI.deleteAllVMs();
                        }
                        break;
                        case TENANT_ALL_VOLUMES:
                        {
                            TenantAPI.deleteAllVolumes();
                        }
                        break;
                        case TENANT_ALL_VOLUME_SNAPSHOTS:
                        {
                            TenantAPI.deleteAllVolumeSnapshots();
                        }
                        break;
                        case TENANT_ALL_IMAGES:
                        {
                            TenantAPI.deleteAllImages();
                        }
                        break;
                        case TENANT_ALL_NETWORKS:
                        { 
                            TenantAPI.deleteAllNetworks();
                        }
                        break;
                        case TENANT_ALL_ROUTERS:
                        {
                            TenantAPI.deleteAllRouters();
                        }
                        break;
                        case TENANT_ALL_SECURITY_GROUP_RULES:
                        {
                            TenantAPI.deleteAllSecurityGroupRules();
                        }
                        case TENANT_INFO:
                        {
                            TenantAPI.deleteTenantInfo();
                        }
                        break;     
                        case HELP:
                        {
                            Osp4jShellCommmandHelpInfo.deleteHelp();
                        }
                        break;
                        case NULL:
                            System.err.println("Invaid command");
                    }
                }
                break;
                case FLUSH:
                {
                    ShellContext.getContext().getShellMemory().flushMemory();
                }
                break;
                case LOGGING_YES:
                {
                    Osp4jSession.enableLogging();
                }
                break;
                case LOGGING_NO:
                {
                    Osp4jSession.disableLogging();
                }
                break;
                case HELP:
                {
                    Osp4jShellCommmandHelpInfo.allHelp();
                }
                break;
                default:
                    System.err.println("Invaid command");
            }
            
            }catch(Exception e){
            //suppress exception
        }
    }


}
