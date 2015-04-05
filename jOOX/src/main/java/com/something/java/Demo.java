/**
 * Copyright (c) 2009-2015, Lukas Eder, lukas.eder@gmail.com
 * All rights reserved.
 *
 * This software is licensed to you under the Apache License, Version 2.0
 * (the "License"); You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * . Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * . Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * . Neither the name "jOOX" nor the names of its contributors may be
 *   used to endorse or promote products derived from this software without
 *   specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.something.java;

import java.io.File;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

public class Demo {
    public static File folder = new File("C://Users//solankibhargav//workspaceWeb//Demo1"
        + "//jOOX//jOOX//src//main//java//com//something//java//folder");
    public static void main(String argv[]) {
        ArrayList<Node> list = new ArrayList<Node>();
        try {
            for (final File fileEntry : folder.listFiles()){
//            File fXmlFile = new File("C://Users//solankibhargav//workspaceWeb//Demo1"
//                + "//jOOX//jOOX//src//main//java//com//something//java//cofe.bpel");
            File fXmlFile=fileEntry;
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            for (Element element : org.joox.JOOX.$(fXmlFile).find("*")) {
                String name="";
              if (element.getNodeName().equals("reply") || element.getNodeName().equals("receive")
                   || element.getNodeName().equals("invoke"))
                {
                   if (!element.hasChildNodes()) {

                     //  System.out.println("Element: " + element.getNodeName() + "=");
                        name=name+element.getNodeName()+"_";
                   }
                    NamedNodeMap attributesList = element.getAttributes();
                    for (int j = 0; j < attributesList.getLength(); j++) {
                        if(attributesList.item(j).getNodeName().equals("operation")||attributesList.item(j).getNodeName().equals("partnerLink")||attributesList.item(j).getNodeName().equals("portType"))
                        name=name+attributesList.item(j).getNodeValue().substring(0, 1).toUpperCase()+attributesList.item(j).getNodeValue().substring(1);
                    }
                }
                if(!name.equals("")){
                //System.out.println(name);
                list.add(new Node(name));
                }
            }
            Iterator<Node> p=list.iterator();
            Node[] array=new Node[list.size()];
            int count=0;
            while(p.hasNext())
            {

                Node z=p.next();
                array[count]=new Node(z.getName());

                System.out.println(z.getName());
                count++;
            }
            String one=array[0].getName();
            String two=array[1].getName();
            java.sql.Connection con = null;
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/something", "root", "root");
            String query = " insert into bpel "
                + " values (?)";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString (1, one+","+two);
            preparedStmt.execute();

            for(int i=2;i<array.length;i++)
            {
               System.out.println(i);
               String temp=array[i-1].getName()+","+array[i-2].getName();
               preparedStmt.setString (1, temp);
               preparedStmt.execute();


            }
            con.close();

        }}
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
