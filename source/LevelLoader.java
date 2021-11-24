public class LevelLoader {
    /**
     * Loads tile map from a file reader.
     * @param levelReader file reader.
     * @param levelController reference to current level controller.
     * @return tile map.
     */
    public static Tile[][] loadTileMap(LevelFileReader levelReader, LevelController levelController) {
        int width = levelReader.getWidth();
        int height = levelReader.getHeight();

        Tile[][] tileMap = new Tile[width][height];
        //String[] tileString = levelReader.getTiles();
        //tileMap = populateTileMap(tileString,tileMap);

        //String[] ratString = levelReader.getRatSpawns();
        //tileMap = addRatsToTileMap(ratString,tileMap,levelController);

        //String[] powerString = levelReader.getPowers();
        //tileMap = addPowersToTileMap(powerString,tileMap);

        return tileMap;
    }

    private static Tile[][] addPowersToTileMap(String[] powerString, Tile[][] tileMap) {
        //for(int i = 0; i < tileMap.length; i++) {
        //    int charPos = -1;
        //    for (int j = 0; j < tileMap[i].length; j++) {
        //        charPos ++;
        //        switch (powerString.charAt(charPos)) {
        //            //ADD CASES HERE
        //        }
        //    }
        //}
        return tileMap;
    }

    /**
     * Turns string of tiles into tile map.
     * @param tiles string of tiles.
     * @param tileMap current tile map.
     * @return tile map.
     */
    private static Tile[][] populateTileMap(String[] tiles, Tile[][] tileMap) {
        for(int i = 0; i < tileMap[0].length; i++) {
            int charPos = -1;
            for (int j = 0; j < tileMap.length; j++) {
                charPos ++;
                switch (tiles[i].charAt(charPos)) {
                    case 'G':
                        tileMap[j][i] = new Grass();
                        break;
                    case 'P':
                        tileMap[j][i] = new Path();
                        break;
                    case 'T':
                        tileMap[j][i] = new Tunnel();
                }
            }
        }
        return tileMap;
    }

    /**
     * Turns string of rats into rats and adds them to tile map.
     * @param rats string of rats.
     * @param tileMap current tile map.
     * @param levelController reference to the level controller that is using level loader.
     * @return tile map.
     */
    private static Tile[][] addRatsToTileMap(String[] rats, Tile[][] tileMap, LevelController levelController) {
        for(int i = 0; i < tileMap[i].length ; i++) {
            int charPos = -1;
            for (int j = 0; j < tileMap.length; j++) {
                charPos ++;
                switch (rats[i].charAt(charPos)) {
                    case 'f':
                        //Rat fRat = new AdultFemale();
                        //tileMap[j][i].addRat(fRat);
                        //levelController.ratAdded(fRat);
                        break;
                    case 'm':
                        //Rat mRat = new AdultMale();
                        //tileMap[j][i].addRat(mRat);
                        //levelController.ratAdded(mRat);
                        break;
                    case 'c':
                        //Rat cRat = new ChildRat();
                        //tileMap[j][i].addRat(cRat);
                        //levelController.ratAdded(cRat);
                }
            }
        }
        return tileMap;
    }

    public static void saveTileMap(Tile[][] tileMap, LevelFileReader levelFileReader) {
        String[] tileString = tilesToString(tileMap);
        String[] ratString = ratsToString(tileMap);
    }

    private static String[] tilesToString(Tile[][] tileMap) {
        String[] tileString = new String[tileMap[0].length];
        for(int i = 0; i < tileMap.length ; i++) {
            for (int j = 0; j < tileMap[i].length; j++) {
                Tile tile = tileMap[i][j];
                if (tile instanceof Grass) {
                    tileString[j] = tileString[j] + "G";
                } else if (tile instanceof Path) {
                    tileString[j] = tileString[j] + "P";
                } else {
                    tileString[j] = tileString[j] + "T";
                }
            }
        }
        return tileString;
    }

    private static String[] ratsToString(Tile[][] tileMap) {
        String[] ratString = new String[tileMap[0].length];
        //for(int i = 0; i < tileMap.length ; i++) {
        //    for (int j = 0; j < tileMap[i].length; j++) {
        //        Rat[] rats = tileMap[i][j].getRats();
        //        if (rats.length == 0) {
        //            ratString[j] += "-";
        //        } else {
        //            for(int k = 0; k < rats.length; k++) {
        //                Rat rat = rats[k];
        //                if (rat instanceof AdultMale) {
        //                    ratString[j] += "m";
        //                } else if (rat instanceof AdultFemale) {
        //                    ratString[j] += "f";
        //                } else if (rat instanceof ChildRat) {
        //if female "c"
        //if male "k"
        //                } else if (rat instanceof DeathRat) {
        //                }
        //            }
        //        }
        //    }
        //}
        return ratString;
    }

}
