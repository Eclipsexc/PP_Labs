package Lab3.Actions;

import Lab3.Droids.Droid;
import Lab3.Replaying.PlaybackBattle;

import java.util.ArrayList;
import java.util.List;

public class ComputerAction {

    private DroidActions droidActions;
    private PlaybackBattle playbackBattle;

    public ComputerAction(DroidActions droidActions, PlaybackBattle playbackBattle) {
        this.droidActions = droidActions;
        this.playbackBattle = playbackBattle;
    }

    public void computerAIAction(Droid computerDroid, int[] computerPosition, List<int[]> starPositions, char lastDirection, int[] playerPosition, Droid playerDroid) {
        if ((computerPosition[0] == playerPosition[0]) || (computerPosition[1] == playerPosition[1])) {
            double shootProbability = Math.random();

            if (shootProbability >= 0.5) {
                char directionChar = getDirection(computerPosition[0], computerPosition[1], playerPosition[0], playerPosition[1]);
                droidActions.shoot(computerPosition[0], computerPosition[1], directionChar, computerDroid.getDamage(), playerDroid);
                playbackBattle.writeLog("Противник вистрілив " + directionChar);
                return;
            }
        }

        if (computerDroid.getStars() >= 3) {
            moveRandomly(computerDroid, computerPosition, lastDirection);
            playbackBattle.writeLog("Противник перемістився " + lastDirection);
            return;
        }

        int[] targetStar = starPositions.get((int) (Math.random() * starPositions.size()));
        int targetX = targetStar[0];
        int targetY = targetStar[1];

        char direction = getDirection(computerPosition[0], computerPosition[1], targetX, targetY);

        if ((lastDirection == 'W' && direction == 'S') ||
                (lastDirection == 'S' && direction == 'W') ||
                (lastDirection == 'A' && direction == 'D') ||
                (lastDirection == 'D' && direction == 'A')) {
            direction = getAlternativeDirection(direction, lastDirection);
        }

        int actualSteps = droidActions.moveDroid(computerDroid, computerPosition[0], computerPosition[1], direction);

        if (actualSteps > 0) {
            computerPosition[0] += actualSteps * (direction == 'D' ? 1 : direction == 'A' ? -1 : 0);
            computerPosition[1] += actualSteps * (direction == 'S' ? 1 : direction == 'W' ? -1 : 0);

            if (computerPosition[0] == targetX && computerPosition[1] == targetY) {
                starPositions.remove(targetStar);
                computerDroid.addStar();
            }
            playbackBattle.writeLog("Противник перемістився " + direction);
        }
        lastDirection = direction;

        if (computerDroid.isAbilityActive()) {
            computerDroid.ability();
            playbackBattle.writeLog("Противник активував суперздібність");
        }
    }

    public void moveRandomly(Droid computerDroid, int[] computerPosition, char lastDirection) {
        List<Character> directions = new ArrayList<>(List.of('W', 'A', 'S', 'D'));

        if (lastDirection == 'W') {
            directions.remove((Character) 'S');
        } else if (lastDirection == 'S') {
            directions.remove((Character) 'W');
        } else if (lastDirection == 'A') {
            directions.remove((Character) 'D');
        } else if (lastDirection == 'D') {
            directions.remove((Character) 'A');
        }

        char direction = directions.get((int) (Math.random() * directions.size()));
        int actualSteps = droidActions.moveDroid(computerDroid, computerPosition[0], computerPosition[1], direction);

        if (actualSteps > 0) {
            computerPosition[0] += actualSteps * (direction == 'D' ? 1 : direction == 'A' ? -1 : 0);
            computerPosition[1] += actualSteps * (direction == 'S' ? 1 : direction == 'W' ? -1 : 0);
            playbackBattle.writeLog("Противник перемістився " + direction);
        }

        lastDirection = direction;
    }

    private char getAlternativeDirection(char currentDirection, char lastDirection) {
        List<Character> directions = new ArrayList<>(List.of('W', 'A', 'S', 'D'));
        directions.remove((Character) currentDirection);
        directions.remove((Character) lastDirection);
        return directions.get((int) (Math.random() * directions.size()));
    }

    private char getDirection(int currentX, int currentY, int targetX, int targetY) {
        if (currentX < targetX) {
            return 'D';
        } else if (currentX > targetX) {
            return 'A';
        } else if (currentY < targetY) {
            return 'S';
        } else {
            return 'W';
        }
    }
}
