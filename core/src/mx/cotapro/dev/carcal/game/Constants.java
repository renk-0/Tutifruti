
package mx.cotapro.dev.carcal.game;

public class Constants {

    /**
     * How many pixels there are in a meter. As explained in the video, this is important, because
     * your simulation is in meters but you have to somehow convert these meters to pixels so that
     * they can be rendered at a size visible by the user.
     */
    public static final float PIXELS_IN_METER = 90f;

    /**
     * The force in N/s that the player uses to jump in an impulse. This force will also be applied
     * in the opposite direction to make the player fall faster multiplied by some value to make
     * it stronger.
     */
    public static final int IMPULSE_JUMP = 20;

    /**
     * This is the speed that the player has. The larger this value is, the faster the player will
     * go. Don't make this value very high without putting more distance between every obstacle
     * in the circuit.
     */
    public static final float PLAYER_SPEED = 8f;
}
