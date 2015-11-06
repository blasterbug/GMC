package se.umu.cs.ht15.dali_ens15bsf;

import java.util.Random;

/**
 * Created by ens15bsf on 2015-11-06.
 */
public class RandomNames
{
  private static final String[] NAMES = {
          "King AdasKing Adas",
          "Padmé Amidala",
          "Darth Andeddu",
          "Jar Jar Binks",
          "Chewbacca",
          "Jango Fett",
          "Boba Fett",
          "Greedo",
          "General Grievous",
          "Jabba the Hutt",
          "Qui-Gon Jinn",
          "Obi-Wan Kenobi",
          "Galen Marek",
          "Darth Maul",
          "Princess Leia Organa",
          "Emperor Sheev Palpatine",
          "R2-D2",
          "Darth Sidious",
          "Anakin Skywalker",
          "Luke Skywalker",
          "Anakin Solo",
          "Han Solo",
          "Jaina Solo",
          "Ahsoka Tano",
          "Grand Moff Wilhuff Tarkin",
          "Darth Tyranus",
          "Darth Vader",
          "Watto",
          "Mace Windu",
          "Yoda"
  };

  private static final String[] PLACES = {
          "Alderaan",
          "Bespin",
          "Coruscant",
          "Dagobah",
          "Endor",
          "Geonosis",
          "Hoth",
          "Jakku",
          "Kamoni",
          "Kashyyyk",
          "Lothal",
          "Mustafar",
          "Naboo",
          "Sullust",
          "Tatooine",
          "Utapau",
          "Yavin",
          "Yavin 4"
  };

  private static final Random dice = new Random();


  public static String getRandomUserName ()
  {
    return NAMES[dice.nextInt( NAMES.length )];
  }

  public static String getRandomGroupName ()
  {
    return PLACES[dice.nextInt( PLACES.length )];
  }
}
