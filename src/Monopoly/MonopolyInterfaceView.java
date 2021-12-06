package Monopoly;

public interface MonopolyInterfaceView {

    void handleRoll();

    void handlePlayerState();

    void handleEndOfTurn();

    void handleJailEntered(String message);

    void handleJailExited(String message);

    void handleBankruptcy(Player player);

    void handlePassedGo();

    boolean askIfJailExit();

    void handleWinner();

    String getFilenameOfGame();

    void handleGameSaving(boolean savedSuccesfully, String fileName);

    boolean askIfLoadPreviousGame();

    void handleGameLoadFailure(String fileName);
}
