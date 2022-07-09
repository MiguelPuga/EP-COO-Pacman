package elements;
//Classe responsável pela nota musical
public class Note extends ElementGivePoint{
    //Seta a imagem, adiciona os sprites e as animações, além de setar a quantidade de pontos do consumível
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
