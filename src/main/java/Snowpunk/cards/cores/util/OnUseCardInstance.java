package Snowpunk.cards.cores.util;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.function.BiConsumer;

public class OnUseCardInstance implements Comparable<OnUseCardInstance>{
    public int priority;
    public BiConsumer<AbstractPlayer, AbstractMonster> onUseConsumer;

    public OnUseCardInstance(int priority, BiConsumer<AbstractPlayer, AbstractMonster> onUseConsumer) {
        this.priority = priority;
        this.onUseConsumer = onUseConsumer;
    }

    @Override
    public int compareTo(OnUseCardInstance o) {
        return this.priority - o.priority;
    }
}
