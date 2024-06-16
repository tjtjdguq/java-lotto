package view;

import java.util.Scanner;

public class InputView {

    private final Scanner sc = new Scanner(System.in);

    public int getLottoBuyAmount(){
        return sc.nextInt();
    }
}
