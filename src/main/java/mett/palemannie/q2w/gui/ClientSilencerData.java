package mett.palemannie.q2w.gui;

public final class ClientSilencerData {

    private ClientSilencerData() {}

    private static int silencedShotsLeft = 0;

    public static void setSilencedShotsLeft(int shotsLeft) {
        silencedShotsLeft = Math.max(0, shotsLeft);
    }

    public static int getSilencedShotsLeft() {
        return silencedShotsLeft;
    }

    public static boolean hasSilencerActive() {
        return silencedShotsLeft > 0;
    }

    public static void clear() {
        silencedShotsLeft = 0;
    }
}