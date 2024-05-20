package Songs;
import java.util.Comparator;

public class Songs {

  //Compare songs based on artist
  public static int compareByArtist(Song a, Song b) {
    return a.artist.compareTo(b.artist);
  }

  //Compare songs based on album
  public static int compareByAlbum(Song a, Song b) {
    return a.album.compareTo(b.album);
  }

  //Compare songs based on release date
  public static int compareByReleaseDate(Song a, Song b) {
    return a.releaseYear - b.releaseYear;
  }

  public static void insertionSort(Song[] arr, Comparator<Song> comparator) {
    for (int i = 1; i < arr.length; i++) {
      Song key = arr[i];
      int j = i - 1;

      //Move elements of arr[0..i-1], that are greater than key,
      //to one position ahead of their current position
      while (j >= 0 && comparator.compare(arr[j], key) > 0) {
        arr[j + 1] = arr[j];
        j = j - 1;
      }
      arr[j + 1] = key;
    }
  }

  public static void main(String[] args) {
    Song[] songs = {
        new Song("Song1", "Artist2", "Album1", 2010),
        new Song("Song2", "Artist1", "Album2", 2005),
        new Song("Song3", "Artist3", "Album1", 2015),
        new Song("Song4", "Artist4", "Album3", 2008),
        new Song("Song5", "Artist1", "Album2", 2003),
        new Song("Song6", "Artist3", "Album4", 2019),
        new Song("Song7", "Artist2", "Album3", 2012),
        new Song("Song8", "Artist4", "Album4", 2017),
        new Song("Song9", "Artist5", "Album5", 2014),
        new Song("Song10", "Artist5", "Album5", 2011)
    };

    //Sort by artist
    insertionSort(songs, Songs::compareByArtist);
    System.out.println("Sorted by Artist:");
    for (Song song : songs) {
      System.out.println(song.title + " from " + song.artist);
    }
    System.out.println();

    //Sort by album
    insertionSort(songs, Songs::compareByAlbum);
    System.out.println("Sorted by Album:");
    for (Song song : songs) {
      System.out.println(song.title + " from " + song.album);
    }
    System.out.println();

    //Sort by release date
    insertionSort(songs, Songs::compareByReleaseDate);
    System.out.println("Sorted by Release Date:");
    for (Song song : songs) {
      System.out.println(song.title + " released in " + song.releaseYear);
    }
  }
}