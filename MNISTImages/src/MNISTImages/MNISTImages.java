package MNISTImages;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Image {
  public byte[] data;
  public int id;

  public Image(byte[] data, int id) {
    this.data = new byte[data.length];
    System.arraycopy(data, 0, this.data, 0, data.length);
    this.id = id;
  }

  public void print() {
    for (int i = 0; i < data.length; i++) {
      if (data[i] == 0)
        System.out.print(" ");
      else
        System.out.print("*");
      if ((i + 1) % 28 == 0)
        System.out.println();
    }
  }

  public double euclideanDistance(Image img) {
    double distance = 0.0;
    for (int i = 0; i < data.length; i++) {
      distance += Math.sqrt(Math.pow((data[i] - img.data[i]), 2));
    }
    return Math.sqrt(distance);
  }
}

public class MNISTImages {
  static final int ImageSize = 784;
  static final int MetaDataSize = 15;

  public static void main(String[] args) {
    List<Image> images = new ArrayList<>();

    try (FileInputStream ifs = new FileInputStream("input.dat")) {
      //Skip the metadata at the beginning of the file
      ifs.skip(MetaDataSize);

      byte[] pixels = new byte[ImageSize];
      int count = 0;
      //Read data from the file and insert images into the list
      while (ifs.read(pixels) > 0) {
        images.add(new Image(pixels, count++));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    System.out.println("Total images: " + (images.size() - 1));

    //Example: Find the closest image to a randomly selected image
    //Seed the random number generator
    Random rand = new Random();

    //Generate a random index within the range of the list length
    int randomIndex = rand.nextInt(images.size());
    System.out.println("Random index: " + randomIndex);

    Image randomImage = images.get(randomIndex);
    randomImage.print();

    Image closestImage = null;
    double minDistance = Double.POSITIVE_INFINITY;
    int minIndex = 0;

    for (int i = 0; i < images.size(); i++) {
      double distance = randomImage.euclideanDistance(images.get(i));
      if (distance != 0 && distance < minDistance) {
        minDistance = distance;
        minIndex = i;
        closestImage = images.get(i);
      }
    }

    //Output the label of the closest image
    System.out.println("\nClosest image (distance=" + minDistance +
        ", index = " + minIndex + ")\n");

    //Print closest image
    closestImage.print();
  }
}
