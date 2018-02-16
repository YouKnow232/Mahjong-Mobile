package com.renhou.model;

class Tile implements Comparable<Tile> {
    private final TileType type;
    private final int rank;
    private final boolean isRed;
    
    Tile(TileType type, int rank, boolean isRed) {
        this.type = type;
        this.rank = rank;
        this.isRed = isRed;
    }
    
    Tile(TileType type, int rank) {
        this(type, rank, false);
    }
    
    /**
     * Converts a string representation of a tile into a Tile object.
     * Format is first character suit/type, second character rank, optional third character 'R' denoting redness.
     * Types are 'm', 'p', 's', 'w',and 'd'. ranks are 0-9 but should be suitable for the type.
     * 
     * @param input String representation of a tile. 
     */
    Tile(String input) {
        this.rank = Character.getNumericValue(input.charAt(0));
        TileType t;
        switch(input.charAt(1)) {
            case 'm':
                t = TileType.MAN; break;
            case 'p':
                t = TileType.PIN; break;
            case 's':
                t = TileType.SOU; break;
            case 'w':
                t = TileType.WIND; break;
            case 'd':
                t = TileType.DRAGON; break;
            default:
                t = null;
        }
        this.type = t;
        boolean isRedTemp;
        try {
            if(input.charAt(2) == 'R')
                isRedTemp = true;
            else
                isRedTemp = false;
        } catch (IndexOutOfBoundsException e) {
            isRedTemp = false;
        }
        this.isRed = isRedTemp;
    }
    
    // Getters
    public TileType getType()   {return this.type;}
    public int getRank()        {return this.rank;}
    public boolean isRed()      {return isRed;}
    
    /**
     * Like compareTo, but boolean and ignores isRed attribute.
     * 
     * @return true if rank and type are equal, else false
     */
    public boolean isCongruent(Tile t) {
        return type == t.getType() && rank == t.getRank();
    }
    
    /**
     * outputs a new Tile object which can be used to detect dora tiles
     * when called on dora indicator tiles.
     * 
     * @return a new Tile object with increased rank modulo that rank's capacity
     */
    public Tile doraTile()  {
        return new Tile(type, (rank) % type.getRankCap() + 1);
    }
    
    
    @Override
    /**
     * for use in sorting.  Use isSame() for triplet detection instead.
     */
    public int compareTo(Tile t) {
        if (this.type == t.type) {
            if (this.rank == t.rank) {
                if (this.isRed && !t.isRed()) {
                    return -1;
                } else if (!this.isRed && t.isRed()) {
                    return 1;
                } else {
                    return 0;
                }
            } else {
                return new Integer(this.rank).compareTo(new Integer(t.getRank()));
            }
        } else {
            return this.type.compareTo(t.getType());
        }
    }
        
    @Override // for debugging readability
    public String toString() {
        String rank = Integer.toString(this.rank);
        String t;
        switch(this.type) {
            case MAN: t = "m"; break;
            case PIN: t = "p"; break;
            case SOU: t = "s"; break;
            case WIND: t = "w"; break;
            case DRAGON: t = "d"; break;
            default: t = this.type.toString();
        }
        
        if (this.isRed) {
            return rank + t + "R";
        } else {
            return rank + t;
        }
    }
}
