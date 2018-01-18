package com.renhou.model;

class Tile implements Comparable<Tile> {
    private final TileType type;
    private final int rank;
       
    Tile(TileType type, int rank) {
        this.type = type;
        this.rank = rank;
    }
        
    Tile(String input) {
        rank = Integer.parseInt(input.substring(0, 1));
        TileType t;
        switch(input.substring(0, 2)) {
            case "m":
                t = TileType.MAN;
            case "p":
                t = TileType.PIN;
            case "s":
                t = TileType.SOU;
            case "w":
                t = TileType.WIND;
            case "d":
                t = TileType.DRAGON;
            default:
                t = null;
        }
        type = t;
    }
        
    public TileType getType() {
        return this.type;
    }
        
    public int getRank() {
        return this.rank;
    }
        
    @Override
    public int compareTo(Tile t) {
        if (this.type == t.type) {
            return new Integer(this.rank).compareTo(new Integer(t.getRank()));
        } else {
            return this.type.compareTo(t.getType());
        }
    }
        
    @Override // for debugging
    public String toString() {
        String rank = Integer.toString(this.rank);
        switch(this.type) {
            case MAN: return rank + "m";
            case PIN:return rank + "p";
            case SOU: return rank + "s";
            case WIND: return rank + "w";
            case DRAGON: return rank + "d";
            default:return rank + this.type.toString();
        }
    }
}
