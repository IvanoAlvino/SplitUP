package com.ivano.splitup;

public class SplitUP {

  public static void main(String[] args) {
    IOManager.printMenu();

    Boolean finished = false;
    while (!finished) {

      try {
        Menu choice = IOManager.getChoice();
        finished = Menu.performOperation(choice);
      }
      catch (IllegalArgumentException e) {
        IOManager.printStatusMessage("Retry");
      }

    }
  }

}
