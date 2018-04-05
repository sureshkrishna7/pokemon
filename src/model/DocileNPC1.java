package model;

import java.util.Vector;

//NON attacking NPC

public class DocileNPC1 extends NPC{
	
	private String filepath;
	
	public DocileNPC1(String npcName, Boolean hostility) {		//Could use Vector<String> dialogue as parameter...
		super(npcName, hostility, null);
		setImage(filepath);
		setType("NPC");
		dialogues.addElement("Hello");
		dialogues.addElement("Next dialogue");
		dialogues.addElement("This is a test...");
		dialogues.addElement("Lets battle.");
	}
}
