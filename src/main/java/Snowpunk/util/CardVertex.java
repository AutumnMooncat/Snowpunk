package Snowpunk.util;

import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;

public class CardVertex {
    public final ArrayList<CardVertex> parents = new ArrayList<>();
    public final ArrayList<CardVertex> children = new ArrayList<>();
    public final int index;
    public final AbstractCard card;
    public int x, y;

    public CardVertex(AbstractCard card, int index) {
        this.card = card;
        this.index = index;
        x = y = 0;
    }

    public void addParent(CardVertex v) {
        parents.add(v);
    }

    public void addChild(CardVertex v) {
        children.add(v);
    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
