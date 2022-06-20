package Snowpunk.cards.cores;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.cards.cores.util.OnUseCardInstance;
import Snowpunk.util.AssembledCardArtRoller;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Consumer;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools @NoCompendium
public class AssembledCard extends AbstractEasyCard {
    public static final String ID = makeID(AssembledCard.class.getSimpleName());

    private final ArrayList<Consumer<AssembledCard>> onUpgradeConsumers = new ArrayList<>();
    private final ArrayList<OnUseCardInstance> onUseInstances = new ArrayList<>();
    public int doubledCost;

    public AssembledCard() {
        this(0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE);
    }

    public AssembledCard(int cost, CardType type, CardRarity rarity, CardTarget target) {
        super(ID, cost, type, rarity, target);
    }

    @Override
    protected Texture getPortraitImage() {
        return AssembledCardArtRoller.getPortraitTexture(this);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (OnUseCardInstance i : onUseInstances) {
            i.onUseConsumer.accept(p, m);
        }
    }

    @Override
    public void upp() {
        for (Consumer<AssembledCard> c : onUpgradeConsumers) {
            c.accept(this);
        }
    }

    public void addUpgradeConsumer(Consumer<AssembledCard> c) {
        onUpgradeConsumers.add(c);
    }

    public void addUseEffects(OnUseCardInstance i) {
        onUseInstances.add(i);
        Collections.sort(onUseInstances);
    }
}
