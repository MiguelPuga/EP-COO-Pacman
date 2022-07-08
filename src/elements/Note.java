package elements;

public class Note extends ElementGivePoint{

    public Note(String imageName) {
        super(imageName);

        sprites.add("note_blue.png");
        sprites.add("note_red.png");
        sprites.add("note_yellow.png");
        sprites.add("note_green.png");

        Integer[] light = new Integer[]{0,1,2,3};

        animationsClips.add(light);

        this.numberPoints= 666;

    }
}
