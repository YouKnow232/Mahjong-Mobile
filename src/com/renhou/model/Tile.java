package com.renhou.model;

class Tile implements Comparable<Tile> {
       private final TileType type;
       private final int rank;
       
       Tile(TileType type, int rank) {
    	   this.type = type;
    	   this.rank = rank;
       }
		
       public TileType getType() {
    	   return this.type;
       }
       
       public int getRank() {
    	   return this.rank;
       }
       
       @Override
       public int compareTo(Tile t) {
    	   if (type == t.type) {
    		   return new Integer(this.rank).compareTo(new Integer(t.getRank()));
    	   } else {
    		   return this.type.compareTo(t.getType());
    	   }
       }
}
