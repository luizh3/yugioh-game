package eg.edu.guc.yugioh.cards.monsterEffect;

import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.cards.Mode;
import eg.edu.guc.yugioh.cards.MonsterCard;
import eg.edu.guc.yugioh.configsGlobais.Logger;

import java.util.ArrayList;

public class MonsterEffects {
    private MonsterCard monster;
    public MonsterEffects(MonsterCard monster) {
        this.monster = monster;
    }


    /*
    * Se o monstro tiver em modo de defesa ele vai destruir um monstro
     */
    public void destroir(MonsterCard monster) {
        if(monster.getMode()== Mode.DEFENSE) {
            Card.getBoard().getActivePlayer().getField().removeMonsterToGraveyard(monster);
        }
    }

    public  void destroirAll(){
        if(monster.getMode()== Mode.DEFENSE) {
            ArrayList<MonsterCard> monsters = Card.getBoard().getActivePlayer()
                    .getField().getMonstersArea();
            Card.getBoard().getActivePlayer().getField()
                    .removeMonsterToGraveyard(new ArrayList<MonsterCard>(monsters));
        }
    }

}
