public abstract class LevelLoader {
    /**
     * Loads tile map from a file reader.
     * @param levelReader file reader.
     * @param levelController
     * @return
     */
    public static Tile[][] loadTileMap(FileReader levelReader, LevelController levelController) {
        //int width = levelReader.getWidth();
        //int height = levelReader.getHeight();

        //Tile[][] tileMap = new Tile[width][height];
        //String tileString = levelReader.getTiles();
        //tileMap = populateTileMap(tileString,tileMap);

        //String ratString = levelReader.getRatSpawns();
        //tileMap = addRatsToTileMap(ratString,tileMap,levelController);

        //String powerString = levelReader.getPowers();
        //tileMap = addPowersToTileMap(powerString,tileMap);

        //return tileMap;
        return null;
    }

    private static Tile[][] addPowersToTileMap(String powerString, Tile[][] tileMap) {
        //int charPos = -1;
        //for(int i = 0; i < tileMap.length; i++) {
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
    private static Tile[][] populateTileMap(String tiles, Tile[][] tileMap) {
        int charPos = -1;
        for(int i = 0; i < tileMap[0].length; i++) {
            for (int j = 0; j < tileMap.length; j++) {
                charPos ++;
                switch (tiles.charAt(charPos)) {
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
    private static Tile[][] addRatsToTileMap(String rats, Tile[][] tileMap, LevelController levelController) {
        int charPos = -1;
        for(int i = 0; i < tileMap[i].length ; i++) {
            for (int j = 0; j < tileMap.length; j++) {
                charPos ++;
                if(rats.charAt(charPos) != '-'){
                    switch (rats.charAt(charPos)) {
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
        }
        return tileMap;
    }

    public static void saveTileMap(Tile[][] tileMap, FileReader levelFileReader) {
        String tileString = tilesToString(tileMap);
        String ratString = ratsToString(tileMap);
    }

    private static String tilesToString(Tile[][] tileMap) {
        String tileString = "";
        for(int i = 0; i < tileMap.length ; i++) {
            for (int j = 0; j < tileMap[i].length; j++) {
                Tile tile = tileMap[i][j];
                if (tile instanceof Grass) {
                    tileString = tileString + "G";
                } else if (tile instanceof Path) {
                    tileString = tileString + "P";
                } else {
                    tileString = tileString + "T";
                }
            }
        }
        return tileString;
    }

    private static String ratsToString(Tile[][] tileMap) {
        String ratString = "";
        //for(int i = 0; i < tileMap.length ; i++) {
        //    for (int j = 0; j < tileMap[i].length; j++) {
        //        Rat[] rats = tileMap[i][j].getRats();
        //        if (rats.length == 0) {
        //            ratString += "-";
        //        } else {
        //            for(int k = 0; k < rats.length; k++) {
        //                Rat rat = rats[k];
        //                if (rat instanceof AdultMale) {
        //                    ratString += "m";
        //                } else if (rat instanceof AdultFemale) {
        //                    ratString += "f";
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
