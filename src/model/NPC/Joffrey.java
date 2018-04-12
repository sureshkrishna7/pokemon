package model.NPC;

import java.util.ArrayList;

import model.Pokemon;

public class Joffrey extends NPC{

  public Joffrey(String name, boolean hostile, ArrayList<String> dialogues, ArrayList<Pokemon> listOfPokemon) {
    super(name, hostile, dialogues, listOfPokemon);
  }

  @Override
  public String getDialogue(int i) {
    // TODO Auto-generated method stub
    return null;
  }

}
