//Judging ACNH villagers based on music taste and other arbitrary factors
//*******CREATE ORDERING OF SONGS BASED ON MY OPINION********

public class Villager implements Comparable <Villager> {
    //Name,Species,Gender,Personality,Hobby,Birthday,Catchphrase,Favorite Song,Style 1,Style 2,Color 1,Color 2,Wallpaper,Flooring,Furniture List,Filename,Unique Entry ID
    String name;
    String personality;
    String hobby;
    String favSong;  int songRank;
    //based on my totally factual and objective measurement of K.K. Slider's ACNH discography
    final String[] finalKKSongRank = {"K.K. Cruisin\'", "Driving\'", "K.K. Metal", "K.K. Milonga", "K.K. House",
            "K.K. Gumbo", "K.K. Rock", "K.K. Western", "The K. Funk", "K.K. Mambo", "K.K. Salsa", "K.K. Condor",
            "Soulful K.K.", "K.K. Parade", "K.K. Soul", "K.K. Groove", "Imperial K.K.", "K.K. Bossa", "Only Me",
            "K.K. Blues", "Bubblegum K.K.", "Space K.K.", "Hypno K.K.", "Rockin\' K.K.", "Surfin\' K.K.",
            "K.K. Ballad", "Comrade K.K.", "K.K. Folk", "K.K. Moody", "Lucky K.K.", "K.K. Sonata", "K.K. Dirge",
            "K.K. Fusion", "K.K. Rockabilly", "Spring Blossoms", "K.K. Synth", "K.K. Flamenco", "K.K. Steppe",
            "K.K. Safari", "K.K. Tango", "K.K. Étude", "K.K. Disco", "K.K. Island", "K.K. Swing", "K.K. Country",
            "K.K. Jazz", "K.K. D&B", "K.K. Rally", "K.K. Aria", "K.K. Lament", "Agent K.K.", "Marine Song 2001",
            "Steep Hill", "Forest Life", "Stale Cupcakes", "K.K. Stroll", "My Place", "Pondering", "K.K. Marathon",
            "K.K. Adventure", "K.K. March", "Two Days Ago", "K.K. Reggae", "K.K. Oasis", "Animal City", "Aloha K.K.",
            "K.K. Lullaby", "K.K. Technopop", "K.K. Faire", "Wandering", "Farewell", "K.K. Love Song", "K.K. Dixie",
            "K.K. Ragtime", "K.K. To The Edge", "K.K. Casbah", "K.K. Calypso", "Café K.K.", "K.K. Bazaar",
            "DJ K.K.", "Go K.K. Rider", "King K.K.", "Mr. K.K.", "K.K. Chorale", "Mountain Song",  "Neapolitan",
            "I Love You", "K.K. Song",};

    //Default constructor
    Villager() {
        name = null;
        personality = null;
        hobby = null;
        favSong = null;
        songRank = -1;
    }

    //Parametrized constructor
    Villager(String name, String personality, String hobby, String favSong) {
        this.name = name;
        this.personality = personality;
        this.hobby = hobby;
        this.favSong = favSong;
        songRank = evaluateSong(favSong);
    }

    //Setter and getter for name
    public void setName(String n){ name = n; }
    public String getName(){ return name; }

    //Setter and getter for personality
    public void setPersonality(String p){ personality = p; }
    public String getPersonality(){ return personality; }

    //Setter and getter for hobby
    public void setHobby(String h){ hobby = h; }
    public String getHobby(){ return hobby; }

    //Setter and getter for favSong
    public void setFavSong(String fs){ favSong = fs; }
    public String getFavSong(){ return favSong; }

    //Getter for songRank
    public int getSongRank() { return songRank; }

    //------------------------------Comparator Stuff------------------------------

    //Returns the index of favSong in the finalKKSongRank list, otherwise returns -1
    private int evaluateSong(String song) {
        if (song.isEmpty()) { return -1; }      //case where song is null
        int i = 0;
        while (i < finalKKSongRank.length) {         //move through ranked list
            if (finalKKSongRank[i].equals(song)) {
                return i;                       //if found, return index of song
            } else {
                i++;
            }
        }
        return -1;                              //otherwise, return -1
    }

    //compares two villagers together
    @Override
    //Compare first by song, then hobby, then personality, then name
    public int compareTo(Villager v) {
        if (songRank > v.getSongRank()) {
            return 1;
        } else if (songRank < v.getSongRank()) {
            return -1;
        } else { ////MAY NEED TO ALTER THIS BECUAUSE IDK WHAT COMPARE WILL RETURN *******************************************************
            return personality.compareTo(v.getPersonality()) + hobby.compareTo(v.getHobby()) + name.compareTo(v.getName());
        }
    }

    //Returns string version of villager
    @Override
    public String toString() {
        return (name + "'s favorite song is " +favSong + "! Fitting for a " + personality + " villager who likes " + hobby + ".");
    }

}
