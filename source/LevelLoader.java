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

    public static void saveTileMap(Tile[][] tileMap) {
        String[] tileString = tilesToString(tileMap);
        String[] ratString = ratsToString(tileMap);
        String[] powerString = powersToString(tileMap);

        //LevelFileReader.saveLevelState(tileMap[0].length,tileMap.length,tileString,ratString,powerString);
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
        String[] ratString = new String[100];
        for(int i = 0; i < tileMap.length ; i++) {
            for (int j = 0; j < tileMap[i].length; j++) {
                Rat[] rats = tileMap[i][j].getOccupantRats().toArray(new Rat[0]);
                if (rats.length > 0) {
                    for (Rat rat : rats) {
                        if (rat instanceof AdultMale) {
                            ratString[j] += "M,";
                            int fertility;
                            if (((AdultMale) rat).getFertile()) {
                                fertility = 1;
                            } else {
                                fertility = 0;
                            }
                            ratString[j] = ratString[j] + rat.getSpeed() + "," + rat.getDirection() + "," + rat.getGasTimer() + "," + i + "," +
                                    j + "," + fertility;
                        } else if (rat instanceof AdultFemale) {
                            ratString[j] += "F,";
                            int fertility;
                            if (((AdultFemale) rat).getFertile()) {
                                fertility = 1;
                            } else {
                                fertility = 0;
                            }
                            ratString[j] = ratString[j] + rat.getSpeed() + "," + rat.getDirection() + "," + rat.getGasTimer() + "," + i + "," +
                                    j + "," + fertility + "," + ((AdultFemale) rat).getPregnancyTime() + "," + ((AdultFemale) rat).getRatFetusCount();
                        } else if (rat instanceof ChildRat) {
                            if (((ChildRat) rat).isFemale()) {
                                ratString[j] += "f,";
                            } else {
                                ratString[j] += "m,";
                            }
                            int fertility;
                            if (((ChildRat) rat).getFertile()) {
                                fertility = 1;
                            } else {
                                fertility = 0;
                            }
                            ratString[j] = ratString[j] + rat.getSpeed() + "," + rat.getDirection() + "," + rat.getGasTimer() + "," + i + "," +
                                    j + "," + fertility + "," + ((ChildRat) rat).getAge();
                        } else if (rat instanceof DeathRat) {
                            ratString[j] += "D,";
                            ratString[j] = ratString[j] + rat.getSpeed() + "," + rat.getDirection() + "," + rat.getGasTimer() + "," + i + "," +
                                    j + "," + ((DeathRat) rat).getKillCounter();
                        }
                    }
                }
            }
        }
        return ratString;
    }

    private static String[] powersToString(Tile[][] tileMap) {
        String[] powerString = new String[100];
        for(int i = 0; i < tileMap.length ; i++) {
            for (int j = 0; j < tileMap[i].length; j++) {
                Power[] powers = tileMap[i][j].getActivePowers().toArray(new Power[0]);
                if (powers.length > 0) {
                    for (Power power : powers) {
                        if (power instanceof Bomb){
                            switch(((Bomb) power).getTicksActive()) {
                                case 0:
                                    break;
                                case 1:
                                    break;
                                case 2:
                                    break;
                                case 3:
                                    break;
                                case 4:
                                    break;
                            }
                        } else if (power instanceof Gas) {
                            //do gas
                        } else if (power instanceof Sterilisation) {
                            //do sterilize
                        } else if (power instanceof Poison) {
                            //do poison
                        } else if (power instanceof MaleSwapper) {
                            //do male swap
                        } else if (power instanceof FemaleSwapper) {
                            //do female swap
                        } else if (power instanceof StopSign) {
                            //do the hustle do do do do dododo do do
                            switch (((StopSign) power).getHP()) {
                                case 1:
                                    break;
                                case 2:
                                    break;
                                case 3:
                                    break;
                                case 4:
                                    break;
                                case 5:
                                    break;
                            }
                        }
                    }
                }
            }
        }
        return powerString;
    }
}
