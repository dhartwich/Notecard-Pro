package de.dhartwich.notecard;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class NotecardxActivity extends Activity {
	
	List<String> xmlFiles;
	public ArrayList<File> xmlFileList;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final AssetManager mgr = getAssets();
        
        
      	displayFiles(mgr, "xml",0); 
      	 xmlFiles = displayFiles(mgr, "xml",0); 
         for (int e = 0; e<=xmlFiles.size()-1;e++)
         {
         	Log.v("Inhalt List"+e+": ", xmlFiles.get(e));
         }     
	
      // 	TextView malfenster;
     //  	malfenster = (TextView) this.findViewById(R.id.editText1);    
       	
       	xmlFileList = new ArrayList<File>();
       	for (int i = 0; i<=xmlFiles.size()-1;i++)
       	{
       		
       		xmlFileList.add(new File("xml/"+xmlFiles.get(i)));
       	}
       	for (int i = 0; i<=xmlFileList.size()-1;i++)
       	{
       		Log.v("Name: ", xmlFileList.get(i).getName());
       		Log.v("Filelocation: ",xmlFileList.get(i).getPath());
       		Log.v("Filelocation: ",xmlFileList.get(i).getAbsolutePath());       		
       	}
       	Log.v("DEBUG","XML FILE LISTE erfolgreich geschrieben!");
       	
       	ArrayList<File> filesa = new ArrayList<File>();
       	List<String> str = getFiles();
       	for (int i=0; i<= str.size()-1;i++)
       	{
       		filesa.add(new File(str.get(i)));
       		Log.v("NeueListe: ", str.get(i));
       	}
       	
       	for (int i=0; i<=filesa.size()-1;i++)
       	{
       		Log.v("FileArrayneu: ", filesa.get(i).getAbsolutePath());
       		Log.v("FileArrayneu: ", filesa.get(i).getName());
       	}

     //  	horst.interpretXML(xmlFileList.get(0));
       	
       	//Alternative zum ausgelagerten XML INTERPRETER
       	try
		{
	//	if(xmlFileList.get(0) == null){
			//Log.v("Debug", "XMLFILE IS NULL");
       		
       	ArrayList<File> abcd = new ArrayList<File>();
     	for (int i=0;i<=xmlFileList.size()-1;i++)     	
     	{	
		InputStream is = getAssets().open(xmlFileList.get(i).getPath());
		File f= xmlFileList.get(i);
			
		 OutputStream out=new FileOutputStream(f);
		  byte buf[]=new byte[2048];
		  int len;
		  while((len=is.read(buf))>0)
		  out.write(buf,0,len);
		  
		  abcd.add(f);
		 
				
		int size = is.available();
		
		byte[] buffer = new byte[size];
		is.read(buffer);
		//is.close();
		
		String text = new String(buffer);
		
		Log.v("Fertiger Dateiname: ",text );
		
     	
		TextView tv = (TextView) this.findViewById(R.id.abc);
        tv.setText(text);
		
      //  File xmldoc = new File(getAssets().open(xmlFileList.get(i).getPath()));
        
     	}
       for(int i = 0; i <=abcd.size()-1;i++)
       {
    	   Log.v("DebugFileArray", abcd.get(i).getName());
       }
		
		File fXmlFile = abcd.get(0);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();
		
		Log.v("Root element :", doc.getDocumentElement().getNodeName());
		NodeList nList = doc.getElementsByTagName("notecard");
		
		
		for (int temp = 0; temp < nList.getLength()-1; temp++) {
			 
			   Node nNode = nList.item(temp);
			   if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	 
			      Element eElement = (Element) nNode;
	 
			      Log.v("GesetzesText: " , getTagValue("legal_text", eElement));
			     // System.out.println("Kommentar : " + getTagValue("comment", eElement));
		          	 
			   }
			}
		
		}
       	
		catch (Exception e) {
			e.printStackTrace();
		  }
       	
    //   	mgr.close();
      /*  
       
	
        
        File directory = new File("/assets/xml");
        System.out.println(directory.getName());
        
         File[] xmls = directory.listFiles();
        
        for (int i = 0; i <=xmls.length-1; i++)
        {
        	xmllist.add(xmls[i]);
        	System.out.println(xmls[i].getName());
        }
        */
        
  		
    }

    private List<String> displayFiles (AssetManager mgr, String path, int level) {
    	List<String> abc = new ArrayList<String>();
        Log.v("Hello","enter displayFiles("+path+")");
       try {
           String list[] = mgr.list(path);
           abc.addAll(Arrays.asList(list));
            Log.v("Hello1","L"+level+": list:"+ Arrays.asList(list));
                               
       } catch (IOException e) {
           Log.v("Fail","List error: can't list" + path);
       }
	return abc;

   }
    
    private String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
		 
        Node nValue = (Node) nlList.item(0);
 
        return nValue.getNodeValue();
	}
    
	private List<String> getFiles()
   	{
   	 AssetManager assetManager = getAssets();
   	    List<String> files = null;
   	    try {
   	        files = Arrays.asList(assetManager.list(""));
   	    	} 
   	    catch (IOException e) 
   	    {
   	        Log.e("tag", e.getMessage());
   	    }
		return files;
   	} 
	

        
   }
