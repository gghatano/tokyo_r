// GameSituation
//
// INPUT :: batter data
// OUTPUT :: 

class GameSituation{
  PlayerData player;

  // initialize
  private static int firstBase=0, secondBase=0, thirdBase = 0;
  private static int score=0, outCount=0;
  private static int inning = 1;
  private static int singleNum=0, doubleNum=0, tripleNum=0, homerunNum=0;
  private static int gameSingleNum = 0;

  public static int outCountGetter(){
    return(outCount);
  }
  public static int inningGetter(){
    return(inning);
  }
  public static int scoreGetter(){
    return(score);
  }
  public static int gameSingleNumGetter(){
    return(gameSingleNum);
  }

  GameSituation(PlayerData player){
    this.player = player;
  }

  void attack(){
    System.out.println("Batter Name: " + player.batterName);
    System.out.print("Base: ");
    System.out.println("1:" + GameSituation.firstBase + ", 2: " + GameSituation.secondBase + ", 3: " + GameSituation.thirdBase);
    System.out.print("Result: ");

    double battingResultRandomNumber = Math.random();

    // Single hit
    if(battingResultRandomNumber<=player.probSingle){
      System.out.println("Single");
      gameSingleNum += 1; 

      // whether second runner come back to home.
      if(secondBase == 1){
        // シングルヒットでは2塁ランナーの生還率が3割, ということらしい
        double secondRunnerScoringRandomNumber = Math.random();
        if(secondRunnerScoringRandomNumber < 0.55){
          score += thirdBase + secondBase; 
          thirdBase = 0;
          secondBase = firstBase;
          firstBase = 1;
        }else{
          score += thirdBase;
          thirdBase = secondBase;
          secondBase = firstBase;
          firstBase = 1; 
        }
      // without second runner
      } else{
        score += thirdBase; 
        thirdBase = secondBase; 
        secondBase = firstBase;
        firstBase = 1;
      }

    // Double
    }else if(battingResultRandomNumber <= player.probSingle + player.probDouble){
      System.out.println("Double");
      score += secondBase + thirdBase;
      thirdBase = firstBase; 
      secondBase = 1;
      firstBase = 0;

    // Triple
    }else if(battingResultRandomNumber <= player.probSingle + player.probDouble + player.probTriple){
      System.out.println("Triple");
      score += firstBase + secondBase + thirdBase;
      thirdBase = 1;
      secondBase = 0;
      firstBase = 0;

    // Homerun
    }else if(battingResultRandomNumber <= player.probSingle + player.probDouble + player.probTriple + player.probHomerun){
      System.out.println("HomeRun");
      score += 1 + firstBase + secondBase + thirdBase;
      firstBase = 0;
      secondBase = 0;
      thirdBase = 0;

    // BB
    }else if(battingResultRandomNumber <= player.probSingle + player.probDouble + player.probTriple + player.probHomerun + 
        player.probBB){
      System.out.println("BB");
      score += thirdBase;
      thirdBase = secondBase;
      secondBase = firstBase;
      firstBase = 1;

    // SwingOut
    }else if(battingResultRandomNumber <= 
        player.probSingle + player.probDouble + player.probTriple + player.probHomerun + 
        player.probBB + player.probSwingOut){
      System.out.println("Swing Out");
      outCount += 1;

    // other hitting out
    } else {
      System.out.println("OUT");
      outCount += 1;
    }

    outCountCheck();
  }

  // if outCount == 3 then inning += 1 and cleanup the runners.
  void outCountCheck(){
    System.out.println("----");
    if(outCount == 3){
      System.out.println("Change");
      firstBase = 0; secondBase = 0; thirdBase = 0;
      inning += 1;
      System.out.println("Three Out. Next Inning :: " + inning + ".");
      outCount = 0;
      System.out.println("-------------");
    }
  }

  // whether the game is end or not
  public static boolean gameEndCheck(){
    if(inning == 10){
      return true;
    } else {
      return false; 
    }
  }

  // Main method for test
  public static void main(String[] args) {

    // default player
    PlayerData player1 = new PlayerData();
    System.out.println(player1.batterName);
    System.out.println(player1.probHomerun);


    GameSituation gameSituation1 = new GameSituation(player1);

    // Simulation by using default player data
    while(GameSituation.inningGetter() < 10){
      gameSituation1.attack();
      System.out.println("inning : " + GameSituation.inningGetter());
      System.out.println("score  : " + GameSituation.scoreGetter());
      System.out.println("1:" + GameSituation.firstBase + ", 2: " + GameSituation.secondBase + ", 3: " + GameSituation.thirdBase);
      System.out.println("out    : " + GameSituation.outCountGetter());
    }
  }
}
