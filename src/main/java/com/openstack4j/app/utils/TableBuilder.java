package com.openstack4j.app.utils;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
 
public class TableBuilder {
    private List<String[]> rows = new LinkedList<String[]>();
    
    public void addRow(String...cls){
        rows.add(cls);
    }
    public int totalrecords()
    {
        return rows.size()-2;
    }
    private int[] colWidths(){
        int cols = -1;
        for(String[] row : rows){
            cols=Math.max(cols, row.length);
        }
        int[] widths = new int[cols];
        for(String[] row : rows){
            for(int colNum = 0; colNum<row.length;colNum++){
                widths[colNum] = Math.max(widths[colNum], StringUtils.length(row[colNum]));
            }
        } 
        return widths;
    }
    @Override
    public String toString()
    {
        StringBuilder buf = new StringBuilder();
        int[] colWidths = colWidths();
        for(String[] row :rows){
            for(int colNum =0 ; colNum<row.length;colNum++){
                buf.append(StringUtils.rightPad(StringUtils.defaultString(row[colNum]), colWidths[colNum]));
                buf.append("    ");
            }
            buf.append('\n');
        }
        return buf.toString();
    }
}