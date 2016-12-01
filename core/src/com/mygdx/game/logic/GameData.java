package com.mygdx.game.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;


/**
 * Created by Madnar on 04/06/2016.
 */

/**
 * Class responsable for load and save  Highscore file
 */
public class GameData {

    private static GameData instance;
    private static final int MAX_SCORES = 5;
    private static long[] highScores;
    private static String[] names;
    private static String FILENAME = "highscore.txt";


    static {
        highScores = new long[MAX_SCORES];
        names =  new String[MAX_SCORES];

        for(int i  = 0; i < MAX_SCORES; i++)
        {
            highScores[i] = 0;
            names[i] = "----";
        }

        FileHandle temp = Gdx.files.local(FILENAME);
        temp.writeString("",true);
    }


    /**
     * Saves GameData content into highscores.txt
     */
    public static void save () {
        FileHandle temp = Gdx.files.local(FILENAME);
      for(int i = 0; i < MAX_SCORES; i++)
      {
          if(i == 0)
          temp.writeString(names[i]+"\n",false);
          else
              temp.writeString(names[i]+"\n",true);
          temp.writeString(highScores[i]+"\n",true);
      }

    }

    /**
     * Load highscore.txt content.
     * If empty initialize GameData.
     */
    public static void load() {
        FileHandle temp = Gdx.files.local(FILENAME);


        String content  = temp.readString();

        String[] lines = content.split("\n");

        if(lines.length < 10)
        {
            GameData.init();
            return;
        }


        for(int i = 0; i < MAX_SCORES; i++)
        {
           names[i] = lines[2*i];
            highScores[i] = Long.parseLong(lines[i*2+1],10);
        }

    }


    /**
     * Inicialize GameData
     */
    public static void init() {

        highScores = new long[MAX_SCORES];
        names =  new String[MAX_SCORES];

        for(int i  = 0; i < MAX_SCORES; i++)
        {
            highScores[i] = 0;
            names[i] = "----";
        }

        FileHandle temp = Gdx.files.local(FILENAME);
        temp.writeString("",true);

    }

    /**
     * @return highscores array
     */
    public static long[] getHighScores () {
        return highScores;
    }

    /**
     * @return names aaray
     */
    public static String[] getNames () {
        return names;
    }


    /**
     * @param score hisgscore to be tested
     * @return true if score is in top 5 scores
     */
    public static boolean isHighScore( long score ) {
        return score > highScores[MAX_SCORES - 1];
    }

    /**
     * If score is in top5 scores add newScore associated with string.
     * @param newScore new highscore
     * @param name new higscore tag
     */
    public static void addHighScore(long newScore, String name)
    {
        if(isHighScore(newScore))
        {
            highScores[MAX_SCORES - 1 ] = newScore;
            names[MAX_SCORES -1] = name;
            sortHighScores();
        }

    }

    /**
     * Sort scores in highscores array
     */
    public static void  sortHighScores(){
        for(int i = 0; i < MAX_SCORES; i++)
        {
            long score = highScores[i];
            String name = names[i];
            int j;
            for(j = i - 1; j >= 0 && highScores[j] < score; j--) {
                highScores[j + 1] = highScores[j];
                names[j + 1] = names[j];
            }
            highScores[j + 1] = score;
            names[j + 1] = name;
        }
    }


}
