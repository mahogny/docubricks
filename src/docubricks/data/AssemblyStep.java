package docubricks.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.jdom2.Element;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

/**
 * 
 * 
 * @author Johan Henriksson
 *
 */
public class AssemblyStep
	{
	public MediaSet media=new MediaSet();
	public ArrayList<Function> parts=new ArrayList<Function>();
	private String desc="";
	
	
	public ArrayList<AssemblyStepComponent> components=new ArrayList<AssemblyStepComponent>();
	
	public void setDescription(String s)
		{
		desc=s;
		}
	public String getDescription()
		{
		return desc;
		}

	
	
	
	public void addPart(Function part)
		{
		parts.add(part);
		}

	
	public Element toXML(File basepath) throws IOException
		{
		Element eroot=new Element("step");

//		eroot.addContent(elWithContent("id", ""+id));
		eroot.addContent(elWithContent("description", desc));
		eroot.addContent(media.toXML(basepath));
		
		for(AssemblyStepComponent c:components)
			if(c.function!=null)
				eroot.addContent(c.toXML());
		
		return eroot;
		}
	
	
	private static Element elWithContent(String el, String content)
		{
		Element e=new Element(el);
		e.addContent(content);
		return e;
		}

	
	public static AssemblyStep fromXML(Brick brick, File basepath, Element root)
		{
		AssemblyStep step=new AssemblyStep();
		step.desc=root.getChildText("description");
		step.media=MediaSet.fromXML(basepath, root.getChild("media"));
		
		for(Element c:root.getChildren())
			{
			if(c.getName().equals("component"))
				step.components.add(AssemblyStepComponent.fromXML(brick, c));
			}
		
		return step;
		}
	
	
	public JSONObject toJSON(File basepath) throws IOException
		{
		JSONObject eroot=new JSONObject();

	//	eroot.addContent(elWithContent("id", ""+id));
		eroot.put("description", desc);
		eroot.put("files",media.toJSON(basepath));
		
		JSONArray arrcomp=new JSONArray();
		eroot.put("components", arrcomp);
		for(AssemblyStepComponent c:components)
			if(c.function!=null)
				arrcomp.add(c.toJSON());
		
		return eroot;
		}
	
	public void getReferencedFiles(Collection<File> files)
		{
		media.getReferencedFiles(files);
		}
	
	
	
	//rebuild: needs explaining
	
	}
